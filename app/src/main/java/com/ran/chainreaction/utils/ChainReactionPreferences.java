package com.ran.chainreaction.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ran.chainreaction.entities.BombValues;
import com.ran.chainreaction.entities.GridSizeValues;
import com.ran.chainreaction.entities.MusicValues;
import com.ran.chainreaction.entities.PlayColorValues;
import com.ran.chainreaction.entities.SoundPreferenceValues;

/**
 * Created by ranjith on 12/11/15.
 * <p/>
 * COnstants and Accessory functions for all Preferences
 */
public class ChainReactionPreferences {

    public static final String APP_PREFERENCE_KEY = "chain_reaction_prefs";


    /**
     * Preference Key for Sound Settings.
     * Possible Index Values :
     * <p/>
     * NO_SOUND(0, "nosound"),
     * MEDIUM_SOUND(1, "medsound"),
     * FULL_SOUND(2, "fullsound");
     */
    public static final String SOUND_KEY = "sound_preferences";
    public static final SoundPreferenceValues SOUND_KEY_DEFAULT_VALUE = SoundPreferenceValues.FULL_SOUND;

    /**
     * Preference Key for Bomb Settings.
     * Possible Index Values :
     * <p/>
     * CIRCLE(10, "circle"),
     * SQUARE(11, "square"),
     * TRIANGLE(12, "triangle");
     */
    public static final String BOMB_KEY = "bmb_preferences";
    public static final BombValues BOMB_KEY_DEFAULT_VALUE = BombValues.CIRCLE;


    /**
     * Preference Key for Player Color Settings.
     * Possible Index Values :
     * <p/>
     * RED(100, "red"),
     * ORANGE(101, "orange"),
     * YELLOW(102, "yellow"),
     * GREEN(103, "green"),
     * WHITE(104, "white"),
     * BLUE(105, "blue"),
     * VIOLET(106, "violet"),
     * PINK(107, "pink");
     */
    public static final String PLAY_COLOR_KEY = "color_preferences";
    public static final PlayColorValues PLAY_COLOR_KEY_DEFAULT_VALUE = PlayColorValues.WHITE;

    /**
     * Preference Key for Music Settings.
     * Possible Index Values :
     * <p/>
     * SOUND1(1000, "sound1.mp3"),
     * SOUND2(1001, "sound2.mp3"),
     * SOUND3(1002, "sound3.mp3");
     */
    public static final String MUSIC_KEY = "music_preferences";
    public static final MusicValues MUSIC_KEY_DEFAULT_VALUE = MusicValues.SOUND1;

    /**
     * Preference Key for Grid size Settings.
     * Possible Index Values :
     * <p/>
     * SMALL(0, "small"),
     * MEDIUM(1, "medium"),
     * LARGE(2, "large");
     */

    public static final String GRIDSIZE_KEY = "grid_preferences";
    public static final GridSizeValues GRID_KEY_DEFAULT_VALUE = GridSizeValues.SMALL;


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
    public static void setSoundPreference(Context context, SoundPreferenceValues soundPref) {

        SharedPreferences sharedPref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(SOUND_KEY, SoundPreferenceValues.getIndex(soundPref));
        editor.commit();

    }

    /**
     * Method to get the BombSettings Preference
     *
     * @param context -- Context of the Application
     * @return -- Enum type of BombValues
     */

    public static BombValues getBombPreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int bmbIndex = sharedPreferences.getInt(BOMB_KEY, BombValues.getIndex(BOMB_KEY_DEFAULT_VALUE));

        return BombValues.getEnumType(bmbIndex);
    }

    /**
     * Method to set the SoundPreference
     *
     * @param context -- Context of the Application
     * @param bmbPref -- Enum type of the BombValues
     */
    public static void setBombPreference(Context context, BombValues bmbPref) {

        SharedPreferences sharedPref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(BOMB_KEY, BombValues.getIndex(bmbPref));
        editor.commit();

    }


    /**
     * Method to get the PlayerColor Preference
     *
     * @param context -- Context of the Application
     * @return -- Enum type of PlayerColorValues
     */

    public static PlayColorValues getPlayerColorPreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int colorIndex = sharedPreferences.getInt(PLAY_COLOR_KEY, PlayColorValues.getIndex(PLAY_COLOR_KEY_DEFAULT_VALUE));

        return PlayColorValues.getEnumType(colorIndex);
    }

    /**
     * Method to set the PlayerColor Preference
     *
     * @param context       -- Context of the Application
     * @param playColorPref -- Enum type of the PlayColorValues
     */
    public static void setPlayerColorPreference(Context context, PlayColorValues playColorPref) {

        SharedPreferences sharedPref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(PLAY_COLOR_KEY, PlayColorValues.getIndex(playColorPref));
        editor.commit();

    }


    /**
     * Method to get the Music  Preference
     *
     * @param context -- Context of the Application
     * @return -- Enum type of MusicValues
     */

    public static MusicValues getMusicPreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int musicIndex = sharedPreferences.getInt(MUSIC_KEY, MusicValues.getIndex(MUSIC_KEY_DEFAULT_VALUE));

        return MusicValues.getEnumType(musicIndex);
    }

    /**
     * Method to set the MusicPreference
     *
     * @param context   -- Context of the Application
     * @param musicPref -- Enum type of the MusicValues
     */
    public static void setMusicPreference(Context context, MusicValues musicPref) {

        SharedPreferences sharedPref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(MUSIC_KEY, MusicValues.getIndex(musicPref));
        editor.commit();

    }

    /**
     * Method to get the Grid Size  Preference
     *
     * @param context -- Context of the Application
     * @return -- Enum type of GridSizeValues
     */

    public static GridSizeValues getGridSizePreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int gridIndex = sharedPreferences.getInt(GRIDSIZE_KEY, GridSizeValues.getIndex(GRID_KEY_DEFAULT_VALUE));

        return GridSizeValues.getEnumType(gridIndex);
    }

    /**
     * Method to set the GridSizePreference
     *
     * @param context  -- Context of the Application
     * @param gridSize -- Enum type of the GridValuesType
     */
    public static void setGridSizePreference(Context context, GridSizeValues gridSize) {

        SharedPreferences sharedPref = context.getSharedPreferences(APP_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(GRIDSIZE_KEY, GridSizeValues.getIndex(gridSize));
        editor.commit();

    }
}
