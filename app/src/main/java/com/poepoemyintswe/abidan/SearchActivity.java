package com.poepoemyintswe.abidan;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.poepoemyintswe.abidan.async.WordAsync;
import com.poepoemyintswe.abidan.utils.SharePref;
import com.poepoemyintswe.abidan.db.DBHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class SearchActivity extends ActionBarActivity {

  private Toolbar toolbar;
  @InjectView(R.id.word_list) ListView wordListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ButterKnife.inject(this);

    if (SharePref.getInstance(this).isFirstTime()) {
      chooseFont();
      SharePref.getInstance(this).noLongerFirstTime();
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Log.d("Build Version", Build.VERSION.SDK_INT + "");
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setNavigationBarTintEnabled(false);
      tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimaryDark));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_search, menu);
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

    MenuItem searchItem = menu.findItem(R.id.action_search);

    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String s) {
        return false;
      }

      @Override public boolean onQueryTextChange(String s) {
        Cursor cursor = DBHelper.getInstance(SearchActivity.this).searchWord(s);
        Log.d(SearchActivity.class.getSimpleName(), s + " " + cursor.getCount());
        new WordAsync(SearchActivity.this, wordListView).execute(cursor);
        return true;
      }
    });

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_choose) {
      chooseFont();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void chooseFont() {
    new MaterialDialog.Builder(this).title("Choose Font")
        .items(new String[] { "Zawgyi", "Unicode" })
        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallback() {
          @Override
          public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
            if (text.equals("Zawgyi")) {
              SharePref.getInstance(SearchActivity.this).saveDB(Config.TABLE_ZAWGYI);
            } else {
              SharePref.getInstance(SearchActivity.this).saveDB(Config.TABLE_UNI);
            }
          }
        })
        .positiveText("Choose")
        .positiveColor(getResources().getColor(R.color.colorPrimaryDark))
        .show();
  }
}
