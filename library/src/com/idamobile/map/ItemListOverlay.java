package com.idamobile.map;

import java.util.ArrayList;
import java.util.List;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

public class ItemListOverlay<T extends OverlayItemBase> implements ItemizedOverlayBase<T> {

    private List<T> items = new ArrayList<T>();
    private Drawable defaultMarker;
    private DataSetObservable observable = new DataSetObservable();

    public ItemListOverlay() {
    }

    public ItemListOverlay(Drawable defaultMarker) {
        this.defaultMarker = defaultMarker;
    }

    @Override
    public T getItem(int index) {
        return items.get(index);
    }

    @Override
    public int getItemCount() {
        return items.size();
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
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public Drawable getMarker() {
        return defaultMarker;
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