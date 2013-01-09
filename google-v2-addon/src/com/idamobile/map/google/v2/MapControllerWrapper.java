package com.idamobile.map.google.v2;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegion;
import com.idamobile.map.IGeoPoint;
import com.idamobile.map.MapControllerBase;

class MapControllerWrapper implements MapControllerBase {

    private GoogleMap googleMap;

    public MapControllerWrapper(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public IGeoPoint getMapCenter() {
        return new UniversalGeoPoint(googleMap.getCameraPosition().target);
    }

    @Override
    public void setMapCenter(IGeoPoint center) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new UniversalGeoPoint(center).createGoogleV2Point()));
    }

    @Override
    public void animateTo(IGeoPoint center) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new UniversalGeoPoint(center).createGoogleV2Point()));
    }

    @Override
    public void animateTo(IGeoPoint center, float zoomLevel) {
        if (zoomLevel == getZoomLevel()) {
            animateTo(center);
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new UniversalGeoPoint(center).createGoogleV2Point(), zoomLevel));
        }
    }

    @Override
    public float getZoomLevel() {
        return googleMap.getCameraPosition().zoom;
    }

    @Override
    public float getMaxZoomLevel() {
        return googleMap.getMaxZoomLevel();
    }

    @Override
    public void setZoomLevel(float zoomLevel) {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
    }

    @Override
    public void zoomIn() {
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    @Override
    public void zoomOut() {
        googleMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    @Override
    public void zoomToSpan(IGeoPoint span) {
        double latDelta = UniversalGeoPoint.from1e6ToDouble(span.getLat() / 2);
        double longDelta = UniversalGeoPoint.from1e6ToDouble(span.getLng() / 2);
        LatLng curPos = googleMap.getCameraPosition().target;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(
                new LatLng(curPos.latitude - latDelta, curPos.longitude - longDelta),
                new LatLng(curPos.latitude + latDelta, curPos.longitude + longDelta)),
                0));
    }

    @Override
    public int getLatitudeSpan() {
        VisibleRegion region = googleMap.getProjection().getVisibleRegion();
        return UniversalGeoPoint.fromDoubleTo1e6(Math.abs(region.farRight.latitude - region.farLeft.latitude));
    }

    @Override
    public int getLongitudeSpan() {
        VisibleRegion region = googleMap.getProjection().getVisibleRegion();
        return UniversalGeoPoint.fromDoubleTo1e6(Math.abs(region.farRight.longitude - region.farLeft.longitude));
    }
}