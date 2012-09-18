package com.idamobile.map.yandex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.location.MyLocationOverlay;

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
                mapView.getMapController().getOverlayManager().addOverlay(adoptOverlay);
                adoptOverlays.put(overlay, adoptOverlay);
                overlays.add(overlay);
            }
        }
    }

    public boolean removeOverlay(OverlayBase overlay) {
        if (overlays.remove(overlay)) {
            Overlay adoptOverlay = adoptOverlays.get(overlay);
            mapView.getMapController().getOverlayManager().removeOverlay(adoptOverlay);
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
            mapView.getMapController().getOverlayManager().addOverlay(adapter.getResultOverlay());
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
