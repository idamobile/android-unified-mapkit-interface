package com.idamobile.map;

import java.util.HashSet;
import java.util.Set;

import android.view.MotionEvent;

public abstract class AbstractMyLocationOverlay implements MyLocationOverlayBase {

    private Set<MyLocationListener> locationListeners = new HashSet<MyLocationOverlayBase.MyLocationListener>();

    @Override
    public boolean onTouchEvent(MotionEvent ev, MapViewBase mapView) {
        return false;
    }

    @Override
    public boolean onTap(IGeoPoint geoPoint, MapViewBase mapView) {
        return false;
    }

    @Override
    public void registerMyLocationListener(MyLocationListener myLocationListener) {
        locationListeners.add(myLocationListener);
    }

    @Override
    public void unregisterMyLocationListener(MyLocationListener myLocationListener) {
        locationListeners.remove(myLocationListener);
    }

    protected void notifyMyLocationListeners(IGeoPoint geoPoint) {
        for (MyLocationListener listener : locationListeners) {
            listener.onMyLocationChanged(geoPoint);
        }
    }
}
