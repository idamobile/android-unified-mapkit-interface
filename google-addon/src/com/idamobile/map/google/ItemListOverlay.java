package com.idamobile.map.google;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ItemListOverlay extends ItemizedOverlay<OverlayItem> {

    private List<OverlayItem> items = new ArrayList<OverlayItem>();
    private boolean isUpdating;
    private boolean ignoreShadow;

    public ItemListOverlay(Drawable marker) {
        super(boundCenterBottom(marker));

        populate();
    }

    public void setIgnoreShadow(boolean ignoreShadow) {
        this.ignoreShadow = ignoreShadow;
    }

    public void beginUpdate() {
        this.isUpdating = true;
    }

    public void endUpdate() {
        if (isUpdating) {
            this.isUpdating = false;
            setLastFocusedIndex(-1);
            populate();
        }
    }

    public void addItem(OverlayItem item) {
        items.add(item);
        if (!isUpdating) {
            populate();
        }
    }

    public boolean removeItem(OverlayItem item) {
        if (items.remove(item)) {
            if (!isUpdating) {
                setLastFocusedIndex(-1);
                populate();
            }
            return true;
        } else {
            return false;
        }
    }

    public void setItems(List<OverlayItem> items) {
        items.clear();
        items.addAll(items);
        if (!isUpdating) {
            setLastFocusedIndex(-1);
            populate();
        }
    }

    protected List<OverlayItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public void draw(Canvas paramCanvas, MapView paramMapView, boolean shadow) {
        super.draw(paramCanvas, paramMapView, shadow && !ignoreShadow);
    }

    @Override
    protected OverlayItem createItem(int i) {
        return items.get(i);
    }

    @Override
    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
        if (!isUpdating) {
            setLastFocusedIndex(-1);
            populate();
        }
    }
}