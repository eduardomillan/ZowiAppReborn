package com.adobe.mobile;

import com.adobe.mobile.Media;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
final class MediaItem {
    private static final Object monitorMutex = new Object();
    protected String CPM;
    protected String channel;
    private int completeCloseOffsetThreshold;
    private boolean completeTracked;
    protected boolean itemClosed;
    protected int lastTrackSegmentNumber;
    protected double length;
    protected boolean mediaAd;
    protected MediaAnalytics mediaAnalytics;
    private MonitorThread monitor;
    protected String name;
    protected String parentName;
    protected String parentPod;
    protected double parentPodPosition;
    protected String playerID;
    protected String playerName;
    protected boolean trackCalled;
    private int trackSecondsThreshold;
    protected Media.MediaCallback<MediaState> callback = null;
    protected MediaState currentMediaState = null;
    protected MediaState previousMediaState = null;
    private HashSet<String> firstEventList = new HashSet<>();
    private ArrayList<Integer> milestones = new ArrayList<>();
    private ArrayList<Integer> offsetMilestones = new ArrayList<>();
    private boolean segmentByMilestones = false;
    private boolean segmentByOffsetMilestones = false;
    protected double timestamp = StaticMethods.getTimeSince1970();

    public MediaItem(MediaSettings settings, MediaAnalytics mediaAnalytics, String cleanName, double validLength, String cleanPlayerName) {
        this.completeCloseOffsetThreshold = 1;
        this.trackSecondsThreshold = 0;
        this.name = cleanName;
        this.length = validLength;
        this.playerName = cleanPlayerName;
        this.mediaAnalytics = mediaAnalytics;
        this.playerID = settings.playerID;
        this.channel = settings.channel;
        setMilestones(settings.milestones);
        setOffsetMilestones(settings.offsetMilestones);
        setSegmentByMilestones(settings.segmentByMilestones && this.milestones.size() > 0);
        setSegmentByOffsetMilestones(settings.segmentByOffsetMilestones && this.offsetMilestones.size() > 0);
        setTrackSecondsThreshold(mediaAnalytics.trackSeconds);
        setCompleteCloseOffsetThreshold(mediaAnalytics.completeCloseOffsetThreshold);
        if (settings.isMediaAd) {
            this.mediaAd = true;
            this.parentPodPosition = settings.parentPodPosition;
            this.parentName = settings.parentName;
            this.parentPod = settings.parentPod;
            this.CPM = settings.CPM;
        }
        this.completeCloseOffsetThreshold = settings.completeCloseOffsetThreshold > 0 ? settings.completeCloseOffsetThreshold : 1;
        this.trackSecondsThreshold = settings.trackSeconds > 0 ? settings.trackSeconds : 0;
    }

    protected void startMonitor() {
        if (this.monitor == null || this.monitor.canceled) {
            if (this.monitor != null) {
                stopMonitor();
            }
            this.monitor = new MonitorThread();
            this.monitor.monitorMediaItem = this;
            this.monitor.start();
        }
    }

    protected void stopMonitor() {
        if (this.monitor != null) {
            synchronized (monitorMutex) {
                this.monitor.canceled = true;
                this.monitor = null;
            }
        }
    }

    private static class MonitorThread extends Thread {
        protected boolean canceled;
        long delay;
        protected MediaItem monitorMediaItem;

