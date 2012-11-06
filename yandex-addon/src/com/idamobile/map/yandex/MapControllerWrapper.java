package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapModel;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;

class MapControllerWrapper implements MapControllerBase {

    private static final long ZOOM_ANIMATION_FRAME_DURATION = 50;

    private MapController mapController;
    private boolean zoomLevelAnimationStopped = true;

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
        return (int) mapController.getZoomCurrent();
    }

    @Override
    public int getMaxZoomLevel() {
        return MapModel.MAX_ZOOM;
    }

    @Override
    public void setZoomLevel(int zoomLevel) {
        setZoomLevelInternal(zoomLevel, true);
    }

    private void setZoomLevelInternal(int zoomLevel, boolean fromUser) {
        if (fromUser) {
            zoomLevelAnimationStopped = true;
        }
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
        zoomLevelAnimationStopped = false;
        zoomToSpanIter(span);
    }

    private void zoomToSpanIter(IGeoPoint span) {
        if (zoomLevelAnimationStopped) {
            return;
        }

        setZoomLevelInternal(getZoomLevel() - 1, false);
        int latSpan = getLatitudeSpan();
        int lngSpan = getLongitudeSpan();
        if ((latSpan < span.getLat() || lngSpan < span.getLng()) && getZoomLevel() != 1) {
            scheduleNextZoomFrame(span);
        }
    }

    private void scheduleNextZoomFrame(final IGeoPoint span) {
        mapController.getMapView().postDelayed(new Runnable() {
            @Override
            public void run() {
                zoomToSpanIter(span);
            }
        }, ZOOM_ANIMATION_FRAME_DURATION);
    }

    @Override
    public int getLatitudeSpan() {
        GeoPoint leftTopCorner = mapController.getGeoPoint(new ScreenPoint(0, 0));
        GeoPoint rightBottomCorner = mapController.getGeoPoint(
                new ScreenPoint(mapController.getMapView().getWidth(), mapController.getMapView().getHeight()));
        return Math.abs(UniversalGeoPoint.fromDoubleTo1e6(rightBottomCorner.getLat() - leftTopCorner.getLat()));
    }

    @Override
    public int getLongitudeSpan() {
        GeoPoint leftTopCorner = mapController.getGeoPoint(new ScreenPoint(0, 0));
        GeoPoint rightBottomCorner = mapController.getGeoPoint(
                new ScreenPoint(mapController.getMapView().getWidth(), mapController.getMapView().getHeight()));
        return Math.abs(UniversalGeoPoint.fromDoubleTo1e6(rightBottomCorner.getLon() - leftTopCorner.getLon()));
    }

}