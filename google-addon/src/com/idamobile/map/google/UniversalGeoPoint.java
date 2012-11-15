package com.idamobile.map.google;

import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.idamobile.map.IGeoPoint;

public class UniversalGeoPoint extends com.idamobile.map.UniversalGeoPoint {

    protected UniversalGeoPoint() {
        super();
    }

    public UniversalGeoPoint(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public UniversalGeoPoint(GeoPoint geoPoint) {
        latE6 = geoPoint.getLatitudeE6();
        longE6 = geoPoint.getLongitudeE6();

        latitude = from1e6ToDouble(latE6);
        longitude = from1e6ToDouble(longE6);
    }

    public UniversalGeoPoint(IGeoPoint geoPoint) {
        super(geoPoint);
    }

    public UniversalGeoPoint(int latE6, int longE6) {
        super(latE6, longE6);
    }

    public UniversalGeoPoint(Location location) {
        super(location);
    }

    public GeoPoint createGooglePoint() {
        return new GeoPoint(latE6, longE6);
    }

}