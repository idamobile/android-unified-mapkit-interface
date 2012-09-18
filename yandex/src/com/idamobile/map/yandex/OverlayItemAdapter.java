package com.idamobile.map.yandex;

import ru.yandex.yandexmapkit.overlay.OverlayItem;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.idamobile.map.OverlayItemBase;

class OverlayItemAdapter {

    public OverlayItem getItem(OverlayItemBase item) {
        OverlayItem result = new OverlayItem(
                new UniversalGeoPoint(item.getGeoPoint()).createYandexPoint(), item.getMarker());
        SpannableStringBuilder title = new SpannableStringBuilder();
        if (!TextUtils.isEmpty(item.getTitle())) {
            title.append(item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getSnippet())) {
            if (title.length() > 0) {
                title.append(", ");
            }
            title.append(item.getSnippet());
        }

        if (title.length() > 0) {
            if (result.getBalloonItem() == null) {
                result.getBalloonItem().setText(title);
            }
        }
        return result;
    }

}