/**
 * OpenQuartz Voice Example
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

package com.openquartz.voiceexample;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.widget.RemoteViews;
import com.google.android.glass.timeline.LiveCard;

public class CardLaunchService extends Service {
    private LiveCard mLiveCard;
    private String cardID = "VoiceLiveCard";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Sets what happens when the service is launced, here we push the card to the Timeline
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Check if the live card has already been created
        if (mLiveCard == null) {
            // create live card *New in XE12 createLiveCard replaced getLiveCard from XE11*
            // TimelineManager removed. Use LiveCard constructor to create LiveCards
            mLiveCard = new LiveCard(this, cardID);
        }
        // Create the remote views from the layout
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.card_layout);
        // Set the text to the results from the voice recognition activity
        remoteViews.setTextViewText(R.id.text_view,
            intent.getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS).get(0));

        // set the text of the text view to the the text from the speech recognition
        // remoteViews.setTextViewText(R.id.text_view,intent.getStringExtra("speechText"));
        // set the views of the card
        mLiveCard.setViews(remoteViews);

        // sets the menu of the card
        final Intent menu = new Intent(this, MenuActivity.class);
        mLiveCard.setAction(PendingIntent.getActivity(this, 0, menu, 0));

        // Check if the card is already publish
        if (mLiveCard.isPublished()) {
            // if it is, unpublish the card
            mLiveCard.unpublish();
        }

        // publish the card to the timeline
        // Set publish mode to reveal *New in XE12 replaced setNonSilent method from XE11*
        mLiveCard.publish(LiveCard.PublishMode.REVEAL);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // remove the card from the timeline
        if (mLiveCard != null && mLiveCard.isPublished()) {
            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
