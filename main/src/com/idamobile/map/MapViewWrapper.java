package com.idamobile.map;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;

public class MapViewWrapper {

    private static final String TAG = MapViewWrapper.class.getSimpleName();

    private static final String YANDEX_MAP_VIEW_CLASS = "ru.yandex.yandexmapkit.MapView";
    private static final String YANDEX_MAP_WRAPPER_CLASS = "com.idamobile.map.yandex.MapViewWrapper";

    private static final String GOOGLE_MAP_VIEW_CLASS = "com.google.android.maps.MapView";
    private static final String GOOGLE_MAP_WRAPPER_CLASS = "com.idamobile.map.google.MapViewWrapper";

    private Map<Class<?>, Constructor<? extends MapViewBase>> wrappers = new HashMap<Class<?>, Constructor<? extends MapViewBase>>();

    private static MapViewWrapper instance;

    public static MapViewWrapper getInstance(Context context) {
        if (instance == null) {
            instance = new MapViewWrapper(context);
        }
        return instance;
    }

    private MapViewWrapper(Context context) {
        if (!tryToLoadWrapper(context, GOOGLE_MAP_VIEW_CLASS, GOOGLE_MAP_WRAPPER_CLASS)) {
            Log.w(TAG, "failed to load google map wrapper");
        }
        if (!tryToLoadWrapper(context, YANDEX_MAP_VIEW_CLASS, YANDEX_MAP_WRAPPER_CLASS)) {
            Log.w(TAG, "failed to load yandex map wrapper");
        }
    }

    private boolean tryToLoadWrapper(Context context, String mapViewClassName, String mapViewWrapperClassName) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends MapViewBase> mapViewWrapperClass = (Class<? extends MapViewBase>) Class.forName(
                    mapViewWrapperClassName, true, context.getClassLoader());
            Class<?> mapViewClass = Class.forName(mapViewClassName, true, context.getClassLoader());
            Constructor<? extends MapViewBase> constructor = mapViewWrapperClass.getConstructor(mapViewClass);
            wrappers.put(mapViewClass, constructor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MapViewBase wrap(View mapView) {
        Constructor<? extends MapViewBase> cons = wrappers.get(mapView.getClass());
        if (cons != null) {
            try {
                return cons.newInstance(mapView);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("This view " + mapView + " isn't mapView or not supported");
    }
}
