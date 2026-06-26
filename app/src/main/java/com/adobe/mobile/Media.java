package com.adobe.mobile;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Media {
    private static final String NO_ANALYTICS_MESSAGE = "Analytics - ADBMobile is not configured correctly to use Analytics.";

    public interface MediaCallback<T> {
        void call(T t);
    }

    public static MediaSettings settingsWith(String name, double length, String playerName, String playerID) {
        return MediaSettings.settingsWith(name, length, playerName, playerID);
    }

    public static MediaSettings adSettingsWith(String name, double length, String playerName, String parentName, String parentPod, double parentPodPosition, String CPM) {
        return MediaSettings.adSettingsWith(name, length, playerName, parentName, parentPod, parentPodPosition, CPM);
    }

    public static void open(final MediaSettings settings, final MediaCallback callback) {
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.1
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().open(settings, callback);
            }
        });
    }

    public static void close(final String name) {
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.2
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().close(name);
            }
        });
    }

    public static void play(final String name, final double offset) {
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.3
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().play(name, offset);
            }
        });
    }

    public static void complete(final String name, final double offset) {
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.4
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().complete(name, offset);
            }
        });
    }

    public static void stop(final String name, final double offset) {
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.5
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().stop(name, offset);
            }
        });
    }

    public static void click(final String name, final double offset) {
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.6
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().click(name, offset);
            }
        });
    }

    public static void track(final String name, Map<String, Object> data) {
        MediaAnalytics.sharedInstance().setTrackCalledOnItem(name);
        final Map<String, Object> fdata = data != null ? new HashMap<>(data) : null;
        StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Media.7
            @Override // java.lang.Runnable
            public void run() {
                MediaAnalytics.sharedInstance().track(name, fdata);
            }
        });
    }
}
