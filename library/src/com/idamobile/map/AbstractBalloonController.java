package com.idamobile.map;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.idamobile.map.BalloonOverlayExtension.BallonController;

public abstract class AbstractBalloonController implements BallonController {

    private Animation showAnimation;
    private Animation hideAnimation;

    public AbstractBalloonController(Context context) {
        this.showAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        this.hideAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
    }

    @Override
    public void showBalloon(OverlayItemBase forItem, boolean withAnimation) {
        if (isBalloonShowing()) {
            hideBalloon(withAnimation);
        }
        showBalloon(forItem, withAnimation ? showAnimation : null);
    }

    protected abstract void showBalloon(OverlayItemBase forItem, Animation animation);

    @Override
    public void hideBalloon(boolean withAnimation) {
        if (isBalloonShowing()) {
            hideBalloon(withAnimation ? hideAnimation : null);
        }
    }

    protected abstract void hideBalloon(Animation animation);

    @Override
    public void setShowAnimation(Animation animation) {
        showAnimation = animation;
    }

    @Override
    public void setHideAnimation(Animation animation) {
        hideAnimation = animation;
    }

    public Animation getHideAnimation() {
        return hideAnimation;
    }

    public Animation getShowAnimation() {
        return showAnimation;
    }

}