package com.idamobile.map.google.v2;

import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.idamobile.map.*;

import java.util.Iterator;

public class MapFragmentWrapper implements MapViewBase {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private MapControllerWrapper controllerWrapper;
    private OverlayManager overlayManager;

    private boolean touchListenerWasSet;
    private GestureDetector detector;

    public MapFragmentWrapper(final SupportMapFragment mapFragment) {
        this.mapFragment = mapFragment;
        init();
    }

    private boolean innerCall;

    private void init() {
        if (!innerCall) {
            innerCall = true;
            if (googleMap == null) {
                googleMap = mapFragment.getMap();

                if (googleMap != null) {
                    googleMap.getUiSettings().setAllGesturesEnabled(true);
                }
            }

            if (googleMap != null && controllerWrapper == null) {
                controllerWrapper = new MapControllerWrapper(googleMap);
            }

            if (googleMap != null && overlayManager == null) {
                overlayManager = new OverlayManager(this);
            }

            if (mapFragment.getView() != null && !touchListenerWasSet) {
                mapFragment.getView().setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        boolean res = false;
                        if (detector != null) {
                            res = detector.onTouchEvent(event);
                        }
                        return res;
                    }
                });
                touchListenerWasSet = true;
            }
            innerCall = false;
        }
    }

    @Override
    public Context getContext() {
        return mapFragment.getActivity();
    }

    @Override
    public void setGestureDetector(GestureDetector detector) {
        this.detector = detector;
        init();
    }

    @Override
    public View getView() {
        init();
        return mapFragment.getView();
    }

    public GoogleMap getGoogleMap() {
        init();
        return googleMap;
    }

    @Override
    public MapControllerBase getController() {
        init();
        return controllerWrapper;
    }

    public OverlayManager getOverlayManager() {
        return overlayManager;
    }

    @Override
    public int getOverlayCount() {
        init();
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
        return new UniversalGeoPoint(getGoogleMap().getProjection().fromScreenLocation(point));
    }

    @Override
    public Point convertGeoPoint(IGeoPoint geoPoint) {
        return getGoogleMap().getProjection().toScreenLocation(new UniversalGeoPoint(geoPoint).createGoogleV2Point());
    }

    @Override
    public boolean hasZoomController() {
        return true;
    }

    @Override
    public void setZoomControllerVisible(boolean visible) {
        init();
        googleMap.getUiSettings().setZoomControlsEnabled(visible);
    }

    @Override
    public boolean isZoomControllerVisible() {
        return googleMap.getUiSettings().isZoomControlsEnabled();
    }

    @Override
    public void invalidate() {
        getView().invalidate();
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