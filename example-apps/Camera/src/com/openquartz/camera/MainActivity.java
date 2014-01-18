/**
 * OpenGlass Voice Example 
 * Github - https://github.com/jaredsburrows/OpenQuartz
 * @author Jared Burrows
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

package com.openquartz.camera;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.google.android.glass.media.CameraManager;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;


public class MainActivity extends Activity 
{
	private static final int TAKE_PICTURE_REQUEST = 1;
	private static final int TAKE_VIDEO_REQUEST = 2;
	private GestureDetector mGestureDetector = null;
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

		// Turn on Gestures
		mGestureDetector = createGestureDetector(this);

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

		// Set the view
		this.setContentView(cameraView);
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

	/**
	 * Gesture detection for fingers on the Glass
	 * @param context
	 * @return
	 */
	private GestureDetector createGestureDetector(Context context) 
	{	
		GestureDetector gestureDetector = new GestureDetector(context);

		//Create a base listener for generic gestures
		gestureDetector.setBaseListener( new GestureDetector.BaseListener() 
		{
			@Override
			public boolean onGesture(Gesture gesture) 
			{
				// Make sure view is initiated
				if (cameraView != null)
				{
					// Tap with a single finger for photo
					if (gesture == Gesture.TAP) 
					{
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						if (intent != null)
						{
							startActivityForResult(intent, TAKE_PICTURE_REQUEST);
						}

						return true;
					}
					// Tap with 2 fingers for video
					else if (gesture == Gesture.TWO_TAP) 
					{
						Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
						if (intent != null)
						{
							startActivityForResult(intent, TAKE_VIDEO_REQUEST);
						}

						return true;
					}
				}

				return false;
			}
		});

		return gestureDetector;
	}

	/*
	 * Send generic motion events to the gesture detector
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) 
	{
		if (mGestureDetector != null) 
		{
			return mGestureDetector.onMotionEvent(event);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// Handle photos
		if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) 
		{
			String picturePath = data.getStringExtra(CameraManager.EXTRA_PICTURE_FILE_PATH);
			processPictureWhenReady(picturePath);
		}

		// Handle videos
		if (requestCode == TAKE_VIDEO_REQUEST && resultCode == RESULT_OK) 
		{
			String picturePath = data.getStringExtra(CameraManager.EXTRA_VIDEO_FILE_PATH);
			processPictureWhenReady(picturePath);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Process picture - from example GDK
	 * @param picturePath
	 */
	private void processPictureWhenReady(final String picturePath) 
	{
		final File pictureFile = new File(picturePath);

		if (pictureFile.exists()) 
		{
			// The picture is ready; process it.
		} 
		else 
		{
			// The file does not exist yet. Before starting the file observer, you
			// can update your UI to let the user know that the application is
			// waiting for the picture (for example, by displaying the thumbnail
			// image and a progress indicator).

			final File parentDirectory = pictureFile.getParentFile();
			FileObserver observer = new FileObserver(parentDirectory.getPath()) 
			{
				// Protect against additional pending events after CLOSE_WRITE is
				// handled.
				private boolean isFileWritten;

				@Override
				public void onEvent(int event, String path) 
				{
					if (! isFileWritten) 
					{
						// For safety, make sure that the file that was created in
						// the directory is actually the one that we're expecting.
						File affectedFile = new File(parentDirectory, path);
						isFileWritten = (event == FileObserver.CLOSE_WRITE && affectedFile.equals(pictureFile));

						if (isFileWritten) 
						{
							stopWatching();

							// Now that the file is ready, recursively call
							// processPictureWhenReady again (on the UI thread).
							runOnUiThread(new Runnable() 
							{
								@Override
								public void run() 
								{
									processPictureWhenReady(picturePath);
								}
							});
						}
					}
				}
			};
			observer.startWatching();
		}
	}

	/**
	 * Added but irrelevant
	 */
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_CAMERA) 
		{
			// Stop the preview and release the camera.
			// Execute your logic as quickly as possible
			// so the capture happens quickly.
			return false;
		} 
		else 
		{
			return super.onKeyDown(keyCode, event);
		}
	}
}