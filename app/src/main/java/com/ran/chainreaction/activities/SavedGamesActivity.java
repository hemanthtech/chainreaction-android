package com.ran.chainreaction.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ran.chainreaction.R;
import com.ran.chainreaction.adapters.SavedGamesRecycleAdapter;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.entities.SavedGamesEntity;
import com.ran.chainreaction.interfaces.SavedGamesDbFetchInterface;
import com.ran.chainreaction.interfaces.SavedGamesSelectionInterface;
import com.ran.chainreaction.presenters.SavedGamesDbPresenter;

import java.util.ArrayList;

public class SavedGamesActivity extends ActionBarActivity implements SavedGamesSelectionInterface,
    SavedGamesDbFetchInterface {

  private static final float ENABLE_ALPHA = 1.0f;
  private static final float DISABLE_ALPHA = 0.25f;
  Toolbar toolbar;
  SoundSettingsView soundSettingsView;
  TextView no_saved_Games;
  ProgressBar savedGamesProgressBar;
  RecyclerView savedGamesRecycler;
  LinearLayoutManager layoutManager;
  SavedGamesRecycleAdapter savedGamesRecycleAdapter;
  SavedGamesDbPresenter savedGamesDbPresenter;

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

  @Override
  public void showAllSavedGames(ArrayList<SavedGamesEntity> savedGamesEntities) {
    if (savedGamesEntities != null && savedGamesEntities.size() == 0) {
      no_saved_Games.setVisibility(View.VISIBLE);
    } else {
      //RecyclerView Stuff..
      layoutManager = new LinearLayoutManager(this);
      layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      savedGamesRecycleAdapter = new SavedGamesRecycleAdapter(this, this, savedGamesEntities);
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
   * Call Back to Activity saying All Games are Deleted
   */
  @Override
  public void onAllGamesDeleted() {
    no_saved_Games.setVisibility(View.VISIBLE);
    savedGamesRecycler.setVisibility(View.GONE);
  }

}
