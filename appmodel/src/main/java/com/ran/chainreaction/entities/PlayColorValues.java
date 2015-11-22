package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 17/11/15.
 *
 * PlayerColor Values Entities for ChainReaction Model
 */
public enum PlayColorValues {

    RED(0, "red"),
    ORANGE(1, "orange"),
    YELLOW(2, "yellow"),
    GREEN(3, "green"),
    WHITE(4, "white"),
    BLUE(5, "blue"),
    VIOLET(6, "violet"),
    PINK(7, "pink");

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
