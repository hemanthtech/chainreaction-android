package com.ran.chainreaction.activities;

/**
 * SPlash /Start Screen for the Application with Options Being Displayed
 */

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ran.chainreaction.R;
import com.ran.chainreaction.customviews.ExitAlertDialogCreator;
import com.ran.chainreaction.customviews.SoundSettingsView;
import com.ran.chainreaction.utils.ChainReactionNavigator;
import com.ran.chainreaction.utils.ChainReactionShareUtils;

public class SplashActivity extends ActionBarActivity implements View.OnClickListener, ExitAlertDialogCreator.ButtonOnClickListener {

    //TAGS for Alert Dialog Click [Match to String Array Entry]
    public static final int EXIT_TAG = 0;
    public static final int CANCEL_TAG = 1;
    private static final String TAG = SplashActivity.class.getSimpleName();
    Button offlinePlayButton;
    Button onlinePlayButton;
    Button savedGamesButton;
    Button howPlayButton;
    ImageView appSettingsView;
    TextView creatorEmail;
    SoundSettingsView soundSettingsView;
    AlertDialog mBackDialog;
    String mBackDialogEntries[];
    String mBackDialogTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "On create");
        setContentView(R.layout.activity_splash);

        //Hide ActionBar for the Splash Screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
    }

    /**
     * Method to initialize the views with Xml id's
     */
    private void initViews() {

        offlinePlayButton = (Button) findViewById(R.id.splash_offline_play);
        onlinePlayButton = (Button) findViewById(R.id.splash_online_play);
        savedGamesButton = (Button) findViewById(R.id.splash_saved_games);
        howPlayButton = (Button) findViewById(R.id.splash_how_play);
        appSettingsView = (ImageView) findViewById(R.id.splash_app_settings);
        creatorEmail = (TextView) findViewById(R.id.splash_creator_email);
        soundSettingsView = (SoundSettingsView) findViewById(R.id.splash_app_soundSettings);

        //Enabling Click Listeners ..
        offlinePlayButton.setOnClickListener(this);
        onlinePlayButton.setOnClickListener(this);
        savedGamesButton.setOnClickListener(this);
        howPlayButton.setOnClickListener(this);
        appSettingsView.setOnClickListener(this);
        creatorEmail.setOnClickListener(this);

        mBackDialogEntries = getResources().getStringArray(R.array.splash_screen_dialog);
        mBackDialogTitle = getResources().getString(R.string.splash_screen_dialog_title);

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        final int clickedId = v.getId();

        switch (clickedId) {

            case R.id.splash_offline_play:
                ChainReactionNavigator.openOfflineGameSettingsActivity(this);
                break;

            case R.id.splash_online_play:
                showCustomToastClick(getResources().getString(R.string.online_play_button));
                break;

            case R.id.splash_saved_games:
                ChainReactionNavigator.openSavedGameSettingsActivity(this);
                break;

            case R.id.splash_how_play:
                showCustomToastClick(getResources().getString(R.string.how_play_button));
                break;

            case R.id.splash_app_settings:
                ChainReactionNavigator.openSettingsActivity(this);
                break;


            case R.id.splash_creator_email:
                sendFeedBackEmail();
                break;
        }

    }

    /**
     * Method to show Custom Toast when the View is clicked..
     *
     * @param message The Message to be displayed
     */
    private void showCustomToastClick(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to send the Email to Creator
     */
    private void sendFeedBackEmail() {
        String subject = getResources().getString(R.string.email_subject);
        String emailId = getResources().getString(R.string.email_mailId);
        try {
            ChainReactionShareUtils.generateEmailMessage(subject, new String[]{emailId}, this);
        } catch (PackageManager.NameNotFoundException exception) {
            Log.d(TAG, exception.toString());
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

    @Override
    public void onBackPressed() {
        if (mBackDialog == null || !mBackDialog.isShowing()) {
            mBackDialog = ExitAlertDialogCreator.createDialog(mBackDialogEntries, mBackDialogTitle, this, false);
            mBackDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onExitButtonClick(View view) {

        try {
            int tag = (int) view.getTag();
            switch (tag) {
                case EXIT_TAG:
                    finish();
                    break;

                case CANCEL_TAG:
                    mBackDialog.dismiss();
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in Button Clicks");
            mBackDialog.dismiss();
        }

    }
}
