package com.ran.chainreaction.utils;

/**
 * Created by ranjith on 12/11/15.
 * <p/>
 * Interface for holding constants of Chain Reaction game
 */
public interface ChainReactionConstants {

  String NEW_LINE = "\n";

  String SPACE = " ";

  String COLON = ":";

  String DASH = "-";

  /**
   * Key to  determine whether Game is  ONLINE Game or not
   */
  String ONLINE_GAME_KEY = "online_game";

  /**
   * Key to determine whether Game is Saved Game or not ..
   */
  String SAVED_GAME_KEY = "saved_game";

  /**
   * Key to pass the GameId from Saved DB to Game Screen ..
   */
  String SAVED_GAME_ID_KEY = "saved_game_id";
}
