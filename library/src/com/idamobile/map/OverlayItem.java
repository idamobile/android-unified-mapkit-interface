package com.idamobile.map;

import android.graphics.drawable.Drawable;

public class OverlayItem implements OverlayItemBaseV2 {

    private Drawable marker;
    private UniversalGeoPoint geoPoint;
    private CharSequence title;
    private CharSequence snippet;
    private float anchorU;
    private float anchorV;

    public OverlayItem(IGeoPoint geoPoint, CharSequence title) {
        this(geoPoint, title, null);
    }

    public OverlayItem(IGeoPoint geoPoint, CharSequence title, CharSequence snippet) {
        this(geoPoint, title, snippet, null);
    }

    public OverlayItem(IGeoPoint geoPoint, CharSequence title, CharSequence snippet, Drawable marker) {
        this(geoPoint, title, snippet, marker, 0.5f, 1f);
    }

    public OverlayItem(IGeoPoint geoPoint, CharSequence title, CharSequence snippet, Drawable marker, float anchorU, float anchorV) {
        this.anchorU = anchorU;
        this.anchorV = anchorV;
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
    public float getMarkerAnchorU() {
        return anchorU;
    }

    @Override
    public float getMarkerAnchorV() {
        return anchorV;
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