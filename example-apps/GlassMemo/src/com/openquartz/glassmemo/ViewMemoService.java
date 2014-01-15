/**
 * OpenQuartz Hello World Example
 * Github - https://github.com/jaredsburrows/OpenQuartz
 * @author Andre Compagno
 * 
 * Copyright (C) 2013 OpenQuartz
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


package com.openquartz.glassmemo;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;


public class ViewMemoService extends Service 
{
	private TimelineManager mTimelineManager;
	private LiveCard mLiveCard;
	private String cardID = "MemoGlassLiveCard";
	private String cardText = "";
	private List<String> memoList;
	private boolean update;
	@Override
	public void onCreate() 
	{
		super.onCreate();
		mTimelineManager = TimelineManager.from(this);
		if (Utils.checkForObjectInSharedPrefs(this,getString(R.string.shared_memo_key)))
		{
			memoList = new ArrayList<String>(Utils.getStringArrayPref(this, getString(R.string.shared_memo_key)));
		}
		else
		{
			memoList = new ArrayList<String>();
		}

	}

	//Sets what happens when the service is launced, here we push the card to the Timeline
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{	
		//get the update boolean from the intent 
		update = intent.getBooleanExtra("update", false);
		//the live card is not created and published yet
		if (mLiveCard ==  null)
		{
			//create live card *New in XE12 createLiveCard replaced getLiveCard from XE11*
			mLiveCard = mTimelineManager.createLiveCard(cardID);
			RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.card_layout);

			//if there are memos
			if (memoList.size() > 0)
			{
				//build string with list 
				for (int i = 0 ; i < memoList.size() && i < 5; i++)
				{
					cardText += "" + (i+1) + ") " + memoList.get(i) + "\n";
				}
				//set the text views 
				remoteViews.setTextViewText(R.id.memo_list_text_view , cardText);
				remoteViews.setTextViewText(R.id.no_memos_text_view, "");
			}
			//if there are no memos 
			else 
			{
				//set the text views 
				remoteViews.setTextViewText(R.id.memo_list_text_view , "");
				remoteViews.setTextViewText(R.id.no_memos_text_view, "No memos!");
			}
			//set the views of the card 
			mLiveCard.setViews(remoteViews);

			//sets the menu of the card
			Intent menu;
			//if there are no memos
			if (memoList.size() == 0)
			{
				menu = new Intent(this, ViewMemoMenuActivityNoMemos.class);
			}
			//if there are memos 
			else 
			{
				menu = new Intent(this, ViewMemoMenuActivity.class);
			}
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, menu, 0));

			//publish the card to the timeline 
			//Set publish mode to reveal *New in XE12 replaced setNonSilent method from XE11*
			if (update)
			{
				mLiveCard.publish(LiveCard.PublishMode.SILENT);
			}
			else
			{
				mLiveCard.publish(LiveCard.PublishMode.REVEAL);
			}
		}
		//Live card is already published 
		else 
		{
			//Jumping to the card not implemented in the gdk yet so we unpublish it and republish it so the glass jumps to it 
			mLiveCard.unpublish();
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() 
	{
		//remove the card from
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