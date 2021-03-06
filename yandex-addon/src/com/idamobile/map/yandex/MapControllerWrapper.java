package com.idamobile.map.yandex;

import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;
import com.idamobile.map.UniversalGeoRect;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapModel;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

class MapControllerWrapper implements MapControllerBase {

    private MapController mapController;
    private MapViewWrapper mapViewWrapper;

    public MapControllerWrapper(MapViewWrapper mapViewWrapper) {
        this.mapViewWrapper = mapViewWrapper;
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
    public void animateTo(IGeoPoint center, float zoomLevel) {
        mapController.setPositionAnimationTo(new UniversalGeoPoint(center).createYandexPoint(), zoomLevel);
    }

    @Override
    public float getZoomLevel() {
        return mapController.getZoomCurrent();
    }

    @Override
    public float getMaxZoomLevel() {
        return MapModel.MAX_ZOOM;
    }

    @Override
    public void setZoomLevel(float zoomLevel) {
        mapController.setZoomCurrent(zoomLevel);
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
        GeoPoint yandexPoint = new UniversalGeoPoint(span).createYandexPoint();
        mapController.setZoomToSpan(yandexPoint.getLat(), yandexPoint.getLon());
    }

    @Override
    public UniversalGeoRect getVisibleRegion() {
        GeoPoint rightTopCorner = mapController.getGeoPoint(new ScreenPoint(mapController.getMapView().getWidth(), 0));
        GeoPoint leftBottomCorner = mapController.getGeoPoint(new ScreenPoint(0, mapController.getMapView().getHeight()));
        return new UniversalGeoRect(new UniversalGeoPoint(rightTopCorner), new UniversalGeoPoint(leftBottomCorner));
    }
}