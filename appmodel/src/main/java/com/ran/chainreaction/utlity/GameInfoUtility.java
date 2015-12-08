package com.ran.chainreaction.utlity;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.ran.chainreaction.entities.GameBombDirections;
import com.ran.chainreaction.entities.GridSizeValues;
import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.gameplay.GameSizeBoxInfo;
import com.ran.chainreactionmodel.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ranjith on 03/12/15.
 * <p/>
 * Utility File responsible for Game Play
 */
public class GameInfoUtility {

    /**
     * Method to return the Game Info Boxes , based on the width , height and GridSizeType
     *
     * @param context        -- App context
     * @param gridSizeValues -- Current GridSize
     * @param width          -- width of screen available for drawing
     * @param height         -- height of screen available for drawing
     * @return -- Current GameSizeBox instance
     */
    public static GameSizeBoxInfo generateGameGridSizes(Context context, GridSizeValues gridSizeValues, int width, int height) {

        int grid_box_size = 0;
        switch (gridSizeValues) {
            case SMALL:
                grid_box_size = context.getResources().getDimensionPixelSize(R.dimen.game_size_small_box_dimen);
                break;
            case MEDIUM:
                grid_box_size = context.getResources().getDimensionPixelSize(R.dimen.game_size_medium_box_dimen);
                break;
            case LARGE:
                grid_box_size = context.getResources().getDimensionPixelSize(R.dimen.game_size_large_box_dimen);
                break;
        }

        int y_boxes = width / grid_box_size;
        int x_boxes = height / grid_box_size;

        return new GameSizeBoxInfo(x_boxes, y_boxes);
    }

    /**
     * Method to return the time Elapsed in game , in Readable format ..
     *
     * @param timeElapsed -- ElapsedTime ..
     * @return Readable Time Format
     */
    public static String generateTimeFormat(long timeElapsed) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeElapsed),
            TimeUnit.MILLISECONDS.toMinutes(timeElapsed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeElapsed)),
            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed)));
    }

    /**
     * Utility to generate the Game Name
     *
     * @param context -- Context of Application
     * @return -- Name of the Game
     */

    public static String generateGameName(Context context) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date(System.currentTimeMillis()));
    }


    /**
     * Utility to generate the GameSizeBox Info
     *
     * @param context        -- Context of Application
     * @param gridSizeValues -- GridSize of the Game
     * @return -- GameSizeBox of the Current Game
     */
    public static GameSizeBoxInfo generateGameSizeBoxInfo(Context context, GridSizeValues gridSizeValues) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x - 2 * context.getResources().getDimensionPixelSize(R.dimen.game_screen_margin);
        int height = point.y - context.getResources().getDimensionPixelSize(R.dimen.game_screen_statusbar_size)
            + context.getResources().getDimensionPixelSize(R.dimen.game_screen_headerLayout);

        return GameInfoUtility.generateGameGridSizes(context, gridSizeValues, width, height);
    }


    /**
     * Utility to generate the Initial GameCellInfo Array list
     *
     * @param context    -- Context of the App
     * @param x_row      -- No of X rows
     * @param y_row      -- No of y Columns
     * @param playerInfo -- Initial PlayerInfo
     * @return - ArrayList of GameCellInfo's
     */
    public static ArrayList<GameCellInfo> generateGameCellInfo(Context context, int x_row, int y_row, GamePlayerInfo playerInfo) {
        ArrayList<GameCellInfo> gameCellInfos = new ArrayList<>();

        for (int k = 0; k < x_row * y_row; k++) {
            gameCellInfos.add(new GameCellInfo(k, playerInfo, x_row, y_row));
        }
        return gameCellInfos;
    }

    /**
     * Utility to generate the Possible Directions.
     */
    public static ArrayList<GameBombDirections> generatePossibleDirections(int x_rows, int y_columns, int index) {

        ArrayList<GameBombDirections> possibleDirections = new ArrayList<>();
        /**
         * Case a) Handle all the corner cases [Max Capacity 1] -- 4 cases
         * Case b) Handle all the border cases [Max Capacity 2] -- 4 rows
         * Case c) Handle all the rest cases [Max Capacity 3] -- Rest all
         */
        if (index == 0) { //[0,0]
            possibleDirections.add(GameBombDirections.RIGHT);
            possibleDirections.add(GameBombDirections.BOTTOM);
        } else if (index == y_columns - 1) { // [0,y-1]
            possibleDirections.add(GameBombDirections.LEFT);
            possibleDirections.add(GameBombDirections.BOTTOM);
        } else if (index == (x_rows * y_columns) - y_columns) {//[x-1,0]
            possibleDirections.add(GameBombDirections.TOP);
            possibleDirections.add(GameBombDirections.RIGHT);
        } else if (index == (x_rows * y_columns) - 1) {//[x-1,y-1]
            possibleDirections.add(GameBombDirections.TOP);
            possibleDirections.add(GameBombDirections.LEFT);
        } else if (index >= 0 && index < y_columns) {// Top row
            possibleDirections.add(GameBombDirections.RIGHT);
            possibleDirections.add(GameBombDirections.BOTTOM);
            possibleDirections.add(GameBombDirections.LEFT);
        } else if (index < x_rows * y_columns && index >= y_columns * (x_rows - 1)) { // Bottom row
            possibleDirections.add(GameBombDirections.RIGHT);
            possibleDirections.add(GameBombDirections.TOP);
            possibleDirections.add(GameBombDirections.LEFT);
        } else if (index % y_columns == 0) {// Left Row
            possibleDirections.add(GameBombDirections.RIGHT);
            possibleDirections.add(GameBombDirections.BOTTOM);
            possibleDirections.add(GameBombDirections.TOP);
        } else if ((index + 1) % y_columns == 0) { // Right Row
            possibleDirections.add(GameBombDirections.TOP);
            possibleDirections.add(GameBombDirections.BOTTOM);
            possibleDirections.add(GameBombDirections.LEFT);
        } else {
            possibleDirections.add(GameBombDirections.TOP);
            possibleDirections.add(GameBombDirections.RIGHT);
            possibleDirections.add(GameBombDirections.BOTTOM);
            possibleDirections.add(GameBombDirections.LEFT);
        }

        return possibleDirections;
    }

}
