package com.ran.chainreaction.gameplay;

import com.ran.chainreaction.entities.GameBombDirections;
import com.ran.chainreaction.interfaces.GameStateObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ranjith on 13/12/15.
 * <p/>
 * Class that holds the Current GamePlayLogic
 */
public class GamePlayLogic {

    private static final Object LOCK = new Object();
    private static GamePlayLogic gamePlayLogic;
    private LinkedList<GameCellInfo> gamePendingMoves;
    private List<GameStateObserver> gameStateObservers;
    private GamePlaySession gamePlaySession;

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
        int index_new_player = (gamePlaySession.getCurrentPlayer().getPlayerIndex() + 1) %
            gamePlaySession.getGamePlayerInfos().size();

        gamePlaySession.setCurrentPlayer(gamePlaySession.getGamePlayerInfos().get(index_new_player));
        for (GameStateObserver gameStateObserver : gameStateObservers) {
            gameStateObserver.updateGameTurnState(gamePlaySession.getCurrentPlayer());
        }

        //Update data in the Game Play Sessions..
        gamePlaySession.getGameCellInfos().set(gameCellInfo.getIndex(), gameCellInfo);
    }

    /**
     * Method call to process the Orb /Bomb Blast
     *
     * @param gameCellInfo -- Index of the GameCellInfo to be processed ..
     */

    public synchronized void blastOrb(GameCellInfo gameCellInfo) {

        //Update data in the Game Play Sessions..
        gamePlaySession.getGameCellInfos().set(gameCellInfo.getIndex(), gameCellInfo);

        //Make Sure , No click is  possible When Blast happens and is in processing  ..
        for (GameStateObserver gameStateObserver : gameStateObservers) {
            gameStateObserver.updateClickableState(false);
        }

        gamePendingMoves.add(gameCellInfo);
        processPendingMoves();

        //Make Sure , Click is possible after processing ..
        for (GameStateObserver gameStateObserver : gameStateObservers) {
            gameStateObserver.updateClickableState(true);
        }
    }

    /**
     * Method to process the PendingMoves ..
     */
    private synchronized void processPendingMoves() {
        while (gamePendingMoves.size() > 0) {
            GameCellInfo cellInfo = gamePendingMoves.pop();

            for (GameBombDirections directions : cellInfo.getPossibleDirections()) {
                //Todo[ranjith.suda] Process the calls
            }
        }
    }


}
