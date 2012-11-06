package com.idamobile.map;

import android.graphics.drawable.Drawable;

public class OverlayItem implements OverlayItemBase {

    private Drawable marker;
    private UniversalGeoPoint geoPoint;
    private CharSequence title;
    private CharSequence snippet;

    public OverlayItem(IGeoPoint geoPoint, CharSequence title) {
        this(geoPoint, title, null);
    }

    public OverlayItem(IGeoPoint geoPoint, CharSequence title, CharSequence snippet) {
        this(geoPoint, title, snippet, null);
    }

    public OverlayItem(IGeoPoint geoPoint, CharSequence title, CharSequence snippet, Drawable marker) {
        this.geoPoint = new UniversalGeoPoint(geoPoint);
        this.title = title;
        this.snippet = snippet;
        this.marker = marker;
    }

    @Override
    public Drawable getMarker() {
        return marker;
    }

    @Override
    public IGeoPoint getGeoPoint() {
        return geoPoint;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public CharSequence getSnippet() {
        return snippet;
    }

}