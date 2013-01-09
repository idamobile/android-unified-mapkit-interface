package com.idamobile.map.google.v2;

import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.idamobile.map.BalloonOverlayExtension;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.MyLocationOverlayBase;
import com.idamobile.map.OverlayBase;

import java.util.*;

class OverlayManager implements Iterable<OverlayBase> {

    private List<OverlayBase> overlays = new ArrayList<OverlayBase>();
    private GoogleMap googleMap;
    private MapFragmentWrapper mapFragmentWrapper;

    private Map<OverlayBase, OverlayAdapter> adoptOverlays = new HashMap<OverlayBase, OverlayAdapter>();

    private Set<GoogleMap.OnMarkerClickListener> onMarkerClickListeners = new HashSet<GoogleMap.OnMarkerClickListener>();
    private Set<GoogleMap.OnInfoWindowClickListener> onInfoWindowClickListeners = new HashSet<GoogleMap.OnInfoWindowClickListener>();
    private Set<GoogleMap.InfoWindowAdapter> infoWindowAdapters = new HashSet<GoogleMap.InfoWindowAdapter>();

    public OverlayManager(MapFragmentWrapper mapFragmentWrapper) {
        this.mapFragmentWrapper = mapFragmentWrapper;
        this.googleMap = mapFragmentWrapper.getGoogleMap();
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (GoogleMap.OnMarkerClickListener listener : onMarkerClickListeners) {
                    if (listener.onMarkerClick(marker)) {
                        return true;
                    }
                }
                return false;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                for (GoogleMap.OnInfoWindowClickListener infoWindowClickListener : onInfoWindowClickListeners) {
                    infoWindowClickListener.onInfoWindowClick(marker);
                }
            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View result = null;
                for (GoogleMap.InfoWindowAdapter adapter : infoWindowAdapters) {
                    result = adapter.getInfoWindow(marker);
                    if (result != null) {
                        break;
                    }
                }
                return result;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View content = null;
                for (GoogleMap.InfoWindowAdapter adapter : infoWindowAdapters) {
                    content = adapter.getInfoContents(marker);
                    if (content != null) {
                        break;
                    }
                }
                return content;
            }
        });
    }

    public int getOverlayCount() {
        return overlays.size();
    }

    public OverlayBase getOverlay(int index) {
        return overlays.get(index);
    }

    public void registerOnMarkerClickListener(GoogleMap.OnMarkerClickListener markerClickListener) {
        onMarkerClickListeners.add(markerClickListener);
    }

    public void unregisterOnMarkerClickListener(GoogleMap.OnMarkerClickListener markerClickListener) {
        onMarkerClickListeners.remove(markerClickListener);
    }

    public void registerOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener) {
        onInfoWindowClickListeners.add(onInfoWindowClickListener);
    }

    public void unregisterOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener) {
        onInfoWindowClickListeners.remove(onInfoWindowClickListener);
    }

    public void registerInfoWindowAdapter(GoogleMap.InfoWindowAdapter infoWindowAdapter) {
        infoWindowAdapters.add(infoWindowAdapter);
    }

    public void unregisterInfoWindowAdapter(GoogleMap.InfoWindowAdapter infoWindowAdapter) {
        infoWindowAdapters.remove(infoWindowAdapter);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addOverlay(OverlayBase overlay) {
        if (!overlays.contains(overlay)) {
            OverlayAdapter adapter = null;
            if (overlay instanceof ItemizedOverlayBase) {
                if (overlay instanceof BalloonOverlayExtension) {
                    adapter = new BalloonOverlayAdapter(mapFragmentWrapper,
                            (ItemizedOverlayBase) overlay);
                } else {
                    adapter = new ItemizedOverlayAdapter(mapFragmentWrapper,
                            (ItemizedOverlayBase) overlay);
                }
            } else {
                throw new IllegalArgumentException("Unable to process overlay class " + overlay.getClass()
                        + ". Supports only instances of " + ItemizedOverlayBase.class);
            }

            if (adapter != null) {
                MarkerList resultOverlay = adapter.getResultOverlay();
                adoptOverlays.put(overlay, adapter);
                overlays.add(overlay);
            }
        }
    }

    public boolean removeOverlay(OverlayBase overlay) {
        if (overlays.remove(overlay)) {
            OverlayAdapter adapter = adoptOverlays.get(overlay);
            MarkerList adoptOverlay = adapter.getResultOverlay();
            adoptOverlay.removeAll();
            adoptOverlays.remove(overlay);
            adapter.release();
            return true;
        } else {
            return false;
        }
    }

    public void addMyLocationOverlay() {
        if (getMyLocationOverlay() == null) {
            MyLocationOverlayAdapter adapter = new MyLocationOverlayAdapter(googleMap);
            overlays.add(adapter);
            adapter.enableMyLocation();
            adoptOverlays.put(adapter, adapter);
        }
    }

    public MyLocationOverlayBase getMyLocationOverlay() {
        for (OverlayBase overlayBase : overlays) {
            if (overlayBase instanceof MyLocationOverlayBase) {
                return (MyLocationOverlayBase) overlayBase;
            }
        }
        return null;
    }

    public void removeAllOverlays() {
        List<OverlayBase> overlays = new ArrayList<OverlayBase>(this.overlays);
        for (OverlayBase overlay : overlays) {
            removeOverlay(overlay);
        }
    }

    @Override
    public Iterator<OverlayBase> iterator() {
        return Collections.unmodifiableList(overlays).iterator();
    }

    public boolean containsOverlay(OverlayBase overlay) {
        return overlays.contains(overlay);
    }
}