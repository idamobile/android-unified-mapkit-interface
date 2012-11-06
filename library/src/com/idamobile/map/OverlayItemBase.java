package com.idamobile.map;

import android.graphics.drawable.Drawable;

public interface OverlayItemBase {

    Drawable getMarker();

    IGeoPoint getGeoPoint();

    CharSequence getTitle();

    CharSequence getSnippet();

}