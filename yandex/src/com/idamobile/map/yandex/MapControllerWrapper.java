package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.MapController;

import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;

class MapControllerWrapper implements MapControllerBase {

    public static final int MAX_ZOOM_LEVELS = 20;

    private MapController mapController;

    public MapControllerWrapper(MapViewWrapper mapViewWrapper) {
        this.mapController = mapViewWrapper.getView().getMapController();
    }

    @Override
    public IGeoPoint getMapCenter() {
        return new UniversalGeoPoint(mapController.getMapCenter());
    }

    @Override
    public void setMapCenter(IGeoPoint center) {
        mapController.setPositionNoAnimationTo(new UniversalGeoPoint(center).createYandexPoint());
    }

    @Override
    public void animateTo(IGeoPoint center) {
        mapController.setPositionAnimationTo(new UniversalGeoPoint(center).createYandexPoint());
    }

    @Override
    public int getZoomLevel() {
        return (int) (mapController.getZoomCurrent() * MAX_ZOOM_LEVELS);
    }

    @Override
    public int getMaxZoomLevel() {
        return MAX_ZOOM_LEVELS;
    }

    @Override
    public void setZoomLevel(int zoomLevel) {
        mapController.setZoomCurrent(zoomLevel / (float) MAX_ZOOM_LEVELS);
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