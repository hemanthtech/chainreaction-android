package com.ran.chainreaction;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ran.chainreaction.customviews.SoundSettingsView;

public class SavedGamesActivity extends ActionBarActivity implements View.OnClickListener {

    Toolbar toolbar;
    SoundSettingsView soundSettingsView;
    Button resumeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);

        initViews();
    }

    /**
     * Method to Initialize the Views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.tool_bar_sound_settings);
        resumeGame = (Button) findViewById(R.id.saved_settings_gamePlay);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resumeGame.setOnClickListener(this);
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
            case R.id.saved_settings_gamePlay:
                Toast.makeText(this, "Resume Game clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
