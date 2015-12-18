package com.ran.chainreaction.interfaces;

/**
 * Created by ranjith on 02/12/15.
 * <p/>
 * Interface responsible for Callback to Activity from Recycler Adapter[SavedGames Screen]
 */
public interface SavedGamesSelectionInterface {

    /**
     * Call Back to Activity for saying GameId Selected
     *
     * @param gameId -- Selected Game Id
     */
    void onGameSelection(long gameId);

    /**
     * Call Back to Activity saying All Games are Deleted
     */
    void onAllGamesDeleted();
}
