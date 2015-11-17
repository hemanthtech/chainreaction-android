package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 17/11/15.
 */
public enum PlayColorValues {

    RED(100, "red"),
    ORANGE(101, "orange"),
    YELLOW(102, "yellow"),
    GREEN(103, "green"),
    WHITE(104, "white"),
    BLUE(105, "blue"),
    VIOLET(106, "violet"),
    PINK(107, "pink");

    int value;
    String name;

    PlayColorValues(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Method to return index of Enum passed
     *
     * @param paramSound -- Enum index to be retrieved
     * @return -- Index of the Enum
     */

    public static int getIndex(PlayColorValues paramSound) {
        for (PlayColorValues item : values()) {
            if (item.value == paramSound.value) {
                return item.value;
            }
        }
        return WHITE.value;
    }

    /**
     * Method to return the Enum based on index
     *
     * @param index -- Index of the Enum to be retrieved
     * @return -- Enum type returned
     */
    public static PlayColorValues getEnumType(int index) {
        for (PlayColorValues item : values()) {
            if (item.value == index) {
                return item;
            }
        }
        return null;
    }
}
