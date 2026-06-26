package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.internal.widget.ActivityChooserView;
import android.text.TextUtils;
import com.comscore.utils.Constants;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzpb;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class Tracker extends zzd {
    private boolean zzLN;
    private final Map<String, String> zzLO;
    private final zzad zzLP;
    private final zza zzLQ;
    private ExceptionReporter zzLR;
    private zzal zzLS;
    private final Map<String, String> zzvS;

    private class zza extends zzd implements GoogleAnalytics.zza {
        private boolean zzMb;
        private int zzMc;
        private long zzMd;
        private boolean zzMe;
        private long zzMf;

        protected zza(zzf zzfVar) {
            super(zzfVar);
            this.zzMd = -1L;
        }

        private void zzhV() {
            if (this.zzMd >= 0 || this.zzMb) {
                zzhK().zza(Tracker.this.zzLQ);
            } else {
                zzhK().zzb(Tracker.this.zzLQ);
            }
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.zzMb = enabled;
            zzhV();
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.zzMd = sessionTimeout;
            zzhV();
        }

        @Override // com.google.android.gms.analytics.internal.zzd
        protected void zzhR() {
        }

        public synchronized boolean zzhU() {
            boolean z;
            z = this.zzMe;
            this.zzMe = false;
            return z;
        }

        boolean zzhW() {
            return zzit().elapsedRealtime() >= this.zzMf + Math.max(1000L, this.zzMd);
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.zza
        public void zzn(Activity activity) {
            if (this.zzMc == 0 && zzhW()) {
                this.zzMe = true;
            }
            this.zzMc++;
            if (this.zzMb) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Tracker.this.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap map = new HashMap();
                map.put("&t", "screenview");
                Tracker.this.set("&cd", Tracker.this.zzLS != null ? Tracker.this.zzLS.zzq(activity) : activity.getClass().getCanonicalName());
                if (TextUtils.isEmpty((CharSequence) map.get("&dr"))) {
                    String strZzp = Tracker.zzp(activity);
                    if (!TextUtils.isEmpty(strZzp)) {
                        map.put("&dr", strZzp);
                    }
                }
                Tracker.this.send(map);
            }
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.zza
        public void zzo(Activity activity) {
            this.zzMc--;
            this.zzMc = Math.max(0, this.zzMc);
            if (this.zzMc == 0) {
                this.zzMf = zzit().elapsedRealtime();
            }
        }
    }

    Tracker(zzf analytics, String trackingId, zzad rateLimiter) {
        super(analytics);
        this.zzvS = new HashMap();
        this.zzLO = new HashMap();
        if (trackingId != null) {
            this.zzvS.put("&tid", trackingId);
        }
        this.zzvS.put("useSecure", "1");
        this.zzvS.put("&a", Integer.toString(new Random().nextInt(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) + 1));
        if (rateLimiter == null) {
            this.zzLP = new zzad("tracking");
        } else {
            this.zzLP = rateLimiter;
        }
        this.zzLQ = new zza(analytics);
    }

    private static boolean zza(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        entry.getValue();
        return key.startsWith("&") && key.length() >= 2;
    }

    private static String zzb(Map.Entry<String, String> entry) {
        if (zza(entry)) {
            return entry.getKey().substring(1);
        }
        return null;
    }

    private static void zzb(Map<String, String> map, Map<String, String> map2) {
        zzx.zzw(map2);
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strZzb = zzb(entry);
            if (strZzb != null) {
                map2.put(strZzb, entry.getValue());
            }
        }
    }

    private static void zzc(Map<String, String> map, Map<String, String> map2) {
        zzx.zzw(map2);
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strZzb = zzb(entry);
            if (strZzb != null && !map2.containsKey(strZzb)) {
                map2.put(strZzb, entry.getValue());
            }
        }
    }

    private boolean zzhS() {
        return this.zzLR != null;
    }

    static String zzp(Activity activity) {
        zzx.zzw(activity);
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (TextUtils.isEmpty(stringExtra)) {
            return null;
        }
        return stringExtra;
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        this.zzLN = enabled;
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.zzLQ.enableAutoActivityTracking(enabled);
    }

    public void enableExceptionReporting(boolean enable) {
        synchronized (this) {
            if (zzhS() == enable) {
                return;
            }
            if (enable) {
                this.zzLR = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                Thread.setDefaultUncaughtExceptionHandler(this.zzLR);
                zzba("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzLR.zzhL());
                zzba("Uncaught exceptions will not be reported to Google Analytics");
            }
        }
    }

    public String get(String key) {
        zziE();
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (this.zzvS.containsKey(key)) {
            return this.zzvS.get(key);
        }
        if (key.equals("&ul")) {
            return zzam.zza(Locale.getDefault());
        }
        if (key.equals("&cid")) {
            return zziz().zzjt();
        }
        if (key.equals("&sr")) {
            return zziC().zzkj();
        }
        if (key.equals("&aid")) {
            return zziB().zzjb().zzuM();
        }
        if (key.equals("&an")) {
            return zziB().zzjb().zzkp();
        }
        if (key.equals("&av")) {
            return zziB().zzjb().zzkr();
        }
        if (key.equals("&aiid")) {
            return zziB().zzjb().zzyt();
        }
        return null;
    }

    public void send(Map<String, String> params) {
        final long jCurrentTimeMillis = zzit().currentTimeMillis();
        if (zzhK().getAppOptOut()) {
            zzbb("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        final boolean zIsDryRunEnabled = zzhK().isDryRunEnabled();
        final HashMap map = new HashMap();
        zzb(this.zzvS, map);
        zzb(params, map);
        final boolean zZze = zzam.zze(this.zzvS.get("useSecure"), true);
        zzc(this.zzLO, map);
        this.zzLO.clear();
        final String str = map.get("t");
        if (TextUtils.isEmpty(str)) {
            zziu().zzh(map, "Missing hit type parameter");
            return;
        }
        final String str2 = map.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zziu().zzh(map, "Missing tracking id parameter");
            return;
        }
        final boolean zZzhT = zzhT();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int i = Integer.parseInt(this.zzvS.get("&a")) + 1;
                if (i >= Integer.MAX_VALUE) {
                    i = 1;
                }
                this.zzvS.put("&a", Integer.toString(i));
            }
        }
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.Tracker.1
            @Override // java.lang.Runnable
            public void run() {
                if (Tracker.this.zzLQ.zzhU()) {
                    map.put("sc", Constants.DEFAULT_START_PAGE_NAME);
                }
                zzam.zzd(map, "cid", Tracker.this.zzhK().getClientId());
                String str3 = (String) map.get("sf");
                if (str3 != null) {
                    double dZza = zzam.zza(str3, 100.0d);
                    if (zzam.zza(dZza, (String) map.get("cid"))) {
                        Tracker.this.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(dZza));
                        return;
                    }
                }
                com.google.android.gms.analytics.internal.zza zzaVarZziA = Tracker.this.zziA();
                if (zZzhT) {
                    zzam.zzb(map, "ate", zzaVarZziA.zzic());
                    zzam.zzc(map, "adid", zzaVarZziA.zzig());
                } else {
                    map.remove("ate");
                    map.remove("adid");
                }
                zzpb zzpbVarZzjb = Tracker.this.zziB().zzjb();
                zzam.zzc(map, "an", zzpbVarZzjb.zzkp());
                zzam.zzc(map, "av", zzpbVarZzjb.zzkr());
                zzam.zzc(map, "aid", zzpbVarZzjb.zzuM());
                zzam.zzc(map, "aiid", zzpbVarZzjb.zzyt());
                map.put("v", "1");
                map.put("_v", zze.zzMH);
                zzam.zzc(map, "ul", Tracker.this.zziC().zzki().getLanguage());
                zzam.zzc(map, "sr", Tracker.this.zziC().zzkj());
                if (!(str.equals("transaction") || str.equals("item")) && !Tracker.this.zzLP.zzkF()) {
                    Tracker.this.zziu().zzh(map, "Too many hits sent too quickly, rate limiting invoked");
                    return;
                }
                long jZzbq = zzam.zzbq((String) map.get("ht"));
                if (jZzbq == 0) {
                    jZzbq = jCurrentTimeMillis;
                }
                if (zIsDryRunEnabled) {
                    Tracker.this.zziu().zzc("Dry run enabled. Would have sent hit", new zzab(Tracker.this, map, jZzbq, zZze));
                    return;
                }
                String str4 = (String) map.get("cid");
                HashMap map2 = new HashMap();
                zzam.zza(map2, "uid", (Map<String, String>) map);
                zzam.zza(map2, "an", (Map<String, String>) map);
                zzam.zza(map2, "aid", (Map<String, String>) map);
                zzam.zza(map2, "av", (Map<String, String>) map);
                zzam.zza(map2, "aiid", (Map<String, String>) map);
                map.put("_s", String.valueOf(Tracker.this.zzhP().zza(new zzh(0L, str4, str2, TextUtils.isEmpty((CharSequence) map.get("adid")) ? false : true, 0L, map2))));
                Tracker.this.zzhP().zza(new zzab(Tracker.this, map, jZzbq, zZze));
            }
        });
    }

    public void set(String key, String value) {
        zzx.zzb(key, "Key should be non-null");
        if (TextUtils.isEmpty(key)) {
            return;
        }
        this.zzvS.put(key, value);
    }

    public void setAnonymizeIp(boolean anonymize) {
        set("&aip", zzam.zzJ(anonymize));
    }

    public void setAppId(String appId) {
        set("&aid", appId);
    }

    public void setAppInstallerId(String appInstallerId) {
        set("&aiid", appInstallerId);
    }

    public void setAppName(String appName) {
        set("&an", appName);
    }

    public void setAppVersion(String appVersion) {
        set("&av", appVersion);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri == null || uri.isOpaque()) {
            return;
        }
        String queryParameter = uri.getQueryParameter("referrer");
        if (TextUtils.isEmpty(queryParameter)) {
            return;
        }
        Uri uri2 = Uri.parse("http://hostname/?" + queryParameter);
        String queryParameter2 = uri2.getQueryParameter("utm_id");
        if (queryParameter2 != null) {
            this.zzLO.put("&ci", queryParameter2);
        }
        String queryParameter3 = uri2.getQueryParameter("anid");
        if (queryParameter3 != null) {
            this.zzLO.put("&anid", queryParameter3);
        }
        String queryParameter4 = uri2.getQueryParameter("utm_campaign");
        if (queryParameter4 != null) {
            this.zzLO.put("&cn", queryParameter4);
        }
        String queryParameter5 = uri2.getQueryParameter("utm_content");
        if (queryParameter5 != null) {
            this.zzLO.put("&cc", queryParameter5);
        }
        String queryParameter6 = uri2.getQueryParameter("utm_medium");
        if (queryParameter6 != null) {
            this.zzLO.put("&cm", queryParameter6);
        }
        String queryParameter7 = uri2.getQueryParameter("utm_source");
        if (queryParameter7 != null) {
            this.zzLO.put("&cs", queryParameter7);
        }
        String queryParameter8 = uri2.getQueryParameter("utm_term");
        if (queryParameter8 != null) {
            this.zzLO.put("&ck", queryParameter8);
        }
        String queryParameter9 = uri2.getQueryParameter("dclid");
        if (queryParameter9 != null) {
            this.zzLO.put("&dclid", queryParameter9);
        }
        String queryParameter10 = uri2.getQueryParameter("gclid");
        if (queryParameter10 != null) {
            this.zzLO.put("&gclid", queryParameter10);
        }
        String queryParameter11 = uri2.getQueryParameter("aclid");
        if (queryParameter11 != null) {
            this.zzLO.put("&aclid", queryParameter11);
        }
    }

    public void setClientId(String clientId) {
        set("&cid", clientId);
    }

    public void setEncoding(String encoding) {
        set("&de", encoding);
    }

    public void setHostname(String hostname) {
        set("&dh", hostname);
    }

    public void setLanguage(String language) {
        set("&ul", language);
    }

    public void setLocation(String location) {
        set("&dl", location);
    }

    public void setPage(String page) {
        set("&dp", page);
    }

    public void setReferrer(String referrer) {
        set("&dr", referrer);
    }

    public void setSampleRate(double sampleRate) {
        set("&sf", Double.toString(sampleRate));
    }

    public void setScreenColors(String screenColors) {
        set("&sd", screenColors);
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setScreenResolution(int width, int height) {
        if (width >= 0 || height >= 0) {
            set("&sr", width + "x" + height);
        } else {
            zzbd("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.zzLQ.setSessionTimeout(1000 * sessionTimeout);
    }

    public void setTitle(String title) {
        set("&dt", title);
    }

    public void setUseSecure(boolean useSecure) {
        set("useSecure", zzam.zzJ(useSecure));
    }

    public void setViewportSize(String viewportSize) {
        set("&vp", viewportSize);
    }

    void zza(zzal zzalVar) {
        zzba("Loading Tracker config values");
        this.zzLS = zzalVar;
        if (this.zzLS.zzlc()) {
            String trackingId = this.zzLS.getTrackingId();
            set("&tid", trackingId);
            zza("trackingId loaded", trackingId);
        }
        if (this.zzLS.zzld()) {
            String string = Double.toString(this.zzLS.zzle());
            set("&sf", string);
            zza("Sample frequency loaded", string);
        }
        if (this.zzLS.zzlf()) {
            int sessionTimeout = this.zzLS.getSessionTimeout();
            setSessionTimeout(sessionTimeout);
            zza("Session timeout loaded", Integer.valueOf(sessionTimeout));
        }
        if (this.zzLS.zzlg()) {
            boolean zZzlh = this.zzLS.zzlh();
            enableAutoActivityTracking(zZzlh);
            zza("Auto activity tracking loaded", Boolean.valueOf(zZzlh));
        }
        if (this.zzLS.zzli()) {
            boolean zZzlj = this.zzLS.zzlj();
            if (zZzlj) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(zZzlj));
        }
        enableExceptionReporting(this.zzLS.zzlk());
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        this.zzLQ.zza();
        String strZzkp = zzhQ().zzkp();
        if (strZzkp != null) {
            set("&an", strZzkp);
        }
        String strZzkr = zzhQ().zzkr();
        if (strZzkr != null) {
            set("&av", strZzkr);
        }
    }

    boolean zzhT() {
        return this.zzLN;
    }
}
