package com.idamobile.map.yandex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;
import android.database.DataSetObserver;
import android.view.MotionEvent;

import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.OverlayItemBase;

class ItemizedOverlayAdapter<T extends OverlayItemBase> extends DataSetObserver implements OverlayAdapter {

    private Map<T, OverlayItem> adoptItems = new HashMap<T, OverlayItem>();

    private ItemizedOverlayBase<T> baseOverlay;
    private Overlay resultOverlay;
    private OverlayItemAdapter itemAdapter;

    public ItemizedOverlayAdapter(MapViewWrapper mapViewWrapper, ItemizedOverlayBase<T> overlay) {
        this(mapViewWrapper, overlay, new SimpleOverlayItemAdapter(overlay.getMarker()));
    }

    public ItemizedOverlayAdapter(MapViewWrapper mapViewWrapper, ItemizedOverlayBase<T> overlay,
            OverlayItemAdapter overlayItemAdapter) {
        this.baseOverlay = overlay;
        this.itemAdapter = overlayItemAdapter;
        this.resultOverlay = wrapOverlay(overlay, mapViewWrapper);

        baseOverlay.registerDataSetObserver(this);
        refreshOverlay();
    }

    protected Overlay wrapOverlay(ItemizedOverlayBase<T> overlay, final MapViewWrapper mapViewWrapper) {
        return new Overlay(mapViewWrapper.getView().getMapController()) {
            @Override
            public boolean onTouchEvent(MotionEvent arg0) {
                return baseOverlay.onTouchEvent(arg0, mapViewWrapper) || super.onTouchEvent(arg0);
            }

            @Override
            public boolean onSingleTapUp(float arg0, float arg1) {
                GeoPoint geoPoint = mapViewWrapper
                        .getView()
                        .getMapController()
                        .getGeoPoint(new ScreenPoint(arg0, arg1));
                return baseOverlay.onTap(new UniversalGeoPoint(geoPoint), mapViewWrapper)
                        || super.onSingleTapUp(arg0, arg1);
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
    public Overlay getResultOverlay() {
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
        resultOverlay.clearOverlayItems();

        Set<T> baseItems = new HashSet<T>(baseOverlay.getItemCount());
        for (int i = 0; i < baseOverlay.getItemCount(); i++) {
            T itemBase = baseOverlay.getItem(i);
            baseItems.add(itemBase);

            OverlayItem resultItem = adoptItems.get(itemBase);
            if (resultItem == null) {
                resultItem = itemAdapter.getItem(itemBase);
                adoptItems.put(itemBase, resultItem);
            }

            resultOverlay.addOverlayItem(resultItem);
        }

        for (Iterator<T> baseItemIter = adoptItems.keySet().iterator(); baseItemIter.hasNext();) {
            if (!baseItems.contains(baseItemIter.next())) {
                baseItemIter.remove();
            }
        }
    }

    @Override
    public void release() {
        baseOverlay.unregisterDataSetObserver(this);
    }
}
