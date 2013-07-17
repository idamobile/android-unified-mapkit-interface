package com.idamobile.map.google.v2;

import android.database.DataSetObserver;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idamobile.map.ItemizedOverlayBase;
import com.idamobile.map.ItemizedOverlayBaseV2;
import com.idamobile.map.OverlayItemBase;

import java.util.*;
import java.util.Map.Entry;

class ItemizedOverlayAdapter<T extends OverlayItemBase> extends DataSetObserver implements com.idamobile.map.google.v2.OverlayAdapter {

    private Map<T, MarkerOptions> adoptItems = new HashMap<T, MarkerOptions>();

    protected MapFragmentWrapper mapFragmentWrapper;
    private ItemizedOverlayBase<T> baseOverlay;
    private MarkerList resultOverlay;
    private OverlayItemAdapter itemAdapter;
    private GoogleMap.OnMarkerClickListener onMarkerClickListener;

    public ItemizedOverlayAdapter(MapFragmentWrapper mapFragmentWrapper, ItemizedOverlayBase<T> overlay) {
        this.mapFragmentWrapper = mapFragmentWrapper;
        this.baseOverlay = overlay;

        init(overlay);
    }

    private void init(ItemizedOverlayBase<T> overlay) {
        this.resultOverlay = wrapOverlay(mapFragmentWrapper.getGoogleMap(), mapFragmentWrapper.getOverlayManager(), overlay);
        this.itemAdapter = new OverlayItemAdapter(baseOverlay.getMarker(),
                overlay instanceof ItemizedOverlayBaseV2 ? ((ItemizedOverlayBaseV2) overlay).getMarkerAnchorU() : 0.5f,
                overlay instanceof ItemizedOverlayBaseV2 ? ((ItemizedOverlayBaseV2) overlay).getMarkerAnchorV() : 1f);

        baseOverlay.registerDataSetObserver(this);
        refreshOverlay();
    }

    protected MarkerList wrapOverlay(GoogleMap googleMap, OverlayManager overlayManager, final ItemizedOverlayBase<T> overlay) {
        MarkerList markerList = new MarkerList(googleMap);
        if (onMarkerClickListener != null) {
            overlayManager.unregisterOnMarkerClickListener(onMarkerClickListener);
        }
        onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MarkerOptions options = resultOverlay.getOptionsForMarker(marker);
                if (options != null) {
                    return overlay.onTap(getOriginalItem(options));
                }
                return false;
            }
        };
        overlayManager.registerOnMarkerClickListener(onMarkerClickListener);
        return markerList;
    }

    @Override
    public void onChanged() {
        refreshOverlay();
    }

    @Override
    public void onInvalidated() {
    }

    @Override
    public MarkerList getResultOverlay() {
        return resultOverlay;
    }

    @Override
    public ItemizedOverlayBase<T> getBaseOverlay() {
        return baseOverlay;
    }

    protected T getOriginalItem(MarkerOptions item) {
        for (Entry<T, MarkerOptions> entry : adoptItems.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected MarkerOptions getResultItem(T original) {
        return adoptItems.get(original);
    }

    private void refreshOverlay() {
        Set<T> baseItems = new HashSet<T>(baseOverlay.getItemCount());
        for (T itemBase : baseOverlay.getItems()) {
            baseItems.add(itemBase);

            MarkerOptions resultItem = adoptItems.get(itemBase);
            if (resultItem == null) {
                resultItem = itemAdapter.getItem(itemBase);
                adoptItems.put(itemBase, resultItem);
                resultOverlay.add(resultItem);
            }
        }

        for (Iterator<T> baseItemIter = adoptItems.keySet().iterator(); baseItemIter.hasNext();) {
            T item = baseItemIter.next();
            if (!baseItems.contains(item)) {
                resultOverlay.remove(adoptItems.get(item));
                baseItemIter.remove();
            }
        }
    }

    @Override
    public void release() {
        baseOverlay.unregisterDataSetObserver(this);
        if (onMarkerClickListener != null) {
            mapFragmentWrapper.getOverlayManager().unregisterOnMarkerClickListener(onMarkerClickListener);
        }
    }
}