package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.overlay.OverlayItem;
import android.graphics.drawable.Drawable;

import com.idamobile.map.OverlayItemBase;

class SimpleOverlayItemAdapter implements OverlayItemAdapter {

    private Drawable defaultMarker;

    public SimpleOverlayItemAdapter(Drawable defaultMarker) {
        this.defaultMarker = defaultMarker;
    }

    @Override
    public OverlayItem getItem(OverlayItemBase item) {
        return new OverlayItem(
                new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint(),
                item.getMarker() != null ? item.getMarker() : defaultMarker);
    }

}