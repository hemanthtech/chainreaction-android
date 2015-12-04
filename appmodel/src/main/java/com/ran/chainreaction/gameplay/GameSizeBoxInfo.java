package com.ran.chainreaction.gameplay;

/**
 * Created by ranjith on 03/12/15.
 *
 * Class responsible for giving the GameSize Box i.e x ,y boxes count and the dimens of each box
 */
public class GameSizeBoxInfo {

    private int x_boxes;
    private int y_boxes;
    private int box_dimen;

    public GameSizeBoxInfo(int x_boxes, int y_boxes, int box_dimen) {
        this.x_boxes = x_boxes;
        this.y_boxes = y_boxes;
        this.box_dimen = box_dimen;
    }


    public int getX_boxes() {
        return x_boxes;
    }

    public int getY_boxes() {
        return y_boxes;
    }

    public int getBox_dimen() {
        return box_dimen;
    }

}
