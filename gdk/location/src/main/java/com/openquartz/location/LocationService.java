package com.openquartz.location;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

//import com.google.android.glass.timeline.TimelineManager;

public class LocationService extends Service implements LocationListener {
    private static final String LIVE_CARD_TAG = "stopwatch";
    private static final String TAG = "StopwatchService";

    private ChronometerDrawer mCallback;
    private LiveCard mLiveCard;
//    private TimelineManager mTimelineManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mTimelineManager = TimelineManager.from(this);
    }

    @Override
    public void onDestroy() {
        if ((mLiveCard != null) && mLiveCard.isPublished()) {
            if (mCallback != null) {
                mLiveCard.getSurfaceHolder().removeCallback(mCallback);
            }
            mLiveCard.unpublish();
            mLiveCard = null;
        }

        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLiveCard == null) {
            Log.d(LocationService.TAG, "Publishing LiveCard");
//            mLiveCard = mTimelineManager
//                    .createLiveCard(LocationService.LIVE_CARD_TAG);

            // Keep track of the callback to remove it before unpublishing.
            mCallback = new ChronometerDrawer(this);
            mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder()
                    .addCallback(mCallback);

            final Intent menuIntent = new Intent(this, MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mLiveCard.setAction(PendingIntent.getActivity(this, 0,
                    menuIntent, 0));

            mLiveCard.publish(PublishMode.REVEAL);
            Log.d(LocationService.TAG, "Done publishing LiveCard");
        } else {
            // TODO(alainv): Jump to the LiveCard when API is available.
        }

        return Service.START_STICKY;
    }

    @Override
    public void onStatusChanged(String provider, final int status,
            final Bundle extras) {
    }
}
