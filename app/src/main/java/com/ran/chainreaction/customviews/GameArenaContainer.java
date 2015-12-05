package com.ran.chainreaction.customviews;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.GridSizeValues;
import com.ran.chainreaction.gameplay.GameSizeBoxInfo;
import com.ran.chainreaction.utlity.GameInfoUtility;
import com.ran.chainreaction.utlity.GamePreferenceUtils;

/**
 * Created by ranjith on 04/12/15.
 * <p/>
 * Class responsible for creating Container View .. to Play ChainReaction Game ..
 */
public class GameArenaContainer extends RelativeLayout {

    private static final String TAG = GameArenaContainer.class.getSimpleName();
    private GameSizeBoxInfo gameSizeBoxes;
    private GridSizeValues gridSizeValues;

    public GameArenaContainer(Context context) {
        super(context);
        initView();
    }

    public GameArenaContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameArenaContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * Method to initialize the Game Container ..
     */
    private void initView() {

        gridSizeValues = GamePreferenceUtils.getGridSizePreference(getContext());
        prepareGameOrbContainerViews();
    }

    /**
     * Method to initialize the Game Orb container Views ..
     */

    private void prepareGameOrbContainerViews() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x - 2 * getContext().getResources().getDimensionPixelSize(R.dimen.game_screen_margin);
        int height = point.y - (getContext().getResources().getDimensionPixelSize(R.dimen.game_screen_statusbar_size)
            + getContext().getResources().getDimensionPixelSize(R.dimen.game_screen_headerLayout));

        gameSizeBoxes = GameInfoUtility.generateGameGridSizes(getContext(), gridSizeValues, width, height);
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
