package com.ran.chainreaction.utlity;

import android.content.Context;

import com.ran.chainreaction.entities.GridSizeValues;
import com.ran.chainreaction.gameplay.GameSizeBoxes;
import com.ran.chainreactionmodel.R;

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
    public static GameSizeBoxes generateGameGridSizes(Context context, GridSizeValues gridSizeValues, int width, int height) {

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

        int x_boxes = width / grid_box_size;
        int y_boxes = height / grid_box_size;

        return new GameSizeBoxes(x_boxes, y_boxes, grid_box_size);
    }
}
