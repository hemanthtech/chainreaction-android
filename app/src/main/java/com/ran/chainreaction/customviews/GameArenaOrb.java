package com.ran.chainreaction.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.BombValues;
import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlayLogic;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.interfaces.GameStateObserver;
import com.ran.chainreaction.utils.CommonUtils;


/**
 * Created by ranjith on 05/12/15.
 * <p/>
 * Class that is used to make Orb based on the Player Info , Orb type ..
 */
public class GameArenaOrb extends View implements View.OnClickListener, GameStateObserver {

    private static final String TAG = GameArenaOrb.class.getSimpleName();
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private GameCellInfo gameCellInfo;
    private GamePlayerInfo gameCurrentPlayer;
    private BombValues gameBombType;
    private Paint paintToDraw;

    //Params for Orb Dimens ..
    private int circleRadius;
    private int squareSide;
    private int triangleSide;

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
        GamePlayLogic.getGameInstance().attach(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (gameCellInfo.getGamePlayerInfo() != null && gameCurrentPlayer != gameCellInfo.getGamePlayerInfo()) {
            Log.d(TAG, "You cannot change Orb/Bomb state here .."); //Todo [Check the GameCellInfo logic]
            return;
        }

        gameCellInfo.setGamePlayerInfo(gameCurrentPlayer);
        gameCellInfo.setCurrentCount(gameCellInfo.getCurrentCount() + 1);
        if (gameCellInfo.getCurrentCount() <= gameCellInfo.getMAX_CAPACITY()) {
            GamePlayLogic.getGameInstance().changeGameTurn(gameCellInfo);
            invalidate();
        } else {
            GamePlayLogic.getGameInstance().blastOrb(gameCellInfo);
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

        if (gameCellInfo.getCurrentCount() == 0) {
            Log.d(TAG, "Don't Process Orb Drawing , if the Count is ZERO");
            return;
        }

        //Draw the Orbs
        paintToDraw.setColor(CommonUtils.getColorByPlayerColor(gameCellInfo.getGamePlayerInfo().getPlayerColor(), getContext()));
        paintToDraw.setStyle(Paint.Style.FILL);
        switch (gameCellInfo.getCurrentCount()) {
            case ONE:
                drawOneOrb(canvas);
                break;
            case TWO:
                drawTwoOrbs(canvas);
                break;
            case THREE:
                drawThreeOrbs(canvas);
                break;
        }
    }

    /**
     * Utility Method to draw Single Orb[Square ,Circle or Triangle] on Canvas
     *
     * @param canvas -- Canvas
     */
    private void drawOneOrb(Canvas canvas) {
        switch (gameBombType) {
            case CIRCLE:
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                    circleRadius, paintToDraw);
                break;
            case TRIANGLE:
                Point p1 = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - triangleSide / 2);
                Point p2 = new Point(getMeasuredWidth() / 2 - triangleSide / 2, getMeasuredHeight() / 2 + triangleSide / 2);
                Point p3 = new Point(getMeasuredWidth() / 2 + triangleSide / 2, getMeasuredHeight() / 2 + triangleSide / 2);

                Path path = new Path();
                path.moveTo(p1.x, p1.y);
                path.lineTo(p2.x, p2.y);
                path.lineTo(p3.x, p3.y);
                canvas.drawPath(path, paintToDraw);
                break;
            case SQUARE:
                float left = getMeasuredWidth() / 2 - (squareSide / 2);
                float top = getMeasuredHeight() / 2 - (squareSide / 2);
                canvas.drawRect(left, top, left + squareSide, top + squareSide, paintToDraw);
                break;
        }
    }

    /**
     * Utility Method to draw Two Orb's[Square ,Circle or Triangle] on Canvas
     *
     * @param canvas -- Canvas
     */
    private void drawTwoOrbs(Canvas canvas) {
        switch (gameBombType) {
            case CIRCLE:
                canvas.drawCircle(getMeasuredWidth() / 2 - circleRadius, getMeasuredHeight() / 2,
                    circleRadius, paintToDraw);
                canvas.drawCircle(getMeasuredWidth() / 2 + circleRadius, getMeasuredHeight() / 2,
                    circleRadius, paintToDraw);

                break;
            case TRIANGLE:
                Point p1 = new Point(getMeasuredWidth() / 2 - triangleSide / 2, getMeasuredHeight() / 2 - triangleSide / 2);
                Point p2 = new Point(getMeasuredWidth() / 2 - triangleSide, getMeasuredHeight() / 2 + triangleSide / 2);
                Point p3 = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 + triangleSide / 2);

                Path path = new Path();
                path.moveTo(p1.x, p1.y);
                path.lineTo(p2.x, p2.y);
                path.lineTo(p3.x, p3.y);
                canvas.drawPath(path, paintToDraw); //Triangle 1

                Point p4 = new Point(getMeasuredWidth() / 2 + triangleSide / 2, getMeasuredHeight() / 2 - triangleSide / 2);
                Point p5 = new Point(getMeasuredWidth() / 2 + triangleSide, getMeasuredHeight() / 2 + triangleSide / 2);
                Point p6 = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 + triangleSide / 2);

                Path path2 = new Path();
                path2.moveTo(p4.x, p4.y);
                path2.lineTo(p5.x, p5.y);
                path2.lineTo(p6.x, p6.y);
                canvas.drawPath(path2, paintToDraw); //Triangle 2

                break;
            case SQUARE:
                float left = getMeasuredWidth() / 2 - (3 * squareSide / 2);
                float top = getMeasuredHeight() / 2 - (squareSide / 2);
                //Square 1
                canvas.drawRect(left, top, left + squareSide, top + squareSide, paintToDraw);
                float left2 = getMeasuredWidth() / 2 + squareSide / 2;
                //Square 2
                canvas.drawRect(left2, top, left2 + squareSide, top + squareSide, paintToDraw);

                break;
        }
    }

    /**
     * Utility Method to draw Three Orb's[Square ,Circle or Triangle] on Canvas
     *
     * @param canvas -- Canvas
     */
    private void drawThreeOrbs(Canvas canvas) {
        switch (gameBombType) {
            case CIRCLE:
                canvas.drawCircle(getMeasuredWidth() / 2 - circleRadius, getMeasuredHeight() / 2 + circleRadius,
                    circleRadius, paintToDraw);
                canvas.drawCircle(getMeasuredWidth() / 2 + circleRadius, getMeasuredHeight() / 2 + circleRadius,
                    circleRadius, paintToDraw);
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - circleRadius,
                    circleRadius, paintToDraw);
                break;
            case TRIANGLE:
                Point p1 = new Point(getMeasuredWidth() / 2 - triangleSide / 2, getMeasuredHeight() / 2);
                Point p2 = new Point(getMeasuredWidth() / 2 + triangleSide / 2, getMeasuredHeight() / 2);
                Point p3 = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - triangleSide);

                Path path = new Path();
                path.moveTo(p1.x, p1.y);
                path.lineTo(p2.x, p2.y);
                path.lineTo(p3.x, p3.y);
                canvas.drawPath(path, paintToDraw); //Triangle 1

                Point p4 = new Point(getMeasuredWidth() / 2 - triangleSide / 2, getMeasuredHeight() / 2);
                Point p5 = new Point(getMeasuredWidth() / 2 - triangleSide, getMeasuredHeight() / 2 + triangleSide);
                Point p6 = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 + triangleSide);

                Path path2 = new Path();
                path2.moveTo(p4.x, p4.y);
                path2.lineTo(p5.x, p5.y);
                path2.lineTo(p6.x, p6.y);
                canvas.drawPath(path2, paintToDraw); //Triangle 2

                Point p7 = new Point(getMeasuredWidth() / 2 + triangleSide / 2, getMeasuredHeight() / 2);
                Point p8 = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 + triangleSide);
                Point p9 = new Point(getMeasuredWidth() / 2 + triangleSide, getMeasuredHeight() / 2 + triangleSide);

                Path path3 = new Path();
                path3.moveTo(p7.x, p7.y);
                path3.lineTo(p8.x, p8.y);
                path3.lineTo(p9.x, p9.y);
                canvas.drawPath(path3, paintToDraw); //Triangle 3

                break;
            case SQUARE:
                float left = getMeasuredWidth() / 2 - (3 * squareSide / 2);
                float top = getMeasuredHeight() / 2;
                //Square 1
                canvas.drawRect(left, top, left + squareSide, top + squareSide, paintToDraw);
                float left2 = getMeasuredWidth() / 2 + squareSide / 2;
                //Square 2
                canvas.drawRect(left2, top, left2 + squareSide, top + squareSide, paintToDraw);
                float left3 = getMeasuredWidth() / 2 - (squareSide / 2);
                float top3 = getMeasuredHeight() / 2 - squareSide;
                //Square 3
                canvas.drawRect(left3, top3, left3 + squareSide, top3 + squareSide, paintToDraw);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * Determine the Circle Radius, Triangle , Square dimens params here ..
         * case a) - Circle
         * case b) - Square
         * case c) - Triangle
         */
        if (getMeasuredWidth() > getMeasuredHeight()) {
            circleRadius = getMeasuredHeight() / 5;
            squareSide = getMeasuredHeight() / 3;
            triangleSide = (int) (getMeasuredHeight() / 2.5);
        } else {
            circleRadius = getMeasuredWidth() / 5;
            squareSide = getMeasuredWidth() / 3;
            triangleSide = (int) (getMeasuredHeight() / 2.5);
        }
    }

    @Override
    public void updateGameTurnState(GamePlayerInfo gamePlayerInfo) {
        this.gameCurrentPlayer = gamePlayerInfo;
        invalidate();
    }

    /**
     * Observer Call Back to say , Whether the View is Clickable or not ..
     *
     * @param isClickable ,Whether View can be clickable or not ..
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
     * @param index               -- Index to be updated ..
     * @param updatedGameCellInfo -- GameCellInfo for Cell
     */
    @Override
    public void updateGameCellInfo(int index, GameCellInfo updatedGameCellInfo) {
        if (index == this.gameCellInfo.getIndex()) {
            this.gameCellInfo = updatedGameCellInfo;
            invalidate();
        }
    }
}
