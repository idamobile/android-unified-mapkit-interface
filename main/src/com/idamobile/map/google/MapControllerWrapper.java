package com.idamobile.map.google;

import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;
import com.idamobile.map.UniversalGeoPoint;

class MapControllerWrapper implements MapControllerBase {

    private MapController mapController;
    private MapView mapView;

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
    public int getZoomLevel() {
        return mapView.getZoomLevel();
    }

    @Override
    public int getMaxZoomLevel() {
        return mapView.getMaxZoomLevel();
    }

    @Override
    public void setZoomLevel(int zoomLevel) {
        mapController.setZoom(zoomLevel);
    }

    @Override
    public void zoomIn() {
        mapController.zoomIn();
    }

    @Override
    public void zoomOut() {
        mapController.zoomOut();
    }

}