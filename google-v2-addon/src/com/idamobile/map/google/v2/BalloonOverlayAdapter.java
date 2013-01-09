package com.idamobile.map.google.v2;

import android.view.View;
import android.view.animation.Animation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idamobile.map.AbstractBalloonController;
import com.idamobile.map.BalloonOverlayExtension;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.OverlayItemBase;

class BalloonOverlayAdapter<T extends OverlayItemBase> extends ItemizedOverlayAdapter<T> {

    private GoogleMap.InfoWindowAdapter infoWindowAdapter;
    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener;

    public BalloonOverlayAdapter(final MapFragmentWrapper mapFragmentWrapper, ItemizedOverlayBase<T> overlay) {
        super(mapFragmentWrapper, overlay);
        @SuppressWarnings("unchecked")
        BalloonOverlayExtension<T> overlayExtension = (BalloonOverlayExtension<T>) overlay;
        overlayExtension.setBalloonController(new AbstractBalloonController(mapFragmentWrapper.getContext()) {
            private GoogleMap googleMap = mapFragmentWrapper.getGoogleMap();

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
                MarkerOptions options = getResultOverlay().getMarkerOptionsWithOpenBalloon();
                return options != null ? getOriginalItem(options) : null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MarkerList wrapOverlay(GoogleMap googleMap, OverlayManager overlayManager, final ItemizedOverlayBase<T> overlay) {
        final BalloonOverlayExtension<T> overlayExtension = (BalloonOverlayExtension<T>) overlay;
        infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = null;
                MarkerOptions options = getResultOverlay().getOptionsForMarker(marker);
                if (options != null) {
                    view = overlayExtension.getAdapter().createView(mapFragmentWrapper.getContext());
                    overlayExtension.getAdapter().bindView(mapFragmentWrapper.getContext(), view, getOriginalItem(options));
                }
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        };

        infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                MarkerOptions options = getResultOverlay().getOptionsForMarker(marker);
                if (options != null) {
                    overlayExtension.getAdapter().onBalloonClick(getOriginalItem(options));
                }
            }
        };

        mapFragmentWrapper.getOverlayManager().registerInfoWindowAdapter(infoWindowAdapter);
        mapFragmentWrapper.getOverlayManager().registerOnInfoWindowClickListener(infoWindowClickListener);
        return super.wrapOverlay(mapFragmentWrapper.getGoogleMap(), mapFragmentWrapper.getOverlayManager(), overlay);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void release() {
        ((BalloonOverlayExtension<T>) getBaseOverlay()).setBalloonController(null);
        if (infoWindowAdapter != null) {
            mapFragmentWrapper.getOverlayManager().unregisterInfoWindowAdapter(infoWindowAdapter);
        }
        if (infoWindowClickListener != null) {
            mapFragmentWrapper.getOverlayManager().unregisterOnInfoWindowClickListener(infoWindowClickListener);
        }
        super.release();
    }
}