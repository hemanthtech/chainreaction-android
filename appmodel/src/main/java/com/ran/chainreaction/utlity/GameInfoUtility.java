package com.ran.chainreaction.utlity;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

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
 *
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


    public static String generateGameName(Context context) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date(System.currentTimeMillis()));
    }


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


    public static ArrayList<GameCellInfo> generateGameCellInfo(Context context, int x_row, int y_row, GamePlayerInfo playerInfo) {
        ArrayList<GameCellInfo> gameCellInfos = new ArrayList<>();

        for (int k = 0; k < x_row * y_row; k++) {
            gameCellInfos.add(new GameCellInfo(k, playerInfo, x_row, y_row));
        }
        return gameCellInfos;
    }
}
