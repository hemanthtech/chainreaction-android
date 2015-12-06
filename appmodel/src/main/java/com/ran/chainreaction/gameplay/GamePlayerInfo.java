package com.ran.chainreaction.gameplay;

import com.ran.chainreaction.entities.PlayColorValues;

import java.io.Serializable;

/**
 * Created by ranjith on 06/12/15.
 * <p/>
 * Class that holds the PlayerInfo's for a Game Session ..
 */
public class GamePlayerInfo implements Serializable {

    private PlayColorValues playerColor;
    private int playerIndex;
    private String playerName;

    public GamePlayerInfo(PlayColorValues playColor, int playerIndex, String playerName) {
        this.playerIndex = playerIndex;
        this.playerName = playerName;
        this.playerColor = playColor;
    }
}
