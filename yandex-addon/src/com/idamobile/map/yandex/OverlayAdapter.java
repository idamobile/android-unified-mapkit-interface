package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.overlay.Overlay;

import com.idamobile.map.OverlayBase;

public interface OverlayAdapter {

    Overlay getResultOverlay();

    OverlayBase getBaseOverlay();

    void release();

}
