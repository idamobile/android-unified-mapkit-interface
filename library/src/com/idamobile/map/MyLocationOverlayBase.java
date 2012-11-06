package com.idamobile.map;


public interface MyLocationOverlayBase extends OverlayBase {

    public interface MyLocationListener {
        void onMyLocationChanged(IGeoPoint location);
    }

    void enableMyLocation();

    void disableMyLocation();

    boolean isMyLocationEnabled();

    IGeoPoint getMyLocation();

    void registerMyLocationListener(MyLocationListener myLocationListener);

    void unregisterMyLocationListener(MyLocationListener myLocationListener);

}