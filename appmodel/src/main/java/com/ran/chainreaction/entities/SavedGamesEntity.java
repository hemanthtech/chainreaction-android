package com.ran.chainreaction.entities;

import com.ran.chainreaction.gameplay.GamePlayerInfo;

import java.util.ArrayList;

/**
 * Created by ranjith on 17/12/15.
 * <p/>
 * This Entity is used to structure the DB Content Values in RecyclerView of Saved Games UI
 */
public class SavedGamesEntity {

  private String gameName;
  private long gameId;
  private ArrayList<GamePlayerInfo> gamePlayerInfos;


  public SavedGamesEntity(String gameName, long gameId, ArrayList<GamePlayerInfo> gamePlayerInfos) {
    this.gameId = gameId;
    this.gameName = gameName;
    this.gamePlayerInfos = gamePlayerInfos;
  }

  public String getGameName() {
    return gameName;
  }

  public long getGameId() {
    return gameId;
  }

  public ArrayList<GamePlayerInfo> getGamePlayerInfos() {
    return gamePlayerInfos;
  }

}
