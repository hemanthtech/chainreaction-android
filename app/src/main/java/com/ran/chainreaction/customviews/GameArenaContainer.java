package com.ran.chainreaction.customviews;

import android.content.Context;
import android.util.AttributeSet;
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
        setBackground(getResources().getDrawable(R.drawable.game_screen_box_background));
        gridSizeValues = GamePreferenceUtils.getGridSizePreference(getContext());
    }

    /**
     * Method to initialize the Game Orb container Views ..
     */

    private void prepareGameOrbContainerViews() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        gameSizeBoxes = GameInfoUtility.generateGameGridSizes(getContext(), gridSizeValues, width, height);
        prepareGameOrbContainerViews();
    }
}
