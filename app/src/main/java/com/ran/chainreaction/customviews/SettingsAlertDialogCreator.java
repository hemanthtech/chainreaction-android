package com.ran.chainreaction.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.BombValues;
import com.ran.chainreaction.entities.MusicValues;
import com.ran.chainreaction.entities.PlayColorValues;
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
    private static int currentColorSelectedIndex = -1;

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

    private static void instantiateColorDialog(final Context context) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        //Views for Top Title Layout [ImageView of Current Selection]
        LayoutInflater mLayoutInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTitleView = mLayoutInflate.inflate(R.layout.color_alertdialog_parent, null);
        final ImageView mSelectedColorView = (ImageView) mTitleView.findViewById(R.id.settings_color_selected);
        final Drawable drawable = context.getResources().getDrawable(R.drawable.rounded_circle);
        updateColorView(PlayColorValues.getIndex(ChainReactionPreferences.getPlayerColorPreference(context)),
            mSelectedColorView, drawable, context);

        //Generic Titles and Item Click Listeners..
        mBuilder.setCustomTitle(mTitleView);
        mBuilder.setSingleChoiceItems(R.array.color_settings_array,
            PlayColorValues.getIndex(ChainReactionPreferences.getPlayerColorPreference(context)),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currentColorSelectedIndex = which;
                    updateColorView(which, mSelectedColorView, drawable, context);
                }
            });
        mBuilder.setPositiveButton(R.string.dialog_color_settings_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currentColorSelectedIndex != -1) {
                    ChainReactionPreferences.setPlayerColorPreference(context,
                        PlayColorValues.getEnumType(currentColorSelectedIndex));
                    settingsPreferenceDialog.dismiss();
                }
            }
        });

        mBuilder.setNegativeButton(R.string.dialog_color_settings_reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingsPreferenceDialog.dismiss();
                Toast.makeText(context, R.string.settings_save_color_toast_reset, Toast.LENGTH_SHORT).show();
            }
        });
        settingsPreferenceDialog = mBuilder.create();
    }


    /**
     * Method to update the Drawable Color based on User Preference
     *
     * @param index      -- Index of selected Preference
     * @param mImageView -- ImageView to be drawn with color
     * @param drawable   -- Drawable to updated
     */
    private static void updateColorView(int index, ImageView mImageView, Drawable drawable, Context context) {
        int colorId;
        switch (index) {
            case 0:
                colorId = R.color.red_background_color;
                break;
            case 1:
                colorId = R.color.orange_background_color;
                break;
            case 2:
                colorId = R.color.yellow_background_color;
                break;
            case 3:
                colorId = R.color.green_background_color;
                break;
            case 4:
                colorId = R.color.white_background_color;
                break;
            case 5:
                colorId = R.color.blue_background_color;
                break;
            case 6:
                colorId = R.color.violet_background_color;
                break;
            case 7:
                colorId = R.color.pink_background_color;
                break;
            default:
                colorId = R.color.white_background_color;
                break;


        }
        drawable.setColorFilter(context.getResources().getColor(colorId), PorterDuff.Mode.SRC_ATOP);
        mImageView.setBackground(drawable);
        currentColorSelectedIndex = index;
    }
}
