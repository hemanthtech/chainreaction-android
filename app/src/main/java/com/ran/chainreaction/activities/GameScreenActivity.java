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

import com.ran.chainreaction.R;
import com.ran.chainreaction.customviews.ExitAlertDialogCreator;
import com.ran.chainreaction.customviews.GameArenaContainer;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.utils.ChainReactionConstants;
import com.ran.chainreaction.utlity.GameInfoUtility;

public class GameScreenActivity extends ActionBarActivity implements ExitAlertDialogCreator.ButtonOnClickListener,
    View.OnClickListener {

    //TAGS for Alert Dialog Click [Match to String Array Entry]
    public static final int EXIT_TAG = 0;
    public static final int RESTART_TAG = 1;
    public static final int SAVE_EXIT_TAG = 2;
    public static final long TIME_INTERVAL = 1000; // 1 Second
    public static final long TIMER_MILLS_FUTURE = Long.MAX_VALUE;
    private static final String TAG = GameScreenActivity.class.getName();
    private static final int SCREEN_LOAD_IN = 3;
    private static final int SCREEN_LOAD_SETUP_TIME = 2000;


    //Alert Dialog Stuff on Back..
    AlertDialog mBackDialog;
    String mBackDialogEntries[];
    String mBackDialogTitle;
    private boolean isOnline;
    private boolean isResumedGame;
    private long savedGameTimeElapsed = 0;

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
        soundSettingsView = (SoundSettingsView) findViewById(R.id.game_screen_sound);
        gameName = (TextView) findViewById(R.id.game_screen_tile);
        gameTimer = (TextView) findViewById(R.id.game_screen_timer);
        offlinePlayerInfo = (TextView) findViewById(R.id.game_offline_Player);
        progressBar = (ProgressBar) findViewById(R.id.screen_load_indicator);
        gameScreenContainer = (GameArenaContainer) findViewById(R.id.game_screen_container);
        mBackDialogEntries = getResources().getStringArray(R.array.game_screen_dialog);
        mBackDialogTitle = getResources().getString(R.string.game_screen_exit_dialog);
        countDownTimer = new CountDownTimer(TIMER_MILLS_FUTURE, TIME_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameTimer.setText(GameInfoUtility.generateTimeFormat(TIMER_MILLS_FUTURE - (savedGameTimeElapsed + millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                // No need to handle this ..
            }
        };

        if (isOnline) {
            offlinePlayerInfo.setVisibility(View.GONE);
        } else if (isResumedGame) {
            //Todo ranjith.suda After Db integration
        } else {
            gameName.setText(getResources().getString(R.string.game_screen_gameTitle) + GameInfoUtility.generateGameName(this));
        }
        gameBack.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundSettingsView.onViewVisible();
        mHandler.sendEmptyMessageDelayed(SCREEN_LOAD_IN, SCREEN_LOAD_SETUP_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundSettingsView.onViewHidden();

        if (mHandler.hasMessages(SCREEN_LOAD_IN)) {
            mHandler.removeMessages(SCREEN_LOAD_IN);
        }

        //Todo [ranjith.suda] Cancel after saving to DB ..
        countDownTimer.cancel();
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
    public void onButtonClick(View view) {
        try {
            int tag = (int) view.getTag();
            switch (tag) {
                case EXIT_TAG:
                    finish();
                    break;
                //TODO ranjith.suda , Need to handle Restart and Save & Exit
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in Button Clicks");
            mBackDialog.dismiss();
        }
    }

    /**
     * Called when a view has been clicked.
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
}
