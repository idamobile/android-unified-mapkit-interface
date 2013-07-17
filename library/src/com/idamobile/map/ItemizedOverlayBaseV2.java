package com.idamobile.map;

public interface ItemizedOverlayBaseV2<T extends OverlayItemBase> extends ItemizedOverlayBase<T> {

    public float getMarkerAnchorU();

    public float getMarkerAnchorV();

}