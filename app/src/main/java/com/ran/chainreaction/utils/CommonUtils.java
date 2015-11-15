package com.ran.chainreaction.utils;

/**
 * Created by ranjith on 12/11/15.
 */
public class CommonUtils {

    /**
     * Utility for returning whether the String is null/Empty..
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }
        return false;
    }
}
