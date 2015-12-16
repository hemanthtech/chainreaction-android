package com.ran.chainreaction.gameplay;

import com.ran.chainreaction.entities.GameBombDirections;
import com.ran.chainreaction.utlity.GameInfoUtility;

import java.util.ArrayList;

/**
 * Created by ranjith on 06/12/15.
 * <p/>
 * A Cell in a Game , which holds required Data ..
 */
public class GameCellInfo {

    private int index;
    private GamePlayerInfo gamePlayerInfo;
    private ArrayList<GameBombDirections> possibleDirections;
    private int x_rows;
    private int y_columns;
    private int currentCount;

    public GameCellInfo(int index, GamePlayerInfo gamePlayerInfo, int x_rows, int y_columns) {
        this.index = index;
        this.gamePlayerInfo = gamePlayerInfo;
        this.x_rows = x_rows;
        this.y_columns = y_columns;
        possibleDirections = GameInfoUtility.generatePossibleDirections(x_rows, y_columns, index);
        currentCount = 0;
    }

    public int getIndex() {
        return index;
    }

    public GamePlayerInfo getGamePlayerInfo() {
        return gamePlayerInfo;
    }

    public void setGamePlayerInfo(GamePlayerInfo currentGamePlayerInfo) {
        this.gamePlayerInfo = currentGamePlayerInfo;
    }

    public ArrayList<GameBombDirections> getPossibleDirections() {
        return possibleDirections;
    }

    public int getX_rows() {
        return x_rows;
    }

    public int getY_columns() {
        return y_columns;
    }

    public int getMAX_CAPACITY() {
        return possibleDirections.size() - 1;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    @Override
    public boolean equals(Object o) {
        return index == ((GameCellInfo) o).index;
    }
}
