package com.ran.chainreaction.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AudioPlayBackService extends Service implements AudioManager.OnAudioFocusChangeListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

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
                ChainReactionPreferences.setSoundPreference(this, SoundPreferenceValues.NO_SOUND);
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    updateCurrentStreamVolume();
                }
                break;
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(ChainReactionPreferences.MUSIC_KEY)) {
            resetMediaPlayer();
            if (ChainReactionPreferences.getSoundPreference(this) != SoundPreferenceValues.NO_SOUND) {
                initializeMediaPlayer();
            }
        }
    }

    /**
     * Helper Method to return the Music Id based on Setting's
     *
     * @return -- Id of the raw Music
     */
    private int getProperMusicId() {
        switch (ChainReactionPreferences.getMusicPreference(this)) {
            case SOUND1:
                return R.raw.sound1;
            case SOUND2:
                return R.raw.sound2;
            case SOUND3:
                return R.raw.sound3;
        }

        return R.raw.sound1; //Default , Ideally doesn't happen
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        this.getSharedPreferences(ChainReactionPreferences.APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(this);
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
            mediaPlayer = MediaPlayer.create(this, getProperMusicId());
            mediaPlayer.setLooping(true);
            playAudio();
        } else {
            ChainReactionPreferences.setSoundPreference(this, SoundPreferenceValues.NO_SOUND);
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
        ChainReactionPreferences.setSoundPreference(this, preferenceValues);

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
     * @param isVisible -- View visibility
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
            Log.d(TAG, "No Sound Preference Volume update");
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
}
