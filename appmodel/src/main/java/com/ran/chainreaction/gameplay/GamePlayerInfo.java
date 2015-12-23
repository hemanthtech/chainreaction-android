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

  public PlayColorValues getPlayerColor() {
    return playerColor;
  }

  public void setPlayerColor(PlayColorValues playerColor) {
    this.playerColor = playerColor;
  }

  public int getPlayerIndex() {
    return playerIndex;
  }

  public void setPlayerIndex(int playerIndex) {
    this.playerIndex = playerIndex;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public boolean equals(Object o) {
    GamePlayerInfo objParm = (GamePlayerInfo) o;
    return objParm.getPlayerIndex() == playerIndex &&
        objParm.getPlayerName().equalsIgnoreCase(playerName);
  }
}
