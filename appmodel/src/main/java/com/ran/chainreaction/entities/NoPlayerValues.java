package com.ran.chainreaction.entities;

/**
 * Created by ranjith on 28/11/15.
 * <p/>
 * Entities responsible for NoPlayerValues..
 */
public enum NoPlayerValues {

  TWO(2, "two"),
  THREE(3, "three"),
  FOUR(4, "four"),
  FIVE(5, "five"),
  SIX(6, "six"),
  SEVEN(7, "seven"),
  EIGHT(8, "eight");

  int value;
  String name;

  NoPlayerValues(int value, String name) {
    this.value = value;
    this.name = name;
  }

  /**
   * Method to return index of Enum passed
   *
   * @param noPlayerEnum -- Enum index to be retrieved
   * @return -- Index of the Enum
   */

  public static int getIndex(NoPlayerValues noPlayerEnum) {
    for (NoPlayerValues item : values()) {
      if (item.value == noPlayerEnum.value) {
        return item.value;
      }
    }
    return TWO.value;
  }

  /**
   * Method to return the Enum based on index
   *
   * @param index -- Index of the Enum to be retrieved
   * @return -- Enum type returned
   */
  public static NoPlayerValues getEnumType(int index) {
    for (NoPlayerValues item : values()) {
      if (item.value == index) {
        return item;
      }
    }
    return NoPlayerValues.TWO;
  }
}
