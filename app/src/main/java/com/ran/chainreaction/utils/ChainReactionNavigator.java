package com.ran.chainreaction.utils;

import android.content.Context;
import android.content.Intent;

import com.ran.chainreaction.activities.OfflineSettingsActivity;
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
}
