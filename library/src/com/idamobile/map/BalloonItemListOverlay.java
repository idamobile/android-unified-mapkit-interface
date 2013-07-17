package com.idamobile.map;

import android.graphics.drawable.Drawable;

public class BalloonItemListOverlay<T extends OverlayItemBase> extends ItemListOverlay<T> implements
BalloonOverlayExtension<T> {

    private boolean initWithAdapterWasCalled;
    private BalloonAdapter balloonAdapter;

    private BalloonController balloonController;

    public BalloonItemListOverlay(Drawable defaultMarker) {
        super(defaultMarker);
    }

    public BalloonItemListOverlay(Drawable defaultMarker, float anchorU, float anchorV) {
        super(defaultMarker, anchorU, anchorV);
    }

    @Override
    public void initWithAdapter(BalloonAdapter balloonAdapter) {
        if (initWithAdapterWasCalled) {
            throw new IllegalStateException("init should me called once");
        }

        this.balloonAdapter = balloonAdapter;
        initWithAdapterWasCalled = true;
    }

    @Override
    public com.idamobile.map.BalloonOverlayExtension.BalloonAdapter getAdapter() {
        return balloonAdapter;
    }

    @Override
    public BalloonController getBalloonController() {
        return balloonController;
    }

    @Override
    public void setBalloonController(BalloonController balloonController) {
        this.balloonController = balloonController;
    }

}