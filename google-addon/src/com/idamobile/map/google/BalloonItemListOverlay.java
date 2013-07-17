package com.idamobile.map.google;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.OverlayItem;
import com.idamobile.map.BalloonOverlayExtension;
import com.idamobile.map.MapViewBase;

abstract class BalloonItemListOverlay extends ItemListOverlay {

    private MapViewBase mapViewBase;
    private MapView mapView;

    private Context context;

    private View balloonView;
    private OverlayItem currentFocusedItem;

    private Animation openAnimation;
    private Animation hideAnimation;

    public BalloonItemListOverlay(MapViewBase mapViewBase) {
        this(mapViewBase, null, 0, 0);
    }

    public BalloonItemListOverlay(MapViewBase mapViewBase, Drawable defaultMarker, float anchorU, float anchorV) {
        super(defaultMarker, anchorU, anchorV);
        this.mapViewBase = mapViewBase;
        this.mapView = (MapView) mapViewBase.getView();
        this.context = mapViewBase.getContext();
    }

    protected Context getContext() {
        return context;
    }

    public void setOpenAnimation(Animation openAnimation) {
        this.openAnimation = openAnimation;
    }

    public void setHideAnimation(Animation hideAnimation) {
        this.hideAnimation = hideAnimation;
    }

    protected void showBalloon(OverlayItem forItem, Animation animation) {
        super.setFocus(forItem);
        if (forItem != null) {
            createAndDisplayBalloonOverlay(forItem, animation);
        }
        currentFocusedItem = forItem;
    }

    protected void hideBalloon(Animation animation) {
        if (balloonView != null) {
            if (animation == null) {
                balloonView.setVisibility(View.GONE);
            } else {
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        balloonView.setVisibility(View.GONE);
                    }
                });
                balloonView.startAnimation(animation);
            }
            currentFocusedItem = null;
        }
    }

    public boolean isBalloonShowing() {
        return balloonView != null && currentFocusedItem != null && balloonView.getVisibility() == View.VISIBLE;
    }

    protected boolean onBalloonOpen(int index) {
        return true;
    }

    @Override
    public boolean onTap(GeoPoint arg0, MapView arg1) {
        boolean res = super.onTap(arg0, arg1);
        if (!res) {
            hideBalloon(hideAnimation);
        }
        return res;
    }

    @Override
    public final boolean onTap(int index) {
        OverlayItem item = getItem(index);
        if (onBalloonOpen(index)) {
            setLastFocusedIndex(index);
            setFocus(item);
            return true;
        } else {
            return false;
        }
    }

    private void hideOtherBalloons() {
        for (int i = 0; i < mapViewBase.getOverlayCount(); i++) {
            if (mapViewBase.getOverlay(i) instanceof BalloonOverlayExtension<?>) {
                ((BalloonOverlayExtension<?>) mapViewBase.getOverlay(i)).getBalloonController().hideBalloon(false);
            }
        }
    }

    @Override
    public OverlayItem getFocus() {
        return currentFocusedItem;
    }

    @Override
    public void setFocus(OverlayItem item) {
        super.setFocus(item);
        if (item != null) {
            showBalloon(item, openAnimation);
        } else {
            hideBalloon(hideAnimation);
        }
    }

    private boolean createAndDisplayBalloonOverlay(final OverlayItem overlayItem, Animation animation) {
        boolean isRecycled;
        if (balloonView == null) {
            balloonView = createBalloonOverlayView(context);
            isRecycled = false;
        } else {
            isRecycled = true;
        }
        bindView(context, balloonView, overlayItem);
        balloonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBalloonClick(overlayItem);
            }
        });
        hideOtherBalloons();

        GeoPoint point = overlayItem.getPoint();
        MapView.LayoutParams params = new MapView.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point,
                MapView.LayoutParams.BOTTOM_CENTER);
        params.mode = MapView.LayoutParams.MODE_MAP;

        balloonView.setVisibility(View.VISIBLE);
        if (isRecycled) {
            balloonView.setLayoutParams(params);
        } else {
            mapView.addView(balloonView, params);
        }

        if (animation != null) {
            balloonView.startAnimation(animation);
        }

        return isRecycled;
    }

    protected abstract View createBalloonOverlayView(Context context);

    protected abstract void bindView(Context context, View balloonView, OverlayItem item);

    protected abstract void onBalloonClick(OverlayItem item);

}