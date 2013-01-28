package com.idamobile.map.google;

import android.database.DataSetObserver;
import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.MapViewBase;
import com.idamobile.map.OverlayItemBase;

import java.util.*;
import java.util.Map.Entry;

class ItemizedOverlayAdapter<T extends OverlayItemBase> extends DataSetObserver implements OverlayAdapter {

    private Map<T, OverlayItem> adoptItems = new HashMap<T, OverlayItem>();

    private ItemizedOverlayBase<T> baseOverlay;
    private ItemListOverlay resultOverlay;
    private OverlayItemAdapter itemAdapter;

    public ItemizedOverlayAdapter(final MapViewBase mapViewBase, ItemizedOverlayBase<T> overlay) {
        this.baseOverlay = overlay;
        this.resultOverlay = wrapOverlay(baseOverlay, mapViewBase);
        this.itemAdapter = new OverlayItemAdapter(baseOverlay.getMarker());

        baseOverlay.registerDataSetObserver(this);
        refreshOverlay();
    }

    protected ItemListOverlay wrapOverlay(final ItemizedOverlayBase<T> overlay, final MapViewBase mapViewBase) {
        return new ItemListOverlay(overlay.getMarker()) {
            @Override
            public boolean onTouchEvent(MotionEvent arg0, MapView arg1) {
                return overlay.onTouchEvent(arg0, mapViewBase) || super.onTouchEvent(arg0, arg1);
            }

            @Override
            public boolean onTap(GeoPoint arg0, MapView arg1) {
                return overlay.onTap(new UniversalGeoPoint(arg0), mapViewBase) || super.onTap(arg0, arg1);
            }

            @Override
            protected boolean onTap(int arg0) {
                Iterator<T> iter = overlay.getItems().iterator();
                for (int i = 0; i < arg0 && iter.hasNext(); i++) {
                    iter.next();
                }
                if (iter.hasNext()) {
                    return overlay.onTap(iter.next());
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public void onChanged() {
        refreshOverlay();
    }

    @Override
    public void onInvalidated() {
    }

    @Override
    public ItemListOverlay getResultOverlay() {
        return resultOverlay;
    }

    @Override
    public ItemizedOverlayBase<T> getBaseOverlay() {
        return baseOverlay;
    }

    protected T getOriginalItem(OverlayItem item) {
        for (Entry<T, OverlayItem> entry : adoptItems.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected OverlayItem getResultItem(T original) {
        return adoptItems.get(original);
    }

    private void refreshOverlay() {
        resultOverlay.beginUpdate();

        Set<T> baseItems = new HashSet<T>(baseOverlay.getItemCount());
        for (T itemBase : baseOverlay.getItems()) {
            baseItems.add(itemBase);

            OverlayItem resultItem = adoptItems.get(itemBase);
            if (resultItem == null) {
                resultItem = itemAdapter.getItem(itemBase);
                adoptItems.put(itemBase, resultItem);
                resultOverlay.addItem(resultItem);
            }
        }

        for (Iterator<T> baseItemIter = adoptItems.keySet().iterator(); baseItemIter.hasNext();) {
            T item = baseItemIter.next();
            if (!baseItems.contains(item)) {
                resultOverlay.removeItem(adoptItems.get(item));
                baseItemIter.remove();
            }
        }

        resultOverlay.endUpdate();
    }

    @Override
    public void release() {
        baseOverlay.unregisterDataSetObserver(this);
    }
}