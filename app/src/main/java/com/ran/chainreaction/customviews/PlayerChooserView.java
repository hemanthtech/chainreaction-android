package com.ran.chainreaction.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.NoPlayerValues;
import com.ran.chainreaction.utils.ChainReactionPreferences;

/**
 * Created by ranjith on 28/11/15.
 * <p/>
 * View Responsible for showing the No of Players..
 */
public class PlayerChooserView extends LinearLayout implements View.OnClickListener {

  private final float WEIGHT_SUM = 7;

  public PlayerChooserView(Context context) {
    super(context);
    initView();
  }

  public PlayerChooserView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public PlayerChooserView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  private void initView() {
    setOrientation(HORIZONTAL);
    setWeightSum(WEIGHT_SUM);

    for (NoPlayerValues value : NoPlayerValues.values()) {

      Button button = new Button(getContext());
      button.setTextColor(getResources().getColor(R.color.player_chooser_option_textColor));
      button.setTag(NoPlayerValues.getIndex(value));
      button.setText(String.valueOf(NoPlayerValues.getIndex(value)));
      button.setOnClickListener(this);
      button.setGravity(Gravity.CENTER);
      button.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      params.setMargins(getResources().getDimensionPixelSize(R.dimen.player_chooser_item_margin),
          getResources().getDimensionPixelSize(R.dimen.player_chooser_item_margin),
          getResources().getDimensionPixelSize(R.dimen.player_chooser_item_margin),
          getResources().getDimensionPixelSize(R.dimen.player_chooser_item_margin));
      params.gravity = Gravity.CENTER;
      params.weight = 1;
      button.setLayoutParams(params);
      addView(button);
    }

    updateChildItemSelectedBackground();
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {

    int tag = (int) v.getTag();
    ChainReactionPreferences.setPlayerNoPreference(getContext(), NoPlayerValues.getEnumType(tag));
    updateChildItemSelectedBackground();

  }

  /**
   * Method to update the ChildItems Background based on the Current NoPlayers Value
   */
  private void updateChildItemSelectedBackground() {

    for (int k = 0; k < getChildCount(); k++) {
      View view = getChildAt(k);
      if (NoPlayerValues.getIndex(ChainReactionPreferences.getPlayerNoReference(getContext
          ())) == (int) view.getTag()) {
        view.setBackground(getResources().getDrawable(R.drawable.player_chooser_selected_drawable));
      } else {
        view.setBackgroundColor(getResources().getColor(R.color.player_chooser_option_unselected));
      }
    }
  }

}
