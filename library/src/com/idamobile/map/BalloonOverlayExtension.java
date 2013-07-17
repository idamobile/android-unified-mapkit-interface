package com.idamobile.map;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

public interface BalloonOverlayExtension<T extends OverlayItemBase> {

    public interface BalloonAdapter {
        View createView(Context context);

        void bindView(Context context, View convertView, OverlayItemBase item);

        void onBalloonClick(OverlayItemBase item);
    }

    public interface BalloonController {

        boolean isBalloonShowing();

        void showBalloon(OverlayItemBase forItem, boolean withAnimation);

        void hideBalloon(boolean withAnimation);

        void setShowAnimation(Animation animation);

        void setHideAnimation(Animation animation);

        OverlayItemBase getItemWithOpenBalloon();

    }

    void initWithAdapter(BalloonAdapter balloonAdapter);

    BalloonAdapter getAdapter();

    /**
     * Returns BalloonController when overlay is attached to MapViewBase
     * 
     * @return BalloonController or null if overlay isn't attached
     */
    BalloonController getBalloonController();

    /**
     * Should be called only by map interface implementation.
     * 
     * @param balloonController
     *            controller
     */
    void setBalloonController(BalloonController balloonController);

}