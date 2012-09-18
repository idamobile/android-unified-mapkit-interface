package com.idamobile.map.google;

import android.location.Location;

import com.google.android.maps.MyLocationOverlay;
import com.idamobile.map.AbstractMyLocationOverlay;
import com.idamobile.map.IGeoPoint;
import com.idamobile.map.UniversalGeoPoint;

class MyLocationOverlayAdapter extends AbstractMyLocationOverlay {

    private MyLocationOverlay resultOverlay;

    public MyLocationOverlayAdapter(MapViewWrapper mapViewWrapper) {
        resultOverlay = new MyLocationOverlay(mapViewWrapper.getContext(),
                mapViewWrapper.getView()) {
            @Override
            public synchronized void onLocationChanged(Location arg0) {
                super.onLocationChanged(arg0);
                notifyMyLocationListeners(new UniversalGeoPoint(arg0));
            }
        };
    }

    public MyLocationOverlay getResultOverlay() {
        return resultOverlay;
    }

    @Override
    public void enableMyLocation() {
        resultOverlay.enableMyLocation();
    }

    @Override
    public void disableMyLocation() {
        resultOverlay.disableMyLocation();
    }

    @Override
    public boolean isMyLocationEnabled() {
        return resultOverlay.isMyLocationEnabled();
    }

    @Override
    public IGeoPoint getMyLocation() {
        return resultOverlay.getMyLocation() != null
                ? new UniversalGeoPoint(resultOverlay.getMyLocation())
        : null;
    }

}