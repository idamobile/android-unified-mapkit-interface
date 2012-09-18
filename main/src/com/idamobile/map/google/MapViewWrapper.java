package com.idamobile.map.google;

import android.content.Context;
import android.graphics.Point;

import com.google.android.maps.MapView;
import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;
import com.idamobile.map.MapViewBase;
import com.idamobile.map.MyLocationOverlayBase;
import com.idamobile.map.OverlayBase;
import com.idamobile.map.UniversalGeoPoint;

public class MapViewWrapper implements MapViewBase {

    private MapView mapView;
    private MapControllerWrapper mapControllerWrapper;
    private OverlayManager overlayManager;

    public MapViewWrapper(MapView mapView) {
        this.mapView = mapView;
        this.mapControllerWrapper = new MapControllerWrapper(mapView);
        this.overlayManager = new OverlayManager(this);
    }

    @Override
    public Context getContext() {
        return mapView.getContext();
    }

    @Override
    public MapView getView() {
        return mapView;
    }

    @Override
    public MapControllerBase getController() {
        return mapControllerWrapper;
    }

    @Override
    public int getOverlayCount() {
        return mapView.getOverlays().size();
    }

    @Override
    public OverlayBase getOverlay(int index) {
        return overlayManager.getOverlay(index);
    }

    @Override
    public void addOverlay(OverlayBase overlay) {
        overlayManager.addOverlay(overlay);
    }

    @Override
    public boolean removeOverlay(OverlayBase overlay) {
        return overlayManager.removeOverlay(overlay);
    }

    @Override
    public void addMyLocationOverlay() {
        overlayManager.addMyLocationOverlay();
    }

    @Override
    public MyLocationOverlayBase getMyLocationOverlay() {
        return overlayManager.getMyLocationOverlay();
    }

    @Override
    public IGeoPoint convertScreenPoint(Point point) {
        return new UniversalGeoPoint(mapView.getProjection().fromPixels(point.x, point.y));
    }

    @Override
    public Point convertGeoPoint(IGeoPoint geoPoint) {
        Point result = new Point();
        mapView.getProjection().toPixels(new UniversalGeoPoint(geoPoint).createGooglePoint(), result);
        return result;
    }

}