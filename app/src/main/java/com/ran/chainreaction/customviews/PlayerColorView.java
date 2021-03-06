package com.ran.chainreaction.customviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.NoPlayerValues;
import com.ran.chainreaction.entities.PlayColorValues;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.utils.ChainReactionPreferences;
import com.ran.chainreaction.utils.CommonUtils;
import com.ran.chainreaction.utlity.GamePreferenceUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ranjith on 29/11/15.
 * <p/>
 * Class that shows the Player Colors based on No of the Players selected ..
 */
public class PlayerColorView extends LinearLayout
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  private final int MAX_IN_ROW = 4;
  private final int CHILD_HOLDER_WEIGHT_SUM = 4;
  private final int PARENT_HOLDER_WEIGHT_SUM = 2;

  private final float PLAYER_DISABLED_ALPHA = 0.25f;
  private final float PLAYER_ENABLED_ALPHA = 1.0f;
  private final int MAX_PLAYERS_SUPPORTED = 8;
  private ArrayList<TextView> playerReferenceHolders;

  public PlayerColorView(Context context) {
    super(context);
    initView();
  }

  public PlayerColorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public PlayerColorView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }


  /**
   * Method to Initialize the View ..
   */
  private void initView() {

    setOrientation(VERTICAL);
    setWeightSum(PARENT_HOLDER_WEIGHT_SUM);
    playerReferenceHolders = new ArrayList<>(MAX_PLAYERS_SUPPORTED);

    //First Linear Layout ..
    LinearLayout firstChildHolder = new LinearLayout(getContext());
    firstChildHolder.setOrientation(HORIZONTAL);
    firstChildHolder.setWeightSum(CHILD_HOLDER_WEIGHT_SUM);

    for (int i = 1; i <= MAX_IN_ROW; i++) {

      TextView textView = new TextView(getContext());
      if (i == 1) {
        textView.setText(getResources().getString(R.string.player_color_self_view));
      } else {
        textView.setText(getResources().getString(R.string.player_color_other_view) + i);
      }
      textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
      textView.setAllCaps(true);
      textView.setGravity(Gravity.CENTER);
      textView.setTypeface(null, Typeface.BOLD);
      textView.setTextColor(getResources().getColor(R.color.player_color_textColor));
      LinearLayout.LayoutParams childParam =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      childParam.setMargins(
          getResources().getDimensionPixelSize(R.dimen.player_color_item_side_margin),
          getResources().getDimensionPixelSize(R.dimen.player_color_item_vertical_margin),
          getResources().getDimensionPixelSize(R.dimen.player_color_item_side_margin),
          getResources().getDimensionPixelSize(R.dimen.player_color_item_vertical_margin));
      childParam.weight = 1;
      textView.setLayoutParams(childParam);
      playerReferenceHolders.add(textView);
      firstChildHolder.addView(textView);
    }

    //Second Linear Layout ..
    LinearLayout secondChildHolder = new LinearLayout(getContext());
    secondChildHolder.setOrientation(HORIZONTAL);
    secondChildHolder.setWeightSum(CHILD_HOLDER_WEIGHT_SUM);

    for (int i = MAX_IN_ROW + 1; i <= 2 * MAX_IN_ROW; i++) {

      TextView textView = new TextView(getContext());
      textView.setText(getResources().getString(R.string.player_color_other_view) + i);
      textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
      textView.setAllCaps(true);
      textView.setGravity(Gravity.CENTER);
      textView.setTypeface(null, Typeface.BOLD);
      textView.setTextColor(getResources().getColor(R.color.player_color_textColor));
      textView.setBackground(getResources().getDrawable(R.drawable.player_color_drawable));

      LinearLayout.LayoutParams childParam =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      childParam.setMargins(
          getResources().getDimensionPixelSize(R.dimen.player_color_item_side_margin),
          getResources().getDimensionPixelSize(R.dimen.player_color_item_vertical_margin),
          getResources().getDimensionPixelSize(R.dimen.player_color_item_side_margin),
          getResources().getDimensionPixelSize(R.dimen.player_color_item_vertical_margin));
      childParam.weight = 1;
      textView.setLayoutParams(childParam);
      playerReferenceHolders.add(textView);
      secondChildHolder.addView(textView);
    }


    LinearLayout.LayoutParams paramsChildHolder =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
    paramsChildHolder.weight = 1;
    paramsChildHolder.gravity = Gravity.CENTER_VERTICAL;

    firstChildHolder.setLayoutParams(paramsChildHolder);
    secondChildHolder.setLayoutParams(paramsChildHolder);

    addView(firstChildHolder);
    addView(secondChildHolder);

    updatePlayerColors();
    updatePlayerStatus();

    //Register for Update on No_Players Key ..
    getContext().getSharedPreferences(ChainReactionPreferences.APP_PREFERENCE_KEY,
        Context.MODE_PRIVATE).
        registerOnSharedPreferenceChangeListener(this);
  }

  /**
   * Method to set the colors to the Players , based on the Ur Preference Color
   */
  private void updatePlayerColors() {

    int index_Ur_Color =
        PlayColorValues.getIndex(ChainReactionPreferences.getPlayerColorPreference(getContext()));
    int Colors_Value_Size = PlayColorValues.values().length;

    for (int k = 0; k < playerReferenceHolders.size(); k++) {
      if (k == 0) {
        CommonUtils.updateColorView(index_Ur_Color % Colors_Value_Size,
            playerReferenceHolders.get(k),
            getResources().getDrawable(R.drawable.player_color_drawable), getContext());
        playerReferenceHolders.get(k).setTag(index_Ur_Color % Colors_Value_Size);
      } else {
        CommonUtils.updateColorView((index_Ur_Color + k) % Colors_Value_Size,
            playerReferenceHolders.get(k),
            getResources().getDrawable(R.drawable.player_color_drawable), getContext());
        playerReferenceHolders.get(k).setTag((index_Ur_Color + k) % Colors_Value_Size);
      }
    }
  }

  /**
   * This method to use update Status of Player color based on the No of Players ..
   */
  private void updatePlayerStatus() {

    int MAX_PLAYERS =
        NoPlayerValues.getIndex(ChainReactionPreferences.getPlayerNoReference(getContext()));

    for (int i = 0; i < playerReferenceHolders.size(); i++) {
      if (i < MAX_PLAYERS) {
        playerReferenceHolders.get(i).setAlpha(PLAYER_ENABLED_ALPHA);
      } else {
        playerReferenceHolders.get(i).setAlpha(PLAYER_DISABLED_ALPHA);
      }
    }
  }

  /**
   * Called when a shared preference is changed, added, or removed. This
   * may be called even if a preference is set to its existing value.
   * <p/>
   * <p>This callback will be run on your main thread.
   *
   * @param sharedPreferences The {@link SharedPreferences} that received
   *                          the change.
   * @param key               The key of the preference that was changed, added, or
   */
  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equalsIgnoreCase(ChainReactionPreferences.PLAYERNO_KEY)) {
      updatePlayerStatus();
    }
  }

  public void saveCurrentPreferences() {

    List<GamePlayerInfo> gamePlayerInfos = new ArrayList<>();
    for (int i = 0;
         i < NoPlayerValues.getIndex(ChainReactionPreferences.getPlayerNoReference(getContext()));
         i++) {
      gamePlayerInfos.add(new GamePlayerInfo(
          PlayColorValues.getEnumType((int) playerReferenceHolders.get(i).getTag()),
          i,
          playerReferenceHolders.get(i).getText().toString()));

      Gson gson = new Gson();
      GamePreferenceUtils.setGamePlayerInfoGame(getContext(), gson.toJson(gamePlayerInfos));
    }
  }
}
