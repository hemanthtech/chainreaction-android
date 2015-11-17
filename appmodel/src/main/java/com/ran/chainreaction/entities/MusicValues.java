package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 17/11/15.
 */
public enum MusicValues {

    SOUND1(1000, "sound1.mp3"),
    SOUND2(1001, "sound2.mp3"),
    SOUND3(1002, "sound3.mp3");

    int value;
    String name;

    MusicValues(int value, String name) {
        this.value = value;
        this.name = name;
    }


    /**
     * Method to return index of Enum passed
     *
     * @param paramSound -- Enum index to be retrieved
     * @return -- Index of the Enum
     */

    public static int getIndex(MusicValues paramSound) {
        for (MusicValues item : values()) {
            if (item.value == paramSound.value) {
                return item.value;
            }
        }
        return SOUND1.value;
    }

    /**
     * Method to return the Enum based on index
     *
     * @param index -- Index of the Enum to be retrieved
     * @return -- Enum type returned
     */
    public static MusicValues getEnumType(int index) {
        for (MusicValues item : values()) {
            if (item.value == index) {
                return item;
            }
        }
        return null;
    }
}
