package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.idamobile.map.BalloonOverlayExtension.BalloonAdapter;
import com.idamobile.map.OverlayItemBase;

class BalloonOverlayItemAdapter implements OverlayItemAdapter {

    private Context context;
    private BalloonAdapter balloonAdapter;
    private Drawable defaultMarker;

    public BalloonOverlayItemAdapter(Context context, BalloonAdapter balloonAdapter, Drawable defaultMarker) {
        this.context = context;
        this.balloonAdapter = balloonAdapter;
        this.defaultMarker = defaultMarker;
    }

    @Override
    public OverlayItem getItem(final OverlayItemBase item) {
        OverlayItem result = new OverlayItem(
                new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint(),
                item.getMarker() != null ? item.getMarker() : defaultMarker);
        result.setBalloonItem(new BalloonItem(context,
                new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint()) {
            @Override
            public void inflateView(Context arg0) {
                model = (ViewGroup) balloonAdapter.createView(context);
                balloonAdapter.bindView(context, model, item);
            }
        });
        return result;
    }

}