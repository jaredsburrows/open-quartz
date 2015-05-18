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
        public void onChange();
    }

    // About 24 FPS. 41
    private static final long DELAY_MILLIS = 41;

    String best = "gps";

    Criteria criteria;
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
        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
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
            ChronometerView.this.mPlaceHandler.postDelayed(
                    ChronometerView.this.mUpdatePlaces, 1000);
        }
    };
    private final Runnable mUpdateTextRunnable = new Runnable() {
        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            if (ChronometerView.this.mRunning) {
                ChronometerView.this.updateText();
                ChronometerView.this.mHandler.postDelayed(
                        ChronometerView.this.mUpdateTextRunnable,
                        ChronometerView.DELAY_MILLIS);
            }
        }
    };
    private boolean mVisible;

    List<String> providers;

    String text = "test";

    public ChronometerView(final Context context) {
        this(context, null, 0);

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        this.geocoder = new Geocoder(context);

        this.criteria = new Criteria();
        this.best = this.locationManager.getBestProvider(this.criteria, true);
        this.location = this.locationManager.getLastKnownLocation(this.best);

        this.providers = this.locationManager.getProviders(this.criteria, true);

        for (final String provider : this.providers) {
            this.locationManager.requestLocationUpdates(provider, 0, 0, this);
            Log.e("TAG", "" + provider);
        }

    }

    public ChronometerView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChronometerView(final Context context, final AttributeSet attrs,
            final int style) {
        super(context, attrs, style);
        LayoutInflater.from(context).inflate(R.layout.card_chronometer, this);

        this.mCentiSecondView = (TextView) this.findViewById(R.id.centi_second);

        this.setBaseMillis(SystemClock.elapsedRealtime());
    }

    /**
     * Get the base value of the chronometer in milliseconds.
     */
    public long getBaseMillis() {
        return this.mBaseMillis;
    }

    public Location getLastLocation(final Context context) {
        if ((this.locationManager != null) && (this.criteria != null)) {
            final List<String> providers = this.locationManager.getProviders(
                    this.criteria, true);
            final List<Location> locations = new ArrayList<Location>();
            for (final String provider : providers) {
                final Location location = this.locationManager
                        .getLastKnownLocation(provider);
                if (location != null) {
                    locations.add(location);
                }
            }
            Collections.sort(locations, new Comparator<Location>() {
                @Override
                public int compare(final Location location,
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

    /*
     * (non-Javadoc)
     * @see android.view.View#onDetachedFromWindow()
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        this.updateRunning();
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
     * @see android.location.LocationListener#onStatusChanged(java.lang.String,
     * int, android.os.Bundle)
     */
    @Override
    public void onStatusChanged(final String provider, final int status,
            final Bundle extras) {
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onWindowVisibilityChanged(int)
     */
    @Override
    protected void onWindowVisibilityChanged(final int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mVisible = (visibility == View.VISIBLE);
        this.updateRunning();
    }

    /**
     * Set the base value of the chronometer in milliseconds.
     */
    public void setBaseMillis(final long baseMillis) {
        this.mBaseMillis = baseMillis;
        this.updateText();
    }

    /**
     * Set whether or not to force the start of the chronometer when a window
     * has not been attached to the view.
     */
    public void setForceStart(final boolean forceStart) {
        this.mForceStart = forceStart;
        this.updateRunning();
    }

    /**
     * Set a {@link ChangeListener}.
     */
    public void setListener(final ChangeListener listener) {
        this.mChangeListener = listener;
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
        this.mStarted = true;
        this.updateRunning();
    }

    /**
     * Stop the chronometer.
     */
    public void stop() {
        this.mStarted = false;
        this.updateRunning();
    }

    /**
     * Update the running state of the chronometer.
     */
    private void updateRunning() {
        final boolean running = (this.mVisible || this.mForceStart)
                && this.mStarted;
        if (running != this.mRunning) {
            if (running) {
                this.mHandler.post(this.mUpdateTextRunnable);
                this.mHandler.post(this.mUpdatePlaces);
            } else {
                this.mHandler.removeCallbacks(this.mUpdateTextRunnable);
                this.mHandler.removeCallbacks(this.mUpdatePlaces);
            }
            this.mRunning = running;
        }
    }

    /**
     * Update the value of the chronometer.
     */
    private void updateText() {
        long millis = SystemClock.elapsedRealtime() - this.mBaseMillis;
        millis %= TimeUnit.HOURS.toMillis(1);
        millis %= TimeUnit.MINUTES.toMillis(1);
        millis = (millis % TimeUnit.SECONDS.toMillis(1)) / 10;

        this.location = this.getLastLocation(this.getContext());

        if (this.location != null) {
            this.text = String.format("Lat:\t %f Long:\t %f\n"
                            + "Alt:\t %f Bearing:\t %f", this.location.getLatitude(),
                    this.location.getLongitude(), this.location.getAltitude(),
                    this.location.getBearing());

            if (this.geocoder != null) {
                try {
                    final List<Address> addresses = this.geocoder
                            .getFromLocation(this.location.getLatitude(),
                                    this.location.getLongitude(), 10);
                    for (final Address address : addresses) {
                        this.text += "\n" + address.getAddressLine(0);
                    }
                } catch (final Exception e) {
                }
            }
        }

        this.mCentiSecondView.setText(this.text + " " + millis + " ");

        if (this.mChangeListener != null) {
            this.mChangeListener.onChange();
        }
    }
}
