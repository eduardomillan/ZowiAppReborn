package com.adobe.mobile;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackCoordinateSpace {
    private static final String COORDINATE_ACTION_NAME = "Coordinates";
    private static final String COORDINATE_FLOAT_FORMAT = "%.2f";
    private static final String COORDINATE_NAME_KEY = "a.map.name";
    private static final String COORDINATE_X_KEY = "a.map.x";
    private static final String COORDINATE_Y_KEY = "a.map.y";

    AnalyticsTrackCoordinateSpace() {
    }

    public static void trackCoordinateSpace(String name, float x, float y, Map<String, Object> data) {
        if (x < 0.0f || x > 1.0f || y < 0.0f || y > 1.0f) {
            StaticMethods.logWarningFormat("Analytics - trackCoordinateSpace failed, the coordinates (x:%.2f, y:%.2f) must be between 0.0f & 1.0f.", Float.valueOf(x), Float.valueOf(y));
            return;
        }
        if (name == null || name.trim().length() == 0) {
            StaticMethods.logWarningFormat("Analytics - trackCoordinateSpace failed, the name was empty or only contained whitespace and is required to map the coorindates to a coordinates space.", new Object[0]);
            return;
        }
        Object xString = String.format(Locale.US, COORDINATE_FLOAT_FORMAT, Float.valueOf(Math.abs(x)));
        Object yString = String.format(Locale.US, COORDINATE_FLOAT_FORMAT, Float.valueOf(Math.abs(y)));
        HashMap<String, Object> contextData = new HashMap<>();
        if (data != null) {
            contextData.putAll(data);
        }
        contextData.put(COORDINATE_NAME_KEY, name);
        contextData.put(COORDINATE_X_KEY, xString);
        contextData.put(COORDINATE_Y_KEY, yString);
        AnalyticsTrackInternal.trackInternal(COORDINATE_ACTION_NAME, contextData, StaticMethods.getTimeSince1970());
    }
}
