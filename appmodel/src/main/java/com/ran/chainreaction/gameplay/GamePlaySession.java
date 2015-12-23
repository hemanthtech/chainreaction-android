package com.ran.chainreaction.gameplay;

import com.ran.chainreaction.entities.BombValues;
import com.ran.chainreaction.entities.GridSizeValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranjith on 06/12/15.
 * <p/>
 * Class that Maintains the Current Play Session for Game
 */
public class GamePlaySession {

  /**
   * List of Players playing this game
   */
  private List<GamePlayerInfo> gamePlayerInfos;

  /**
   * Current Player playing this Game
   */
  private GamePlayerInfo currentPlayer;

  /**
   * Type pf Grid used for this Game [Small,Middle,Large]
   */
  private GridSizeValues playerGridType;

  /**
   * Type of Bomb Used for this Game[Circle ,Triangle or Square]
   */
  private BombValues gameBombType;

  /**
   * Current Game X, y boxes to be drawn based on GridSize Value
   */
  private GameSizeBoxInfo gameSizeBoxInfo;

  /**
   * Array list holding all the Info of the Game Cell's
   */
  private ArrayList<GameCellInfo> gameCellInfos;


  public GamePlaySession(List<GamePlayerInfo> gamePlayerInfos, GamePlayerInfo currentPlayer,
                         GridSizeValues playerGridType, BombValues gameBombType,
                         GameSizeBoxInfo gameSizeBoxInfo,
                         ArrayList<GameCellInfo> gameCellInfos) {

    this.gameBombType = gameBombType;
    this.gamePlayerInfos = gamePlayerInfos;
    this.gameSizeBoxInfo = gameSizeBoxInfo;
    this.gameCellInfos = gameCellInfos;
    this.currentPlayer = currentPlayer;
    this.playerGridType = playerGridType;
  }


  public List<GamePlayerInfo> getGamePlayerInfos() {
    return gamePlayerInfos;
  }

  public void setGamePlayerInfos(ArrayList<GamePlayerInfo> gamePlayerInfos) {
    this.gamePlayerInfos = gamePlayerInfos;
  }

  public GamePlayerInfo getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(GamePlayerInfo currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public ArrayList<GameCellInfo> getGameCellInfos() {
    return gameCellInfos;
  }

  public void setGameCellInfos(ArrayList<GameCellInfo> gameCellInfos) {
    this.gameCellInfos = gameCellInfos;
  }

  public GameSizeBoxInfo getGameSizeBoxInfo() {
    return gameSizeBoxInfo;
  }

  public void setGameSizeBoxInfo(GameSizeBoxInfo gameSizeBoxInfo) {
    this.gameSizeBoxInfo = gameSizeBoxInfo;
  }

  public GridSizeValues getPlayerGridType() {
    return playerGridType;
  }

  public void setPlayerGridType(GridSizeValues playerGridType) {
    this.playerGridType = playerGridType;
  }

  public BombValues getGameBombType() {
    return gameBombType;
  }

  public void setGameBombType(BombValues gameBombType) {
    this.gameBombType = gameBombType;
  }
}
