package com.ran.chainreaction.gameplay;

import java.io.Serializable;

/**
 * Created by ranjith on 03/12/15.
 * <p/>
 * Class responsible for giving the GameSize Box i.e x ,y boxes count
 */
public class GameSizeBoxInfo implements Serializable {

    private int x_boxes = 0;
    private int y_boxes = 0;

    public GameSizeBoxInfo(int x_boxes, int y_boxes) {
        this.x_boxes = x_boxes;
        this.y_boxes = y_boxes;
    }


    public int getX_boxes() {
        return x_boxes;
    }

    public int getY_boxes() {
        return y_boxes;
    }

}
