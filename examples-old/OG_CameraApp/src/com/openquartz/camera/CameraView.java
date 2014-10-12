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

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback
{
	private SurfaceHolder surfaceHolder = null;
	private Camera camera = null;

	@SuppressWarnings("deprecation")
	public CameraView(Context context) 
	{
		super(context);

		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		camera = Camera.open();

		// Set the Hotfix for Google Glass
		this.setCameraParameters(camera);
		
		// Show the Camera display
		try 
		{
			camera.setPreviewDisplay(holder);
		} 
		catch (Exception e) 
		{
			this.releaseCamera();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		// Start the preview for surfaceChanged
		if (camera != null)
		{
			camera.startPreview();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		// Do not hold the camera during surfaceDestroyed - view should be gone
		this.releaseCamera();
	}

	/**
	 * Important HotFix for Google Glass (post-XE11) update
	 * @param camera Object
	 */
	public void setCameraParameters(Camera camera)
	{
		if (camera != null)
		{
			Parameters parameters = camera.getParameters();
			parameters.setPreviewFpsRange(30000, 30000);
			camera.setParameters(parameters);	
		}
	}
	
	/**
	 * Release the camera from use
	 */
	public void releaseCamera() 
	{
		if (camera != null) 
		{
			camera.release();
			camera = null;
		}
	}
}