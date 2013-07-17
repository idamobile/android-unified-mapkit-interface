package com.idamobile.map.yandex;

import android.view.View;
import com.idamobile.map.OverlayItemBaseV2;
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
    private float anchorU;
    private float anchorV;

    public BalloonOverlayItemAdapter(Context context, BalloonAdapter balloonAdapter, Drawable defaultMarker, float anchorU, float anchorV) {
        this.context = context;
        this.balloonAdapter = balloonAdapter;
        this.defaultMarker = defaultMarker;
        this.anchorU = anchorU;
        this.anchorV = anchorV;
    }

    @Override
    public OverlayItem getItem(final OverlayItemBase item) {
        Drawable drawable = item.getMarker() != null ? item.getMarker() : defaultMarker;
        OverlayItem result = new OverlayItem(new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint(),
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
            result.setOffsetX(x - result.getOffsetCenterX());
            result.setOffsetY(y - result.getOffsetCenterY());
        }
        result.setBalloonItem(new BalloonItem(context,
                new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint()) {
            @Override
            public void inflateView(Context arg0) {
                model = (ViewGroup) balloonAdapter.createView(context);
                balloonAdapter.bindView(context, model, item);
                model.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        balloonAdapter.onBalloonClick(item);
                    }
                });
            }
        });
        return result;
    }

}