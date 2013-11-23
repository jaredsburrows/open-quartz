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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;


//Due to a bug in XE11, services cant be lanuched after using speech recognition.
//so we use use an activity to get the text and send it to the service where the card will be published 
//Source: http://stackoverflow.com/questions/20100120/google-glass-live-card-not-inserting/20127883#20127883
public class ProcessSpeechActivity extends Activity
{
	@Override 
	protected void onResume()
	{
		ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
		Intent serviceIntent = new Intent(this, CardLaunchService.class);
		serviceIntent.putExtra("speechText", voiceResults.get(0));
		startService(serviceIntent);
		finish();
		super.onResume();
	}

}