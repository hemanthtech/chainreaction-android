package com.ran.chainreaction.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.ran.chainreaction.R;

/**
 * Created by ranjith on 12/11/15.
 */
public class ChainReactionShareUtils {


  private static final String TAG = ChainReactionConstants.class.getName();

  /**
   * Method to send Email from App
   *
   * @param subject -- Subject of the Email
   * @param toEmail -- toEmail Address/es
   * @param context -- Context of the Application
   */

  public static void generateEmailMessage(String subject, String[] toEmail, Context context)
      throws PackageManager.NameNotFoundException {

    //Package Info for APpVersion..
    PackageManager packageManager = context.getPackageManager();
    PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
    String app_version = info.versionName + ChainReactionConstants.SPACE + info.versionCode;

    //Device Name ..
    String deviceManufacturer = Build.MANUFACTURER;
    String deviceModel = Build.MODEL;

    String body_Email = context.getResources().getString(R.string.email_app_name) +
        context.getString(R.string.app_name) +
        ChainReactionConstants.NEW_LINE +
        context.getString(R.string.email_app_version) + app_version +
        ChainReactionConstants.NEW_LINE +
        context.getString(R.string.email_device_name) +
        deviceManufacturer + ChainReactionConstants.DASH + deviceModel +
        ChainReactionConstants.NEW_LINE +
        context.getString(R.string.email_device_version) +
        Build.VERSION.RELEASE;


    Intent emailIntent = new Intent();
    emailIntent.setAction(Intent.ACTION_SEND);
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
    emailIntent.putExtra(Intent.EXTRA_EMAIL, toEmail);
    emailIntent.putExtra(Intent.EXTRA_TEXT, body_Email);
    emailIntent.setType("message/rfc822");

    try {
      context.startActivity(emailIntent);
    } catch (ActivityNotFoundException exception) {
      Log.d(TAG, exception.toString());
      Toast.makeText(context, R.string.noApp_email_share, Toast.LENGTH_SHORT).show();
    }


  }
}
