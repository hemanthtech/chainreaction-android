package com.ran.chainreaction.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.chainreaction.R;
import com.ran.chainreaction.customviews.ExitAlertDialogCreator;
import com.ran.chainreaction.customviews.GameArenaContainer;
import com.ran.chainreaction.customviews.GameWinDialogCreator;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.database.ChainReactionDBOpsHelper;
import com.ran.chainreaction.entities.PlayColorValues;
import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlayLogic;
import com.ran.chainreaction.gameplay.GamePlaySession;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.gameplay.GameSizeBoxInfo;
import com.ran.chainreaction.interfaces.GameStateObserver;
import com.ran.chainreaction.utils.ChainReactionConstants;
import com.ran.chainreaction.utils.ChainReactionPreferences;
import com.ran.chainreaction.utils.CommonUtils;
import com.ran.chainreaction.utlity.GameInfoUtility;
import com.ran.chainreaction.utlity.GamePreferenceUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Game Screen responsible for Current Game , Creates Session and Timer for Current Game
 * Handles CallBacks from below :
 *
 * @see ExitAlertDialogCreator.ButtonOnClickListener -- Exit Dialog CallBacks
 * @see GameStateObserver                            -- Game State CallBacks
 * @see GameWinDialogCreator.GameWinCallBacks        -- Game Win Dialog CallBacks
 */
