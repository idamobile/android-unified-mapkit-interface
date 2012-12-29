package com.idamobile.map.google;

import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.google.android.maps.MapView;
import com.idamobile.map.*;

import java.util.Iterator;

public class MapViewWrapper implements MapViewBase {

    private MapView mapView;
    private MapControllerWrapper mapControllerWrapper;
    private OverlayManager overlayManager;
    private GestureDetector detector;


    public MapViewWrapper(final MapView mapView) {
        this.mapView = mapView;
        this.mapControllerWrapper = new MapControllerWrapper(mapView);
        this.overlayManager = new OverlayManager(this);

        final GestureDetector gd = new GestureDetector(mapView.getContext(), new SimpleOnGestureListener());
        gd.setOnDoubleTapListener(new OnDoubleTapListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mapView.getController().zoomInFixing((int) e.getX(), (int) e.getY());
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }

        });
        mapView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean res = false;
                if (detector != null) {
                    res = detector.onTouchEvent(event);
                }
                return res || gd.onTouchEvent(event);
            }
        });
    }

    @Override
    public Context getContext() {
        return mapView.getContext();
    }

    @Override
    public void setGestureDetector(GestureDetector detector) {
        this.detector = detector;
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
        return mapView.getOverlays().size();
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
        return new UniversalGeoPoint(mapView.getProjection().fromPixels(point.x, point.y));
    }

    @Override
    public Point convertGeoPoint(IGeoPoint geoPoint) {
        Point result = new Point();
        mapView.getProjection().toPixels(new UniversalGeoPoint(geoPoint).createGooglePoint(), result);
        return result;
    }

    @Override
    public boolean hasZoomController() {
        return true;
    }

    @Override
    public void setZoomControllerVisible(boolean visible) {
        if (visible) {
            if (mapView.getZoomButtonsController() != null) {
                mapView.getZoomButtonsController().setVisible(true);
            } else {
                mapView.setBuiltInZoomControls(true);
            }
        } else {
            if (mapView.getZoomButtonsController() != null) {
                mapView.getZoomButtonsController().setVisible(false);
            }
        }
    }

    @Override
    public boolean isZoomControllerVisible() {
        return mapView.getZoomButtonsController() != null && mapView.getZoomButtonsController().isVisible();
    }

    @Override
    public void invalidate() {
        mapView.invalidate();
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

}