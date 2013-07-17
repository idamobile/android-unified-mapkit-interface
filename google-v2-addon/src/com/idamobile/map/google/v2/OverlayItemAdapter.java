package com.idamobile.map.google.v2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idamobile.map.OverlayItemBase;
import com.idamobile.map.OverlayItemBaseV2;

class OverlayItemAdapter {

    private Drawable defaultMarker;
    private float anchorU;
    private float anchorV;

    public OverlayItemAdapter(Drawable defaultMarker, float anchorU, float anchorV) {
        this.defaultMarker = defaultMarker;
        this.anchorU = anchorU;
        this.anchorV = anchorV;
    }

    public MarkerOptions getItem(OverlayItemBase item) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.anchor(
                item instanceof OverlayItemBaseV2 ? ((OverlayItemBaseV2) item).getMarkerAnchorU() : anchorU,
                item instanceof OverlayItemBaseV2 ? ((OverlayItemBaseV2) item).getMarkerAnchorV() : anchorV);
        if (item.getTitle() != null) {
            markerOptions.title(item.getTitle().toString());
        }
        if (item.getSnippet() != null) {
            markerOptions.snippet(item.getSnippet().toString());
        }
        markerOptions.position(new UniversalGeoPoint(item.getGeoPoint()).createGoogleV2Point());
        Drawable itemMarker = item.getMarker();
        if (itemMarker == null) {
            itemMarker = defaultMarker;
        }
        if (itemMarker != null) {
            if (itemMarker instanceof BitmapDrawable) {
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(((BitmapDrawable) itemMarker).getBitmap()));
            } else {
                int width = itemMarker.getBounds().width();
                if (width <= 0) {
                    width = itemMarker.getIntrinsicWidth();
                }
                int height = itemMarker.getBounds().height();
                if (height <= 0) {
                    height = itemMarker.getIntrinsicHeight();
                }

                if (width > 0 && height > 0) {
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    itemMarker.draw(canvas);
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                } else {
                    throw new IllegalArgumentException("google maps v2 addon supports only BitmapDrawables and drawables with positive width and height for map item marker");
                }
            }
        }
        return markerOptions;
    }

}