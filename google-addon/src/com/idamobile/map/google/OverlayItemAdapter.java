package com.idamobile.map.google;

import android.graphics.drawable.Drawable;

import com.google.android.maps.OverlayItem;
import com.idamobile.map.OverlayItemBase;

class OverlayItemAdapter {

    private Drawable defaultMarker;

    public OverlayItemAdapter(Drawable defaultMarker) {
        this.defaultMarker = defaultMarker;
    }

    public OverlayItem getItem(OverlayItemBase item) {
        OverlayItem overlayItem = new OverlayItem(
                new UniversalGeoPoint(item.getGeoPoint()).createGooglePoint(),
                item.getTitle() != null ? item.getTitle().toString() : null,
                        item.getSnippet() != null ? item.getSnippet().toString() : null);
        overlayItem.setMarker(item.getMarker() != null ? item.getMarker() : defaultMarker);
        return overlayItem;
    }

}