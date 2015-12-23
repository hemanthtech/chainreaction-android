package com.ran.chainreaction.interfaces;

import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlaySession;

/**
 * Created by ranjith.suda on 12/23/2015.
 *
 * Interface for Giving CallBacks from PendingMoves AsyncTask to GamePlayLogic
 */
public interface GameAsyncBlastObserver {

  /**
   * Update the game Play Session after  Pending moves are done ..
   *
   * @param gamePlaySession -- Current GamePlaySession
   */
  void updateGamePlaySession(GamePlaySession gamePlaySession);

  /**
   * Method to update the Intermediate GameCellInfo to UI Thread for Updating the Orbs
   *
   * @param gameCellInfo -- Updated Game Cell Info
   */
  void updateGameCellProgress(GameCellInfo gameCellInfo);
}
