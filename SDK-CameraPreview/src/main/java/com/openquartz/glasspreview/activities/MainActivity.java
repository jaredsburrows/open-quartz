package com.openquartz.glasspreview.activities;

import android.app.Activity;
import android.os.Bundle;

import com.openquartz.glasspreview.CameraSurfaceView;

public class MainActivity extends Activity {
    private CameraSurfaceView cameraView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate CameraView
        cameraView = new CameraSurfaceView(this);

        // Set the view
        this.setContentView(cameraView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Do not hold the camera during onResume
        if (cameraView != null) {
            cameraView.releaseCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Do not hold the camera during onPause
        if (cameraView != null) {
            cameraView.releaseCamera();
        }
    }
}
