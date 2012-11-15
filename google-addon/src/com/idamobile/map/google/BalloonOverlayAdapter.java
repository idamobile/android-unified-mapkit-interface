package com.idamobile.map.google;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.idamobile.map.AbstractBalloonController;
import com.idamobile.map.BalloonOverlayExtension;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.MapViewBase;
import com.idamobile.map.OverlayItemBase;

class BalloonOverlayAdapter<T extends OverlayItemBase> extends ItemizedOverlayAdapter<T> {

    public BalloonOverlayAdapter(MapViewBase mapViewBase, ItemizedOverlayBase<T> overlay) {
        super(mapViewBase, overlay);
        @SuppressWarnings("unchecked")
        BalloonOverlayExtension<T> overlayExtension = (BalloonOverlayExtension<T>) overlay;
        overlayExtension.setBalloonController(new AbstractBalloonController(mapViewBase.getContext()) {
            @Override
            public boolean isBalloonShowing() {
                return getResultOverlay().isBalloonShowing();
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void showBalloon(OverlayItemBase forItem, Animation animation) {
                getResultOverlay().showBalloon(getResultItem((T) forItem), animation);
            }

            @Override
            protected void hideBalloon(Animation animation) {
                getResultOverlay().hideBalloon(animation);
            }

            @Override
            public OverlayItemBase getItemWithOpenBalloon() {
                return getResultOverlay().getFocus() != null ? getOriginalItem(getResultOverlay().getFocus()) : null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ItemListOverlay wrapOverlay(final ItemizedOverlayBase<T> overlay, final MapViewBase mapViewBase) {
        final BalloonOverlayExtension<T> overlayExtension = (BalloonOverlayExtension<T>) overlay;
        return new BalloonItemListOverlay(mapViewBase, overlay.getMarker()) {
            @Override
            protected View createBalloonOverlayView(Context context) {
                return overlayExtension.getAdapter().createView(context);
            }

            @Override
            protected void bindView(Context context, View balloonView, OverlayItem item) {
                overlayExtension.getAdapter().bindView(context, balloonView, getOriginalItem(item));
            }

            @Override
            public boolean onTouchEvent(MotionEvent arg0, MapView arg1) {
                return overlay.onTouchEvent(arg0, mapViewBase) || super.onTouchEvent(arg0, arg1);
            }

            @Override
            public boolean onTap(GeoPoint arg0, MapView arg1) {
                return overlay.onTap(new UniversalGeoPoint(arg0), mapViewBase) || super.onTap(arg0, arg1);
            }
        };
    }

    @Override
    public BalloonItemListOverlay getResultOverlay() {
        return (BalloonItemListOverlay) super.getResultOverlay();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void release() {
        ((BalloonOverlayExtension<T>) getBaseOverlay()).setBalloonController(null);
        super.release();
    }
}