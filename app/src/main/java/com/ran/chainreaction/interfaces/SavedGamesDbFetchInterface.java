package com.ran.chainreaction.interfaces;

import com.ran.chainreaction.entities.SavedGamesEntity;

import java.util.ArrayList;

/**
 * Created by ranjith on 18/12/15.
 *
 * Interface between Presenter and View
 *
 * @see com.ran.chainreaction.activities.SavedGamesActivity
 * @see com.ran.chainreaction.presenters.SavedGamesDbPresenter
 */
public interface SavedGamesDbFetchInterface {

    /**
     * Call Back to UI screen , giving the Saved Games Arraylist
     *
     * @param savedGamesEntities -- list of saved Games
     */
    void showAllSavedGames(ArrayList<SavedGamesEntity> savedGamesEntities);

    /**
     * Call back to show progress on UI
     *
     * @param show -- true/false
     */
    void showProgressBar(boolean show);

}
