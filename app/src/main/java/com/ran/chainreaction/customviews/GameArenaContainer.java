package com.ran.chainreaction.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ran.chainreaction.gameplay.GameSizeBoxInfo;

/**
 * Created by ranjith on 04/12/15.
 * <p/>
 * Class responsible for creating Container View .. to Play ChainReaction Game ..
 */
public class GameArenaContainer extends RelativeLayout {

    private static final String TAG = GameArenaContainer.class.getSimpleName();
    private GameSizeBoxInfo gameSizeBoxes;
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
    public void initView(GameSizeBoxInfo gameSizeBoxes) {
        this.gameSizeBoxes = gameSizeBoxes;
        prepareGameOrbContainerViews();
        viewInitialized = true;
    }

    /**
     * Method to initialize the Game Orb container Views ..
     */

    private void prepareGameOrbContainerViews() {

        Log.d(TAG, "game x box : " + gameSizeBoxes.getX_boxes());
        Log.d(TAG, "game y box : " + gameSizeBoxes.getY_boxes());

        for (int i = 0; i < gameSizeBoxes.getX_boxes() * gameSizeBoxes.getY_boxes(); i++) {
            GameArenaOrb gameArenaOrb = new GameArenaOrb(getContext());
            gameArenaOrb.setTag(i);
            gameArenaOrb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(gameArenaOrb);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (viewInitialized) {
            int childWidth = getMeasuredWidth() / gameSizeBoxes.getY_boxes();
            int childHeight = getMeasuredHeight() / gameSizeBoxes.getX_boxes();
            Log.d(TAG, "childwidth :" + childWidth + "child height : " + childHeight);
            for (int k = 0; k < getChildCount(); k++) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getChildAt(k).getLayoutParams();
                params.width = childWidth;
                params.height = childHeight;
                params.leftMargin = childWidth * (k % gameSizeBoxes.getY_boxes());
                params.topMargin = childHeight * (k / gameSizeBoxes.getY_boxes());
                Log.d(TAG, "index  " + k + " : left : " + params.leftMargin + "top : " + params.topMargin);
            }
        }
    }
}
