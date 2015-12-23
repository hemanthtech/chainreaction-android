package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 12/11/15.
 */
public enum SoundPreferenceValues {

  NO_SOUND(0, "nosound"),
  MEDIUM_SOUND(1, "medsound"),
  FULL_SOUND(2, "fullsound");


  int value;
  String name;

  SoundPreferenceValues(int value, String name) {
    this.value = value;
    this.name = name;
  }

  /**
   * Method to return index of Enum passed
   *
   * @param paramSound -- Enum index to be retrieved
   * @return -- Index of the Enum
   */

  public static int getIndex(SoundPreferenceValues paramSound) {
    for (SoundPreferenceValues item : values()) {
      if (item.value == paramSound.value) {
        return item.value;
      }
    }
    return NO_SOUND.value;
  }

  /**
   * Method to return the Enum based on index
   *
   * @param index -- Index of the Enum to be retrieved
   * @return -- Enum type returned
   */
  public static SoundPreferenceValues getEnumType(int index) {
    for (SoundPreferenceValues item : values()) {
      if (item.value == index) {
        return item;
      }
    }
    return null;
  }
}
