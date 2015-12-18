package com.ran.chainreaction.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ran.chainreaction.R;
import com.ran.chainreaction.adapters.SavedGamesRecycleAdapter;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.entities.SavedGamesEntity;
import com.ran.chainreaction.interfaces.SavedGamesDbFetchInterface;
import com.ran.chainreaction.interfaces.SavedGamesSelectionInterface;
import com.ran.chainreaction.presenters.SavedGamesDbPresenter;

import java.util.ArrayList;

public class SavedGamesActivity extends ActionBarActivity implements View.OnClickListener, SavedGamesSelectionInterface, SavedGamesDbFetchInterface {

    private static final float ENABLE_ALPHA = 1.0f;
    private static final float DISABLE_ALPHA = 0.25f;
    Toolbar toolbar;
    SoundSettingsView soundSettingsView;
    Button resumeGame;
    TextView no_saved_Games;
    ProgressBar savedGamesProgressBar;
    RecyclerView savedGamesRecycler;
    LinearLayoutManager layoutManager;
    SavedGamesRecycleAdapter savedGamesRecycleAdapter;
    SavedGamesDbPresenter savedGamesDbPresenter;
    private long currentGameIdSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);

        initViews();
        savedGamesDbPresenter = new SavedGamesDbPresenter(this, this);
    }

    /**
     * Method to Initialize the Views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.tool_bar_sound_settings);
        resumeGame = (Button) findViewById(R.id.saved_settings_gamePlay);
        resumeGame.setOnClickListener(this);
        resumeGame.setEnabled(false);
        resumeGame.setAlpha(DISABLE_ALPHA);

        savedGamesRecycler = (RecyclerView) findViewById(R.id.saved_games_recycler);
        no_saved_Games = (TextView) findViewById(R.id.saved_games_noItems);
        savedGamesProgressBar = (ProgressBar) findViewById(R.id.saved_games_progress);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundSettingsView.onViewVisible();
        savedGamesDbPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundSettingsView.onViewHidden();
        savedGamesDbPresenter.stop();
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


    @Override
    public void showAllSavedGames(ArrayList<SavedGamesEntity> savedGamesEntities) {
        if (savedGamesEntities != null && savedGamesEntities.size() == 0) {
            no_saved_Games.setVisibility(View.VISIBLE);
        } else {
            //RecyclerView Stuff..
            layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            savedGamesRecycleAdapter = new SavedGamesRecycleAdapter(getApplicationContext(), this, savedGamesEntities);
            savedGamesRecycler.setAdapter(savedGamesRecycleAdapter);
            savedGamesRecycler.setLayoutManager(layoutManager);
            savedGamesRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            savedGamesProgressBar.setVisibility(View.VISIBLE);
        } else {
            savedGamesProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Call Back to Activity for saying GameId Selected
     *
     * @param gameId -- Selected Game Id
     */
    @Override
    public void onGameSelection(long gameId) {
        currentGameIdSelected = gameId;
        resumeGame.setEnabled(true);
        resumeGame.setAlpha(ENABLE_ALPHA);

    }

    /**
     * Call Back to Activity saying All Games are Deleted
     */
    @Override
    public void onAllGamesDeleted() {
        no_saved_Games.setVisibility(View.VISIBLE);
        resumeGame.setEnabled(false);
        resumeGame.setAlpha(DISABLE_ALPHA);
        savedGamesRecycler.setVisibility(View.GONE);
    }
}
