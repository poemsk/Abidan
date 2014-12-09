package com.poepoemyintswe.abidan.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.poepoemyintswe.abidan.utils.SharePref;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by poepoe on 12/8/14.
 */
public class DBHelper extends SQLiteAssetHelper {
  private static final String DATABASE_NAME = "ornagai.sql";
  private static final int DATABASE_VERSION = 1;

  private static DBHelper dbHelper;
  private SQLiteDatabase db;

  private Context mContext;

  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.mContext = context;
  }

  public static synchronized DBHelper getInstance(Context mContext) {
    if (dbHelper == null) {
      dbHelper = new DBHelper(mContext);
    }
    return dbHelper;
  }

  public void open() throws SQLException {
    this.db = getWritableDatabase();
  }

  public void close() {
    this.db.close();
  }

  public Cursor searchWord(String word) throws SQLException {
    open();
    String query = "SELECT * FROM " + SharePref.getInstance(mContext).geDB() + " WHERE word LIKE ?";
    Cursor c = this.db.rawQuery(query, new String[] { word + "%" });
    return c;
  }

  public Cursor searchWord() throws SQLException {
    open();
    String query = "SELECT * FROM " + SharePref.getInstance(mContext).geDB();
    Cursor c = this.db.rawQuery(query, null);
    return c;
  }
}