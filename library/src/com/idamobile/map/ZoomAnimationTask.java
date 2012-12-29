package com.idamobile.map;

import android.os.Handler;

public class ZoomAnimationTask implements Runnable {
    public static long DEFAULT_DELAY_MILLIS = 100;

    private IGeoPoint geoPoint;
    private int zoomLevel;
    private MapControllerBase mapController;
    private long delayMillis;
    private boolean canceled;

    private Handler handler;

    private int previousZoomLevel = -1;

    public ZoomAnimationTask(IGeoPoint geoPoint, int zoomLevel, MapControllerBase mapController) {
        this(geoPoint, zoomLevel, mapController, null);
    }

    public ZoomAnimationTask(IGeoPoint geoPoint, int zoomLevel, MapControllerBase mapController, Handler handler) {
        this(geoPoint, zoomLevel, mapController, handler, DEFAULT_DELAY_MILLIS);
    }

    public ZoomAnimationTask(IGeoPoint geoPoint, int zoomLevel, MapControllerBase mapController, Handler handler, long delayMillis) {
        this.geoPoint = geoPoint;
        this.zoomLevel = zoomLevel;
        this.mapController = mapController;
        this.delayMillis = delayMillis;

        if (handler != null) {
            this.handler = handler;
        } else {
            this.handler = new Handler();
        }
    }

    public void cancel() {
        canceled = true;
        handler.removeCallbacks(this);
    }

    public void start() {
        clipRequestedZoomLevelIfNeeded();
        run();
    }

    private void clipRequestedZoomLevelIfNeeded() {
        if (zoomLevel > mapController.getMaxZoomLevel()) {
            zoomLevel = mapController.getMaxZoomLevel();
        } else if (zoomLevel < 0) {
            zoomLevel = 0;
        }
    }

    @Override
    public void run() {
        int currentZoomLevel = mapController.getZoomLevel();
        if (currentZoomLevel != zoomLevel && !canceled) {
            if (currentZoomLevel > zoomLevel) {
                if (currentZoomLevel < previousZoomLevel || previousZoomLevel == -1) {
                    mapController.setMapCenter(geoPoint);
                    mapController.zoomOut();
                } else {
                    return;
                }
            } else {
                if (currentZoomLevel > previousZoomLevel || previousZoomLevel == -1) {
                    mapController.setMapCenter(geoPoint);
                    mapController.zoomIn();
                } else {
                    return;
                }
            }
            previousZoomLevel = currentZoomLevel;
            handler.postDelayed(this, delayMillis);
        }
    }
}