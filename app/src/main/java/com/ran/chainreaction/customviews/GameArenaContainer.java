package com.ran.chainreaction.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlayLogic;
import com.ran.chainreaction.gameplay.GamePlaySession;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.interfaces.GameStateObserver;

/**
 * Created by ranjith on 04/12/15.
 * <p/>
 * Class responsible for creating Container View .. to Play ChainReaction Game ..
 */
public class GameArenaContainer extends RelativeLayout implements GameStateObserver {

    private static final String TAG = GameArenaContainer.class.getSimpleName();
    private GamePlaySession gamePlaySession;
    private boolean viewInitialized;

    public GameArenaContainer(Context context) {
        super(context);
    }

    public GameArenaContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameArenaContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Method to initialize the Game Container ..
     */
    public void initView(GamePlaySession gamePlaySession) {
        this.gamePlaySession = gamePlaySession;
        prepareGameOrbContainerViews();
        viewInitialized = true;
        GamePlayLogic.getGameInstance().attach(this);
    }

    /**
     * Method to initialize the Game Orb container Views ..
     */

    private void prepareGameOrbContainerViews() {
        for (int i = 0; i < gamePlaySession.getGameSizeBoxInfo().getX_boxes() *
            gamePlaySession.getGameSizeBoxInfo().getY_boxes(); i++) {
            GameArenaOrb gameArenaOrb = new GameArenaOrb(getContext());
            gameArenaOrb.setTag(i);
            gameArenaOrb.initView(gamePlaySession.getGameBombType(),
                gamePlaySession.getGameCellInfos().get(i), gamePlaySession.getCurrentPlayer());
            gameArenaOrb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(gameArenaOrb);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (viewInitialized) {
            int childWidth = getMeasuredWidth() / gamePlaySession.getGameSizeBoxInfo().getY_boxes();
            int childHeight = getMeasuredHeight() / gamePlaySession.getGameSizeBoxInfo().getX_boxes();
            for (int k = 0; k < getChildCount(); k++) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getChildAt(k).getLayoutParams();
                params.width = childWidth;
                params.height = childHeight;
                params.leftMargin = childWidth * (k % gamePlaySession.getGameSizeBoxInfo().getY_boxes());
                params.topMargin = childHeight * (k / gamePlaySession.getGameSizeBoxInfo().getY_boxes());
            }
        }
    }

    @Override
    public void updateGameTurnState(GamePlayerInfo gamePlayerInfo) {
        //View Container , need not use updates on Turn State
    }

    /**
     * Observer Call Back to say , Whether the View is Clickable or not ..
     *
     * @param isClickable -- Whether view is clickable or not
     */
    @Override
    public void updateClickableState(boolean isClickable) {
        if (isClickable) {
            setClickable(true);
        } else {
            setClickable(false);
        }
    }

    /**
     * Observer Call Back from Game Play logic ..
     *
     * @param index        -- Index to be updated ..
     * @param gameCellInfo -- GameCellInfo for Cell
     */
    @Override
    public void updateGameCellInfo(int index, GameCellInfo gameCellInfo) {
        //View Container doesn't require , may be Animation time it is required
    }

    /**
     * Observer call Back , passing Current Player hasWon ..
     *
     * @param gamePlayerInfo -- PlayerInfo Who has Won
     */
    @Override
    public void updatePlayerWinStatus(GamePlayerInfo gamePlayerInfo) {
        //Nothing to do ..
    }


}
