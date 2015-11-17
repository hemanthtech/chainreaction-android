package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 17/11/15.
 */
public enum BombValues {

    CIRCLE(10, "circle"),
    SQUARE(11, "square"),
    TRIANGLE(12, "triangle");

    int value;
    String name;

    BombValues(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Method to return index of Enum passed
     *
     * @param paramSound -- Enum index to be retrieved
     * @return -- Index of the Enum
     */

    public static int getIndex(BombValues paramSound) {
        for (BombValues item : values()) {
            if (item.value == paramSound.value) {
                return item.value;
            }
        }
        return CIRCLE.value;
    }

    /**
     * Method to return the Enum based on index
     *
     * @param index -- Index of the Enum to be retrieved
     * @return -- Enum type returned
     */
    public static BombValues getEnumType(int index) {
        for (BombValues item : values()) {
            if (item.value == index) {
                return item;
            }
        }
        return null;
    }
}
