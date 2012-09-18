package com.idamobile.map;

import android.view.MotionEvent;

public interface OverlayBase {

    /**
     * Handle a touch event. By default does nothing and returns false.
     * 
     * @param ev
     *            The motion event
     * @param mapView
     *            the MapView that generated the touch event
     * @return True if the tap was handled by this overlay.
     */
    boolean onTouchEvent(MotionEvent ev, MapViewBase mapView);

    /**
     * Handle a "tap" event. This can be either a touchscreen tap anywhere on the map, or a trackball click on the
     * center of the map. By default does nothing and returns false.
     * 
     * @param geoPoint
     *            The point that has been tapped.
     * @param mapView
     *            the MapView that generated the tap event
     * @return True if the tap was handled by this overlay.
     */
    boolean onTap(IGeoPoint geoPoint, MapViewBase mapView);

}