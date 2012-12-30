package com.idamobile.map.yandex;

import android.content.Context;
import android.util.AttributeSet;

public class MapView extends ru.yandex.yandexmapkit.MapView {

    private static final String RESOURCE_PREFIX = "@";

    public MapView(Context context, String s) {
        super(context, s);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public String getApiKey() {
        String apiKey = super.getApiKey();
        if (apiKey != null && apiKey.startsWith(RESOURCE_PREFIX)) {
            String strResName = apiKey.substring(RESOURCE_PREFIX.length());
            try {
                int apiKeyResourceId = Integer.parseInt(strResName);
                if (apiKeyResourceId > 0) {
                    String apiKeyFromResorce = getContext().getResources().getString(apiKeyResourceId);
                    return apiKeyFromResorce;
                }
            } catch (Exception ex) {
                //return api key from super
            }
        }
        return apiKey;
    }
}