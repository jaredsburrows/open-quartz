/**
 * OpenQuartz Memo for Glass
 * Github - https://github.com/jaredsburrows/OpenQuartz
 *
 * @author Andre Compagno
 *
 * Copyright (C) 2014 OpenQuartz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
