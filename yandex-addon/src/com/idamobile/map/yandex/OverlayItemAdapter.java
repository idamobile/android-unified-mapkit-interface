package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.overlay.OverlayItem;

import com.idamobile.map.OverlayItemBase;

interface OverlayItemAdapter {

    public abstract OverlayItem getItem(OverlayItemBase item);

}