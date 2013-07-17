package com.idamobile.map.yandex;

import com.idamobile.map.OverlayItemBaseV2;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import android.graphics.drawable.Drawable;

import com.idamobile.map.OverlayItemBase;

class SimpleOverlayItemAdapter implements OverlayItemAdapter {

    private Drawable defaultMarker;
    private float anchorU;
    private float anchorV;

    public SimpleOverlayItemAdapter(Drawable defaultMarker, float anchorU, float anchorV) {
        this.defaultMarker = defaultMarker;
        this.anchorU = anchorU;
        this.anchorV = anchorV;
    }

    @Override
    public OverlayItem getItem(OverlayItemBase item) {
        Drawable drawable = item.getMarker() != null ? item.getMarker() : defaultMarker;
        OverlayItem overlayItem = new OverlayItem(new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint(),
                drawable);
        float u = anchorU;
        float v = anchorV;
        if (item instanceof OverlayItemBaseV2) {
            u = ((OverlayItemBaseV2) item).getMarkerAnchorU();
            v = ((OverlayItemBaseV2) item).getMarkerAnchorV();
        }
        if (drawable.getIntrinsicHeight() != -1 && drawable.getIntrinsicWidth() != -1) {
            int x = (int) (drawable.getIntrinsicWidth() * u);
            int y = (int) (drawable.getIntrinsicHeight() * v);
            overlayItem.setOffsetX(x - overlayItem.getOffsetCenterX());
            overlayItem.setOffsetY(y - overlayItem.getOffsetCenterY());
        }
        return overlayItem;
    }

}