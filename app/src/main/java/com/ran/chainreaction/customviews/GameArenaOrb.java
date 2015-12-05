package com.ran.chainreaction.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ran.chainreaction.R;

/**
 * Created by ranjith on 05/12/15.
 * Class that is used to make Orb based on the Player Info , Orb type ..
 */
public class GameArenaOrb extends View implements View.OnClickListener {

    public GameArenaOrb(Context context) {
        super(context);
        initView();
    }


    public GameArenaOrb(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public GameArenaOrb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * Method to initialize the View .
     */
    private void initView() {
        setBackground(getResources().getDrawable(R.drawable.game_screen_box_background));
        setSoundEffectsEnabled(true);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
