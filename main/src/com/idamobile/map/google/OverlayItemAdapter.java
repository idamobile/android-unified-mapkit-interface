package com.idamobile.map.google;

import com.google.android.maps.OverlayItem;
import com.idamobile.map.OverlayItemBase;
import com.idamobile.map.UniversalGeoPoint;

class OverlayItemAdapter {

    public OverlayItem getItem(OverlayItemBase item) {
        OverlayItem overlayItem = new OverlayItem(
                new UniversalGeoPoint(item.getGeoPoint()).createGooglePoint(),
                item.getTitle() != null ? item.getTitle().toString() : null,
                        item.getSnippet() != null ? item.getSnippet().toString() : null);
        overlayItem.setMarker(item.getMarker());
        return overlayItem;
    }

}