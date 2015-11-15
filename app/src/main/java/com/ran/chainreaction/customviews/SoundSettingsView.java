package com.ran.chainreaction.customviews;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.SoundPreferenceValues;
import com.ran.chainreaction.interfaces.SoundSettingsViewInterface;
import com.ran.chainreaction.services.AudioPlayBackService;
import com.ran.chainreaction.utils.ChainReactionPreferences;


/**
 * Created by ranjith on 12/11/15.
 * <p/>
 * This View is used in All Activities /Fragments for Monitoring the Changes in SoundUI
 * a) Current Sound State
 * b) Play/Pause Sound based on the Activity/Fragment Pause ..
 */

public class SoundSettingsView extends ImageView implements View.OnClickListener,
    SharedPreferences.OnSharedPreferenceChangeListener, SoundSettingsViewInterface {

    private Context viewContext;
    /**
     * Reference to Hold the currentSoundPreference [Full ,Medium or NO]
     */
    private SoundPreferenceValues currentSoundPref;

    /**
     * Reference to the AudioPlayBackService , responsible for MediaPlayer PlayBack..
     */
    private AudioPlayBackService mAudioService;
    private boolean mServiceBound = false;

    public SoundSettingsView(Context context) {
        super(context);
        this.viewContext = context;
        initView();
    }

    public SoundSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.viewContext = context;
        initView();
    }

    public SoundSettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.viewContext = context;
        initView();
    }


    /**
     * Method to Initialize the view ..
     */
    private void initView() {
        setOnClickListener(this);
        viewContext.getSharedPreferences(ChainReactionPreferences.APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(this);
        currentSoundPref = ChainReactionPreferences.getSoundPreference(viewContext);
        updateUI();
    }

    /**
     * Method to Update UI images , Alpha and enabled State
     */
    private void updateUI() {
        this.setAlpha(1.0f);
        this.setEnabled(true);
        switch (currentSoundPref) {
            case FULL_SOUND:
                setImageResource(R.mipmap.sound_full);
                break;
            case MEDIUM_SOUND:
                setImageResource(R.mipmap.sound_medium);
                break;
            case NO_SOUND:
                setImageResource(R.mipmap.sound_off);
                break;
        }
    }

    private void bindToAudioService() {
        Intent intent = new Intent(viewContext, AudioPlayBackService.class);
        viewContext.bindService(intent, mAudioServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindAudioService() {
        if (mServiceBound) {
            viewContext.unbindService(mAudioServiceConnection);
            mServiceBound = false;
        }
    }

    /**
     * Inner class responsible for Binding to the Audio Intent Service..
     */
    private ServiceConnection mAudioServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioPlayBackService.LocalAudioBinder mLocalBinder = (AudioPlayBackService.LocalAudioBinder) service;
            mAudioService = mLocalBinder.getService();
            mAudioService.viewVisible(true);
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        /**
         * a) update the State to the Service ..
         * b) On CallBack of Preference Update , update the image state
         */

        this.setEnabled(false);
        this.setAlpha(0.5f);

        switch (currentSoundPref) {
            case FULL_SOUND:
                mAudioService.updateAudioPlayState(SoundPreferenceValues.MEDIUM_SOUND);
                break;
            case MEDIUM_SOUND:
                mAudioService.updateAudioPlayState(SoundPreferenceValues.NO_SOUND);
                break;
            case NO_SOUND:
                mAudioService.updateAudioPlayState(SoundPreferenceValues.FULL_SOUND);
                break;
        }

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        //Bind to the Service Running..
        bindToAudioService();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        //UnBind to the Service Running ..
        unBindAudioService();
    }

    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     * <p/>
     * This callback will be run on your main thread.
     *
     * @param sharedPreferences The {@link SharedPreferences} that received
     *                          the change.
     * @param key               The key of the preference that was changed, added, or
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(ChainReactionPreferences.SOUND_KEY)) {
            currentSoundPref = ChainReactionPreferences.getSoundPreference(viewContext);
            updateUI();
        }
    }

    /**
     * Method to say that view isVisible
     */
    @Override
    public void onViewVisible() {
        if (mServiceBound) {
            mAudioService.viewVisible(true);
        }
    }

    /**
     * Method to say that view isHidden
     */
    @Override
    public void onViewHidden() {
        if (mServiceBound) {
            mAudioService.viewVisible(false);
        }
    }
}
