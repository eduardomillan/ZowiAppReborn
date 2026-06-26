package com.comscore.applications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.comscore.analytics.ApplicationState;
import com.comscore.analytics.Core;
import com.comscore.measurement.Label;
import com.comscore.measurement.Measurement;
import com.comscore.streaming.Constants;
import com.comscore.utils.API13;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationMeasurement extends Measurement {
    protected ApplicationMeasurement(Core core, EventType eventType, String str) {
        this(core, eventType, str, false, false, true);
    }

    protected ApplicationMeasurement(Core core, EventType eventType, String str, boolean z, boolean z2, boolean z3) {
        super(core);
        core.update(z3);
        if (z3) {
            int foregroundTransitionsCountDelta = core.getForegroundTransitionsCountDelta(z2);
            long foregroundTotalTime = core.getForegroundTotalTime(z);
            long foregroundTimeDelta = core.getForegroundTimeDelta(z2);
            long backgroundTotalTime = core.getBackgroundTotalTime(z);
            long backgroundTimeDelta = core.getBackgroundTimeDelta(z2);
            long inactiveTotalTime = core.getInactiveTotalTime(z);
            long inactiveTimeDelta = core.getInactiveTimeDelta(z2);
            long applicationSessionTimeDelta = core.getApplicationSessionTimeDelta(z2);
            long activeUserSessionTimeDelta = core.getActiveUserSessionTimeDelta(z2);
            long userSessionTimeDelta = core.getUserSessionTimeDelta(z2);
            long autoUpdateInterval = core.getAutoUpdateInterval();
            int applicationSessionCountDelta = core.getApplicationSessionCountDelta(z2);
            int activeUserSessionCountDelta = core.getActiveUserSessionCountDelta(z2);
            int userSessionCountDelta = core.getUserSessionCountDelta(z2);
            int userInteractionCount = core.getUserInteractionCount(z2);
            setLabel(new Label("ns_ap_fg", String.valueOf(foregroundTransitionsCountDelta), false));
            setLabel(new Label("ns_ap_ft", String.valueOf(foregroundTotalTime), false));
            setLabel(new Label("ns_ap_dft", String.valueOf(foregroundTimeDelta), false));
            setLabel(new Label("ns_ap_bt", String.valueOf(backgroundTotalTime), false));
            setLabel(new Label("ns_ap_dbt", String.valueOf(backgroundTimeDelta), false));
            setLabel(new Label("ns_ap_it", String.valueOf(inactiveTotalTime), false));
            setLabel(new Label("ns_ap_dit", String.valueOf(inactiveTimeDelta), false));
            if (autoUpdateInterval >= 60000) {
                setLabel(new Label("ns_ap_ut", String.valueOf(autoUpdateInterval), false));
            }
            setLabel(new Label("ns_ap_as", String.valueOf(applicationSessionCountDelta), false));
            setLabel(new Label("ns_ap_das", String.valueOf(applicationSessionTimeDelta), false));
            if (activeUserSessionCountDelta >= 0) {
                setLabel(new Label("ns_ap_aus", String.valueOf(activeUserSessionCountDelta), false));
                setLabel(new Label("ns_ap_daus", String.valueOf(activeUserSessionTimeDelta), false));
                setLabel(new Label("ns_ap_uc", String.valueOf(userInteractionCount), false));
            }
            if (userSessionCountDelta >= 0) {
                setLabel(new Label("ns_ap_us", String.valueOf(userSessionCountDelta), false));
                setLabel(new Label("ns_ap_dus", String.valueOf(userSessionTimeDelta), false));
            }
            setLabel(new Label("ns_ap_usage", Long.toString(this.c - core.getGenesis()), false));
        }
        if (str != null) {
            setPixelURL(str);
        }
        setLabel(new Label("c1", Constants.C1_VALUE, false));
        setLabel(new Label("ns_ap_an", core.getAppName(), false));
        setLabel(new Label("ns_ap_pn", Constants.C10_VALUE, false));
        setLabel(new Label("c12", core.getVisitorId(), false));
        if (core.getCrossPublisherId() != null) {
            setLabel(new Label("ns_ak", core.getCrossPublisherId(), false));
            if (core.getIdHelper().isIdChanged()) {
                setLabel(new Label("ns_ap_ni", "1", false));
            }
        }
        if (core.getIdHelper().getMD5AdvertisingId() != null) {
            setLabel("ns_ap_i3", core.getIdHelper().getMD5AdvertisingId());
        }
        setLabel(new Label("ns_ap_device", Build.DEVICE, false));
        setLabel(new Label("ns_type", a(eventType).toString(), false));
        setLabel(new Label("ns_ts", Long.toString(this.c), false));
        setLabel(new Label("ns_nc", "1", false));
        setLabel(new Label("ns_ap_pfv", Build.VERSION.RELEASE, false));
        setLabel(new Label("ns_ap_pv", Build.VERSION.RELEASE, false));
        setLabel(new Label("ns_ap_pfm", Constants.C10_VALUE, false));
        setLabel(new Label("ns_ap_ar", System.getProperty("os.arch"), false));
        setLabel(new Label("ns_ap_ev", eventType.toString(), false));
        Context appContext = core.getAppContext();
        setLabel(new Label("ns_ap_ver", core.getCurrentVersion(), false));
        Point pointA = a(appContext);
        setLabel(new Label("ns_ap_res", Integer.toString(pointA.x) + "x" + Integer.toString(pointA.y), false));
        setLabel(new Label("ns_ap_lang", Locale.getDefault().getLanguage(), false));
        setLabel(new Label("ns_ap_sv", core.getVersion(), false));
        if (eventType.equals(EventType.KEEPALIVE)) {
            setLabel("ns_ap_oc", String.valueOf(core.getOfflineCache().getEventCount()));
        }
        long coldStartId = core.getColdStartId();
        int coldStartCount = core.getColdStartCount();
        setLabel(new Label("ns_ap_id", String.valueOf(coldStartId), false));
        setLabel(new Label("ns_ap_cs", String.valueOf(coldStartCount), false));
        setLabel(new Label("ns_ap_bi", core.getAppContext().getPackageName(), false));
    }

    @SuppressLint({"NewApi"})
    private Point a(Context context) {
        Point point = new Point();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            return API13.getDisplaySize(defaultDisplay);
        }
        point.x = defaultDisplay.getWidth();
        point.y = defaultDisplay.getHeight();
        return point;
    }

    private static com.comscore.metrics.EventType a(EventType eventType) {
        return (eventType == EventType.START || eventType == EventType.CLOSE || eventType == EventType.VIEW) ? com.comscore.metrics.EventType.VIEW : com.comscore.metrics.EventType.HIDDEN;
    }

    public static ApplicationMeasurement newApplicationMeasurement(Core core, EventType eventType, HashMap<String, String> map, String str) {
        ApplicationMeasurement applicationMeasurement = null;
        if (eventType == EventType.START) {
            core.incrementRunsCount();
            applicationMeasurement = new AppStartMeasurement(core, eventType, str, core.handleColdStart());
        } else if (eventType == EventType.AGGREGATE) {
            applicationMeasurement = new AggregateMeasurement(core, eventType, str);
        } else if (eventType != EventType.CLOSE) {
            applicationMeasurement = new ApplicationMeasurement(core, eventType, str, false, map == null || map.get("ns_st_ev") != "hb", map == null || !map.containsKey("ns_st_ev"));
        }
        if (eventType != EventType.AGGREGATE) {
            applicationMeasurement.a(core.getLabels());
        }
        applicationMeasurement.a(map, eventType == EventType.AGGREGATE);
        if (!applicationMeasurement.hasLabel("name").booleanValue()) {
            if (core.getCurrentActivityName() != null) {
                applicationMeasurement.setLabel("name", core.getCurrentActivityName());
            } else if (eventType == EventType.START) {
                applicationMeasurement.setLabel("name", com.comscore.utils.Constants.DEFAULT_START_PAGE_NAME);
            } else if (core.getApplicationState() == ApplicationState.FOREGROUND) {
                applicationMeasurement.setLabel("name", com.comscore.utils.Constants.DEFAULT_FOREGROUND_PAGE_NAME);
            } else {
                applicationMeasurement.setLabel("name", com.comscore.utils.Constants.DEFAULT_BACKGROUND_PAGE_NAME);
            }
        }
        return applicationMeasurement;
    }
}
