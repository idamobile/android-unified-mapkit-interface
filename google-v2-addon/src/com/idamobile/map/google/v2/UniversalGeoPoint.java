package com.idamobile.map.google.v2;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import com.idamobile.map.IGeoPoint;

public class UniversalGeoPoint extends com.idamobile.map.UniversalGeoPoint {

    protected UniversalGeoPoint() {
        super();
    }

    public UniversalGeoPoint(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public UniversalGeoPoint(LatLng geoPoint) {
        latE6 = fromDoubleTo1e6(geoPoint.latitude);
        longE6 = fromDoubleTo1e6(geoPoint.longitude);

        latitude = geoPoint.latitude;
        longitude = geoPoint.longitude;
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

    public LatLng createGoogleV2Point() {
        return new LatLng(latitude, longitude);
    }

}