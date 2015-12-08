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
    private GamePlayerInfo currentGamePlayerInfo;
    private ArrayList<GameBombDirections> possibleDirections;
    private int x_rows;
    private int y_columns;

    public GameCellInfo(int index, GamePlayerInfo gamePlayerInfo, int x_rows, int y_columns) {
        this.index = index;
        this.currentGamePlayerInfo = gamePlayerInfo;
        this.x_rows = x_rows;
        this.y_columns = y_columns;
        possibleDirections = GameInfoUtility.generatePossibleDirections(x_rows, y_columns, index);
    }

    public int getIndex() {
        return index;
    }

    public GamePlayerInfo getCurrentGamePlayerInfo() {
        return currentGamePlayerInfo;
    }

    public void setCurrentGamePlayerInfo(GamePlayerInfo currentGamePlayerInfo) {
        this.currentGamePlayerInfo = currentGamePlayerInfo;
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

}
