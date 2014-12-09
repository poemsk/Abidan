package com.poepoemyintswe.abidan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.abidan.R;
import com.poepoemyintswe.abidan.models.Word;
import java.util.ArrayList;

/**
 * Created by poepoe on 12/8/14.
 */
public class WordAdapter extends BaseAdapter {

  Context mContext;
  ArrayList<Word> wordArrayList;

  public WordAdapter(Context mContext, ArrayList<Word> wordArrayList) {
    this.mContext = mContext;
    this.wordArrayList = wordArrayList;
  }

  @Override public int getCount() {
    return wordArrayList.size();
  }

  @Override public Word getItem(int i) {
    return wordArrayList.get(i);
  }

  @Override public long getItemId(int i) {
    return i;
  }

  @Override public View getView(int i, View convertView, ViewGroup viewGroup) {
    final ViewHolder holder;
    View view = convertView;
    if (view != null) {
      holder = (ViewHolder) view.getTag();
    } else {
      LayoutInflater inflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.row_word, null, false);
      holder = new ViewHolder(view);
      view.setTag(holder);
    }

    final Word word = getItem(i);
    holder.word.setText(word.word);
    holder.type.setText(word.type);
    holder.def.setText(word.def);
    return view;
  }

  public static class ViewHolder {
    @InjectView(R.id.word) TextView word;
    @InjectView(R.id.type) TextView type;
    @InjectView(R.id.def) TextView def;

    public ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
