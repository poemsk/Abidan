package com.poepoemyintswe.abidan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by poepoe on 10/15/14.
 */
public class SharePref {

  private static final String PREF_DATABASE = "pref_database";
  private static final String PREF_FIRST_TIME_CHECK = "pref_first_time_check";

  private static SharePref pref;
  protected SharedPreferences mSharedPreferences;
  protected SharedPreferences.Editor mEditor;
  protected Context mContext;

  public SharePref(Context context) {
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    mEditor = mSharedPreferences.edit();
    this.mContext = context;
  }

  public static synchronized SharePref getInstance(Context mContext) {
    if (pref == null) {
      pref = new SharePref(mContext);
    }
    return pref;
  }

  public boolean isFirstTime() {
    return mSharedPreferences.getBoolean(PREF_FIRST_TIME_CHECK, true);
  }

  public void noLongerFirstTime() {
    mEditor.putBoolean(PREF_FIRST_TIME_CHECK, false);
  }

  public void saveDB(String key) {
    mEditor.putString(PREF_DATABASE, key);
    mEditor.commit();
  }

  public String geDB() {
    return mSharedPreferences.getString(PREF_DATABASE, null);
  }
}