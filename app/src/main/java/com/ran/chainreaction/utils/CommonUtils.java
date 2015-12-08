package com.ran.chainreaction.utils;

import android.content.Context;

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
     * @param string
     * @return -- Whether String is empty/null..
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

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
}
