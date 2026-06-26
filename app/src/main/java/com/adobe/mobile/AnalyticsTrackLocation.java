package com.adobe.mobile;

import android.location.Location;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackLocation {
    private static final String ACCURACY_KEY = "a.loc.acc";
    private static final String LOCATION_ACTION_NAME = "Location";
    private static final String LOCATION_LAT_PART1_KEY = "a.loc.lat.a";
    private static final String LOCATION_LAT_PART2_KEY = "a.loc.lat.b";
    private static final String LOCATION_LAT_PART3_KEY = "a.loc.lat.c";
    private static final String LOCATION_LON_PART1_KEY = "a.loc.lon.a";
    private static final String LOCATION_LON_PART2_KEY = "a.loc.lon.b";
    private static final String LOCATION_LON_PART3_KEY = "a.loc.lon.c";
    private static final String POI_DIST_KEY = "a.loc.dist";
    private static final String POI_NAME_KEY = "a.loc.poi";
    private static final String WHOLE_ONLY_FLOAT_FORMAT = "%.0f";
    private static final String ZERO_PADDED_11_6_FLOAT_FORMAT = "% 011.6f";

    AnalyticsTrackLocation() {
    }

    public static void trackLocation(Location location, Map<String, Object> data) {
        if (location == null) {
            StaticMethods.logWarningFormat("Analytics - trackLocation failed, invalid location specified", new Object[0]);
            return;
        }
        String latString = String.format(Locale.US, ZERO_PADDED_11_6_FLOAT_FORMAT, Double.valueOf(location.getLatitude()));
        String lonString = String.format(Locale.US, ZERO_PADDED_11_6_FLOAT_FORMAT, Double.valueOf(location.getLongitude()));
        HashMap<String, Object> contextData = new HashMap<>();
        if (data != null) {
            contextData.putAll(data);
        }
        contextData.put(LOCATION_LAT_PART1_KEY, latString.substring(0, 6).trim());
        contextData.put(LOCATION_LAT_PART2_KEY, latString.substring(6, 8));
        contextData.put(LOCATION_LAT_PART3_KEY, latString.substring(8, 10));
        contextData.put(LOCATION_LON_PART1_KEY, lonString.substring(0, 6).trim());
        contextData.put(LOCATION_LON_PART2_KEY, lonString.substring(6, 8));
        contextData.put(LOCATION_LON_PART3_KEY, lonString.substring(8, 10));
        if (location.hasAccuracy() && location.getAccuracy() > 0.0f) {
            contextData.put(ACCURACY_KEY, String.format(Locale.US, WHOLE_ONLY_FLOAT_FORMAT, Float.valueOf(location.getAccuracy())));
        }
        TargetWorker.removePersistentParameter(POI_NAME_KEY);
        TargetWorker.removePersistentParameter(POI_DIST_KEY);
        Lifecycle.removeContextData(POI_NAME_KEY);
        List<List<Object>> poi = MobileConfig.getInstance().getPointsOfInterest();
        if (poi != null) {
            Iterator<List<Object>> it = poi.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                List<Object> poiArray = it.next();
                if (poiArray != null && poiArray.size() == 4) {
                    try {
                        String name = poiArray.get(0).toString();
                        double latitude = ((Double) poiArray.get(1)).doubleValue();
                        double longitude = ((Double) poiArray.get(2)).doubleValue();
                        double radius = ((Double) poiArray.get(3)).doubleValue();
                        Location poiLocation = new Location("poi");
                        poiLocation.setLatitude(latitude);
                        poiLocation.setLongitude(longitude);
                        double distance = poiLocation.distanceTo(location);
                        if (distance <= radius && name != null) {
                            contextData.put(POI_NAME_KEY, name);
                            TargetWorker.addPersistentParameter(POI_NAME_KEY, name);
                            contextData.put(POI_DIST_KEY, String.format(Locale.US, WHOLE_ONLY_FLOAT_FORMAT, Double.valueOf(distance)));
                            TargetWorker.addPersistentParameter(POI_DIST_KEY, String.valueOf(distance));
                            HashMap<String, Object> poiData = new HashMap<>();
                            poiData.put(POI_NAME_KEY, name);
                            Lifecycle.updateContextData(poiData);
                            break;
                        }
                    } catch (ClassCastException ex) {
                        StaticMethods.logWarningFormat("Analytics - Invalid data for point of interest(%s)", ex.getLocalizedMessage());
                    }
                }
            }
        }
        AnalyticsTrackInternal.trackInternal(LOCATION_ACTION_NAME, contextData, StaticMethods.getTimeSince1970());
    }
}
