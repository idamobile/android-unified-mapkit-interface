package com.idamobile.map.yandex;

import android.location.Location;

import com.idamobile.map.IGeoPoint;

public class UniversalGeoPoint extends com.idamobile.map.UniversalGeoPoint {

    public UniversalGeoPoint(com.google.android.maps.GeoPoint geoPoint) {
        super(geoPoint);
    }

    public UniversalGeoPoint(IGeoPoint geoPoint) {
        super(geoPoint);
    }

    public UniversalGeoPoint(Location location) {
        super(location);
    }

    public UniversalGeoPoint(ru.yandex.yandexmapkit.utils.GeoPoint geoPoint) {
        latitude = geoPoint.getLat();
        longitude = geoPoint.getLon();

        latE6 = fromDoubleTo1e6(latitude);
        longE6 = fromDoubleTo1e6(longitude);
    }

    public ru.yandex.yandexmapkit.utils.GeoPoint createYandexPoint() {
        return new ru.yandex.yandexmapkit.utils.GeoPoint(latitude, longitude);
    }
}
