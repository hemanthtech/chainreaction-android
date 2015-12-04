package com.ran.chainreaction.interfaces;

/**
 * Created by ranjith on 02/12/15.
 * <p/>
 * Interface responsible for Callback to Activity from Recycler Adapter[SavedGames Screen]
 */
public interface SavedGamesSelectionInterface {

    /**
     * Method to say which View is Selected in RecyclerAdapter
     *
     * @param isSelect -- View is Selected /not
     * @param position -- Position of the view ..
     */
    void onGameSelectionChanged(boolean isSelect, int position);

    /**
     * Method to say that game is deleted from Recycler Adapter
     *
     * @param position -- View that is deleted
     */
    void onGameDeleted(int position);
}
