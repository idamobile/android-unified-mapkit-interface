package com.idamobile.map.google;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.database.DataSetObserver;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.MapViewBase;
import com.idamobile.map.OverlayItemBase;
import com.idamobile.map.UniversalGeoPoint;

class ItemizedOverlayAdapter<T extends OverlayItemBase> extends DataSetObserver {

    private Map<T, OverlayItem> adoptItems = new HashMap<T, OverlayItem>();

    private ItemizedOverlayBase<T> baseOverlay;
    private ItemListOverlay resultOverlay;
    private OverlayItemAdapter itemAdapter;

    private MapViewBase mapViewBase;

    public ItemizedOverlayAdapter(MapViewBase mapViewBase) {
        this.mapViewBase = mapViewBase;
    }

    public ItemizedOverlayAdapter(ItemizedOverlayBase<T> overlay) {
        this.baseOverlay = overlay;
        this.resultOverlay = new ItemListOverlay(baseOverlay.getMarker()) {
            @Override
            public boolean onTouchEvent(MotionEvent arg0, MapView arg1) {
                return baseOverlay.onTouchEvent(arg0, mapViewBase) || super.onTouchEvent(arg0, arg1);
            }

            @Override
            public boolean onTap(GeoPoint arg0, MapView arg1) {
                return baseOverlay.onTap(new UniversalGeoPoint(arg0), mapViewBase) || super.onTap(arg0, arg1);
            }
        };
        this.itemAdapter = new OverlayItemAdapter();

        baseOverlay.registerDataSetObserver(this);
        refreshOverlay();
    }

    @Override
    public void onChanged() {
        refreshOverlay();
    }

    @Override
    public void onInvalidated() {
    }

    public ItemListOverlay getResultOverlay() {
        return resultOverlay;
    }

    private void refreshOverlay() {
        resultOverlay.beginUpdate();

        OverlayItem focusedItem = resultOverlay.getFocus();
        OverlayItem foundFocusedItem = null;
        resultOverlay.clear();

        Set<T> baseItems = new HashSet<T>(baseOverlay.getItemCount());
        for (int i = 0; i < baseOverlay.getItemCount(); i++) {
            T itemBase = baseOverlay.getItem(i);
            baseItems.add(itemBase);

            OverlayItem resultItem = adoptItems.get(itemBase);
            if (resultItem == null) {
                resultItem = itemAdapter.getItem(itemBase);
                adoptItems.put(itemBase, resultItem);
            } else if (resultItem == focusedItem) {
                foundFocusedItem = resultItem;
            }
            resultOverlay.addItem(resultItem);
        }

        for (Iterator<T> baseItemIter = adoptItems.keySet().iterator(); baseItemIter.hasNext();) {
            if (!baseItems.contains(baseItemIter.next())) {
                baseItemIter.remove();
            }
        }

        resultOverlay.endUpdate();
        resultOverlay.setFocus(foundFocusedItem);
    }
}