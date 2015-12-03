package com.ran.chainreaction.activities;

/**
 * Activity responsible for showing the Offline Game Settings /Options
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.ran.chainreaction.R;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.utils.ChainReactionNavigator;

public class OfflineSettingsActivity extends ActionBarActivity implements View.OnClickListener {

    Toolbar toolbar;
    SoundSettingsView soundSettingsView;
    Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_settings);
        initViews();
    }

    /**
     * Method to Initialize the Views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.tool_bar_sound_settings);
        startGame = (Button) findViewById(R.id.offline_settings_play);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startGame.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundSettingsView.onViewVisible();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundSettingsView.onViewHidden();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.offline_settings_play:
                ChainReactionNavigator.openNewGameScreenActivity(this);
                break;
        }
    }
}
