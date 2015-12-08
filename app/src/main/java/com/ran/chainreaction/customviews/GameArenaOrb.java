package com.ran.chainreaction.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.BombValues;
import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.utils.CommonUtils;


/**
 * Created by ranjith on 05/12/15.
 * Class that is used to make Orb based on the Player Info , Orb type ..
 */
public class GameArenaOrb extends View implements View.OnClickListener {

    private static final String TAG = GameArenaOrb.class.getName();
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private GameCellInfo gameCellInfo;
    private GamePlayerInfo gameCurrentPlayer;
    private BombValues gameBombType;
    private int count_orbs = 0;
    private Paint paintToDraw;


    public GameArenaOrb(Context context) {
        super(context);
    }


    public GameArenaOrb(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public GameArenaOrb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Method to first initialise the View Based on Current Orb,GameSizeInfo and GameCellInfo of it
     *
     * @param bombValues        -- BombType Used for the Game
     * @param gameCellInfo      -- Each Game Cell Info
     * @param gameCurrentPlayer -- Current Player playing the Game
     */
    public void initView(BombValues bombValues, GameCellInfo gameCellInfo, GamePlayerInfo gameCurrentPlayer) {
        this.gameBombType = bombValues;
        this.gameCellInfo = gameCellInfo;
        this.gameCurrentPlayer = gameCurrentPlayer;

        setSoundEffectsEnabled(true);
        setHapticFeedbackEnabled(true);
        setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (gameCellInfo.getCurrentGamePlayerInfo() != null && gameCurrentPlayer == gameCellInfo.getCurrentGamePlayerInfo()) {
            Log.d(TAG, "You cannot change Orb/Bomb state here .."); //Todo [Check the GameCellInfo logic]
            return;
        }

        //Now process the Orb state ..
        if (count_orbs < gameCellInfo.getMAX_CAPACITY()) {
            count_orbs++;
            postInvalidate();
        } else {
            //Do processing ,Blast send to Parent Container ..
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        //Now Draw the Boundary Rectangle..
        paintToDraw = new Paint();
        paintToDraw.setColor(CommonUtils.getColorByPlayerColor(gameCurrentPlayer.getPlayerColor(), getContext()));
        paintToDraw.setStyle(Paint.Style.STROKE);
        paintToDraw.setStrokeWidth(getContext().getResources().getDimension(R.dimen.game_screen_stroke_dimen));
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paintToDraw);

        //Draw the Orbs
        paintToDraw.setStyle(Paint.Style.FILL);
        switch (count_orbs) {
            case ONE:
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    generateCircleRadius(), paintToDraw);
                break;
            case TWO:
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    generateCircleRadius(), paintToDraw);
                break;
            case THREE:
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    generateCircleRadius(), paintToDraw);
                break;
        }
    }

    /**
     * Utility Method to generate the Radius of Circle Orb Type
     *
     * @return -- Radius of Circle Orb
     */
    private float generateCircleRadius() {
        if (getMeasuredWidth() > getMeasuredHeight()) {
            return getMeasuredHeight() / 4; //Todo [ranjith, finalize the UI logic]
        } else {
            return getMeasuredWidth() / 4;
        }
    }

}
