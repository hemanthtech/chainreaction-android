package com.ran.chainreaction;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ran.chainreaction.adapters.SavedGamesRecycleAdapter;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.interfaces.SavedGamesSelectionInterface;

import java.util.ArrayList;

public class SavedGamesActivity extends ActionBarActivity implements View.OnClickListener, SavedGamesSelectionInterface {

    private static final float ENABLE_ALPHA = 1.0f;
    private static final float DISABLE_ALPHA = 0.25f;
    Toolbar toolbar;
    SoundSettingsView soundSettingsView;
    Button resumeGame;
    RecyclerView savedGamesRecycler;
    LinearLayoutManager layoutManager;
    SavedGamesRecycleAdapter savedGamesRecycleAdapter;

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
        savedGamesRecycler = (RecyclerView) findViewById(R.id.saved_games_recycler);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resumeGame.setOnClickListener(this);
        resumeGame.setEnabled(true);
        resumeGame.setAlpha(ENABLE_ALPHA);

        //RecyclerView Stuff..
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //TODO [ranjith.suda] Send Proper Data from DB ..
        ArrayList<Integer> data = new ArrayList<>(5);
        data.add(0);
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);

        savedGamesRecycleAdapter = new SavedGamesRecycleAdapter(getApplicationContext(), this, data);
        savedGamesRecycler.setAdapter(savedGamesRecycleAdapter);
        savedGamesRecycler.setLayoutManager(layoutManager);


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


    /**
     * Method to say which View is Selected in RecyclerAdapter
     *
     * @param isSelect -- View is Selected /not
     * @param position -- Position of the view ..
     */
    @Override
    public void onGameSelectionChanged(boolean isSelect, int position) {

        //Update the Select status in the DataSet , Use for taking Decision ..
        //TODO [ranjith.suda] - Enable the state in local dataset and check

        if (isResumeGameAvailable()) {
            resumeGame.setEnabled(true);
            resumeGame.setAlpha(ENABLE_ALPHA);
        } else {
            resumeGame.setAlpha(DISABLE_ALPHA);
            resumeGame.setEnabled(false);
        }
    }

    /**
     * Method to say that game is deleted from Recycler Adapter
     *
     * @param position -- View that is deleted
     */
    @Override
    public void onGameDeleted(int position) {

        //Logic to update Deletion status ..
        //Todo [ranjith.suda] Add logic ..
    }

    private boolean isResumeGameAvailable() {

        return true;
    }
}
