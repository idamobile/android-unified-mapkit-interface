package com.idamobile.map.google;

import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;
import com.idamobile.map.ZoomAnimationTask;

class MapControllerWrapper implements MapControllerBase {

    private MapController mapController;
    private MapView mapView;

    private ZoomAnimationTask zoomTask;

    public MapControllerWrapper(MapView mapView) {
        this.mapView = mapView;
        this.mapController = mapView.getController();
    }

    @Override
    public IGeoPoint getMapCenter() {
        return new UniversalGeoPoint(mapView.getMapCenter());
    }

    @Override
    public void setMapCenter(IGeoPoint center) {
        mapController.setCenter(new UniversalGeoPoint(center).createGooglePoint());
    }

    @Override
    public void animateTo(IGeoPoint center) {
        mapController.animateTo(new UniversalGeoPoint(center).createGooglePoint());
    }

    @Override
    public void animateTo(IGeoPoint center, float zoomLevel) {
        if (zoomLevel == getZoomLevel()) {
            animateTo(center);
        } else {
            if (zoomTask != null) {
                zoomTask.cancel();
            }
            zoomTask = new ZoomAnimationTask(center, zoomLevel, this, mapView.getHandler());
            zoomTask.start();
        }
    }

    @Override
    public float getZoomLevel() {
        return mapView.getZoomLevel();
    }

    @Override
    public float getMaxZoomLevel() {
        return mapView.getMaxZoomLevel();
    }

    @Override
    public void setZoomLevel(float zoomLevel) {
        mapController.setZoom((int) zoomLevel);
    }

    @Override
    public void zoomIn() {
        mapController.zoomIn();
    }

    @Override
    public void zoomOut() {
        mapController.zoomOut();
    }

    @Override
    public void zoomToSpan(IGeoPoint span) {
        mapController.zoomToSpan(span.getLat(), span.getLng());
    }

    @Override
    public int getLatitudeSpan() {
        return mapView.getLatitudeSpan();
    }

    @Override
    public int getLongitudeSpan() {
        return mapView.getLongitudeSpan();
    }
}