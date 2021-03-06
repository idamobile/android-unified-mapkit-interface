package com.idamobile.map;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import java.util.*;

public class ItemListOverlay<T extends OverlayItemBase> implements ItemizedOverlayBaseV2<T> {

    private Set<T> items = new HashSet<T>();
    private Drawable defaultMarker;
    private float anchorU;
    private float anchorV;

    private DataSetObservable observable = new DataSetObservable();

    public ItemListOverlay(Drawable defaultMarker) {
        this(defaultMarker, 0.5f, 1f);
    }

    public ItemListOverlay(Drawable defaultMarker, float anchorU, float anchorV) {
        this.defaultMarker = defaultMarker;
        this.anchorU = anchorU;
        this.anchorV = anchorV;
    }

    @Override
    public Collection<T> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public boolean contains(T item) {
        return items.contains(item);
    }

    @Override
    public void addItem(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public boolean remove(T item) {
        if (items.remove(item)) {
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void apply(Collection<T> toRemove, Collection<T> toAdd) {
        for (T item : toRemove) {
            items.remove(item);
        }
        for (T item : toAdd) {
            items.add(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public Drawable getMarker() {
        return defaultMarker;
    }

    @Override
    public float getMarkerAnchorU() {
        return anchorU;
    }

    @Override
    public float getMarkerAnchorV() {
        return anchorV;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        observable.unregisterObserver(observer);
    }

    public void notifyDataSetChanged() {
        observable.notifyChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev, MapViewBase mapView) {
        return false;
    }

    @Override
    public boolean onTap(IGeoPoint geoPoint, MapViewBase mapView) {
        return false;
    }

    @Override
    public boolean onTap(T item) {
        return false;
    }

}