        private MonitorThread() {
            this.delay = 1000L;
            this.canceled = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (!this.canceled) {
                try {
                    Thread.sleep(this.delay);
                    StaticMethods.getMediaExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MediaItem.MonitorThread.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MonitorThread.this.monitorMediaItem.mediaAnalytics.monitor(MonitorThread.this.monitorMediaItem.name, -1.0d);
                        }
                    });
                } catch (InterruptedException e) {
                    StaticMethods.logWarningFormat("Media - Background Thread Interrupted : %s", e.getMessage());
                    return;
                }
            }
        }
    }

    protected synchronized void play(double offset) {
        if (this.currentMediaState == null || !isPlaying()) {
            updateMediaStates();
            updateCurrentMediaStateWithOffset(offset, 1);
            if (!this.currentMediaState.complete) {
                startMonitor();
            }
        }
    }

    protected synchronized void monitor(double offset) {
        updateMediaStates();
        if (this.previousMediaState != null) {
            updateCurrentMediaStateWithOffset(offset, 3);
            if (this.currentMediaState.complete) {
                stopMonitor();
            }
        }
    }

    protected synchronized void click(double offset) {
        updateMediaStates();
        if (this.previousMediaState != null) {
            updateCurrentMediaStateWithOffset(offset, 6);
        }
    }

    protected synchronized void complete(double offset) {
        updateMediaStates();
        if (this.previousMediaState != null && this.previousMediaState.getEventType() != 5) {
            updateCurrentMediaStateWithOffset(offset, 5);
            if (this.currentMediaState.complete) {
                stopMonitor();
            }
            this.currentMediaState.complete = true;
        }
    }

    protected synchronized void stop(double offset) {
        updateMediaStates();
        updateCurrentMediaStateWithOffset(offset, 2);
        stopMonitor();
    }

    protected synchronized void close() {
        updateMediaStates();
        if (this.previousMediaState != null && this.previousMediaState.getEventType() != 0) {
            if (this.previousMediaState.eventType == 2) {
                updateCurrentMediaStateWithOffset(this.currentMediaState.offset, 0);
            } else {
                updateCurrentMediaStateWithOffset(-1.0d, 0);
            }
            if (isCurrentOffsetPastCompleteThreshold()) {
                this.currentMediaState.complete = true;
            }
            stopMonitor();
        }
    }

    private void updateMediaStates() {
        this.previousMediaState = this.currentMediaState;
        this.currentMediaState = new MediaState(this.name, this.length, this.playerName, (long) this.timestamp);
    }

    private void updateCurrentMediaStateWithOffset(double offset, int defaultEventType) {
        this.currentMediaState.clicked = defaultEventType == 6;
        this.currentMediaState.ad = this.mediaAd;
        this.currentMediaState.setOffset(validateOffset(offset));
        calculateCurrentOffsetMilestoneAndSegment();
        calculateCurrentMilestoneAndSegment();
        updateTimePlayed(defaultEventType);
        this.currentMediaState.setEventType(defaultEventType);
        updateCurrentMediaStateMediaEventIfNeeded(defaultEventType);
        setEventFirstTime(this.currentMediaState);
    }

    private void calculateCurrentMilestoneAndSegment() {
        int lastPassedMilestoneIndex;
        if (!isLive() && this.milestones.size() != 0 && (lastPassedMilestoneIndex = calculateLastPassedMilestoneIndex()) != -1) {
            int milestone = this.milestones.get(lastPassedMilestoneIndex).intValue();
            this.currentMediaState.milestone = milestone;
            if (this.segmentByMilestones) {
                this.currentMediaState.segmentNum = lastPassedMilestoneIndex + 1;
                StringBuilder segmentBuilder = new StringBuilder();
                segmentBuilder.append("M:");
                segmentBuilder.append(Integer.toString(milestone));
                segmentBuilder.append("-");
                if (lastPassedMilestoneIndex < this.milestones.size() - 1) {
                    segmentBuilder.append(Integer.toString(this.milestones.get(lastPassedMilestoneIndex + 1).intValue()));
                } else {
                    segmentBuilder.append("100");
                }
                this.currentMediaState.segment = segmentBuilder.toString();
            }
        }
    }

    private void calculateCurrentOffsetMilestoneAndSegment() {
        int lastPassedOffsetMilestoneIndex;
        if (this.offsetMilestones.size() != 0 && (lastPassedOffsetMilestoneIndex = calculateLastPassedOffsetMilestoneIndex()) != -1) {
            int milestone = this.offsetMilestones.get(lastPassedOffsetMilestoneIndex).intValue();
            this.currentMediaState.offsetMilestone = milestone;
            if (this.segmentByOffsetMilestones) {
                this.currentMediaState.segmentNum = lastPassedOffsetMilestoneIndex + 1;
                StringBuilder segmentBuilder = new StringBuilder();
                segmentBuilder.append("O:");
                segmentBuilder.append(Integer.toString(milestone));
                segmentBuilder.append("-");
                if (lastPassedOffsetMilestoneIndex < this.offsetMilestones.size() - 1) {
                    segmentBuilder.append(Integer.toString(this.offsetMilestones.get(lastPassedOffsetMilestoneIndex + 1).intValue()));
                } else {
                    String end = isLive() ? "E" : Integer.toString((int) this.length);
                    segmentBuilder.append(end);
                }
                this.currentMediaState.segment = segmentBuilder.toString();
            }
        }
    }

    private int calculateLastPassedMilestoneIndex() {
        if (this.milestones.size() == 0) {
            return -1;
        }
        int returnVal = -1;
        for (int i = 0; i < this.milestones.size(); i++) {
            int milestone = this.milestones.get(i).intValue();
            if (this.currentMediaState.percent >= milestone) {
                returnVal = i;
            }
        }
        return returnVal;
    }

    private int calculateLastPassedOffsetMilestoneIndex() {
        if (this.offsetMilestones.size() == 0) {
            return -1;
        }
        int returnVal = -1;
        for (int i = 0; i < this.offsetMilestones.size(); i++) {
            int milestone = this.offsetMilestones.get(i).intValue();
            if (this.currentMediaState.offset >= milestone) {
                returnVal = i;
            }
        }
        return returnVal;
    }

    private void updateCurrentMediaStateMediaEventIfNeeded(int eventType) {
        if (eventType == 0) {
            return;
        }
        if (this.currentMediaState.percent >= 100.0d) {
            this.currentMediaState.mediaEvent = "CLOSE";
            return;
        }
        if (this.previousMediaState != null) {
            if (this.currentMediaState.milestone > this.previousMediaState.milestone) {
                this.currentMediaState.mediaEvent = "MILESTONE";
                return;
            }
            if (this.currentMediaState.offsetMilestone > this.previousMediaState.offsetMilestone) {
                this.currentMediaState.mediaEvent = "OFFSET_MILESTONE";
            } else if (getTrackSecondsThreshold() > 0 && this.currentMediaState.getTimePlayedSinceTrack() >= getTrackSecondsThreshold()) {
                this.currentMediaState.mediaEvent = "SECONDS";
            }
        }
    }

    private double validateOffset(double offset) {
        if (offset < 0.0d && this.previousMediaState != null) {
            return (this.currentMediaState.getTimestamp() - this.previousMediaState.getTimestamp()) + this.previousMediaState.offset;
        }
        return offset;
    }

    private void updateTimePlayed(int defaultEventType) {
        if (this.previousMediaState != null) {
            double timePlayed = 0.0d;
            if (this.currentMediaState.offset > this.previousMediaState.offset && defaultEventType != 1) {
                timePlayed = this.currentMediaState.offset - this.previousMediaState.offset;
            }
            this.currentMediaState.setTimePlayed(this.previousMediaState.getTimePlayed() + timePlayed);
            this.currentMediaState.setTimePlayedSinceTrack(this.previousMediaState.getTimePlayedSinceTrack() + timePlayed);
        }
    }

    private void setEventFirstTime(MediaState mediaState) {
        String eventKey = mediaState.mediaEvent;
        if (eventKey.equals("MILESTONE")) {
            eventKey = eventKey + "_" + mediaState.milestone;
        } else if (eventKey.equals("OFFSET_MILESTONE")) {
            eventKey = eventKey + "_" + mediaState.offsetMilestone;
        }
        if (!this.firstEventList.contains(eventKey)) {
            mediaState.eventFirstTime = true;
            this.firstEventList.add(eventKey);
        }
    }

    protected boolean isPlaying() {
        return (this.currentMediaState == null || this.currentMediaState.eventType == 0 || this.currentMediaState.eventType == 2) ? false : true;
    }

    protected boolean isCurrentOffsetPastCompleteThreshold() {
        return this.currentMediaState.offset >= this.length - ((double) this.completeCloseOffsetThreshold);
    }

    protected boolean isLive() {
        return this.length == -1.0d;
    }

    private void setMilestones(String milestones) {
        this.milestones.clear();
        if (milestones != null && milestones.length() > 0) {
            this.milestones.add(0);
            String[] milestoneStrings = milestones.split(",");
            for (String milestoneString : milestoneStrings) {
                int milestone = (int) Double.parseDouble(milestoneString);
                if (milestone > 0 && milestone <= 100 && !this.milestones.contains(Integer.valueOf(milestone))) {
                    this.milestones.add(Integer.valueOf(milestone));
                }
            }
            Collections.sort(this.milestones);
        }
    }

    private void setOffsetMilestones(String offsetMilestones) {
        this.offsetMilestones.clear();
        if (offsetMilestones != null && offsetMilestones.length() > 0) {
            this.offsetMilestones.add(0);
            String[] milestoneStrings = offsetMilestones.split(",");
            for (String milestoneString : milestoneStrings) {
                int milestone = (int) Double.parseDouble(milestoneString);
                if (milestone > 0 && !this.offsetMilestones.contains(Integer.valueOf(milestone)) && (isLive() || milestone <= this.length)) {
                    this.offsetMilestones.add(Integer.valueOf(milestone));
                }
            }
            Collections.sort(this.offsetMilestones);
        }
    }

    protected MediaState getReportMediaState() {
        MediaState reportedState = new MediaState(this.currentMediaState);
        if (this.previousMediaState != null) {
            boolean usePreviousSegmentInfo = false;
            if (this.currentMediaState.milestone <= this.previousMediaState.milestone) {
                reportedState.milestone = 0;
                usePreviousSegmentInfo = true;
            }
            if (this.currentMediaState.offsetMilestone <= this.previousMediaState.offsetMilestone) {
                reportedState.offsetMilestone = 0;
                usePreviousSegmentInfo = true;
            }
            if (usePreviousSegmentInfo) {
                reportedState.segment = this.previousMediaState.segment;
                reportedState.segmentNum = this.previousMediaState.segmentNum;
                reportedState.segmentLength = this.previousMediaState.segmentLength;
            }
        }
        return reportedState;
    }

    protected boolean isSegmentByMilestones() {
        return this.segmentByMilestones;
    }

    protected String getName() {
        return this.name;
    }

    protected double getLength() {
        return this.length;
    }

    protected String getPlayerName() {
        return this.playerName;
    }

    protected String getPlayerID() {
        return this.playerID;
    }

    protected void setSegmentByMilestones(boolean segmentByMilestones) {
        this.segmentByMilestones = segmentByMilestones;
    }

    protected boolean isSegmentByOffsetMilestones() {
        return this.segmentByOffsetMilestones;
    }

    protected void setSegmentByOffsetMilestones(boolean segmentByOffsetMilestones) {
        this.segmentByOffsetMilestones = segmentByOffsetMilestones;
    }

    public int getTrackSecondsThreshold() {
        return this.trackSecondsThreshold;
    }

    public void setTrackSecondsThreshold(int trackSecondsThreshold) {
        this.trackSecondsThreshold = trackSecondsThreshold;
    }

    protected boolean isCompleteTracked() {
        return this.completeTracked;
    }

    protected void setCompleteTracked(boolean completeTracked) {
        this.completeTracked = completeTracked;
    }

    public void setCompleteCloseOffsetThreshold(int completeCloseOffsetThreshold) {
        this.completeCloseOffsetThreshold = completeCloseOffsetThreshold;
    }
}
