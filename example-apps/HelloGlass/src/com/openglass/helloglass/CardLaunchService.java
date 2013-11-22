/**
 * OpenGlass Hello World Example
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


package com.openglass.helloglass;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

public class CardLaunchService extends Service 
{
	TimelineManager mTimelineManager;
	LiveCard mLiveCard;
	String cardID = "test";
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		mTimelineManager = TimelineManager.from(this);
	}

	//Sets what happens when the service is launced, here we push the card to the Timeline
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		mLiveCard = mTimelineManager.getLiveCard(cardID);

		//set the views of the card from a xml file 
		mLiveCard.setViews(new RemoteViews(this.getPackageName(),R.layout.test_layout));
		mLiveCard.setNonSilent(true);
		
		//sets the menu of the card 
		Intent menuIntent = new Intent(this, MenuActivity.class);
		mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));
		
		//publish the card to the timeline 
		mLiveCard.publish();
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() 
	{
		//remove the card from
		mLiveCard.unpublish();
		mLiveCard = null;
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

}