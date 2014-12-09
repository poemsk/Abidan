package com.poepoemyintswe.abidan.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import com.poepoemyintswe.abidan.adapter.WordAdapter;
import com.poepoemyintswe.abidan.models.Word;
import java.util.ArrayList;

/**
 * Created by poepoe on 12/8/14.
 */
public class WordAsync extends AsyncTask<Cursor, Void, ArrayList<Word>> {

  private static WordAsync wordAsync;
  private Context mContext;
  private ListView mListView;

  public WordAsync(Context mContext, ListView mListView) {
    this.mContext = mContext;
    this.mListView = mListView;
  }

  @Override protected void onPreExecute() {
    super.onPreExecute();
    mListView.setAdapter(null);
  }

  @Override protected ArrayList<Word> doInBackground(Cursor... cursors) {

    ArrayList<Word> wordArrayList = new ArrayList<>();
    wordArrayList.clear();
    Cursor c = cursors[0];

    try {
      if (c != null) {
        if (c.moveToFirst()) {
          do {
            Word word = new Word();
            word.id = c.getInt(0);
            word.word = c.getString(1);
            word.type = c.getString(2);
            word.def = c.getString(3);
            wordArrayList.add(word);
          } while (c.moveToNext());
          return wordArrayList;
        }
      }
    } catch (Exception e) {

    }

    return null;
  }

  @Override protected void onPostExecute(ArrayList<Word> words) {
    super.onPostExecute(words);
    try {

      WordAdapter adapter = new WordAdapter(mContext, words);
      if (adapter.getCount() == 0) {
        Toast.makeText(mContext, "Nothing to show", Toast.LENGTH_LONG).show();
      } else {
        mListView.setAdapter(adapter);
      }
    } catch (NullPointerException e) {
      Toast.makeText(mContext, "Sorry! No records", Toast.LENGTH_LONG).show();
    }
  }
}
