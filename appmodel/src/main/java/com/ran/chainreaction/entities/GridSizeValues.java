package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 27/11/15.
 * <p/>
 * Entities responsible saying various Grid Sizes ..
 */
public enum GridSizeValues {

    SMALL(0, "small"),
    MEDIUM(1, "medium"),
    LARGE(2, "large");

    int value;
    String name;

    GridSizeValues(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Method to return index of Enum passed
     *
     * @param gridSize -- Enum index to be retrieved
     * @return -- Index of the Enum
     */

    public static int getIndex(GridSizeValues gridSize) {
        for (GridSizeValues item : values()) {
            if (item.value == gridSize.value) {
                return item.value;
            }
        }
        return SMALL.value;
    }

    /**
     * Method to return the Enum based on index
     *
     * @param index -- Index of the Enum to be retrieved
     * @return -- Enum type returned
     */
    public static GridSizeValues getEnumType(int index) {
        for (GridSizeValues item : values()) {
            if (item.value == index) {
                return item;
            }
        }
        return GridSizeValues.SMALL;
    }
}
