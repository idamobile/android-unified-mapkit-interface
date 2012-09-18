package com.idamobile.map;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class UniversalGeoPoint implements IGeoPoint {

    protected double latitude;
    protected double longitude;

    protected int latE6;
    protected int longE6;

    public UniversalGeoPoint(IGeoPoint geoPoint) {
        latE6 = geoPoint.getLat();
        longE6 = geoPoint.getLng();

        latitude = from1e6ToDouble(latE6);
        longitude = from1e6ToDouble(longE6);
    }

    public UniversalGeoPoint(GeoPoint geoPoint) {
        latE6 = geoPoint.getLatitudeE6();
        longE6 = geoPoint.getLongitudeE6();

        latitude = from1e6ToDouble(latE6);
        longitude = from1e6ToDouble(longE6);
    }

    public UniversalGeoPoint(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        latE6 = fromDoubleTo1e6(latitude);
        longE6 = fromDoubleTo1e6(longitude);
    }

    protected UniversalGeoPoint() {
    }

    @Override
    public int getLat() {
        return latE6;
    }

    @Override
    public int getLng() {
        return longE6;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public GeoPoint createGooglePoint() {
        return new GeoPoint(latE6, longE6);
    }

    public static double from1e6ToDouble(int coord) {
        return coord / 1e6;
    }

    public static int fromDoubleTo1e6(double coord) {
        return (int) (coord * 1e6);
    }
}