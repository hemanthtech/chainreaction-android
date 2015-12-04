package com.ran.chainreaction.utlity;

import android.content.Context;
import android.content.SharedPreferences;

import com.ran.chainreaction.entities.GridSizeValues;

/**
 * Created by ranjith on 04/12/15.
 * <p/>
 * Class Responsible to save/get Current Game Preference Utils [New , Saved and Online]
 */
public class GamePreferenceUtils {

    public static final String GAME_PREFERENCE_KEY = "game_preferences";

    public static final String GAME_GRID_SIZE = "game_grid_preferences";
    public static final GridSizeValues GAME_GRID_SIZE_DEFAULT = GridSizeValues.SMALL;


    /**
     * Method to get the Current Game Grid Preference
     *
     * @param context -- Context of the Application
     * @return -- Enum type of GridSizeValues
     */

    public static GridSizeValues getGridSizePreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_PREFERENCE_KEY, Context.MODE_PRIVATE);
        int gridIndex = sharedPreferences.getInt(GAME_GRID_SIZE, GridSizeValues.getIndex(GAME_GRID_SIZE_DEFAULT));

        return GridSizeValues.getEnumType(gridIndex);
    }

    /**
     * Method to set the Current Grid Size Preference
     *
     * @param context  -- Context of the Application
     * @param gridSize -- Enum type of the GridValuesType
     */
    public static void setGridSizePreference(Context context, GridSizeValues gridSize) {

        SharedPreferences sharedPref = context.getSharedPreferences(GAME_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(GAME_GRID_SIZE, GridSizeValues.getIndex(gridSize));
        editor.commit();

    }
}
