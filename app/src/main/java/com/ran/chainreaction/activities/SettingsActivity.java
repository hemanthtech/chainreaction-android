package com.ran.chainreaction.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.ran.chainreaction.R;
import com.ran.chainreaction.customviews.SoundSettingsView;

public class SettingsActivity extends ActionBarActivity implements View.OnClickListener {


    ImageView facebookLoginView;
    ImageView mailLoginView;
    ImageView googlePlusLoginView;
    ImageView profilePicView;
    ImageView backButtonView;
    SoundSettingsView soundSettingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initView();

    }

    /**
     * Method to initialize the View ..
     */
    private void initView() {

        facebookLoginView = (ImageView) findViewById(R.id.settings_facebook_login);
        googlePlusLoginView = (ImageView) findViewById(R.id.settings_google_login);
        mailLoginView = (ImageView) findViewById(R.id.settings_mail_login);
        profilePicView = (ImageView) findViewById(R.id.settings_screen_profile_view);
        backButtonView = (ImageView) findViewById(R.id.settings_screen_back_view);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.settings_screen_sound_view);

        //Initialize the Click listeners
        facebookLoginView.setOnClickListener(this);
        googlePlusLoginView.setOnClickListener(this);
        mailLoginView.setOnClickListener(this);
        backButtonView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.settings_facebook_login:

                break;

            case R.id.settings_google_login:

                break;

            case R.id.settings_mail_login:

                break;

            case R.id.settings_screen_back_view:
                finish();
                break;
        }
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
