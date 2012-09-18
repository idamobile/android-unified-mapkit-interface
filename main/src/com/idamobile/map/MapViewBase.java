package com.idamobile.map;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

public interface MapViewBase {

    Context getContext();

    View getView();

    MapControllerBase getController();

    int getOverlayCount();

    OverlayBase getOverlay(int index);

    void addOverlay(OverlayBase overlay);

    boolean removeOverlay(OverlayBase overlay);

    void addMyLocationOverlay();

    MyLocationOverlayBase getMyLocationOverlay();

    IGeoPoint convertScreenPoint(Point point);

    Point convertGeoPoint(IGeoPoint geoPoint);

    boolean hasZoomController();

    void setZoomControllerVisible(boolean visible);

    boolean isZoomControllerVisible();

}