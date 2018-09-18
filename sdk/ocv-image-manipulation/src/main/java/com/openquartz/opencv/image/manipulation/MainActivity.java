package com.openquartz.opencv.image.manipulation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity implements CvCameraViewListener2, OnGestureListener {
    /** Constants **/
    private static final String TAG = "OpenQuartz::MainActivity";
    public static final int VIEW_MODE_RGBA = 0;
    public static final int VIEW_MODE_HIST = 1;
    public static final int VIEW_MODE_CANNY = 2;
    public static final int VIEW_MODE_SEPIA = 3;
    public static final int VIEW_MODE_SOBEL = 4;
    public static final int VIEW_MODE_ZOOM = 5;
    public static final int VIEW_MODE_PIXELIZE = 6;
    public static final int VIEW_MODE_POSTERIZE = 7;
    public static final int VIEW_MODE_GRAY = 8;
    public static final int VIEW_MODE_FEATURES = 9;
    public static int viewMode = VIEW_MODE_RGBA;
    public static final String VIEWS[] = {"RGBA", "HIST", "CANNY",
        "SEPIA", "SOBEL", "ZOOM", "PIXELIZE", "POSTERIZE", "GRAY", "FEATRURES"};

    /** OpenCV View **/
    private OGView mOpenCvCameraView;

    /** Gestures for Glass **/
    private GestureDetector mGestureDetector;

    /** Native Interface for OpenCV JNI **/
    public native void FindFeatures(long matAddrGr, long matAddrRgba);

    /** Mats **/
    private Mat mRgba;
    private Mat mGray;
    private Mat mIntermediateMat;
    private Size mSize0;

    /** Callback for loading OpenCV **/
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");

                    /** Native JNI Library **/
                    System.loadLibrary("mixed_sample");

                    mOpenCvCameraView.enableView();
                    break;

                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.surface_view);

        /** View for OpenCV **/
        mOpenCvCameraView = (OGView) findViewById(R.id.tutorial3_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        /** Gesture for Glass **/
        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

     */
    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat(height, width, CvType.CV_8UC4);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);
        mSize0 = new Size();

        //		mResolutionList = mOpenCvCameraView.getResolutionList();
        //		for (int i = 0; i < mResolutionList.size(); i++)
        //		{
        //			Log.e("TAG",  i + " " + mResolutionList.get(i).height + " " + mResolutionList.get(i).width);
        //		}
    }

    public void onCameraViewStopped() {
        if (mRgba != null) {
            mRgba.release();
        }

        if (mGray != null) {
            mGray.release();
        }

        if (mIntermediateMat != null) {
            mIntermediateMat.release();
        }

        mRgba = null;
        mGray = null;
        mIntermediateMat = null;
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        switch (MainActivity.viewMode) {
            case MainActivity.VIEW_MODE_RGBA:
                return mRgba;

            case MainActivity.VIEW_MODE_HIST:
                return mRgba;

            case MainActivity.VIEW_MODE_CANNY:
                Imgproc.Canny(mGray, mIntermediateMat, 80, 100);
                Imgproc.cvtColor(mIntermediateMat, mGray, Imgproc.COLOR_GRAY2BGRA, 4);
                return mGray;

            case MainActivity.VIEW_MODE_SOBEL:
                Imgproc.Sobel(mGray, mGray, CvType.CV_8U, 1, 1);
                //			Core.convertScaleAbs(mIntermediateMat, mIntermediateMat, 10, 0);
                Imgproc.cvtColor(mGray, mGray, Imgproc.COLOR_GRAY2BGRA, 4);
                return mGray;

            case MainActivity.VIEW_MODE_PIXELIZE:
                Imgproc.resize(mGray, mIntermediateMat, mSize0, 0.1, 0.1,
                    Imgproc.INTER_NEAREST);
                Imgproc.resize(mIntermediateMat, mRgba, mRgba.size(), 0.0, 0.0,
                    Imgproc.INTER_NEAREST);
                return mRgba;

            case MainActivity.VIEW_MODE_GRAY:
                return mGray;

            case MainActivity.VIEW_MODE_FEATURES:
                FindFeatures(mGray.getNativeObjAddr(), mRgba.getNativeObjAddr());
                return mRgba;

            default:
                return mRgba;
        }
    }

    /**
     * Turn on touch events for Google Glass
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }

        return true;
    }

    /** Phone Tap **/
    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return mGestureDetector.onTouchEvent(me);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        viewMode++;

        if (viewMode == VIEWS.length) {
            viewMode = 0;
        } else if (viewMode <= 0) {
            viewMode = VIEWS.length - 1;
        }

        Toast.makeText(getApplicationContext(), "Mode: " + VIEWS[viewMode], Toast.LENGTH_SHORT)
            .show();

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 0.0f) // swipe forward
        {
            viewMode++;
        } else if (velocityX < 0.0f) // swipe backward
        {
            viewMode--;
        }

        if (viewMode == VIEWS.length) {
            viewMode = 0;
        } else if (viewMode <= 0) {
            viewMode = VIEWS.length - 1;
        }

        Toast.makeText(getApplicationContext(), "Mode: " + VIEWS[viewMode], Toast.LENGTH_SHORT)
            .show();

        return false;
    }
}
