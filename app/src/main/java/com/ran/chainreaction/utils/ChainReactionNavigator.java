package com.ran.chainreaction.utils;

import android.content.Context;
import android.content.Intent;

import com.ran.chainreaction.activities.GameScreenActivity;
import com.ran.chainreaction.activities.OfflineSettingsActivity;
import com.ran.chainreaction.activities.SavedGamesActivity;
import com.ran.chainreaction.activities.SettingsActivity;

/**
 * Created by ranjith on 16/11/15.
 * <p/>
 * Class Responsible for Making Navigation Inside the Chain Reaction
 */
public class ChainReactionNavigator {

    /**
     * Method responsbile for Opening the Settings Activity ..
     *
     * @param context -- Context of App
     */
    public static void openSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    /**
     * Method to open Offline Game Settings View
     *
     * @param context -- Context of the App
     */
    public static void openOfflineGameSettingsActivity(Context context) {
        Intent intent = new Intent(context, OfflineSettingsActivity.class);
        context.startActivity(intent);
    }

    /**
     * Method to open Saved Games Screen View
     *
     * @param context -- Context of the App
     */
    public static void openSavedGameSettingsActivity(Context context) {
        Intent intent = new Intent(context, SavedGamesActivity.class);
        context.startActivity(intent);
    }


    /**
     * Method to open New Offline Game Screen
     *
     * @param context -- Context of the App ..
     */
    public static void openNewGameScreenActivity(Context context) {
        Intent intent = new Intent(context, GameScreenActivity.class);
        context.startActivity(intent);

    }


    /**
     * Method to Open Saved Game from DB screen ..
     *
     * @param context -- Context of the App ..
     * @param gameId  -- saved GameId
     */
    public static void openSavedGameScreenActivity(Context context, long gameId) {
        Intent intent = new Intent(context, GameScreenActivity.class);
        intent.putExtra(ChainReactionConstants.SAVED_GAME_ID_KEY, gameId);
        intent.putExtra(ChainReactionConstants.SAVED_GAME_KEY, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
