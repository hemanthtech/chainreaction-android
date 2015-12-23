package com.ran.chainreaction.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ran.chainreaction.R;
import com.ran.chainreaction.entities.GridSizeValues;
import com.ran.chainreaction.utils.ChainReactionPreferences;

/**
 * Created by ranjith on 27/11/15.
 * <p/>
 * Custom GridChooser View , that inflates and shows Views dynamically based on View Width and height
 */
public class GridChooserView extends LinearLayout implements View.OnClickListener {

  public static final String TAG = GridChooserView.class.getName();
  public final int GRID_TYPES = 3;
  public final float WEIGHT_SUM = 3.0f;

  public GridChooserView(Context context) {
    super(context);
    initView();
  }

  public GridChooserView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public GridChooserView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  /**
   * Method to initialize the Grid Chooser ..
   */
  private void initView() {

    setOrientation(HORIZONTAL);
    setWeightSum(WEIGHT_SUM);
    for (int i = 0; i < GRID_TYPES; i++) {
      Button button = new Button(getContext());
      button.setTextColor(getResources().getColor(R.color.grid_view_option_textColor));
      button.setTag(i);
      button.setOnClickListener(this);
      updateChildItemText(button, i);
      button.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      params.setMargins(getResources().getDimensionPixelSize(R.dimen.grid_chooser_item_margin_side),
          getResources().getDimensionPixelSize(R.dimen.grid_chooser_item_margin_top),
          getResources().getDimensionPixelSize(R.dimen.grid_chooser_item_margin_side),
          getResources().getDimensionPixelSize(R.dimen.grid_chooser_item_margin_top));
      params.gravity = Gravity.CENTER;
      params.weight = 1;
      button.setLayoutParams(params);
      addView(button);
    }

    updateChildItemsBackground();

  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    int tag = (int) v.getTag();
    ChainReactionPreferences.setGridSizePreference(getContext(), GridSizeValues.getEnumType(tag));
    updateChildItemsBackground();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    for (int k = 0; k < getChildCount(); k++) {

      int width = getChildAt(k).getMeasuredWidth();
      int height = getChildAt(k).getMeasuredHeight();

      if (width > height) {
        getChildAt(k).getLayoutParams().width = height;
      } else {
        getChildAt(k).getLayoutParams().height = width;
      }
    }

  }

  /**
   * Method to update the ChildItems Background based on the Current GridSize
   */
  private void updateChildItemsBackground() {

    for (int k = 0; k < getChildCount(); k++) {
      View view = getChildAt(k);
      if (GridSizeValues.getIndex(ChainReactionPreferences.getGridSizePreference(getContext())) ==
          k) {
        view.setBackground(getResources().getDrawable(R.drawable.chooser_drawable_selected));
      } else {
        view.setBackground(getResources().getDrawable(R.drawable.chooser_drawable_unselected));
      }
    }
  }

  /**
   * Method to update the Child ite text ..
   *
   * @param button -- Button text to be changed
   * @param i      -- Index of the Button
   */
  private void updateChildItemText(Button button, int i) {

    switch (GridSizeValues.getEnumType(i)) {
      case SMALL:
        button.setText(getResources().getText(R.string.grid_chooser_view_small_item));
        break;
      case MEDIUM:
        button.setText(getResources().getText(R.string.grid_chooser_view_medium_item));
        break;
      case LARGE:
        button.setText(getResources().getText(R.string.grid_chooser_view_large_item));
        break;
    }
  }
}
