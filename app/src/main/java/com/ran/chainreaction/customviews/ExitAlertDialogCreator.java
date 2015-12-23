package com.ran.chainreaction.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.chainreaction.R;

/**
 * Created by ranjith on 15/11/15.
 * <p/>
 * Class Responsible for Creating Exit AlertDialogs with Buttons Names and Title Specified ..
 */
public class ExitAlertDialogCreator {


  private static ButtonOnClickListener buttonOnClickListener;

  /**
   * Method responsible for giving AlertDialog with ButtonNames specified in order.
   *
   * @param buttonNames -- Name of buttons.
   * @param title       -- Title of Dialog
   * @param context     -- Application Context
   * @param cancelable  -- Whether dialog can be cancelled
   * @return -- AlertDialog Instance
   */

  public static AlertDialog createDialog(String[] buttonNames, String title, final Context context,
                                         boolean cancelable) {

    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LinearLayout parentLayout =
        (LinearLayout) layoutInflater.inflate(R.layout.alertdialog_parent, null);
    LinearLayout parentTitleLayout =
        (LinearLayout) layoutInflater.inflate(R.layout.alertdialog_parent_title, null);

    //Generic Buttons Layout Params ..
    TextView title_view = (TextView) parentTitleLayout.findViewById(R.id.alertDialogParent_title);
    title_view.setText(title);

    int verticalPadding =
        (int) context.getResources().getDimension(R.dimen.alert_dialog_padding_vetical);
    int horizontalPadding =
        (int) context.getResources().getDimension(R.dimen.alert_dialog_padding_horizantal);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    params.setMargins(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
    params.gravity = Gravity.CENTER;

    for (int k = 0; k < buttonNames.length; k++) {
      Button button = new Button(context);
      button.setLayoutParams(params);
      button.setText(buttonNames[k]);
      button.setBackgroundColor(
          context.getResources().getColor(R.color.alertDialog_buttons_background));
      button.setTextColor(context.getResources().getColor(R.color.alertDialog_textview_color));
      button.setTag(k); //Tagging Used for callbacks
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          buttonOnClickListener = (ButtonOnClickListener) context;
          buttonOnClickListener.onExitButtonClick(v);
        }
      });
      parentLayout.addView(button);
    }

    AlertDialog.Builder mBuilder =
        new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            .setCustomTitle(parentTitleLayout)
            .setView(parentLayout)
            .setCancelable(cancelable);

    return mBuilder.create();
  }

  public interface ButtonOnClickListener {

    void onExitButtonClick(View view);
  }
}
