package com.ran.chainreaction.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.ran.chainreaction.R;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.utils.ChainReactionNavigator;

/**
 * Activity for shwing the How to Play UI
 */
public class HowToPlayActivity extends ActionBarActivity {

    Toolbar toolbar;
    SoundSettingsView soundSettingsView;
    Button playButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_how_to_play);
        initViews();
    }

    /**
     * Method to Initialize the Views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.tool_bar_sound_settings);
        playButton = (Button) findViewById(R.id.how_to_Play_start);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChainReactionNavigator.openOfflineGameSettingsActivity(context);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
