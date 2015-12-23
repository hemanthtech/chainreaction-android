package com.ran.chainreaction.gameplay;

import android.os.AsyncTask;
import android.util.Log;

import com.ran.chainreaction.interfaces.GameAsyncBlastObserver;
import com.ran.chainreaction.interfaces.GameStateObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ranjith on 13/12/15.
 * <p/>
 * Class that holds the Current GamePlayLogic
 * a) Update the Current Player
 * b) Update the GameCellInfo for each GameArena Orb for sync with Current Session
 * c) Process any Blast of orb and propagates to UI
 */
public class GamePlayLogic implements GameAsyncBlastObserver {

  private static final Object LOCK = new Object();
  private static final String TAG = GamePlayLogic.class.getSimpleName();
  private static GamePlayLogic gamePlayLogic;
  private LinkedList<GameCellInfo> gamePendingMoves;
  private List<GameStateObserver> gameStateObservers;
  private GamePlaySession gamePlaySession;
  private GamePlayAsyncTask gamePlayAsyncTask;

  private GamePlayLogic() {
    gamePendingMoves = new LinkedList<>();
    gameStateObservers = new ArrayList<>();
  }

  public static GamePlayLogic getGameInstance() {
    synchronized (LOCK) {
      if (gamePlayLogic == null) {
        gamePlayLogic = new GamePlayLogic();
      }
    }
    return gamePlayLogic;
  }

  /**
   * Utility Method to retrieve the Current Game Session
   *
   * @return -- Current Game Session
   */
  public GamePlaySession getGamePlaySession() {
    return gamePlaySession;
  }

  /**
   * Utility Method to set the Current GamePlaySession ..
   *
   * @param gamePlaySession -- Current Game Play Session
   */
  public void setGamePlaySession(GamePlaySession gamePlaySession) {
    this.gamePlaySession = gamePlaySession;
  }

  /**
   * Method to reset the Game [Nullify the GamePlayLogic , Observers and Current Game Play Session]
   */
  public void resetGame() {
    synchronized (LOCK) {
      if (gamePlayAsyncTask != null && gamePlayAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
        gamePlayAsyncTask.cancel(true);
        gamePlayAsyncTask = null;
      }
      gamePlayLogic = null;
      gameStateObservers = null;
      gamePlaySession = null;
    }
  }

  /**
   * Method to attach the Observers to be noted on Updates
   *
   * @param gameStateObserver GameStateObserver Implementation [View,Activity etc]
   */
  public void attach(GameStateObserver gameStateObserver) {
    gameStateObservers.add(gameStateObserver);
  }


  /**
   * Method call to change the Game Turn ..
   *
   * @param gameCellInfo -- gameCellInfo changed
   */
  public void changeGameTurn(GameCellInfo gameCellInfo) {
    //Update data in the Game Play Sessions..
    gamePlaySession.getGameCellInfos().set(gameCellInfo.getIndex(), gameCellInfo);
    notifyCurrentPlayerUpdates();
  }


  // ------------------------ All Private Methods /Helpers are defined Below --------------------

  /**
   * Method call to process the Orb /Bomb Blast
   * a) Update the passed gameCellInfo to the Current Session
   * b) Notify all Observers , that click is false
   * c) Start the Async Task , that handles the GamePending Moves /Blast to be done
   *
   * @param gameCellInfo -- Index of the GameCellInfo to be processed ..
   */

  public void blastOrb(GameCellInfo gameCellInfo) {

    gamePlaySession.getGameCellInfos().set(gameCellInfo.getIndex(), gameCellInfo);
    notifyClickStates(false);
    gamePendingMoves.add(gamePlaySession.getGameCellInfos().get(gameCellInfo.getIndex()));
    gamePlayAsyncTask = new GamePlayAsyncTask(this, gamePlaySession);
    gamePlayAsyncTask.execute(gamePendingMoves);
  }


  /**
   * Utility Method to Notify Current Game Cell Info Updates
   *
   * @param index        -- Index to be updated
   * @param gameCellInfo -- Updated GameCellInfo
   */
  private void notifyGameCellInfoUpdates(int index, GameCellInfo gameCellInfo) {
    Log.d(TAG, "Notify called : " + index + " game Cell Info : " + gameCellInfo.getCurrentCount());
    gamePlaySession.getGameCellInfos().set(index, gameCellInfo);
    for (GameStateObserver gameStateObserver : gameStateObservers) {
      gameStateObserver.updateGameCellInfo(index, gameCellInfo);
    }
  }

  /**
   * Utility method to Notify the CLick States of Orbs/Container holding it
   *
   * @param clickable -- Is it Clickable
   */
  private void notifyClickStates(boolean clickable) {
    for (GameStateObserver gameStateObserver : gameStateObservers) {
      gameStateObserver.updateClickableState(clickable);
    }
  }

  /**
   * Utility to update the Current Player ..
   */
  private void notifyCurrentPlayerUpdates() {
    int index_new_player = (gamePlaySession.getCurrentPlayer().getPlayerIndex() + 1) %
        gamePlaySession.getGamePlayerInfos().size();

    gamePlaySession.setCurrentPlayer(gamePlaySession.getGamePlayerInfos().get(index_new_player));

    for (GameStateObserver gameStateObserver : gameStateObservers) {
      gameStateObserver.updateGameTurnState(gamePlaySession.getCurrentPlayer());
    }
  }

  /**
   * Method to update the Game Win Status to UI
   */
  private void notifyGameWinStatus() {
    for (GameStateObserver gameStateObserver : gameStateObservers) {
      gameStateObserver.updatePlayerWinStatus(gamePlaySession.getCurrentPlayer());
    }
  }

  /**
   * Utility Method to say , Current Player has won ..Hurray
   *
   * @return -- Is Current Player Won ?
   */
  private boolean isCurrentPlayerWon() {
    boolean isCurrentPlayerWon = true;

    for (GameCellInfo gameCellInfo : gamePlaySession.getGameCellInfos()) {
      if (gameCellInfo.getGamePlayerInfo() != null &&
          gameCellInfo.getGamePlayerInfo() != gamePlaySession.getCurrentPlayer()) {
        isCurrentPlayerWon = false;
        break;
      }
    }
    return isCurrentPlayerWon;
  }

  /**
   * Method to update the Player Win Status Dialog ..
   */
  private void updatePlayerWonDialog() {
    if (isCurrentPlayerWon()) {
      notifyGameWinStatus();
    } else {
      notifyCurrentPlayerUpdates();
      notifyClickStates(true);
    }
  }

  // ------------------------- Call Backs from GameAsyncBlastObserver ------------------------ //
  @Override
  public void updateGamePlaySession(GamePlaySession gamePlaySession) {
    this.gamePlaySession = gamePlaySession;
    updatePlayerWonDialog();
  }

  @Override
  public void updateGameCellProgress(GameCellInfo gameCellInfo) {
    notifyGameCellInfoUpdates(gameCellInfo.getIndex(), gameCellInfo);
  }
}
