package com.ran.chainreaction.presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.ran.chainreaction.database.ChainReactionDBOpsHelper;
import com.ran.chainreaction.entities.SavedGamesEntity;
import com.ran.chainreaction.interfaces.SavedGamesDbFetchInterface;

import java.util.ArrayList;

/**
 * Created by ranjith on 18/12/15.
 * <p/>
 * Saved Game Presenter used to retrieve all the Games in DB and Pass to Recycler
 */
public class SavedGamesDbPresenter {

    AsyncTask<Context, Void, ArrayList<SavedGamesEntity>> fetchAsyncTask;
    private Context context;
    private SavedGamesDbFetchInterface fetchInterface;
    private ArrayList<SavedGamesEntity> savedGamesEntities;

    public SavedGamesDbPresenter(Context context, SavedGamesDbFetchInterface dbFetchInterface) {
        this.context = context;
        this.fetchInterface = dbFetchInterface;
    }

    public void start() {
        fetchInterface.showProgressBar(true);
        fetchAsyncTask = new AsyncTask<Context, Void, ArrayList<SavedGamesEntity>>() {
            @Override
            protected ArrayList<SavedGamesEntity> doInBackground(Context... params) {
                savedGamesEntities = ChainReactionDBOpsHelper.getDBInstance(params[0]).retrieveGamesFromDB();
                return savedGamesEntities;
            }

            @Override
            protected void onPostExecute(ArrayList<SavedGamesEntity> savedGamesEntities) {
                fetchInterface.showProgressBar(false);
                fetchInterface.showAllSavedGames(savedGamesEntities);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                fetchInterface.showProgressBar(false);
            }
        };
        //Start the Async Task here ..
        fetchAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, context);
    }

    public void stop() {
        if (fetchAsyncTask != null) {
            fetchAsyncTask.cancel(true);
        }
    }
}
