package com.idamobile.map;

import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;

public interface ItemizedOverlayBase<T extends OverlayItemBase> extends OverlayBase {

    Drawable getMarker();

    T getItem(int index);

    int getItemCount();

    void addItem(T item);

    boolean remove(T item);

    void clear();

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);

}