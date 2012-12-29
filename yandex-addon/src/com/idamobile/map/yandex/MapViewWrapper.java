package com.idamobile.map.yandex;

import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.idamobile.map.*;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

import java.util.Iterator;

public class MapViewWrapper implements MapViewBase {

    private MapView mapView;
    private MapControllerWrapper mapControllerWrapper;
    private OverlayManager overlayManager;

    private boolean zoomButtonsVisible;

    public MapViewWrapper(MapView mapView) {
        this.mapView = mapView;
        this.mapControllerWrapper = new MapControllerWrapper(this);
        this.overlayManager = new OverlayManager(this);

        mapView.showFindMeButton(false);
        mapView.showJamsButton(false);
        mapView.showScaleView(false);
        mapView.showZoomButtons(zoomButtonsVisible);
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
        return true;
    }

    @Override
    public void setZoomControllerVisible(boolean visible) {
        mapView.showZoomButtons(visible);
    }

    @Override
    public boolean isZoomControllerVisible() {
        return zoomButtonsVisible;
    }

    @Override
    public void invalidate() {
        mapView.getMapController().notifyRepaint();
    }

    @Override
    public Iterator<OverlayBase> iterator() {
        return overlayManager.iterator();
    }

    @Override
    public void removeAllOverlays() {
        overlayManager.removeAllOverlays();
    }

    @Override
    public boolean containsOverlay(OverlayBase overlay) {
        return overlayManager.containsOverlay(overlay);
    }

    @Override
    public void setGestureDetector(final GestureDetector detector) {
        mapView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }
}