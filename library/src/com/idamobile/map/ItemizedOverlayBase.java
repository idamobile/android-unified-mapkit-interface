package com.idamobile.map;

import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;

import java.util.Collection;

public interface ItemizedOverlayBase<T extends OverlayItemBase> extends OverlayBase {

    Drawable getMarker();

    Collection<T> getItems();

    int getItemCount();

    void addItem(T item);

    boolean contains(T item);

    boolean remove(T item);

    void apply(Collection<T> toRemove, Collection<T> toAdd);

    void clear();

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);

    boolean onTap(T item);

}