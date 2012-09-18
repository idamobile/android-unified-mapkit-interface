package com.idamobile.map;


public interface MapControllerBase {

    IGeoPoint getMapCenter();

    void setMapCenter(IGeoPoint center);

    void animateTo(IGeoPoint center);

    int getZoomLevel();

    int getMaxZoomLevel();

    void setZoomLevel(int zoomLevel);

    void zoomIn();

    void zoomOut();

}