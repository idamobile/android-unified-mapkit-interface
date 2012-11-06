package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.overlay.location.MyLocationItem;
import ru.yandex.yandexmapkit.overlay.location.MyLocationOverlay;
import ru.yandex.yandexmapkit.overlay.location.OnMyLocationListener;

import com.idamobile.map.AbstractMyLocationOverlay;
import com.idamobile.map.IGeoPoint;
import com.idamobile.map.OverlayBase;

class MyLocationOverlayAdapter extends AbstractMyLocationOverlay implements OverlayAdapter {

    private MyLocationOverlay resultOverlay;

    public MyLocationOverlayAdapter(MapViewWrapper mapViewWrapper) {
        resultOverlay = new MyLocationOverlay(mapViewWrapper.getView().getMapController());
        resultOverlay.addMyLocationListener(new OnMyLocationListener() {
            @Override
            public void onMyLocationChange(MyLocationItem arg0) {
                notifyMyLocationListeners(new UniversalGeoPoint(arg0.getGeoPoint()));
            }
        });
    }

    @Override
    public MyLocationOverlay getResultOverlay() {
        return resultOverlay;
    }

    @Override
    public void enableMyLocation() {
        resultOverlay.setEnabled(true);
    }

    @Override
    public void disableMyLocation() {
        resultOverlay.setEnabled(false);
    }

    @Override
    public boolean isMyLocationEnabled() {
        return resultOverlay.isEnabled();
    }

    @Override
    public IGeoPoint getMyLocation() {
        return resultOverlay.getMyLocationItem() != null
                ? new UniversalGeoPoint(resultOverlay.getMyLocationItem().getGeoPoint())
        : null;
    }

    @Override
    public OverlayBase getBaseOverlay() {
        return this;
    }

    @Override
    public void release() {
    }

}