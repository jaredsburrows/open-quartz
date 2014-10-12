package com.openquartz.facedetect;

import org.opencv.android.JavaCameraView;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

public class JView extends JavaCameraView
{
	public JView(Context context, int cameraId) {
		super(context, cameraId);
	}

	public JView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected boolean initializeCamera(int width, int height) 
	{
		super.initializeCamera(width, height);
		
		Camera.Parameters params = mCamera.getParameters();

		// Post XE10 Hotfix
		params.setPreviewFpsRange(30000, 30000);
		mCamera.setParameters(params);

		return true;
	}
}
