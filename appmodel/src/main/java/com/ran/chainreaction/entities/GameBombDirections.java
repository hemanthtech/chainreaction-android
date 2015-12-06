package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 06/12/15.
 * <p/>
 * Enum stating possible Directions for a Bomb
 */
public enum GameBombDirections {

    LEFT(0, "left"),
    TOP(1, "top"),
    RIGHT(2, "right"),
    BOTTOM(3, "bottom");

    int index;
    String name;

    GameBombDirections(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
