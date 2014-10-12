package com.openquartz.location;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;

public class LocationProvider {
    private String mBestProvider;
    private final Geocoder mGeocoder;
    private final LocationManager mLocationManager;

    public LocationProvider(final Context context) {
        this.mLocationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        this.mGeocoder = new Geocoder(context);

        final Criteria criteria = new Criteria();
        this.mBestProvider = this.mLocationManager.getBestProvider(criteria,
                true);

        final List<String> providers = this.mLocationManager.getProviders(
                criteria, true);

        for (final String provider : providers) {
            // mLocationManager.requestLocationUpdates(provider, 0, 0, this);
        }
    }

    /**
     * @return the mBestProvider
     */
    public String getBestProvider() {
        final Criteria criteria = new Criteria();
        this.mBestProvider = this.mLocationManager.getBestProvider(criteria,
                true);

        return this.mBestProvider;
    }

}
