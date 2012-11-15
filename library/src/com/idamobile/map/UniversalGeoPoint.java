package com.idamobile.map;

import android.location.Location;

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

    public UniversalGeoPoint(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        latE6 = fromDoubleTo1e6(latitude);
        longE6 = fromDoubleTo1e6(longitude);
    }

    public UniversalGeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        latE6 = fromDoubleTo1e6(latitude);
        longE6 = fromDoubleTo1e6(longitude);
    }

    public UniversalGeoPoint(int latE6, int longE6) {
        this.latE6 = latE6;
        this.longE6 = longE6;

        latitude = from1e6ToDouble(latE6);
        longitude = from1e6ToDouble(longE6);
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

    public static double from1e6ToDouble(int coord) {
        return coord / 1e6;
    }

    public static int fromDoubleTo1e6(double coord) {
        return (int) (coord * 1e6);
    }
}