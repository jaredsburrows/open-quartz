package com.openquartz.opencv.image.manipulation;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity implements CvCameraViewListener2, OnGestureListener {
	private static final String TAG = "OCVSample::Activity";
	public static final int      VIEW_MODE_RGBA      = 0;
	public static final int      VIEW_MODE_HIST      = 1;
	public static final int      VIEW_MODE_CANNY     = 2;
	public static final int      VIEW_MODE_SEPIA     = 3;
	public static final int      VIEW_MODE_SOBEL     = 4;
	public static final int      VIEW_MODE_ZOOM      = 5;
	public static final int      VIEW_MODE_PIXELIZE  = 6;
	public static final int      VIEW_MODE_POSTERIZE = 7;
	public static final int 	 VIEW_MODE_GRAY 	 = 8;

	private MenuItem             mItemPreviewRGBA;
	private MenuItem             mItemPreviewHist;
	private MenuItem             mItemPreviewCanny;
	private MenuItem             mItemPreviewSepia;
	private MenuItem             mItemPreviewSobel;
	private MenuItem             mItemPreviewZoom;
	private MenuItem             mItemPreviewPixelize;
	private MenuItem             mItemPreviewPosterize;
	private OGView 		 mOpenCvCameraView;

	public static int            viewMode = VIEW_MODE_RGBA;

	private GestureDetector 	 mGestureDetector;
	private Mat 				 mRgba;
	private Mat 				 mGray;
	private Mat                  mIntermediateMat;
	private Size                 mSize0;


	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS:
			{
				Log.i(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();
			} break;
			default:
			{
				super.onManagerConnected(status);
			} break;
			}
		}
	};

	public MainActivity() {
		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.surface_view);

		mOpenCvCameraView = (OGView) findViewById(R.id.tutorial3_activity_java_surface_view);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCvCameraViewListener(this);
		this.mGestureDetector = new GestureDetector(this, this);

	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "called onCreateOptionsMenu");
		mItemPreviewRGBA  = menu.add("Preview RGBA");
		mItemPreviewHist  = menu.add("Histograms");
		mItemPreviewCanny = menu.add("Canny");
		mItemPreviewSepia = menu.add("Sepia");
		mItemPreviewSobel = menu.add("Sobel");
		mItemPreviewZoom  = menu.add("Zoom");
		mItemPreviewPixelize  = menu.add("Pixelize");
		mItemPreviewPosterize = menu.add("Posterize");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
		if (item == mItemPreviewRGBA)
			viewMode = VIEW_MODE_RGBA;
		if (item == mItemPreviewHist)
			viewMode = VIEW_MODE_HIST;
		else if (item == mItemPreviewCanny)
			viewMode = VIEW_MODE_CANNY;
		else if (item == mItemPreviewSepia)
			viewMode = VIEW_MODE_SEPIA;
		else if (item == mItemPreviewSobel)
			viewMode = VIEW_MODE_SOBEL;
		else if (item == mItemPreviewZoom)
			viewMode = VIEW_MODE_ZOOM;
		else if (item == mItemPreviewPixelize)
			viewMode = VIEW_MODE_PIXELIZE;
		else if (item == mItemPreviewPosterize)
			viewMode = VIEW_MODE_POSTERIZE;
		return true;
	}

	public void onCameraViewStarted(int width, int height) {
		mGray = new Mat();
		mRgba = new Mat();
		mIntermediateMat = new Mat();
		mSize0 = new Size();

		//		mResolutionList = mOpenCvCameraView.getResolutionList();
		//		for (int i = 0; i < mResolutionList.size(); i++)
		//		{
		//			Log.e("TAG",  i + " " + mResolutionList.get(i).height + " " + mResolutionList.get(i).width);
		//		}
	}

	public void onCameraViewStopped() {
		if (this.mRgba != null)
			this.mRgba.release();
		if (this.mGray != null)
			this.mGray.release();
		if (this.mIntermediateMat != null)
			this.mIntermediateMat.release();
		this.mRgba = null;
		this.mGray = null;
		this.mIntermediateMat = null;
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		mRgba = inputFrame.rgba();
		mGray = inputFrame.gray();

		switch (MainActivity.viewMode) 
		{
		case MainActivity.VIEW_MODE_RGBA:
			return this.mRgba;

			//			case Tutorial3Activity.VIEW_MODE_HIST:
		case MainActivity.VIEW_MODE_CANNY:
			Imgproc.Canny(this.mGray, mIntermediateMat, 80, 100);
			Imgproc.cvtColor(mIntermediateMat, this.mGray, Imgproc.COLOR_GRAY2BGRA, 4);
			return this.mGray;

		case MainActivity.VIEW_MODE_SOBEL:
			Imgproc.Sobel(this.mGray, this.mGray, CvType.CV_8U, 1, 1);
			//			Core.convertScaleAbs(mIntermediateMat, mIntermediateMat, 10, 0);
			Imgproc.cvtColor(this.mGray, this.mGray, Imgproc.COLOR_GRAY2BGRA, 4);
			return this.mGray;

		case MainActivity.VIEW_MODE_PIXELIZE:
			Imgproc.resize(this.mGray, mIntermediateMat, mSize0, 0.1, 0.1, Imgproc.INTER_NEAREST);
			Imgproc.resize(mIntermediateMat, this.mRgba, this.mRgba.size(), 0.0, 0.0, Imgproc.INTER_NEAREST);
			return this.mRgba;

		case MainActivity.VIEW_MODE_GRAY:
			return this.mGray;

		default:
			return this.mRgba;
		}
	}

	/**
	 * Turn on touch events for Google Glass
	 */
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onGenericMotionEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) 
	{
		if (mGestureDetector != null)
		{
			mGestureDetector.onTouchEvent(event);
		}

		return true;
	}

	/** phone tap **/
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent me) 
	{
		return mGestureDetector.onTouchEvent(me);
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
	@Override
	public boolean onDown(MotionEvent e) 
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
	 */
	@Override
	public void onShowPress(MotionEvent e) 
	{

	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) 
	{
		viewMode++;

		if (viewMode >= 9)
		{
			viewMode = 0;
		}
		else if (viewMode <= 0)
		{
			viewMode = 8;
		}

		Toast.makeText(getApplicationContext(), "Mode: " + viewMode , Toast.LENGTH_SHORT).show();

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
	 */
	@Override
	public void onLongPress(MotionEvent e) 
	{

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		if (velocityX > 0.0f) // swipe forward
		{
			viewMode++;
		}
		else if (velocityX < 0.0f) // swipe backward
		{
			viewMode--;
		}

		if (viewMode >= 9)
		{
			viewMode = 0;
		}
		else if (viewMode <= 0)
		{
			viewMode = 8;
		}

		Toast.makeText(getApplicationContext(), "Mode: " + viewMode , Toast.LENGTH_SHORT).show();

		return false;
	}
}
