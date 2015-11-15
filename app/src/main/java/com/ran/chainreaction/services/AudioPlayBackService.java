package com.ran.chainreaction.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.SoundPreferenceValues;
import com.ran.chainreaction.utils.ChainReactionPreferences;

/**
 * Created by ranjith on 13/11/15.
 * <p/>
 * Audio Service running in Background for Playing Audio when View [SoundSettingsView] is attached
 */

public class AudioPlayBackService extends Service implements AudioManager.OnAudioFocusChangeListener {

    private static final String TAG = AudioPlayBackService.class.getSimpleName();
    private final IBinder mBinder = new LocalAudioBinder();

    // Media Related Players/Service
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    /**
     * Called on the listener to notify it the audio focus for this listener has been changed.
     *
     * @param focusChange the type of focus change.
     */
    @Override
    public void onAudioFocusChange(int focusChange) {
        Log.d(TAG, "Current Focus : " + focusChange);
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                playAudio();
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                resetMediaPlayer();
                ChainReactionPreferences.setSoundPrefernce(this, SoundPreferenceValues.NO_SOUND);
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    updateCurrentStreamVolume();
                }
                break;
        }
    }


    /**
     * Binder class to Bind to the Service Connection ..
     */
    public class LocalAudioBinder extends Binder {

        public AudioPlayBackService getService() {
            return AudioPlayBackService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "On Bind Service");
        return mBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "On Destroy");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Method to Initialize the Media Player ..
     */
    private void initializeMediaPlayer() {
        int audioFocus = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN);
        if (audioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
            mediaPlayer.setLooping(true);
            playAudio();
        } else {
            ChainReactionPreferences.setSoundPrefernce(this, SoundPreferenceValues.NO_SOUND);
            Log.d(TAG, "The Audio Focus / SoundPreference is not granted");
        }
    }

    /**
     * Method to Reset/Release MediaPlayer Resources on Stop
     */
    private void resetMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Method that updates the Click State from SoundSettings View to MediaPlayer ..
     *
     * @param preferenceValues -- Preference Needed..
     */
    public void updateAudioPlayState(SoundPreferenceValues preferenceValues) {

        //Update the Current Preference to the ChainReactionPreferences Utils
        ChainReactionPreferences.setSoundPrefernce(this, preferenceValues);

        switch (preferenceValues) {
            case FULL_SOUND:
            case MEDIUM_SOUND:
                playAudio();
                break;
            case NO_SOUND:
                resetMediaPlayer();
                break;
        }

    }

    /**
     * Method to play Audio
     */
    private void playAudio() {
        //Create or initialize the MediaPlayer..
        if (mediaPlayer == null) {
            initializeMediaPlayer();
        }
        mediaPlayer.start();
        updateCurrentStreamVolume();
    }

    /**
     * Method called from Sound Settings View [onResume /onPause ]
     *
     * @param isVisible
     */
    public void viewVisible(boolean isVisible) {
        if (isVisible && ChainReactionPreferences.getSoundPreference(this) != SoundPreferenceValues.NO_SOUND) {
            initializeMediaPlayer();
        } else {
            resetMediaPlayer();
            audioManager.abandonAudioFocus(this);
        }
    }

    /**
     * Method to update Music Stream to Full /Medium Sound ..
     */
    private void updateCurrentStreamVolume() {
        int maxIndex = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (ChainReactionPreferences.getSoundPreference(this) == SoundPreferenceValues.FULL_SOUND) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxIndex,
                AudioManager.FLAG_PLAY_SOUND);
        } else if (ChainReactionPreferences.getSoundPreference(this) == SoundPreferenceValues.MEDIUM_SOUND) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxIndex / 2,
                AudioManager.FLAG_PLAY_SOUND);
        } else {
            //Do nothing..
        }
    }
}
