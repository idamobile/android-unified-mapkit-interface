package com.idamobile.map.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.MyLocationOverlayBase;
import com.idamobile.map.OverlayBase;

class OverlayManager {

    private List<OverlayBase> overlays = new ArrayList<OverlayBase>();
    private MapView mapView;
    private MapViewWrapper mapViewWrapper;

    private Map<OverlayBase, Overlay> adoptOverlays = new HashMap<OverlayBase, Overlay>();

    public OverlayManager(MapViewWrapper mapViewWrapper) {
        this.mapViewWrapper = mapViewWrapper;
        this.mapView = mapViewWrapper.getView();
    }

    public int getOverlayCount() {
        return overlays.size();
    }

    public OverlayBase getOverlay(int index) {
        return overlays.get(index);
    }

    @SuppressWarnings("rawtypes")
    public void addOverlay(OverlayBase overlay) {
        if (!overlays.contains(overlay)) {
            Overlay adoptOverlay = null;
            if (overlay instanceof ItemizedOverlayBase) {
                ItemizedOverlayAdapter adapter = new ItemizedOverlayAdapter(mapViewWrapper);
                adoptOverlay = adapter.getResultOverlay();
            } else {
                throw new IllegalArgumentException("Unable to process overlay class " + overlay.getClass()
                        + ". Supports only instances of " + ItemizedOverlayBase.class);
            }

            if (adoptOverlay != null) {
                mapView.getOverlays().add(adoptOverlay);
                adoptOverlays.put(overlay, adoptOverlay);
                overlays.add(overlay);
            }
        }
    }

    public boolean removeOverlay(OverlayBase overlay) {
        if (overlays.remove(overlay)) {
            Overlay adoptOverlay = adoptOverlays.get(overlay);
            mapView.getOverlays().remove(adoptOverlay);
            adoptOverlays.remove(overlay);
            return true;
        } else {
            return false;
        }
    }

    public void addMyLocationOverlay() {
        if (getMyLocationOverlay() == null) {
            MyLocationOverlayAdapter adapter = new MyLocationOverlayAdapter(mapViewWrapper);
            overlays.add(adapter);
            mapView.getOverlays().add(adapter.getResultOverlay());
            adoptOverlays.put(adapter, adapter.getResultOverlay());
        }
    }

    public MyLocationOverlayBase getMyLocationOverlay() {
        for (OverlayBase overlayBase : overlays) {
            if (overlayBase instanceof MyLocationOverlay) {
                return (MyLocationOverlayBase) overlayBase;
            }
        }
        return null;
    }

}