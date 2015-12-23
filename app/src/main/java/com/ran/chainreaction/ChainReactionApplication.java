package com.ran.chainreaction;

import android.app.Application;
import android.content.Intent;

import com.ran.chainreaction.services.AudioPlayBackService;

/**
 * Created by ranjith on 15/11/15.
 */
public class ChainReactionApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    //Start the Audio Play BAck here ..
    Intent audioPlayBackService = new Intent(this, AudioPlayBackService.class);
    startService(audioPlayBackService);
  }
}
