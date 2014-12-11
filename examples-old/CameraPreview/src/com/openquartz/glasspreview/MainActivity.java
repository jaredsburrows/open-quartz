/**
 * OpenGlass Voice Example 
 * Github - https://github.com/jaredsburrows/OpenQuartz
 * @author Jared Burrows
 * 
 * Copyright (C) 2014 OpenQuartz
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

package com.openquartz.glasspreview;


import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity 
{
	private CameraView cameraView = null;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		// Initiate CameraView
		cameraView = new CameraView(this);

		// Set the view
		this.setContentView(cameraView);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() 
	{
		super.onResume();

		// Do not hold the camera during onResume
		if (cameraView != null)
		{
			cameraView.releaseCamera();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() 
	{
		super.onPause();

		// Do not hold the camera during onPause
		if (cameraView != null)
		{
			cameraView.releaseCamera();
		}
	}
}
