package com.ran.chainreaction.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ran.chainreaction.entities.SoundPreferenceValues;

/**
 * Created by ranjith on 12/11/15.
 */
public class ChainReactionPreferences {

    public static final String APP_PREFERENCE_KEY = "chain_reaction_prefs";


    /**
     * Preference Key for Sound Settings.
     * Possible Index Values :
     * <p/>
     * 0 -- No Sound
     * 1 -- Medium Sound
     * 2 -- Full Sound
     */
    public static final String SOUND_KEY = "sound_preferences";


    public static final SoundPreferenceValues SOUND_KEY_DEFAULT_VALUE = SoundPreferenceValues.FULL_SOUND;

    /**
     * Method to get the SoundPreference Preference
     *
     * @param context -- Context of the Application
     * @return -- Enum type of SoundPreferences
     */

    public static SoundPreferenceValues getSoundPreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int soundIndex = sharedPreferences.getInt(SOUND_KEY, SoundPreferenceValues.getIndex(SOUND_KEY_DEFAULT_VALUE));

        return SoundPreferenceValues.getEnumType(soundIndex);
    }


    /**
     * Method to set the SoundPreference
     *
     * @param context   -- Context of the Application
     * @param soundPref -- Enum type of the SoundPreference
     */
    public static void setSoundPrefernce(Context context, SoundPreferenceValues soundPref) {

        SharedPreferences sharedPref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(SOUND_KEY, SoundPreferenceValues.getIndex(soundPref));
        editor.commit();

    }

}
