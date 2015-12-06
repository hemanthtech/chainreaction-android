package com.ran.chainreaction.gameplay;

import com.ran.chainreaction.entities.GameBombDirections;

import java.util.ArrayList;

/**
 * Created by ranjith on 06/12/15.
 * <p/>
 * A Cell in a Game , which holds required Data ..
 */
public class GameCellInfo {

    private int index;
    private GamePlayerInfo currentGamePlayerInfo;
    private ArrayList<GameBombDirections> possibleDirections;
    private int x_rows;
    private int y_columns;

    public GameCellInfo(int index, GamePlayerInfo gamePlayerInfo, int x_rows, int y_columns) {
        this.index = index;
        this.currentGamePlayerInfo = gamePlayerInfo;
        this.x_rows = x_rows;
        this.y_columns = y_columns;

        generatePossibleDirections();
    }

    /**
     * Utility to generate the Possible Directions.
     */
    private void generatePossibleDirections() {

    }

}
