package com.ran.chainreaction.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.BombValues;
import com.ran.chainreaction.entities.MusicValues;
import com.ran.chainreaction.utils.ChainReactionPreferences;

/**
 * Created by ranjith on 22/11/15.
 * <p/>
 * Dialog Creator for Settings Options [Music ,Color and Bomb]
 */
public class SettingsAlertDialogCreator {

    public static final int BMB_TYPE_DIALOG = 1;
    public static final int MUSIC_TYPE_DIALOG = 2;
    public static final int COLOR_TYPE_DIALOG = 3;

    private static AlertDialog settingsPreferenceDialog;

    public static AlertDialog createOptionsDialog(int type, Context context) {
        switch (type) {
            case BMB_TYPE_DIALOG:
                instantiateBmbDialog(context);
                break;

            case MUSIC_TYPE_DIALOG:
                instantiateMusicDialog(context);
                break;

            case COLOR_TYPE_DIALOG:
                instantiateColorDialog(context);
                break;
        }
        return settingsPreferenceDialog;
    }

    /**
     * Method responsible for showing the Bomb Settings Preference View ..
     */
    private static void instantiateBmbDialog(final Context context) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        mBuilder.setTitle(context.getResources().getString(R.string.dialog_bmb_settings_title));
        mBuilder.setSingleChoiceItems(R.array.bmb_settings_array,
            BombValues.getIndex(ChainReactionPreferences.getBombPreference(context)),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BombValues currentChoice = BombValues.getEnumType(which);
                    ChainReactionPreferences.setBombPreference(context, currentChoice);
                    settingsPreferenceDialog.dismiss();
                }
            });

        settingsPreferenceDialog = mBuilder.create();
    }

    /**
     * Method responsible for showing the Music Settings Preference View ..
     */
    private static void instantiateMusicDialog(final Context context) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        mBuilder.setTitle(context.getResources().getString(R.string.dialog_music_settings_title));
        mBuilder.setSingleChoiceItems(R.array.music_settings_array,
            MusicValues.getIndex(ChainReactionPreferences.getMusicPreference(context)),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MusicValues currentChoice = MusicValues.getEnumType(which);
                    ChainReactionPreferences.setMusicPreference(context, currentChoice);
                    settingsPreferenceDialog.dismiss();
                }
            });

        settingsPreferenceDialog = mBuilder.create();
    }

    /**
     * Method responsible for showing the Color Settings Preference View ..
     */

    private static void instantiateColorDialog(Context context) {

    }
}
