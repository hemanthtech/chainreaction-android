package com.ran.chainreaction.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.chainreaction.R;
import com.ran.chainreaction.gameplay.GamePlayerInfo;

/**
 * Created by ranjith on 15/12/15.
 * <p/>
 * Utility Class to create the Dialog saying the Current Player has Won ..
 */
public class GameWinDialogCreator {

  private static AlertDialog alertDialog;

  /**
   * Utility Method to create the Game Win Dialog
   *
   * @param context    -- Context of the Screen
   * @param playerInfo -- PlayerInfo who Won
   */
  public static void createDialog(final GameWinCallBacks gameWinCallBacks, GamePlayerInfo
      playerInfo, Context context) {
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LinearLayout parentLayout =
        (LinearLayout) layoutInflater.inflate(R.layout.dialog_game_win, null);

    Button Exit = (Button) parentLayout.findViewById(R.id.game_win_player_exit);
    Exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        alertDialog.dismiss();
        gameWinCallBacks.onGameWinExitClick();
      }
    });
    Button Restart = (Button) parentLayout.findViewById(R.id.game_win_player_restart);
    Restart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        alertDialog.dismiss();
        gameWinCallBacks.onGameWinRestartClick();
      }
    });
    TextView WinMessage = (TextView) parentLayout.findViewById(R.id.game_win_player);
    String text = String.format(context.getResources().getString(R.string.player_win_message),
        playerInfo.getPlayerName());
    WinMessage.setText(text);

    AlertDialog.Builder mBuilder =
        new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            .setView(parentLayout)
            .setCancelable(false);
    alertDialog = mBuilder.create();
    alertDialog.show();
  }

  public interface GameWinCallBacks {

    /**
     * Callback for the Game Win Dialog [Exit Click]
     */
    void onGameWinExitClick();

    /**
     * Callback for the Game Win Dialog [Restart Dialog]
     */
    void onGameWinRestartClick();
  }
}
