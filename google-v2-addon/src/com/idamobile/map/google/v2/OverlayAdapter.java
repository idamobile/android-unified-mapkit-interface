package com.idamobile.map.google.v2;

import com.idamobile.map.OverlayBase;

public interface OverlayAdapter {

    MarkerList getResultOverlay();

    OverlayBase getBaseOverlay();

    void release();

}
