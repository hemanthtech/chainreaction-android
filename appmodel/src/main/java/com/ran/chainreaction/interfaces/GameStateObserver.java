package com.ran.chainreaction.interfaces;

import com.ran.chainreaction.gameplay.GameCellInfo;
import com.ran.chainreaction.gameplay.GamePlayerInfo;

/**
 * Created by ranjith on 13/12/15.
 *
 * Interface between Game Play Session and Who wants to listen the Current Game ..
 */
public interface GameStateObserver {

    /**
     * Observer Call Back to say , Game Turn is changed
     *
     * @param gamePlayerInfo -- Current Game Turn Player
     */
    void updateGameTurnState(GamePlayerInfo gamePlayerInfo);

    /**
     * Observer Call Back to say , Whether the View is Clickable or not ..
     *
     * @param isClickable -- Whether View is clickable or not
     */
    void updateClickableState(boolean isClickable);

    /**
     * Observer Call Back from Game Play logic ..
     *
     * @param index        -- Index to be updated ..
     * @param gameCellInfo -- GameCellInfo for Cell
     */

    void updateGameCellInfo(int index, GameCellInfo gameCellInfo);

}
