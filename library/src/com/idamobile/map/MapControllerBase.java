package com.idamobile.map;


public interface MapControllerBase {

    IGeoPoint getMapCenter();

    void setMapCenter(IGeoPoint center);

    void animateTo(IGeoPoint center);

    void animateTo(IGeoPoint center, float zoomLevel);

    float getZoomLevel();

    float getMaxZoomLevel();

    void zoomToSpan(IGeoPoint span);

    void setZoomLevel(float zoomLevel);

    void zoomIn();

    void zoomOut();

    UniversalGeoRect getVisibleRegion();

}