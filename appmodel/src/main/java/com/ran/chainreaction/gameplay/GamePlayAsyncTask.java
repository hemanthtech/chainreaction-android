package com.ran.chainreaction.gameplay;

import android.os.AsyncTask;
import android.util.Log;

import com.ran.chainreaction.entities.GameBombDirections;
import com.ran.chainreaction.interfaces.GameAsyncBlastObserver;
import com.ran.chainreaction.utlity.GameInfoUtility;

import java.util.LinkedList;

/**
 * Created by ranjith.suda on 12/23/2015.
 * <p/>
 * Class Responsible for Doing Async Task Operation of the Game , It does start a background
 * Task , that checks each Pending Moves and iterates till the Pending move is EMpty
 */
public class GamePlayAsyncTask
    extends AsyncTask<LinkedList<GameCellInfo>, GameCellInfo, GamePlaySession> {

  private GamePlaySession gamePlaySession;
  private GameAsyncBlastObserver gameAsyncBlastObserver;
  private static final int ZER0 = 0;
  private final String TAG = GamePlayAsyncTask.class.getSimpleName();
  private final int SLEEP_INTERVAL_MILLIS = 200;

  public GamePlayAsyncTask(GameAsyncBlastObserver gameAsyncBlastObserver, GamePlaySession
      gamePlaySession) {
    this.gameAsyncBlastObserver = gameAsyncBlastObserver;
    this.gamePlaySession = gamePlaySession;
  }

  @Override
  protected GamePlaySession doInBackground(LinkedList<GameCellInfo>... params) {
    LinkedList<GameCellInfo> gamePendingMoves = params[0];
    while (gamePendingMoves.size() > 0 && !isCancelled()) {
      GameCellInfo cellInfo = gamePendingMoves.remove();
      if (ZER0 < cellInfo.getCurrentCount() &&
          cellInfo.getCurrentCount() <= cellInfo.getMAX_CAPACITY()) {
        publishProgress(cellInfo);
      } else {
        int blastedCount = 0;
        //Blast process ..
        for (GameBombDirections directions : cellInfo.getPossibleDirections()) {
          GameCellInfo processedCell = null;
          switch (directions) {
            case LEFT:
              processedCell = gamePlaySession.getGameCellInfos().
                  get(GameInfoUtility.generateChildOrbIndex(cellInfo, GameBombDirections.LEFT));
              processedCell.setCurrentCount(processedCell.getCurrentCount() + 1);
              processedCell.setGamePlayerInfo(gamePlaySession.getCurrentPlayer());
              blastedCount++;
              break;
            case RIGHT:
              processedCell = gamePlaySession.getGameCellInfos().
                  get(GameInfoUtility.generateChildOrbIndex(cellInfo, GameBombDirections.RIGHT));
              processedCell.setCurrentCount(processedCell.getCurrentCount() + 1);
              processedCell.setGamePlayerInfo(gamePlaySession.getCurrentPlayer());
              blastedCount++;
              break;
            case TOP:
              processedCell = gamePlaySession.getGameCellInfos().
                  get(GameInfoUtility.generateChildOrbIndex(cellInfo, GameBombDirections.TOP));
              processedCell.setCurrentCount(processedCell.getCurrentCount() + 1);
              processedCell.setGamePlayerInfo(gamePlaySession.getCurrentPlayer());
              blastedCount++;
              break;
            case BOTTOM:
              processedCell = gamePlaySession.getGameCellInfos().
                  get(GameInfoUtility.generateChildOrbIndex(cellInfo, GameBombDirections.BOTTOM));
              processedCell.setCurrentCount(processedCell.getCurrentCount() + 1);
              processedCell.setGamePlayerInfo(gamePlaySession.getCurrentPlayer());
              blastedCount++;
              break;
          }

          gamePlaySession.getGameCellInfos().set(processedCell.getIndex(), processedCell);
          //First Remove any of them are there and Add at same location..
          if (!gamePendingMoves.contains(
              gamePlaySession.getGameCellInfos().get(processedCell.getIndex()))) {
            gamePendingMoves.add(gamePlaySession.getGameCellInfos().get(processedCell.getIndex()));
          }
        }
        //Update the Current Cell Info Count ..
        cellInfo.setCurrentCount(cellInfo.getCurrentCount() - blastedCount);
        if (cellInfo.getCurrentCount() > 0) {
          cellInfo.setGamePlayerInfo(gamePlaySession.getCurrentPlayer());
        } else {
          cellInfo.setGamePlayerInfo(null);
        }
        publishProgress(cellInfo);
      }
      sleepCurrentThread(); // Sleep before Next Operation Takes Place..
    }
    return gamePlaySession;
  }

  @Override
  protected void onPostExecute(GamePlaySession gamePlaySession) {
    gameAsyncBlastObserver.updateGamePlaySession(gamePlaySession);
  }

  @Override
  protected void onProgressUpdate(GameCellInfo... values) {
    gameAsyncBlastObserver.updateGameCellProgress(values[0]);
  }

  @Override
  protected void onCancelled(GamePlaySession gamePlaySession) {
    //Task is cancelled , here
    gameAsyncBlastObserver.updateGamePlaySession(null);
  }

  private void sleepCurrentThread() {
    try {
      Thread.currentThread().sleep(SLEEP_INTERVAL_MILLIS);
    } catch (InterruptedException e) {
      Log.d(TAG, e.toString());
    }
  }
}
