package com.idamobile.map;

import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.View;

public interface MapViewBase extends Iterable<OverlayBase> {

    Context getContext();

    View getView();

    void setGestureDetector(GestureDetector detector);

    MapControllerBase getController();

    int getOverlayCount();

    OverlayBase getOverlay(int index);

    void addOverlay(OverlayBase overlay);

    boolean removeOverlay(OverlayBase overlay);

    void removeAllOverlays();

    boolean containsOverlay(OverlayBase overlay);

    void addMyLocationOverlay();

    MyLocationOverlayBase getMyLocationOverlay();

    IGeoPoint convertScreenPoint(Point point);

    Point convertGeoPoint(IGeoPoint geoPoint);

    boolean hasZoomController();

    void setZoomControllerVisible(boolean visible);

    boolean isZoomControllerVisible();

    void invalidate();

    boolean isAvailable();

}