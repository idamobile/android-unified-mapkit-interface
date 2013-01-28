package com.idamobile.map;

public class UniversalGeoRect {

    public final UniversalGeoPoint northeast;
    public final UniversalGeoPoint southwest;

    public UniversalGeoRect(UniversalGeoPoint northeast, UniversalGeoPoint southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }
}