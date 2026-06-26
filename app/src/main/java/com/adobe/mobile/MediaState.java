package com.adobe.mobile;

import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public final class MediaState {
    public boolean ad;
    public boolean clicked;
    public boolean complete;
    public boolean eventFirstTime;
    protected int eventType;
    public double length;
    public String mediaEvent;
    public int milestone;
    public String name;
    public double offset;
    public int offsetMilestone;
    public Date openTime;
    public double percent;
    public String playerName;
    public String segment;
    public double segmentLength;
    public int segmentNum;
    public double timePlayed;
    private double timePlayedSinceTrack;
    private long timestamp;

    protected MediaState(String name, double length, String playerName, long openTime) {
        this.openTime = new Date();
        this.complete = false;
        this.clicked = false;
        this.name = name;
        this.length = length;
        this.playerName = playerName;
        this.timestamp = StaticMethods.getTimeSince1970();
        this.segment = "";
        this.segmentNum = 0;
        this.segmentLength = 0.0d;
        this.openTime.setTime(openTime);
    }

    protected MediaState(MediaState masterState) {
        this.openTime = new Date();
        this.complete = false;
        this.clicked = false;
        this.name = masterState.name;
        this.length = masterState.length;
        this.playerName = masterState.playerName;
        this.mediaEvent = masterState.mediaEvent;
        this.eventFirstTime = masterState.eventFirstTime;
        this.openTime = masterState.openTime;
        this.offset = masterState.offset;
        this.percent = masterState.percent;
        this.timePlayed = masterState.timePlayed;
        this.milestone = masterState.milestone;
        this.offsetMilestone = masterState.offsetMilestone;
        this.segmentNum = masterState.segmentNum;
        this.segment = masterState.segment;
        this.segmentLength = masterState.segmentLength;
        this.complete = masterState.complete;
        this.eventType = masterState.eventType;
        this.timestamp = masterState.timestamp;
        this.timePlayedSinceTrack = masterState.timePlayedSinceTrack;
        this.clicked = masterState.clicked;
        this.ad = masterState.ad;
    }

    protected int getEventType() {
        return this.eventType;
    }

    protected void setEventType(int eventType) {
        String mediaEvent;
        this.eventType = eventType;
        switch (this.eventType) {
            case 1:
                mediaEvent = "PLAY";
                break;
            case 2:
                mediaEvent = "STOP";
                break;
            case 3:
                mediaEvent = "MONITOR";
                break;
            case 4:
                mediaEvent = "TRACK";
                break;
            case 5:
                mediaEvent = "COMPLETE";
                break;
            case 6:
                mediaEvent = "CLICK";
                break;
            default:
                mediaEvent = "CLOSE";
                break;
        }
        this.mediaEvent = mediaEvent;
    }

    protected void setOffset(double offset) {
        this.offset = offset;
        if (this.length > 0.0d) {
            if (offset >= this.length) {
                offset = this.length;
            }
            this.offset = offset;
        }
        if (this.offset < 0.0d) {
            this.offset = 0.0d;
        }
        generatePercent();
        checkComplete();
    }

    private void generatePercent() {
        if (this.length != -1.0d) {
            this.percent = (this.offset / this.length) * 100.0d;
            this.percent = this.percent < 100.0d ? this.percent : 100.0d;
        }
    }

    private void checkComplete() {
        if (this.length == -1.0d) {
            this.complete = false;
        } else if (this.percent >= 100.0d) {
            this.complete = true;
        }
    }

    protected double getTimestamp() {
        return this.timestamp;
    }

    protected double getTimePlayedSinceTrack() {
        return this.timePlayedSinceTrack;
    }

    protected void setTimePlayedSinceTrack(double timePlayedSinceTrack) {
        this.timePlayedSinceTrack = timePlayedSinceTrack;
    }

    protected double getTimePlayed() {
        return this.timePlayed;
    }

    protected void setTimePlayed(double timePlayed) {
        this.timePlayed = timePlayed;
    }
}
