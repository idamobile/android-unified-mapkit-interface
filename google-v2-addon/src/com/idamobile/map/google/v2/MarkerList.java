package com.idamobile.map.google.v2;

import android.view.animation.Animation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MarkerList {

    private HashMap<MarkerOptions, Marker> markers;
    private GoogleMap googleMap;

    public MarkerList(GoogleMap googleMap) {
        this.googleMap = googleMap;
        markers = new HashMap<MarkerOptions, Marker>();
    }

    public void add(MarkerOptions options) {
        Marker marker = markers.get(options);
        if (marker == null) {
            markers.put(options, googleMap.addMarker(options));
        }
    }

    public void remove(MarkerOptions options) {
        Marker marker = markers.remove(options);
        if (marker != null) {
            marker.remove();
        }
    }

    public void removeAll() {
        for (Marker marker : markers.values()) {
            marker.remove();
        }
        markers.clear();
    }

    public boolean hasMarker(Marker marker) {
        return getOptionsForMarker(marker) != null;
    }

    public MarkerOptions getOptionsForMarker(Marker marker) {
        for (Map.Entry<MarkerOptions, Marker> entry : markers.entrySet()) {
            if (entry.getValue().equals(marker)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isBalloonShowing() {
        return getMarkerWithOpenBalloon() != null;
    }

    public MarkerOptions getMarkerOptionsWithOpenBalloon() {
        for (Map.Entry<MarkerOptions, Marker> entry : markers.entrySet()) {
            if (entry.getValue().isInfoWindowShown()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Marker getMarkerWithOpenBalloon() {
        MarkerOptions options = getMarkerOptionsWithOpenBalloon();
        return options != null ? markers.get(options) : null;
    }

    public void showBalloon(MarkerOptions options, Animation animation) {
        Marker marker = markers.get(options);
        if (marker != null) {
            marker.showInfoWindow();
        }
    }

    public void hideBalloon(Animation animation) {
        Marker marker = getMarkerWithOpenBalloon();
        if (marker != null) {
            marker.hideInfoWindow();
        }
    }
}
