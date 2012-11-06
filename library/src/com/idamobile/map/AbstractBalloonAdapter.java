package com.idamobile.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.idamobile.map.BalloonOverlayExtension.BalloonAdapter;

public abstract class AbstractBalloonAdapter implements BalloonAdapter {

    private int layoutResId;

    public AbstractBalloonAdapter(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(layoutResId, null);
    }

}