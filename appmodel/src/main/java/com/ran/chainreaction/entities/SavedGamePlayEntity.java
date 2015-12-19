package com.ran.chainreaction.entities;

import com.ran.chainreaction.gameplay.GamePlaySession;

/**
 * Created by ranjith on 20/12/15.
 * <p/>
 * Entity Model for passing Saved Game UI required from DB
 */
public class SavedGamePlayEntity {

    GamePlaySession savedGamePlaySession;
    String savedGameName;
    long savedGameTimeElapsed;

    public SavedGamePlayEntity(GamePlaySession gameSession, String name, long time) {
        this.savedGameName = name;
        this.savedGamePlaySession = gameSession;
        this.savedGameTimeElapsed = time;
    }

    public GamePlaySession getSavedGamePlaySession() {
        return savedGamePlaySession;
    }

    public void setSavedGamePlaySession(GamePlaySession savedGamePlaySession) {
        this.savedGamePlaySession = savedGamePlaySession;
    }

    public String getSavedGameName() {
        return savedGameName;
    }

    public void setSavedGameName(String savedGameName) {
        this.savedGameName = savedGameName;
    }

    public long getSavedGameTimeElapsed() {
        return savedGameTimeElapsed;
    }

    public void setSavedGameTimeElapsed(long savedGameTimeElapsed) {
        this.savedGameTimeElapsed = savedGameTimeElapsed;
    }
}
