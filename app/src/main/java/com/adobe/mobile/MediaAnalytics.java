package com.adobe.mobile;

import com.adobe.mobile.Media;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class MediaAnalytics {
    private static final String AD_CLICKED_KEY = "a.media.ad.clicked";
    private static final String AD_COMPLETE_KEY = "a.media.ad.complete";
    private static final String AD_CPM = "a.media.ad.CPM";
    private static final String AD_LENGTH_KEY = "a.media.ad.length";
    private static final String AD_MILESTONE_KEY = "a.media.ad.milestone";
    private static final String AD_NAME_KEY = "a.media.ad.name";
    private static final String AD_OFFSET_MILESTONE_KEY = "a.media.ad.offsetMilestone";
    private static final String AD_PLAYER_NAME_KEY = "a.media.ad.playerName";
    private static final String AD_POD = "a.media.ad.pod";
    private static final String AD_POD_POSITION = "a.media.ad.podPosition";
    private static final String AD_SEGMENT_KEY = "a.media.ad.segment";
    private static final String AD_SEGMENT_NUM_KEY = "a.media.ad.segmentNum";
    private static final String AD_SEGMENT_VIEW_KEY = "a.media.ad.segmentView";
    private static final String AD_TIME_PLAYED_KEY = "a.media.ad.timePlayed";
    private static final String AD_VIEW_KEY = "a.media.ad.view";
    private static final String CHANNEL_KEY = "a.media.channel";
    private static final String COMPLETE_KEY = "a.media.complete";
    private static final String CONTENT_TYPE_KEY = "a.contentType";
    private static final String CONTENT_TYPE_VALUE = "video";
    private static final String CONTENT_TYPE_VALUE_AD = "videoAd";
    private static final String DEFAULT_PLAYER_NAME = "Not_Specified";
    private static final String INITIAL_HIT_PAGE_EVENT = "m_s";
    private static final String LENGTH_KEY = "a.media.length";
    protected static final double LIVE_EVENT_LENGTH = -1.0d;
    private static final String MEDIA_CLICKED_KEY = "a.media.clicked";
    private static final String MEDIA_HIT_PAGE_EVENT = "m_i";
    private static final String MEDIA_VIEW_KEY = "a.media.view";
    private static final String MILESTONE_KEY = "a.media.milestone";
    private static final String NAME_KEY = "a.media.name";
    private static final String OFFSET_MILESTONE_KEY = "a.media.offsetMilestone";
    private static final String PAGE_EVENT_VAR_OVERRIDE = "&&pe";
    private static final String PEV_VALUE_OVERRIDE = "video";
    private static final String PEV_VALUE_OVERRIDE_AD = "videoAd";
    private static final String PEV_VAR_OVERRIDE = "&&pev3";
    private static final String PLAYER_NAME_KEY = "a.media.playerName";
    private static final String SEGMENT_KEY = "a.media.segment";
    private static final String SEGMENT_NUM_KEY = "a.media.segmentNum";
    private static final String SEGMENT_VIEW_KEY = "a.media.segmentView";
    private static final String TIME_PLAYED_KEY = "a.media.timePlayed";
    private static final List<String> unwantedValues = Arrays.asList(null, "");
    private static MediaAnalytics _instance = null;
    private static final Object _instanceMutex = new Object();
    protected int trackSeconds = 0;
    protected int completeCloseOffsetThreshold = 1;
    private final HashMap<String, Object> mediaItemList = new HashMap<>();

    MediaAnalytics() {
    }

    protected static MediaAnalytics sharedInstance() {
        MediaAnalytics mediaAnalytics;
        synchronized (_instanceMutex) {
            if (_instance == null) {
                _instance = new MediaAnalytics();
            }
            mediaAnalytics = _instance;
        }
        return mediaAnalytics;
    }

    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    protected synchronized void open(MediaSettings settings, Media.MediaCallback callback) {
        String cleanMediaName = cleanName(settings.name);
        if (isNilOrEmptyString(cleanMediaName)) {
            StaticMethods.logWarningFormat("Analytics - ADBMediaSettings is required with a valid name. Media item not opened", new Object[0]);
        } else if (settings.isMediaAd && isNilOrEmptyString(settings.parentName)) {
            StaticMethods.logWarningFormat("Analytics - Media ad requires parent name, please specify a parent name. Media item not opened", new Object[0]);
        } else {
            double validLength = settings.length > 0.0d ? settings.length : LIVE_EVENT_LENGTH;
            String cleanPlayerName = isNilOrEmptyString(settings.playerName) ? DEFAULT_PLAYER_NAME : cleanName(settings.playerName);
            if (this.mediaItemList.containsKey(cleanMediaName)) {
                close(cleanMediaName);
            }
            if (!isNilOrEmptyString(settings.playerID)) {
                Iterator<String> it = this.mediaItemList.keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object key = it.next();
                    String mediaName = (String) key;
                    String existingPlayerID = ((MediaItem) this.mediaItemList.get(mediaName)).getPlayerID();
                    if (existingPlayerID != null && existingPlayerID.equals(settings.playerID)) {
                        close(mediaName);
                        break;
                    }
                }
            }
            MediaItem mediaItem = new MediaItem(settings, this, cleanMediaName, validLength, cleanPlayerName);
            mediaItem.callback = callback;
            this.mediaItemList.put(cleanMediaName, mediaItem);
        }
    }

    protected synchronized void close(String name) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null) {
            mediaItem.trackCalled = false;
            mediaItem.close();
            notifyDelegateOfMediaState(mediaItem);
            if (!mediaItem.trackCalled) {
                if (mediaItem.currentMediaState.getTimePlayed() > 0.0d) {
                    trackMediaStateIfNecessary(mediaItem, null, true);
                }
                this.mediaItemList.remove(mediaItem.name);
            } else {
                mediaItem.itemClosed = true;
            }
        }
    }

    protected synchronized void play(String name, double offset) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null) {
            mediaItem.trackCalled = false;
            mediaItem.play(offset);
            notifyDelegateOfMediaState(mediaItem);
            if (!mediaItem.trackCalled) {
                if (mediaItem.previousMediaState == null) {
                    trackMediaViewed(mediaItem);
                } else if (mediaItem.currentMediaState.segmentNum != mediaItem.lastTrackSegmentNumber && mediaItem.currentMediaState.timePlayed > 0.0d) {
                    trackMediaStateIfNecessary(mediaItem, null, true);
                } else {
                    trackMediaStateIfNecessary(mediaItem, null, false);
                }
            }
            mediaItem.trackCalled = false;
            removeMediaItemIfComplete(mediaItem);
        }
    }

    protected synchronized void complete(String name, double offset) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null && mediaItem.isPlaying()) {
            mediaItem.trackCalled = false;
            mediaItem.complete(offset);
            notifyDelegateOfMediaState(mediaItem);
            if (!mediaItem.trackCalled && mediaItem.previousMediaState != null) {
                trackMediaStateIfNecessary(mediaItem, null, false);
            }
        }
    }

    protected synchronized void stop(String name, double offset) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null && mediaItem.isPlaying()) {
            mediaItem.trackCalled = false;
            mediaItem.stop(offset);
            notifyDelegateOfMediaState(mediaItem);
            if (!mediaItem.trackCalled && mediaItem.previousMediaState != null) {
                trackMediaStateIfNecessary(mediaItem, null, false);
            }
        }
    }

    protected synchronized void click(String name, double offset) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null && mediaItem.isPlaying()) {
            mediaItem.trackCalled = false;
            mediaItem.click(offset);
            notifyDelegateOfMediaState(mediaItem);
            if (!mediaItem.trackCalled && mediaItem.previousMediaState != null) {
                trackMediaStateIfNecessary(mediaItem, null, true);
            }
        }
    }

    protected void setTrackCalledOnItem(String name) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null) {
            mediaItem.trackCalled = true;
        }
    }

    protected synchronized void track(String name, Map<String, Object> data) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null) {
            if (mediaItem.currentMediaState != null) {
                HashMap<String, Object> contextData = data != null ? new HashMap<>(data) : new HashMap<>();
                removeEmptyValues(contextData);
                trackMediaStateIfNecessary(mediaItem, contextData, true);
            }
            if (mediaItem.itemClosed) {
                this.mediaItemList.remove(mediaItem.name);
            }
            mediaItem.trackCalled = false;
        }
    }

    protected synchronized void monitor(String name, double offset) {
        MediaItem mediaItem = mediaItemWithName(name);
        if (mediaItem != null && mediaItem.isPlaying()) {
            if (mediaItem.trackCalled) {
                mediaItem.trackCalled = false;
            } else {
                mediaItem.monitor(offset);
                notifyDelegateOfMediaState(mediaItem);
                if (mediaItem.previousMediaState != null && !mediaItem.trackCalled) {
                    trackMediaStateIfNecessary(mediaItem, null, false);
                }
            }
        }
    }

    private void notifyDelegateOfMediaState(MediaItem mediaItem) {
        if (mediaItem.callback != null) {
            mediaItem.callback.call(mediaItem.getReportMediaState());
        }
    }

    private void trackMediaViewed(MediaItem mediaItem) {
        HashMap<String, Object> contextData = new HashMap<>();
        contextData.put(!mediaItem.mediaAd ? MEDIA_VIEW_KEY : AD_VIEW_KEY, String.valueOf(true));
        addGenericMediaContextData(contextData, mediaItem, true);
        addSegmentContextData(contextData, mediaItem);
        trackMediaItemWithContextData(mediaItem, contextData);
        removeMediaItemIfComplete(mediaItem);
    }

    private void trackMediaStateIfNecessary(MediaItem mediaItem, HashMap<String, Object> data, boolean forceTrack) {
        HashMap<String, Object> contextData = data != null ? new HashMap<>(data) : new HashMap<>();
        addGenericMediaContextData(contextData, mediaItem, false);
        addSegmentContextData(contextData, mediaItem);
        boolean track = forceTrack;
        if (mediaItem.previousMediaState == null) {
            contextData.put(PAGE_EVENT_VAR_OVERRIDE, INITIAL_HIT_PAGE_EVENT);
            contextData.put(!mediaItem.mediaAd ? MEDIA_VIEW_KEY : AD_VIEW_KEY, true);
            trackMediaItemWithContextData(mediaItem, contextData);
            return;
        }
        if (mediaItem.currentMediaState.complete) {
            if (!mediaItem.isCompleteTracked()) {
                contextData.put(!mediaItem.mediaAd ? COMPLETE_KEY : AD_COMPLETE_KEY, String.valueOf(true));
                mediaItem.setCompleteTracked(true);
                track = true;
            }
            removeMediaItemIfComplete(mediaItem);
        }
        if (mediaItem.currentMediaState.clicked) {
            contextData.put(!mediaItem.mediaAd ? MEDIA_CLICKED_KEY : AD_CLICKED_KEY, String.valueOf(true));
        }
        if (mediaItem.currentMediaState.offsetMilestone > mediaItem.previousMediaState.offsetMilestone) {
            contextData.put(!mediaItem.mediaAd ? OFFSET_MILESTONE_KEY : AD_OFFSET_MILESTONE_KEY, Integer.toString(mediaItem.currentMediaState.offsetMilestone));
            track = true;
        }
        if (mediaItem.currentMediaState.milestone > mediaItem.previousMediaState.milestone) {
            contextData.put(!mediaItem.mediaAd ? MILESTONE_KEY : AD_MILESTONE_KEY, Integer.toString(mediaItem.currentMediaState.milestone));
            track = true;
        }
        if (mediaItem.getTrackSecondsThreshold() > 0 && mediaItem.currentMediaState.getTimePlayedSinceTrack() >= mediaItem.getTrackSecondsThreshold()) {
            track = true;
        }
        if (track) {
            if (mediaItem.currentMediaState.getTimePlayedSinceTrack() > 0.0d) {
                contextData.put(!mediaItem.mediaAd ? TIME_PLAYED_KEY : AD_TIME_PLAYED_KEY, Integer.toString((int) mediaItem.currentMediaState.getTimePlayedSinceTrack()));
            }
            trackMediaItemWithContextData(mediaItem, contextData);
        }
    }

    private void trackMediaItemWithContextData(MediaItem mediaItem, HashMap<String, Object> contextData) {
        trackMedia(contextData);
        mediaItem.currentMediaState.setTimePlayedSinceTrack(0.0d);
    }

    private void addSegmentContextData(HashMap<String, Object> contextData, MediaItem mediaItem) {
        if (mediaItem.isSegmentByMilestones() || mediaItem.isSegmentByOffsetMilestones()) {
            MediaState segmentMediaState = mediaItem.currentMediaState;
            if (mediaItem.previousMediaState != null) {
                if (mediaItem.currentMediaState.segmentNum != mediaItem.lastTrackSegmentNumber || mediaItem.currentMediaState.complete) {
                    contextData.put(!mediaItem.mediaAd ? SEGMENT_VIEW_KEY : AD_SEGMENT_VIEW_KEY, String.valueOf(true));
                }
                if (mediaItem.currentMediaState.segmentNum != mediaItem.previousMediaState.segmentNum) {
                    segmentMediaState = mediaItem.previousMediaState;
                }
            }
            if (segmentMediaState.segmentNum > 0) {
                contextData.put(!mediaItem.mediaAd ? SEGMENT_NUM_KEY : AD_SEGMENT_NUM_KEY, Integer.toString(segmentMediaState.segmentNum));
            }
            if (segmentMediaState.segment != null) {
                contextData.put(!mediaItem.mediaAd ? SEGMENT_KEY : AD_SEGMENT_KEY, segmentMediaState.segment);
            }
        }
        mediaItem.lastTrackSegmentNumber = mediaItem.currentMediaState.segmentNum;
    }

    private void addGenericMediaContextData(HashMap<String, Object> contextData, MediaItem mediaItem, boolean mediaViewed) {
        contextData.put(PAGE_EVENT_VAR_OVERRIDE, mediaViewed ? INITIAL_HIT_PAGE_EVENT : MEDIA_HIT_PAGE_EVENT);
        if (mediaItem.mediaAd && !isNilOrEmptyString(mediaItem.parentName)) {
            contextData.put(PEV_VAR_OVERRIDE, "videoAd");
            contextData.put(CONTENT_TYPE_KEY, "videoAd");
            contextData.put(AD_NAME_KEY, mediaItem.getName());
            contextData.put(AD_PLAYER_NAME_KEY, mediaItem.getPlayerName());
            contextData.put(NAME_KEY, cleanName(mediaItem.parentName));
            if (!mediaItem.isLive()) {
                contextData.put(AD_LENGTH_KEY, Integer.toString((int) mediaItem.getLength()));
            }
            if (mediaItem.parentPod != null && mediaItem.parentPod.length() > 0) {
                contextData.put(AD_POD, mediaItem.parentPod);
            }
            if (mediaItem.parentPodPosition > 0.0d) {
                contextData.put(AD_POD_POSITION, Integer.toString((int) mediaItem.parentPodPosition));
            }
            if (mediaViewed && !isNilOrEmptyString(mediaItem.CPM)) {
                contextData.put(AD_CPM, mediaItem.CPM);
            }
        } else {
            contextData.put(PEV_VAR_OVERRIDE, "video");
            contextData.put(CONTENT_TYPE_KEY, "video");
            contextData.put(NAME_KEY, mediaItem.getName());
            contextData.put(PLAYER_NAME_KEY, mediaItem.getPlayerName());
            if (!mediaItem.isLive()) {
                contextData.put(LENGTH_KEY, Integer.toString((int) mediaItem.getLength()));
            }
        }
        if (!isNilOrEmptyString(mediaItem.channel)) {
            contextData.put(CHANNEL_KEY, mediaItem.channel);
        }
    }

    private void trackMedia(HashMap<String, Object> contextData) {
        AnalyticsTrackInternal.trackInternal("Media", contextData, StaticMethods.getTimeSince1970());
    }

    private void removeMediaItemIfComplete(MediaItem mediaItem) {
        if (mediaItem.currentMediaState.percent >= 100.0d) {
            this.mediaItemList.remove(mediaItem.name);
        }
    }

    private String cleanName(String name) {
        if (isNilOrEmptyString(name)) {
            return null;
        }
        return name.replace(Droid2InoConstants.NEW_LINE_CHARACTER, "").replace("\r", "").replace("--**--", "");
    }

    private MediaItem mediaItemWithName(String name) {
        String cleanName = cleanName(name);
        if (isNilOrEmptyString(cleanName) || hashMapIsNullOrEmpty(this.mediaItemList)) {
            return null;
        }
        return (MediaItem) this.mediaItemList.get(cleanName);
    }

    private void removeEmptyValues(HashMap<String, Object> data) {
        Collection<Object> cleanValues = data.values();
        cleanValues.removeAll(unwantedValues);
    }

    private boolean hashMapIsNullOrEmpty(HashMap val) {
        return val == null || val.size() == 0;
    }

    private boolean isNilOrEmptyString(String val) {
        return val == null || val.trim().length() == 0;
    }
}
