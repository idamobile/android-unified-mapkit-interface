package com.idamobile.map;

import android.graphics.drawable.Drawable;

public class BalloonItemListOverlay<T extends OverlayItemBase> extends ItemListOverlay<T> implements
BalloonOverlayExtension<T> {

    private boolean initWithAdapterWasCalled;
    private BalloonAdapter balloonAdapter;

    private BallonController ballonController;

    public BalloonItemListOverlay(Drawable defaultMarker) {
        super(defaultMarker);
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
    public com.idamobile.map.BalloonOverlayExtension.BallonController getBalloonController() {
        return ballonController;
    }

    @Override
    public void setBalloonController(com.idamobile.map.BalloonOverlayExtension.BallonController ballonController) {
        this.ballonController = ballonController;
    }

}