public class GameScreenActivity extends ActionBarActivity implements ExitAlertDialogCreator.ButtonOnClickListener,
    View.OnClickListener, GameStateObserver, GameWinDialogCreator.GameWinCallBacks {

    public static final int EXIT_TAG = 0;
    public static final int RESTART_TAG = 1;
    public static final int SAVE_EXIT_TAG = 2;
    public static final long TIME_INTERVAL = 1000; // 1 Second
    public static final long TIMER_MILLS_FUTURE = 99999999L;
    private static final String TAG = GameScreenActivity.class.getName();
    private static final int SCREEN_LOAD_IN = 3;
    private static final int SCREEN_LOAD_SETUP_TIME = 2000;

    //AlertDialog Related
    AlertDialog mBackDialog;
    AlertDialog mGameWinDialog;
    String mBackDialogEntries[];
    String mBackDialogTitle;

    //Game Related..
    private boolean isOnline;
    private boolean isResumedGame;
    private long currentGameTimeElapsed = 0;
    private long currentGamePrevTimeElapsed = 0;
    private boolean isGameStarted;

    //Views on Game Screen ..
    private ImageView gameBack;
    private SoundSettingsView soundSettingsView;
    private TextView gameName;
    private TextView gameTimer;
    private TextView offlinePlayerInfo;
    private GameArenaContainer gameScreenContainer;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCREEN_LOAD_IN:
                    progressBar.setVisibility(View.GONE);
                    countDownTimer.start();
                    gameScreenContainer.setVisibility(View.VISIBLE);
                    isGameStarted = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getIncomingIntentParams();
        initView();
    }

    /**
     * Method to get the Incoming Params
     */
    private void getIncomingIntentParams() {
        isOnline = getIntent().getBooleanExtra(ChainReactionConstants.ONLINE_GAME_KEY, false);
        isResumedGame = getIntent().getBooleanExtra(ChainReactionConstants.SAVED_GAME_KEY, false);
    }

    /**
     * Method to initialize the Views ..
     */
    private void initView() {

        gameBack = (ImageView) findViewById(R.id.game_screen_back);
        gameBack.setOnClickListener(this);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.game_screen_sound);
        gameName = (TextView) findViewById(R.id.game_screen_tile);
        gameTimer = (TextView) findViewById(R.id.game_screen_timer);
        offlinePlayerInfo = (TextView) findViewById(R.id.game_offline_Player);
        progressBar = (ProgressBar) findViewById(R.id.screen_load_indicator);
        gameScreenContainer = (GameArenaContainer) findViewById(R.id.game_screen_container);
        mBackDialogEntries = getResources().getStringArray(R.array.game_screen_dialog);
        mBackDialogTitle = getResources().getString(R.string.game_screen_exit_dialog);
        if (isOnline) {
            offlinePlayerInfo.setVisibility(View.GONE);
        } else if (isResumedGame) {
            //Todo ranjith.suda After Db integration
        } else {
            gameName.setText(getResources().getString(R.string.game_screen_gameTitle) + GameInfoUtility.generateGameName(this));
        }

        timerInitialization(TIMER_MILLS_FUTURE);
        initialiseGameSession();
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundSettingsView.onViewVisible();

        //Logic for Timer ..
        if (isGameStarted) {
            timerInitialization(TIMER_MILLS_FUTURE);
            countDownTimer.start();
        } else {
            initialiseGameSession();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundSettingsView.onViewHidden();

        if (mHandler.hasMessages(SCREEN_LOAD_IN)) {
            mHandler.removeMessages(SCREEN_LOAD_IN);
        }
        //Logic for timer ..
        if (isGameStarted) {
            currentGamePrevTimeElapsed = currentGamePrevTimeElapsed + currentGameTimeElapsed;
        }
        countDownTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GamePlayLogic.getGameInstance().resetGame();
    }

    @Override
    public void onBackPressed() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            Log.d(TAG, "Progress bar is in progress");
            return;
        }

        if (mBackDialog == null || !mBackDialog.isShowing()) {
            mBackDialog = ExitAlertDialogCreator.createDialog(mBackDialogEntries, mBackDialogTitle, this, true);
            mBackDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onExitButtonClick(View view) {
        try {
            int tag = (int) view.getTag();
            switch (tag) {
                case EXIT_TAG:
                    GamePlayLogic.getGameInstance().resetGame();
                    finish();
                    break;
                case RESTART_TAG:
                    GamePlayLogic.getGameInstance().resetGame();
                    initialiseGameSession();
                    mBackDialog.dismiss();
                    break;
                case SAVE_EXIT_TAG:
                    ChainReactionDBOpsHelper.getDBInstance(this).addCurrentGame(GamePlayLogic.getGameInstance().getGamePlaySession(),
                        currentGameTimeElapsed + currentGamePrevTimeElapsed,
                        gameName.getText().toString());
                    finish();
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in Button Clicks");
            mBackDialog.dismiss();
        }
    }

    /**
     * Needed , when a Back Key of Activity [Top Navigation] has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.game_screen_back:
                onBackPressed();
                break;
        }
    }


    /**
     * Method to initialize the Game Session , used for Online ,Offline and Saved Games
     */
    private void initialiseGameSession() {

        //Reset all Variables First ..
        GamePlaySession gamePlaySession = null;
        countDownTimer.cancel();
        gameScreenContainer.removeAllViews();
        gameScreenContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        if (isOnline) {
            //Todo ranjith.suda [online]
        } else if (isResumedGame) {
            //Todo ranjith.suda [resumed]
        } else {
            GameSizeBoxInfo sizeBoxInfo = GameInfoUtility.generateGameSizeBoxInfo(this, ChainReactionPreferences.getGridSizePreference(this));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<GamePlayerInfo>>() {
            }.getType();

            ArrayList<GamePlayerInfo> gamePlayerInfos = gson.fromJson(GamePreferenceUtils.getPlayerInfoGame(this), type);
            GamePlayerInfo currentPlayer = gamePlayerInfos.get(0);
            gamePlaySession = new GamePlaySession(gamePlayerInfos,
                currentPlayer,
                ChainReactionPreferences.getGridSizePreference(this),
                ChainReactionPreferences.getBombPreference(this),
                sizeBoxInfo,
                GameInfoUtility.generateGameCellInfo(this, sizeBoxInfo.getX_boxes(), sizeBoxInfo.getY_boxes(), null));
            //update Current Player Info.
            updateGameTurnState(currentPlayer);
        }
        gameScreenContainer.initView(gamePlaySession);
        mHandler.sendEmptyMessageDelayed(SCREEN_LOAD_IN, SCREEN_LOAD_SETUP_TIME);
        GamePlayLogic.getGameInstance().setGamePlaySession(gamePlaySession);
        GamePlayLogic.getGameInstance().attach(this);
    }


    /**
     * Method to Initialize the Timer ..
     *
     * @param futureTime -- FutureTime at whihc it needs to be stopped [Infinite]
     */
    private void timerInitialization(final long futureTime) {
        countDownTimer = new CountDownTimer(futureTime, TIME_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameTimer.setText(GameInfoUtility.generateTimeFormat(futureTime + currentGamePrevTimeElapsed - millisUntilFinished));
                currentGameTimeElapsed = futureTime - millisUntilFinished;
            }

            @Override
            public void onFinish() {
                // No need to handle this ..
            }
        };
    }

    @Override
    public void updateGameTurnState(GamePlayerInfo gamePlayerInfo) {
        offlinePlayerInfo.setText(gamePlayerInfo.getPlayerName());
        CommonUtils.updateColorView(PlayColorValues.getIndex(gamePlayerInfo.getPlayerColor()),
            offlinePlayerInfo,
            getResources().getDrawable(R.drawable.player_color_drawable),
            this);
    }

    /**
     * Observer Call Back to say , Whether the View is Clickable or not ..
     *
     * @param isClickable view is clickable or not
     */
    @Override
    public void updateClickableState(boolean isClickable) {
        //Activity need not update Clickable State..
    }

    /**
     * Observer Call Back from Game Play logic ..
     *
     * @param index        -- Index to be updated ..
     * @param gameCellInfo -- GameCellInfo for Cell
     */
    @Override
    public void updateGameCellInfo(int index, GameCellInfo gameCellInfo) {
        //Activity need not update the GameCell Info ..
    }

    /**
     * Observer call Back , passing Current Player hasWon ..
     *
     * @param gamePlayerInfo -- Player who has won the Game
     */
    @Override
    public void updatePlayerWinStatus(GamePlayerInfo gamePlayerInfo) {
        mGameWinDialog = GameWinDialogCreator.createDialog(this, gamePlayerInfo);
        mGameWinDialog.show();
    }


    /**
     * Callback for the Game Win Dialog [Exit Click]
     */
    @Override
    public void onGameWinExitClick() {
        mGameWinDialog.dismiss();
        finish();
    }

    /**
     * Callback for the Game Win Dialog [Restart Dialog]
     */
    @Override
    public void onGameWinRestartClick() {
        GamePlayLogic.getGameInstance().resetGame();
        initialiseGameSession();
        mGameWinDialog.dismiss();
    }

}
