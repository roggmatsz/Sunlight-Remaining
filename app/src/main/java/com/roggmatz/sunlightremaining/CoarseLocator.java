package com.roggmatz.sunlightremaining;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

/**
 * Created by roggmatz on 11/19/15.
 */
public class CoarseLocator implements LocationListener {
    final View localView;
    public double latitude;
    public double longitude;

    public CoarseLocator(View view) {
        localView = view;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.i("CoarseLocator.class", "Location acquired.");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Snackbar.make(localView, "Location is enabled", Snackbar.LENGTH_LONG);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Snackbar.make(localView, "Location is disabled", Snackbar.LENGTH_LONG);
    }
}
