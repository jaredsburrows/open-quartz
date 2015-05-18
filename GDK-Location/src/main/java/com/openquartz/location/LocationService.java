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

    /*
     * (non-Javadoc)
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        this.mTimelineManager = TimelineManager.from(this);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Service#onDestroy()
     */
    @Override
    public void onDestroy() {
        if ((this.mLiveCard != null) && this.mLiveCard.isPublished()) {
            if (this.mCallback != null) {
                this.mLiveCard.getSurfaceHolder()
                        .removeCallback(this.mCallback);
            }
            this.mLiveCard.unpublish();
            this.mLiveCard = null;
        }

        super.onDestroy();
    }

    /*
     * (non-Javadoc)
     * @see
     * android.location.LocationListener#onLocationChanged(android.location.
     * Location)
     */
    @Override
    public void onLocationChanged(final Location location) {
    }

    /*
     * (non-Javadoc)
     * @see
     * android.location.LocationListener#onProviderDisabled(java.lang.String)
     */
    @Override
    public void onProviderDisabled(final String provider) {
    }

    /*
     * (non-Javadoc)
     * @see
     * android.location.LocationListener#onProviderEnabled(java.lang.String)
     */
    @Override
    public void onProviderEnabled(final String provider) {
    }

    /*
     * (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(final Intent intent, final int flags,
            final int startId) {
        if (this.mLiveCard == null) {
            Log.d(LocationService.TAG, "Publishing LiveCard");
//            this.mLiveCard = this.mTimelineManager
//                    .createLiveCard(LocationService.LIVE_CARD_TAG);

            // Keep track of the callback to remove it before unpublishing.
            this.mCallback = new ChronometerDrawer(this);
            this.mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder()
                    .addCallback(this.mCallback);

            final Intent menuIntent = new Intent(this, MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.mLiveCard.setAction(PendingIntent.getActivity(this, 0,
                    menuIntent, 0));

            this.mLiveCard.publish(PublishMode.REVEAL);
            Log.d(LocationService.TAG, "Done publishing LiveCard");
        } else {
            // TODO(alainv): Jump to the LiveCard when API is available.
        }

        return Service.START_STICKY;
    }

    /*
     * (non-Javadoc)
     * @see android.location.LocationListener#onStatusChanged(java.lang.String,
     * int, android.os.Bundle)
     */
    @Override
    public void onStatusChanged(final String provider, final int status,
            final Bundle extras) {
    }
}
