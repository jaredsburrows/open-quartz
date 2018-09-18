package com.openquartz.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;

import java.util.List;

public class LocationProvider {
    private String mBestProvider;
    private final Geocoder mGeocoder;
    private final LocationManager mLocationManager;

    public LocationProvider(Context context) {
        mLocationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        mGeocoder = new Geocoder(context);

        final Criteria criteria = new Criteria();
        mBestProvider = mLocationManager.getBestProvider(criteria,
                true);

        final List<String> providers = mLocationManager.getProviders(
                criteria, true);

        for (String provider : providers) {
            // mLocationManager.requestLocationUpdates(provider, 0, 0, this);
        }
    }

    /**
     * @return the mBestProvider
     */
    public String getBestProvider() {
        final Criteria criteria = new Criteria();
        mBestProvider = mLocationManager.getBestProvider(criteria, true);

        return mBestProvider;
    }

}
