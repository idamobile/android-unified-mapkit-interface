package com.idamobile.map;


public interface MapControllerBase {

    IGeoPoint getMapCenter();

    void setMapCenter(IGeoPoint center);

    void animateTo(IGeoPoint center);

    void animateTo(IGeoPoint center, int zoomLevel);

    int getZoomLevel();

    int getMaxZoomLevel();

    void zoomToSpan(IGeoPoint span);

    void setZoomLevel(int zoomLevel);

    void zoomIn();

    void zoomOut();

    int getLatitudeSpan();

    int getLongitudeSpan();

}