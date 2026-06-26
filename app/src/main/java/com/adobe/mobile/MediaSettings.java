package com.adobe.mobile;

/* JADX INFO: loaded from: classes.dex */
public class MediaSettings {
    public String CPM;
    public String channel;
    public boolean isMediaAd;
    public double length;
    public String milestones;
    public String name;
    public String offsetMilestones;
    public String parentName;
    public String parentPod;
    public double parentPodPosition;
    public String playerID;
    public String playerName;
    public boolean segmentByMilestones;
    public boolean segmentByOffsetMilestones;
    public int trackSeconds = 0;
    public int completeCloseOffsetThreshold = 1;

    public static MediaSettings settingsWith(String name, double length, String playerName, String playerID) {
        MediaSettings mediaSettings = new MediaSettings();
        mediaSettings.name = name;
        mediaSettings.length = length;
        mediaSettings.playerName = playerName;
        mediaSettings.playerID = playerID;
        return mediaSettings;
    }

    public static MediaSettings adSettingsWith(String name, double length, String playerName, String parentName, String parentPod, double parentPodPosition, String CPM) {
        MediaSettings mediaSettings = new MediaSettings();
        mediaSettings.isMediaAd = true;
        mediaSettings.name = name;
        mediaSettings.length = length;
        mediaSettings.playerName = playerName;
        mediaSettings.parentName = parentName;
        mediaSettings.parentPod = parentPod;
        mediaSettings.parentPodPosition = parentPodPosition;
        mediaSettings.CPM = CPM;
        return mediaSettings;
    }
}
