package com.openquartz.glassmemo;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import java.util.List;

class MemoScrollAdapter extends CardScrollAdapter {
    private List<Card> mCards;

    public MemoScrollAdapter(List<Card> cards) {
        super();
        mCards = cards;
    }

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int position) {
        return mCards.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mCards.get(position).getView();
    }

    // Remove a memo from the card scroll
    public void removeMemo(int position) {
        mCards.remove(position);
        updateMemoNumbers();
        notifyDataSetChanged();
    }

    // updates the numbering on the memos
    public void updateMemoNumbers() {
        for (int i = 0; i < mCards.size(); i++) {
            mCards.get(i).setFootnote("Memo " + (i + 1) + " of " + mCards.size());
        }
    }

    @Override
    public int getPosition(Object arg0) {
        return 0;
    }
}
