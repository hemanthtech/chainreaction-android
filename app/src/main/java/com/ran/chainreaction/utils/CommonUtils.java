package com.ran.chainreaction.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.PlayColorValues;

/**
 * Created by ranjith on 12/11/15.
 * <p/>
 * Common Utils file for the APplication
 */
public class CommonUtils {

    /**
     * Utility for returning whether the String is null/Empty..
     *
     * @param string -- String to be compared
     * @return -- Whether String is empty/null..
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Utility to get the Color Resource Id based on Current PlayColorValues
     *
     * @param playColorValues -- PlayColorValues
     * @param context         -- Context of the App
     * @return -- Resource Id of the Color
     */
    public static int getColorByPlayerColor(PlayColorValues playColorValues, Context context) {
        int colorId;
        switch (playColorValues) {
            case RED:
                colorId = R.color.red_background_color;
                break;
            case ORANGE:
                colorId = R.color.orange_background_color;
                break;
            case YELLOW:
                colorId = R.color.yellow_background_color;
                break;
            case GREEN:
                colorId = R.color.green_background_color;
                break;
            case WHITE:
                colorId = R.color.white_background_color;
                break;
            case BLUE:
                colorId = R.color.blue_background_color;
                break;
            case VIOLET:
                colorId = R.color.violet_background_color;
                break;
            case PINK:
                colorId = R.color.pink_background_color;
                break;
            default:
                colorId = R.color.white_background_color;
                break;

        }
        return context.getResources().getColor(colorId);
    }


    /**
     * Method to update the Drawable Color based on passed PlayerColorValue
     *
     * @param index     -- Index of PlayerColorValue
     * @param mTextView -- TextView to be drawn with color
     * @param drawable  -- Drawable to updated
     */
    public static void updateColorView(int index, TextView mTextView, Drawable drawable, Context context) {
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
        mTextView.setBackground(drawable);
    }
}
