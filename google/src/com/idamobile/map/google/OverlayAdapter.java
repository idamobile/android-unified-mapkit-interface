package com.idamobile.map.google;

import com.google.android.maps.Overlay;
import com.idamobile.map.OverlayBase;

public interface OverlayAdapter {

    Overlay getResultOverlay();

    OverlayBase getBaseOverlay();

    void release();

}
