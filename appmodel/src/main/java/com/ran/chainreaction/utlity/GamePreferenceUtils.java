package com.ran.chainreaction.utlity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ranjith on 04/12/15.
 * <p/>
 * Class Responsible to save/get Current Game Preference Utils [New , Saved and Online]
 */
public class GamePreferenceUtils {

    public static final String GAME_PREFERENCE_KEY = "game_preferences_chainReaction";
    public static final String GAME_PLAYER_INFO_KEY = "game_playerInfoKey";


    public static String getPlayerInfoGame(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(GAME_PLAYER_INFO_KEY, null);
    }


    public static void setGamePlayerInfoGame(Context context, String pref) {

        SharedPreferences sharedPref = context.getSharedPreferences(GAME_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(GAME_PLAYER_INFO_KEY, pref);
        editor.commit();

    }

}
