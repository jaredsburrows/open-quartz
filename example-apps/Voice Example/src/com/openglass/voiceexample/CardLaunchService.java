/**
 * OpenGlass Voice Example 
 * Github - https://github.com/jaredsburrows/OpenGlass
 * @author Andre Compagno
 * 
 * Copyright (C) 2013 OpenGlass
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.openglass.voiceexample;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

public class CardLaunchService extends Service 
{
	private TimelineManager mTimelineManager;
	private LiveCard mLiveCard;
	private String cardID = "test";

	@Override
	public void onCreate() 
	{
		mTimelineManager = TimelineManager.from(this);
		super.onCreate();
	}

	//Sets what happens when the service is launced, here we push the card to the Timeline
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		//Check if the live card has already been created
		if (mLiveCard == null)
		{
			mLiveCard = mTimelineManager.getLiveCard(cardID);
			mLiveCard.setNonSilent(true);
		}
		//Create the remote views from the layout
		RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.test_layout);
		//set the text of the text view to the the text from the speech recognition
		remoteViews.setTextViewText(R.id.text_view,intent.getStringExtra("speechText"));
		//set the views of the card
		mLiveCard.setViews(remoteViews);

		//sets the menu of the card 
		Intent menu = new Intent(this, MenuActivity.class);
		mLiveCard.setAction(PendingIntent.getActivity(this, 0, menu, 0));
		
		//Check if the card is already publish
		if (mLiveCard.isPublished())
		{
			//if it is, unpublish the card
			mLiveCard.unpublish();
		}
		
		//publish the card to the timeline 
		mLiveCard.publish();
		return START_STICKY;
	}

	@Override
	public void onDestroy() 
	{
		//remove the card from the timeline
		if (mLiveCard != null && mLiveCard.isPublished()) 
		{
			mLiveCard.unpublish();
			mLiveCard = null;
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

}