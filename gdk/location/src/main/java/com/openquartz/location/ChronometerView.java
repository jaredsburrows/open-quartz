package com.openquartz.location;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChronometerView extends FrameLayout implements LocationListener {
    /**
     * Interface to listen for changes on the view layout.
     */
    public interface ChangeListener {
        /** Notified of a change in the view. */
        void onChange();
    }

    // About 24 FPS. 41
    private static final long DELAY_MILLIS = 41;

    String best = "gps";

    private Criteria criteria;
    private Geocoder geocoder;
    private Location location;
    private LocationManager locationManager;

    private long mBaseMillis;

    private final TextView mCentiSecondView;

    private ChangeListener mChangeListener;
    private boolean mForceStart;
    private final Handler mHandler = new Handler();
    private final Handler mPlaceHandler = new Handler();
    private boolean mRunning;
    private boolean mStarted;
    private final Runnable mUpdatePlaces = new Runnable() {
        @Override
        public void run() {
            // Result result = null;
            // try {
            // result = gp.getNearbyPlaces(28.601623, -81.198884);
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            // List<Place> results = (List<Place>) result.getResults();
            //
            // for (Place place : results)
            // {
            // Log.e("TAG", "" + place.getName());
            // }
            mPlaceHandler.postDelayed(mUpdatePlaces, 1000);
        }
    };
    private final Runnable mUpdateTextRunnable = new Runnable() {
        @Override
        public void run() {
            if (mRunning) {
                updateText();
                mHandler.postDelayed(mUpdateTextRunnable, ChronometerView.DELAY_MILLIS);
            }
        }
    };
    private boolean mVisible;

    List<String> providers;

    String text = "test";

    public ChronometerView(Context context) {
        this(context, null, 0);

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(context);

        criteria = new Criteria();
        best = locationManager.getBestProvider(criteria, true);
        location = locationManager.getLastKnownLocation(best);

        providers = locationManager.getProviders(criteria, true);

        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 0, 0, this);
            Log.e("TAG", "" + provider);
        }

    }

    public ChronometerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChronometerView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        LayoutInflater.from(context).inflate(R.layout.card_chronometer, this);

        mCentiSecondView = (TextView) findViewById(R.id.centi_second);

        setBaseMillis(SystemClock.elapsedRealtime());
    }

    /**
     * Get the base value of the chronometer in milliseconds.
     */
    public long getBaseMillis() {
        return mBaseMillis;
    }

    public Location getLastLocation(Context context) {
        if (locationManager != null && criteria != null) {
            final List<String> providers = locationManager.getProviders(
                    criteria, true);
            final List<Location> locations = new ArrayList<>();
            for (String provider : providers) {
                final Location location = locationManager
                        .getLastKnownLocation(provider);
                if (location != null) {
                    locations.add(location);
                }
            }
            Collections.sort(locations, new Comparator<Location>() {
                @Override
                public int compare(Location location,
                        final Location location2) {
                    return (int) (location.getAccuracy() - location2
                            .getAccuracy());
                }
            });
            if (locations.size() > 0) {
                return locations.get(0);
            }
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;
        updateRunning();
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
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == View.VISIBLE;
        updateRunning();
    }

    /**
     * Set the base value of the chronometer in milliseconds.
     */
    public void setBaseMillis(long baseMillis) {
        mBaseMillis = baseMillis;
        updateText();
    }

    /**
     * Set whether or not to force the start of the chronometer when a window
     * has not been attached to the view.
     */
    public void setForceStart(boolean forceStart) {
        mForceStart = forceStart;
        updateRunning();
    }

    /**
     * Set a {@link ChangeListener}.
     */
    public void setListener(ChangeListener listener) {
        mChangeListener = listener;
    }

    // public static Location getLastLocation(Context context) {
    // LocationManager manager = (LocationManager)
    // context.getSystemService(Context.LOCATION_SERVICE);
    // Criteria criteria = new Criteria();
    // criteria.setAccuracy(Criteria.NO_REQUIREMENT);
    // List<String> providers = manager.getProviders(criteria, true);
    // List<Location> locations = new ArrayList<Location>();
    // for (String provider : providers) {
    // Location location = manager.getLastKnownLocation(provider);
    // if (location != null) {
    // locations.add(location);
    // }
    // }
    // Collections.sort(locations, new Comparator<Location>() {
    // @Override
    // public int compare(Location location, Location location2) {
    // return (int) (location.getAccuracy() - location2.getAccuracy());
    // }
    // });
    // if (locations.size() > 0) {
    // return locations.get(0);
    // }
    // return null;
    // }

    /**
     * Start the chronometer.
     */
    public void start() {
        mStarted = true;
        updateRunning();
    }

    /**
     * Stop the chronometer.
     */
    public void stop() {
        mStarted = false;
        updateRunning();
    }

    /**
     * Update the running state of the chronometer.
     */
    private void updateRunning() {
        final boolean running = (mVisible || mForceStart)
                && mStarted;
        if (running != mRunning) {
            if (running) {
                mHandler.post(mUpdateTextRunnable);
                mHandler.post(mUpdatePlaces);
            } else {
                mHandler.removeCallbacks(mUpdateTextRunnable);
                mHandler.removeCallbacks(mUpdatePlaces);
            }
            mRunning = running;
        }
    }

    /**
     * Update the value of the chronometer.
     */
    private void updateText() {
        long millis = SystemClock.elapsedRealtime() - mBaseMillis;
        millis %= TimeUnit.HOURS.toMillis(1);
        millis %= TimeUnit.MINUTES.toMillis(1);
        millis = (millis % TimeUnit.SECONDS.toMillis(1)) / 10;

        location = getLastLocation(getContext());

        if (location != null) {
            text = String.format("Lat:\t %f Long:\t %f\n"
                            + "Alt:\t %f Bearing:\t %f", location.getLatitude(),
                    location.getLongitude(), location.getAltitude(),
                    location.getBearing());

            if (geocoder != null) {
                try {
                    final List<Address> addresses = geocoder
                            .getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 10);
                    for (Address address : addresses) {
                        text += "\n" + address.getAddressLine(0);
                    }
                } catch (Exception ignored) {
                }
            }
        }

        mCentiSecondView.setText(text + " " + millis + " ");

        if (mChangeListener != null) {
            mChangeListener.onChange();
        }
    }
}
