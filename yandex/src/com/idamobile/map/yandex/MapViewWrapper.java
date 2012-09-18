package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.utils.ScreenPoint;
import android.content.Context;
import android.graphics.Point;

import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;
import com.idamobile.map.MapViewBase;
import com.idamobile.map.MyLocationOverlayBase;
import com.idamobile.map.OverlayBase;

public class MapViewWrapper implements MapViewBase {

    private MapView mapView;
    private MapControllerWrapper mapControllerWrapper;
    private OverlayManager overlayManager;

    public MapViewWrapper(MapView mapView) {
        this.mapView = mapView;
        this.mapControllerWrapper = new MapControllerWrapper(this);
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
        return overlayManager.getOverlayCount();
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
        return new UniversalGeoPoint(mapView.getMapController().getGeoPoint(new ScreenPoint(point.x, point.y)));
    }

    @Override
    public Point convertGeoPoint(IGeoPoint geoPoint) {
        ScreenPoint screenPoint = mapView.getMapController().getScreenPoint(
                new UniversalGeoPoint(geoPoint).createYandexPoint());
        return new Point((int) screenPoint.getX(), (int) screenPoint.getY());
    }

    @Override
    public boolean hasZoomController() {
        return false;
    }

    @Override
    public void setZoomControllerVisible(boolean visible) {
    }

    @Override
    public boolean isZoomControllerVisible() {
        return false;
    }
}