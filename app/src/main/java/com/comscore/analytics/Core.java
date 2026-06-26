package com.comscore.analytics;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import com.comscore.applications.ApplicationMeasurement;
import com.comscore.applications.EventType;
import com.comscore.applications.KeepAlive;
import com.comscore.measurement.Measurement;
import com.comscore.measurement.MeasurementDispatcher;
import com.comscore.utils.CSLog;
import com.comscore.utils.CacheFlusher;
import com.comscore.utils.ConnectivityChangeReceiver;
import com.comscore.utils.Constants;
import com.comscore.utils.CustomExceptionHandler;
import com.comscore.utils.Date;
import com.comscore.utils.DispatchQueue;
import com.comscore.utils.OfflineMeasurementsCache;
import com.comscore.utils.Storage;
import com.comscore.utils.TransmissionMode;
import com.comscore.utils.Utils;
import com.comscore.utils.id.IdHelper;
import com.comscore.utils.task.TaskExecutor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class Core {
    protected static final long x = 300;
    protected long C;
    protected long D;
    protected long E;
    protected long F;
    protected long G;
    protected long H;
    protected long I;
    protected long J;
    protected long K;
    protected long M;
    protected long N;
    protected long O;
    protected int P;
    protected int Q;
    protected int R;
    protected long S;
    protected long T;
    protected long U;
    protected int V;
    protected long W;
    protected long X;
    protected Runnable Y;
    protected String Z;
    OfflineMeasurementsCache a;
    String aa;
    Context ab;
    boolean ai;
    TransmissionMode aj;
    TransmissionMode ak;
    String[] al;
    private IdHelper am;
    private boolean ao;
    Storage b;
    KeepAlive c;
    CacheFlusher d;

    @Deprecated
    DispatchQueue e;
    TaskExecutor f;
    MeasurementDispatcher g;
    ConnectivityChangeReceiver h;
    protected Runnable i;
    protected Runnable j;
    protected long k;
    long p;
    long r;
    long s;
    String t;
    String u;
    String w;
    protected boolean l = true;
    protected boolean m = true;
    boolean n = false;
    boolean v = true;
    protected ApplicationState y = ApplicationState.INACTIVE;
    protected SessionState L = SessionState.INACTIVE;
    protected long af = 0;
    protected boolean ag = false;
    private boolean an = true;
    AtomicInteger q = new AtomicInteger(0);
    AtomicInteger o = new AtomicInteger();
    protected AtomicInteger B = new AtomicInteger(0);
    protected AtomicInteger z = new AtomicInteger(0);
    protected AtomicInteger A = new AtomicInteger(0);
    protected Thread.UncaughtExceptionHandler ah = Thread.getDefaultUncaughtExceptionHandler();
    boolean ae = true;
    protected final HashMap<String, String> ac = new HashMap<>();
    protected final HashMap<String, String> ad = new HashMap<>();

    public class UserInteractionTask implements Runnable {
        public UserInteractionTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (Core.this) {
                if (Core.this.an) {
                    if (Core.this.Y != null) {
                        Core.this.f.removeEnqueuedTask(Core.this.Y);
                        Core.this.Y = null;
                        Core.this.n();
                    }
                }
            }
        }
    }

    public Core() {
        reset();
    }

    private void A() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(Constants.LAST_APPLICATION_ACCUMULATION_TIMESTAMP_KEY);
        arrayList.add(Constants.LAST_SESSION_ACCUMULATION_TIMESTAMP_KEY);
        a("lastActivityTime", arrayList);
        a("ns_ap_fg", Constants.FOREGROUND_TRANSITION_COUNT_KEY);
        a("installTime", Constants.INSTALL_ID_KEY);
        a("ns_ap_ver", Constants.PREVIOUS_VERSION_KEY);
    }

    private String a(String str, Properties properties, boolean z) {
        String property;
        if (properties != null && (property = properties.getProperty(str)) != null) {
            this.b.set(str, property);
            return property;
        }
        if (z && this.b.has(str).booleanValue()) {
            return this.b.get(str);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TransmissionMode transmissionMode) {
        if (this.an) {
            this.aj = transmissionMode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(TransmissionMode transmissionMode) {
        if (this.an) {
            this.ak = transmissionMode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (this.an && this.am != null) {
            this.am.setPublisherSecret(str);
            this.am.generateIds();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(String str, String str2) {
        if (this.an) {
            this.ac.put(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        if (this.an) {
            this.aa = str;
            if (this.b != null) {
                this.b.set(Storage.APP_NAME_KEY, this.aa);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(String str) {
        if (this.an) {
            a(isSecure() ? Constants.CENSUS_URL_SECURE : Constants.CENSUS_URL);
            b("c2", str);
        }
    }

    @Deprecated
    public static Core getInstance() {
        return comScore.getCore();
    }

    protected Measurement a(EventType eventType, HashMap<String, String> map, String str) {
        return ApplicationMeasurement.newApplicationMeasurement(this, eventType, map, str);
    }

    protected IdHelper a(Context context, Storage storage) {
        return new IdHelper(context, storage);
    }

    protected void a() {
        this.b = b();
        this.g = e();
        a(this.b);
        this.e = c();
        this.c = f();
        this.a = g();
        this.d = h();
        this.h = i();
        j();
        this.am = a(this.ab, this.b);
    }

    synchronized void a(int i, boolean z) {
        if (this.an) {
            w();
            if (i < 60) {
                i = 60;
            }
            this.l = z;
            this.k = i * 1000;
            if (this.y == ApplicationState.FOREGROUND) {
                v();
            } else if (this.y == ApplicationState.BACKGROUND_UX_ACTIVE && !this.l) {
                v();
            }
        }
    }

    protected void a(ApplicationState applicationState) {
        CSLog.d(this, "Leaving application state: " + applicationState);
        switch (aa.a[applicationState.ordinal()]) {
            case 1:
                this.h.start();
                this.c.start(3000);
                this.ab.registerReceiver(this.h, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.d.start();
                break;
            case 2:
                w();
                break;
            case 3:
                setCurrentActivityName(null);
                w();
                break;
        }
    }

    protected void a(ApplicationState applicationState, ApplicationState applicationState2) {
        if (this.an && applicationState2 != ApplicationState.INACTIVE && isAutoStartEnabled() && !this.n) {
            notify(EventType.START, this.ad, false);
        }
    }

    protected void a(SessionState sessionState) {
        if (this.an) {
            CSLog.d(this, "Leaving session state: " + sessionState);
            long jUnixTime = Date.unixTime();
            switch (aa.b[sessionState.ordinal()]) {
                case 1:
                    if (this.Y != null) {
                        this.f.removeEnqueuedTask(this.Y);
                        this.Y = null;
                    }
                    this.U = jUnixTime;
                case 2:
                    this.T = jUnixTime;
                case 3:
                    this.S = jUnixTime;
                    break;
                case 4:
                    if (!p()) {
                        this.M = (jUnixTime - this.X) + this.M;
                    }
                    break;
            }
        }
    }

    protected void a(SessionState sessionState, SessionState sessionState2) {
    }

    void a(EventType eventType, HashMap<String, String> map) {
        if (this.an) {
            if (z()) {
                x();
                return;
            }
            y();
            if (!this.n && eventType != EventType.START) {
                this.g.sendMeasurmement(a(EventType.START, new HashMap<>(), this.Z), false);
            }
            if (eventType != EventType.CLOSE) {
                this.g.sendMeasurmement(a(eventType, Utils.mapOfStrings(map), this.Z), false);
            }
        }
    }

    protected void a(Storage storage) {
        A();
        this.g.loadEventData();
    }

    void a(String str) {
        if (this.an) {
            int iIndexOf = str.indexOf(63);
            if (iIndexOf < 0) {
                str = str + '?';
            } else if (iIndexOf < str.length() - 1) {
                for (String str2 : str.substring(iIndexOf + 1).split("&")) {
                    String[] strArrSplit = str2.split("=");
                    if (strArrSplit.length == 2) {
                        setLabel(strArrSplit[0], strArrSplit[1], false);
                    } else if (strArrSplit.length == 1) {
                        setLabel("name", strArrSplit[0], false);
                    }
                }
                str = str.substring(0, iIndexOf + 1);
            }
            this.Z = str;
        }
    }

    void a(String str, String str2) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str2);
        a(str, arrayList);
    }

    void a(String str, ArrayList<String> arrayList) {
        for (String str2 : arrayList) {
            String str3 = this.b.get(str);
            String str4 = this.b.get(str2);
            if (Utils.isNotEmpty(str3) && Utils.isEmpty(str4)) {
                this.b.set(str2, str3);
            }
        }
        this.b.remove(str);
    }

    protected void a(boolean z) {
        if (this.an) {
            long jUnixTime = Date.unixTime();
            long j = jUnixTime - this.K;
            switch (aa.a[this.y.ordinal()]) {
                case 1:
                    this.H += j;
                    this.E = j + this.E;
                    break;
                case 2:
                    this.F += j;
                    this.D = j + this.D;
                    break;
                case 3:
                    this.G += j;
                    this.C = j + this.C;
                    break;
            }
            this.K = jUnixTime;
            if (z) {
                this.b.set(Constants.LAST_APPLICATION_ACCUMULATION_TIMESTAMP_KEY, Long.toString(this.K));
                this.b.set(Constants.FOREGROUND_TRANSITION_COUNT_KEY, Long.toString(this.B.get()));
                this.b.set(Constants.ACCUMULATED_FOREGROUND_TIME_KEY, Long.toString(this.G));
                this.b.set(Constants.ACCUMULATED_BACKGROUND_TIME_KEY, Long.toString(this.F));
                this.b.set(Constants.ACCUMULATED_INACTIVE_TIME_KEY, Long.toString(this.H));
                this.b.set(Constants.TOTAL_FOREGROUND_TIME_KEY, Long.toString(this.C));
                this.b.set(Constants.TOTAL_BACKGROUND_TIME_KEY, Long.toString(this.D));
                this.b.set(Constants.TOTAL_INACTIVE_TIME_KEY, Long.toString(this.E));
            }
        }
    }

    public void allowLiveTransmission(TransmissionMode transmissionMode, boolean z) {
        if (this.an && transmissionMode != null) {
            if (!z) {
                a(transmissionMode);
            } else {
                if (this.f == null || getLiveTransmissionMode() == transmissionMode) {
                    return;
                }
                this.f.execute(new h(this, transmissionMode), z);
            }
        }
    }

    public void allowOfflineTransmission(TransmissionMode transmissionMode, boolean z) {
        if (this.an && transmissionMode != null) {
            if (!z) {
                b(transmissionMode);
            } else {
                if (this.f == null || getOfflineTransmissionMode() == transmissionMode) {
                    return;
                }
                this.f.execute(new i(this, transmissionMode), z);
            }
        }
    }

    protected Storage b() {
        return new Storage(this.ab);
    }

    protected void b(ApplicationState applicationState) {
        if (this.an) {
            CSLog.d(this, "Entering application state: " + applicationState);
            switch (aa.a[applicationState.ordinal()]) {
                case 1:
                    this.h.stop();
                    this.c.stop();
                    this.d.stop();
                    try {
                        this.ab.unregisterReceiver(this.h);
                        break;
                    } catch (IllegalArgumentException e) {
                    }
                    w();
                    break;
                case 2:
                    if (!this.l) {
                        v();
                    }
                    break;
                case 3:
                    v();
                    this.B.getAndIncrement();
                    break;
            }
        }
    }

    protected void b(SessionState sessionState) {
        if (this.an) {
            CSLog.d(this, "Entering session state: " + sessionState);
            switch (aa.b[sessionState.ordinal()]) {
                case 1:
                    q();
                    o();
                case 2:
                    r();
                case 3:
                    p();
                    break;
            }
        }
    }

    protected void b(boolean z) {
        if (this.an) {
            long jUnixTime = Date.unixTime();
            long j = jUnixTime - this.X;
            switch (aa.b[this.L.ordinal()]) {
                case 1:
                    this.O += j;
                    this.U = jUnixTime;
                case 2:
                    this.N += j;
                    this.T = jUnixTime;
                case 3:
                    this.M = j + this.M;
                    this.S = jUnixTime;
                    break;
            }
            this.X = jUnixTime;
            if (z) {
                this.b.set(Constants.LAST_SESSION_ACCUMULATION_TIMESTAMP_KEY, Long.toString(this.X));
                this.b.set(Constants.LAST_APPLICATION_SESSION_TIMESTAMP_KEY, Long.toString(this.S));
                this.b.set(Constants.LAST_USER_SESSION_TIMESTAMP_KEY, Long.toString(this.T));
                this.b.set(Constants.LAST_ACTIVE_USER_SESSION_TIMESTAMP_KEY, Long.toString(this.U));
                this.b.set(Constants.ACCUMULATED_APPLICATION_SESSION_TIME_KEY, Long.toString(this.M));
                this.b.set(Constants.ACCUMULATED_ACTIVE_USER_SESSION_TIME_KEY, Long.toString(this.O));
                this.b.set(Constants.ACCUMULATED_USER_SESSION_TIME_KEY, Long.toString(this.N));
                this.b.set(Constants.ACTIVE_USER_SESSION_COUNT_KEY, Long.toString(this.R));
                this.b.set(Constants.USER_SESSION_COUNT_KEY, Long.toString(this.Q));
                this.b.set(Constants.LAST_USER_INTERACTION_TIMESTAMP_KEY, Long.toString(this.W));
                this.b.set(Constants.USER_INTERACTION_COUNT_KEY, Integer.toString(this.V));
                this.b.set(Constants.PREVIOUS_GENESIS_KEY, Long.toString(this.J));
                this.b.set(Constants.GENESIS_KEY, Long.toString(this.I));
                this.b.set(Constants.APPLICATION_SESSION_COUNT_KEY, Integer.toString(this.P));
            }
        }
    }

    @Deprecated
    protected DispatchQueue c() {
        return new DispatchQueue(this);
    }

    void c(boolean z) {
        this.ae = z;
    }

    protected TaskExecutor d() {
        return new TaskExecutor(this);
    }

    protected void d(boolean z) {
        if (this.an) {
            this.n = z;
        }
    }

    public synchronized void disableAutoUpdate() {
        if (this.an) {
            w();
            this.l = true;
            this.k = -1L;
        }
    }

    protected MeasurementDispatcher e() {
        return new MeasurementDispatcher(this);
    }

    public void enableAutoUpdate(int i, boolean z, boolean z2) {
        if (this.an) {
            if (!z2) {
                a(i, z);
            } else if (this.f != null) {
                this.f.execute(new v(this, i, z), z2);
            }
        }
    }

    protected KeepAlive f() {
        return new KeepAlive(this, MeasurementDispatcher.MILLIS_PER_DAY);
    }

    public void flush(boolean z) {
        if (this.an && this.f != null) {
            this.f.execute(new u(this), z);
        }
    }

    protected OfflineMeasurementsCache g() {
        return new OfflineMeasurementsCache(this);
    }

    public synchronized int getActiveUserSessionCountDelta(boolean z) {
        int i;
        i = -1;
        if (this.R >= 0) {
            i = this.R;
            if (z && this.an) {
                this.R = 0;
                this.b.set(Constants.ACTIVE_USER_SESSION_COUNT_KEY, Integer.toString(this.R));
            }
        }
        return i;
    }

    public synchronized long getActiveUserSessionTimeDelta(boolean z) {
        long j;
        j = this.O;
        if (z && this.an) {
            this.O = 0L;
            this.b.set(Constants.ACCUMULATED_ACTIVE_USER_SESSION_TIME_KEY, Long.toString(this.O));
        }
        return j;
    }

    public Context getAppContext() {
        return this.ab;
    }

    public String getAppName() {
        if ((this.aa == null || this.aa.length() == 0) && this.ab != null) {
            String packageName = this.ab.getPackageName();
            PackageManager packageManager = this.ab.getPackageManager();
            try {
                CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0));
                if (applicationLabel != null) {
                    setAppName(applicationLabel.toString(), false);
                }
            } catch (PackageManager.NameNotFoundException e) {
                this.aa = this.b.get(Storage.APP_NAME_KEY);
            }
        }
        return this.aa;
    }

    public synchronized int getApplicationSessionCountDelta(boolean z) {
        int i;
        i = this.P;
        if (z && this.an) {
            this.P = 0;
            this.b.set(Constants.APPLICATION_SESSION_COUNT_KEY, Integer.toString(this.P));
        }
        return i;
    }

    public synchronized long getApplicationSessionTimeDelta(boolean z) {
        long j;
        j = this.M;
        if (z && this.an) {
            this.M = 0L;
            this.b.set(Constants.ACCUMULATED_APPLICATION_SESSION_TIME_KEY, Long.toString(this.M));
        }
        return j;
    }

    public ApplicationState getApplicationState() {
        return this.y;
    }

    public String getAutoStartLabel(String str) {
        return this.ad.get(str);
    }

    public HashMap<String, String> getAutoStartLabels() {
        return this.ad;
    }

    public synchronized long getAutoUpdateInterval() {
        return this.k;
    }

    public synchronized long getBackgroundTimeDelta(boolean z) {
        long j;
        j = this.F;
        if (z && this.an) {
            this.F = 0L;
            this.b.set(Constants.ACCUMULATED_BACKGROUND_TIME_KEY, Long.toString(this.F));
        }
        return j;
    }

    public synchronized long getBackgroundTotalTime(boolean z) {
        long j;
        j = this.D;
        if (z && this.an) {
            this.D = 0L;
            this.b.set(Constants.TOTAL_BACKGROUND_TIME_KEY, Long.toString(this.D));
        }
        return j;
    }

    public CacheFlusher getCacheFlusher() {
        return this.d;
    }

    public long getCacheFlushingInterval() {
        return this.af;
    }

    public int getCacheMaxBatchFiles() {
        if (this.a != null) {
            return this.a.getCacheMaxBatchFiles();
        }
        return 100;
    }

    public int getCacheMaxFlushesInARow() {
        if (this.a != null) {
            return this.a.getCacheMaxPosts();
        }
        return 10;
    }

    public int getCacheMaxMeasurements() {
        if (this.a != null) {
            return this.a.getCacheMaxMeasurements();
        }
        return 2000;
    }

    public long getCacheMeasurementExpiry() {
        if (this.a != null) {
            return this.a.getCacheMeasurementExpiry();
        }
        return 31L;
    }

    public long getCacheMinutesToRetry() {
        if (this.a != null) {
            return this.a.getCacheWaitMinutes();
        }
        return 30L;
    }

    public int getColdStartCount() {
        return this.q.get();
    }

    public long getColdStartId() {
        return this.p;
    }

    public ConnectivityChangeReceiver getConnectivityReceiver() {
        return this.h;
    }

    public String getCrossPublisherId() {
        if (this.am == null) {
            return null;
        }
        return this.am.getCrossPublisherId();
    }

    public String getCurrentActivityName() {
        return this.w;
    }

    public String getCurrentVersion() {
        return this.t;
    }

    public String getCustomerC2() {
        return getLabels().get("c2");
    }

    public boolean getErrorHandlingEnabled() {
        return this.ag;
    }

    public long getFirstInstallId() {
        return this.s;
    }

    public synchronized long getForegroundTimeDelta(boolean z) {
        long j;
        j = this.G;
        if (z && this.an) {
            this.G = 0L;
            this.b.set(Constants.ACCUMULATED_FOREGROUND_TIME_KEY, Long.toString(this.G));
        }
        return j;
    }

    public synchronized long getForegroundTotalTime(boolean z) {
        long j;
        j = this.C;
        if (z && this.an) {
            this.C = 0L;
            this.b.set(Constants.TOTAL_FOREGROUND_TIME_KEY, Long.toString(this.C));
        }
        return j;
    }

    public synchronized int getForegroundTransitionsCountDelta(boolean z) {
        int i;
        i = this.B.get();
        if (z && this.an) {
            this.B.set(0);
            this.b.set(Constants.FOREGROUND_TRANSITION_COUNT_KEY, Long.toString(this.B.get()));
        }
        return i;
    }

    public long getGenesis() {
        return this.I;
    }

    public IdHelper getIdHelper() {
        return this.am;
    }

    public synchronized long getInactiveTimeDelta(boolean z) {
        long j;
        j = this.H;
        if (z && this.an) {
            this.H = 0L;
            this.b.set(Constants.ACCUMULATED_INACTIVE_TIME_KEY, Long.toString(this.H));
        }
        return j;
    }

    public synchronized long getInactiveTotalTime(boolean z) {
        long j;
        j = this.E;
        if (z && this.an) {
            this.E = 0L;
            this.b.set(Constants.TOTAL_INACTIVE_TIME_KEY, Long.toString(this.E));
        }
        return j;
    }

    public long getInstallId() {
        return this.r;
    }

    public KeepAlive getKeepAlive() {
        return this.c;
    }

    public String getLabel(String str) {
        return this.ac.get(str);
    }

    public HashMap<String, String> getLabels() {
        return this.ac;
    }

    public TransmissionMode getLiveTransmissionMode() {
        return this.aj;
    }

    public MeasurementDispatcher getMeasurementDispatcher() {
        return this.g;
    }

    public String[] getMeasurementLabelOrder() {
        return this.al;
    }

    public OfflineMeasurementsCache getOfflineCache() {
        return this.a;
    }

    public TransmissionMode getOfflineTransmissionMode() {
        return this.ak;
    }

    public String getPixelURL() {
        return this.Z;
    }

    public long getPreviousGenesis() {
        return this.J;
    }

    public synchronized String getPreviousVersion() {
        String str;
        str = this.u;
        if (this.u != null && this.u.length() > 0) {
            this.b.remove(Constants.PREVIOUS_VERSION_KEY);
            this.u = null;
        }
        return str;
    }

    public String getPublisherSecret() {
        return this.am == null ? "" : this.am.getPublisherSecret();
    }

    public DispatchQueue getQueue() {
        return this.e;
    }

    public int getRunsCount() {
        return this.o.get();
    }

    @Deprecated
    public String getSalt() {
        return getPublisherSecret();
    }

    public SessionState getSessionState() {
        return this.L;
    }

    public Storage getStorage() {
        return this.b;
    }

    public TaskExecutor getTaskExecutor() {
        return this.f;
    }

    public synchronized int getUserInteractionCount(boolean z) {
        int i;
        i = this.V;
        if (z && this.an) {
            this.V = 0;
            this.b.set(Constants.USER_INTERACTION_COUNT_KEY, Integer.toString(this.V));
        }
        return i;
    }

    public synchronized int getUserSessionCountDelta(boolean z) {
        int i;
        i = -1;
        if (this.Q >= 0) {
            i = this.Q;
            if (z && this.an) {
                this.Q = 0;
                this.b.set(Constants.USER_SESSION_COUNT_KEY, Integer.toString(this.Q));
            }
        }
        return i;
    }

    public synchronized long getUserSessionTimeDelta(boolean z) {
        long j;
        j = this.N;
        if (z && this.an) {
            this.N = 0L;
            this.b.set(Constants.ACCUMULATED_USER_SESSION_TIME_KEY, Long.toString(this.N));
        }
        return j;
    }

    public String getVersion() {
        return Constants.SDK_VERSION;
    }

    public String getVisitorId() {
        if (this.am == null) {
            return null;
        }
        return this.am.getVisitorId();
    }

    protected CacheFlusher h() {
        return new CacheFlusher(this);
    }

    public synchronized boolean handleColdStart() {
        boolean z = false;
        synchronized (this) {
            if (this.an && !this.n) {
                this.n = true;
                this.q.getAndIncrement();
                this.b.set(Constants.COLD_START_COUNT_KEY, String.valueOf(this.q));
                this.p = Date.unixTime();
                z = true;
            }
        }
        return z;
    }

    protected ConnectivityChangeReceiver i() {
        return new ConnectivityChangeReceiver(this);
    }

    public void incrementRunsCount() {
        if (this.an) {
            this.o.getAndIncrement();
            this.b.set(Constants.RUNS_COUNT_KEY, Long.toString(this.o.get()));
        }
    }

    public boolean isAutoStartEnabled() {
        return this.v;
    }

    public synchronized boolean isAutoUpdateEnabled() {
        return this.k > 0;
    }

    public boolean isEnabled() {
        return this.an;
    }

    public boolean isKeepAliveEnabled() {
        return this.ae;
    }

    public boolean isSecure() {
        return this.ai;
    }

    protected void j() {
        this.K = Utils.getLong(this.b.get(Constants.LAST_APPLICATION_ACCUMULATION_TIMESTAMP_KEY), -1L);
        this.X = Utils.getLong(this.b.get(Constants.LAST_SESSION_ACCUMULATION_TIMESTAMP_KEY), -1L);
        this.S = Utils.getLong(this.b.get(Constants.LAST_APPLICATION_SESSION_TIMESTAMP_KEY), -1L);
        this.T = Utils.getLong(this.b.get(Constants.LAST_USER_SESSION_TIMESTAMP_KEY), -1L);
        this.U = Utils.getLong(this.b.get(Constants.LAST_ACTIVE_USER_SESSION_TIMESTAMP_KEY), -1L);
        this.B.set(Utils.getInteger(this.b.get(Constants.FOREGROUND_TRANSITION_COUNT_KEY)));
        this.G = Utils.getLong(this.b.get(Constants.ACCUMULATED_FOREGROUND_TIME_KEY));
        this.F = Utils.getLong(this.b.get(Constants.ACCUMULATED_BACKGROUND_TIME_KEY));
        this.H = Utils.getLong(this.b.get(Constants.ACCUMULATED_INACTIVE_TIME_KEY));
        this.C = Utils.getLong(this.b.get(Constants.TOTAL_FOREGROUND_TIME_KEY));
        this.D = Utils.getLong(this.b.get(Constants.TOTAL_BACKGROUND_TIME_KEY));
        this.E = Utils.getLong(this.b.get(Constants.TOTAL_INACTIVE_TIME_KEY));
        this.M = Utils.getLong(this.b.get(Constants.ACCUMULATED_APPLICATION_SESSION_TIME_KEY));
        this.O = Utils.getLong(this.b.get(Constants.ACCUMULATED_ACTIVE_USER_SESSION_TIME_KEY));
        this.N = Utils.getLong(this.b.get(Constants.ACCUMULATED_USER_SESSION_TIME_KEY));
        this.R = Utils.getInteger(this.b.get(Constants.ACTIVE_USER_SESSION_COUNT_KEY), -1);
        this.Q = Utils.getInteger(this.b.get(Constants.USER_SESSION_COUNT_KEY), -1);
        this.W = Utils.getLong(this.b.get(Constants.LAST_USER_INTERACTION_TIMESTAMP_KEY), -1L);
        this.V = Utils.getInteger(this.b.get(Constants.USER_INTERACTION_COUNT_KEY), 0);
        this.P = Utils.getInteger(this.b.get(Constants.APPLICATION_SESSION_COUNT_KEY), 0);
        this.t = k();
        this.J = Utils.getLong(this.b.get(Constants.PREVIOUS_GENESIS_KEY), 0L);
        this.I = Utils.getLong(this.b.get(Constants.GENESIS_KEY), -1L);
        if (this.I < 0) {
            this.I = Date.unixTime();
            this.J = 0L;
            this.S = this.I;
            this.P++;
        } else {
            if (!p()) {
                this.M += Date.unixTime() - this.X;
                this.b.set(Constants.ACCUMULATED_APPLICATION_SESSION_TIME_KEY, Long.toString(this.M));
            }
            this.S = this.I;
        }
        this.s = Utils.getLong(this.b.get(Constants.FIRST_INSTALL_ID_KEY), -1L);
        if (this.s < 0) {
            this.s = this.I;
            this.r = this.I;
            this.b.set(Constants.CURRENT_VERSION_KEY, this.t);
            this.b.set(Constants.FIRST_INSTALL_ID_KEY, String.valueOf(this.s));
            this.b.set(Constants.INSTALL_ID_KEY, String.valueOf(this.r));
        } else {
            if (this.b.has(Constants.PREVIOUS_VERSION_KEY).booleanValue()) {
                this.u = this.b.get(Constants.PREVIOUS_VERSION_KEY);
            }
            String str = this.b.get(Constants.CURRENT_VERSION_KEY);
            if (str.equals(this.t)) {
                this.r = Utils.getLong(this.b.get(Constants.INSTALL_ID_KEY), -1L);
            } else {
                this.u = str;
                this.b.set(Constants.PREVIOUS_VERSION_KEY, this.u);
                this.r = this.I;
                this.b.set(Constants.INSTALL_ID_KEY, String.valueOf(this.r));
            }
            this.b.set(Constants.CURRENT_VERSION_KEY, this.t);
        }
        this.b.set(Constants.GENESIS_KEY, Long.toString(this.I));
        this.b.set(Constants.PREVIOUS_GENESIS_KEY, Long.toString(this.J));
        long jUnixTime = Date.unixTime();
        if (this.K >= 0) {
            long j = jUnixTime - this.K;
            this.H += j;
            this.b.set(Constants.ACCUMULATED_INACTIVE_TIME_KEY, Long.toString(this.H));
            this.E = j + this.E;
            this.b.set(Constants.TOTAL_INACTIVE_TIME_KEY, Long.toString(this.E));
        }
        this.K = jUnixTime;
        this.X = jUnixTime;
        this.b.set(Constants.LAST_APPLICATION_ACCUMULATION_TIMESTAMP_KEY, Long.toString(this.K));
        this.b.set(Constants.LAST_SESSION_ACCUMULATION_TIMESTAMP_KEY, Long.toString(this.X));
        this.b.set(Constants.LAST_APPLICATION_SESSION_TIMESTAMP_KEY, Long.toString(this.S));
        if (!this.b.has(Constants.RUNS_COUNT_KEY).booleanValue()) {
            this.b.set(Constants.RUNS_COUNT_KEY, "0");
        }
        this.o.set(Utils.getInteger(this.b.get(Constants.RUNS_COUNT_KEY)));
        this.q.set(Utils.getInteger(this.b.get(Constants.COLD_START_COUNT_KEY)));
    }

    protected String k() {
        try {
            return this.ab.getPackageManager().getPackageInfo(this.ab.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "0";
        }
    }

    protected void l() {
        if (this.ab != null) {
            try {
                InputStream inputStreamOpen = this.ab.getResources().getAssets().open("comScore.properties");
                Properties properties = new Properties();
                properties.load(inputStreamOpen);
                Constants.DEBUG = Utils.getBoolean(a("Debug", properties, false));
                this.ai = Utils.getBoolean(a("Secure", properties, false));
                String strA = a("PublisherSecret", properties, true);
                if (strA != null) {
                    b(strA);
                }
                String strA2 = a("AppName", properties, true);
                if (strA2 != null) {
                    c(strA2);
                }
                String strA3 = a("CustomerC2", properties, false);
                if (strA3 != null) {
                    d(strA3);
                }
                String strA4 = a("PixelURL", properties, false);
                if (strA4 != null) {
                    a(strA4);
                }
                String strA5 = a("OfflineURL", properties, false);
                if (strA5 != null) {
                    this.a.setUrl(strA5);
                }
                String strA6 = a("LiveTransmissionMode", properties, false);
                if (strA6 != null) {
                    try {
                        this.aj = TransmissionMode.valueOf(strA6.toUpperCase(Locale.getDefault()));
                    } catch (IllegalArgumentException e) {
                        this.aj = TransmissionMode.DEFAULT;
                    }
                }
                String strA7 = a("OfflineTransmissionMode", properties, false);
                if (strA7 != null) {
                    try {
                        this.ak = TransmissionMode.valueOf(strA7.toUpperCase(Locale.getDefault()));
                    } catch (IllegalArgumentException e2) {
                        this.ak = TransmissionMode.DEFAULT;
                    }
                }
                this.ae = Utils.getBoolean(a("KeepAliveEnabled", properties, false), true);
                int integer = Utils.getInteger(a("CacheMaxSize", properties, false), -1);
                if (integer >= 0) {
                    this.a.setCacheMaxMeasurements(integer);
                }
                int integer2 = Utils.getInteger(a("CacheMaxBatchSize", properties, false), -1);
                if (integer2 >= 0) {
                    this.a.setCacheMaxBatchFiles(integer2);
                }
                int integer3 = Utils.getInteger(a("CacheMaxFlushesInARow", properties, false), -1);
                if (integer3 >= 0) {
                    this.a.setCacheMaxPosts(integer3);
                }
                int integer4 = Utils.getInteger(a("CacheMinutesToRetry", properties, false), -1);
                if (integer4 >= 0) {
                    this.a.setCacheWaitMinutes(integer4);
                }
                int integer5 = Utils.getInteger(a("CacheExpiryInDays", properties, false), -1);
                if (integer5 >= 0) {
                    this.a.setCacheMeasurementExpiry(integer5);
                }
                long j = Utils.getLong(a("CacheFlushingInterval", properties, false), -1L);
                if (j >= 0) {
                    this.af = j;
                    if (this.d != null) {
                        this.d.update();
                    }
                }
                setErrorHandlingEnabled(Utils.getBoolean(a("ErrorHandlingEnabled", properties, false)));
                this.v = Utils.getBoolean(a("AutoStartEnabled", properties, false), true);
                boolean z = Utils.getBoolean(a("AutoUpdateInForegroundOnly", properties, false), true);
                int integer6 = Utils.getInteger(a("AutoUpdateInterval", properties, false), -1);
                if (integer6 >= 60) {
                    a(integer6, z);
                }
            } catch (IOException e3) {
                if (Constants.DEBUG) {
                    CSLog.printStackTrace(e3);
                }
            }
        }
    }

    protected Context m() {
        return this.ab;
    }

    protected void n() {
        if (this.an) {
            if (this.f.containsTask(this.j)) {
                this.f.removeEnqueuedTask(this.j);
                this.j = null;
            }
            long jUnixTime = Date.unixTime();
            ApplicationState applicationState = this.z.get() > 0 ? ApplicationState.FOREGROUND : this.A.get() > 0 ? ApplicationState.BACKGROUND_UX_ACTIVE : ApplicationState.INACTIVE;
            SessionState sessionState = jUnixTime - this.W < Constants.USER_SESSION_INACTIVE_PERIOD ? SessionState.ACTIVE_USER : this.A.get() > 0 ? SessionState.USER : this.z.get() > 0 ? SessionState.APPLICATION : SessionState.INACTIVE;
            ApplicationState applicationState2 = this.y;
            SessionState sessionState2 = this.L;
            if (applicationState == applicationState2 && sessionState == sessionState2) {
                return;
            }
            this.j = new ad(this, applicationState2, applicationState, sessionState2, sessionState);
            if (this.m && applicationState != ApplicationState.FOREGROUND) {
                this.f.execute(this.j, x);
            } else {
                this.j.run();
                this.j = null;
            }
        }
    }

    public void notify(EventType eventType, HashMap<String, String> map, boolean z) {
        if (this.an) {
            if (!z) {
                a(eventType, map);
            } else if (this.f != null) {
                this.f.execute(new y(this, eventType, map), z);
            }
        }
    }

    protected void o() {
        if (this.an) {
            if (this.Y != null) {
                this.f.removeEnqueuedTask(this.Y);
                this.Y = null;
            }
            this.Y = new UserInteractionTask();
            this.f.execute(this.Y, Constants.USER_SESSION_INACTIVE_PERIOD);
        }
    }

    public void onEnterForeground() {
        if (this.an && this.f != null) {
            this.f.execute((Runnable) new w(this), true);
        }
    }

    public void onExitForeground() {
        if (this.an && this.f != null) {
            this.f.execute((Runnable) new ab(this), true);
        }
    }

    public void onUserInteraction() {
        if (this.an && this.f != null) {
            this.f.execute((Runnable) new ac(this), true);
        }
    }

    public synchronized void onUxActive() {
        if (this.an && this.f != null) {
            this.f.execute((Runnable) new a(this), true);
        }
    }

    public synchronized void onUxInactive() {
        if (this.an && this.f != null) {
            this.f.execute((Runnable) new l(this), true);
        }
    }

    protected boolean p() {
        boolean z = false;
        if (this.an) {
            long jUnixTime = Date.unixTime();
            if (jUnixTime - this.S > Constants.SESSION_INACTIVE_PERIOD) {
                this.J = this.I;
                this.I = jUnixTime;
                this.P++;
                z = true;
            }
            this.S = jUnixTime;
        }
        return z;
    }

    protected void q() {
        if (this.an) {
            long jUnixTime = Date.unixTime();
            if (jUnixTime - this.U >= Constants.USER_SESSION_INACTIVE_PERIOD) {
                this.R++;
            }
            this.U = jUnixTime;
        }
    }

    protected void r() {
        if (this.an) {
            long jUnixTime = Date.unixTime();
            if (jUnixTime - this.T >= Constants.USER_SESSION_INACTIVE_PERIOD) {
                this.Q++;
            }
            this.T = jUnixTime;
        }
    }

    public synchronized void reset() {
        this.aj = TransmissionMode.DEFAULT;
        this.ak = TransmissionMode.DEFAULT;
        this.ai = false;
        this.al = Constants.LABELS_ORDER;
        this.y = ApplicationState.INACTIVE;
        this.L = SessionState.INACTIVE;
        this.n = false;
        this.o.set(0);
        this.p = -1L;
        this.q.set(0);
        this.s = -1L;
        this.r = -1L;
        this.t = null;
        this.u = null;
        this.z.set(0);
        this.A.set(0);
        this.C = 0L;
        this.D = 0L;
        this.E = 0L;
        this.F = 0L;
        this.G = 0L;
        this.H = 0L;
        this.M = 0L;
        this.O = 0L;
        this.N = 0L;
        this.I = -1L;
        this.J = 0L;
        this.R = -1;
        this.Q = -1;
        this.V = 0;
        this.W = -1L;
        this.K = -1L;
        this.X = -1L;
        this.S = -1L;
        this.T = -1L;
        this.U = -1L;
        this.r = -1L;
        this.s = -1L;
        disableAutoUpdate();
        if (this.j != null) {
            this.f.removeEnqueuedTask(this.j);
            this.j = null;
        }
        if (this.Y != null) {
            this.f.removeEnqueuedTask(this.Y);
            this.Y = null;
        }
        if (this.c != null) {
            this.c.cancel();
        }
        if (this.d != null) {
            this.d.stop();
        }
        if (this.f != null) {
            this.f.removeAllEnqueuedTasks();
        }
        if (this.b != null) {
            this.b.close();
        }
    }

    protected void s() {
        a(true);
    }

    public void setAppContext(Context context) {
        if (this.ab != null || context == null) {
            return;
        }
        this.ab = context;
        this.f = d();
        this.f.execute((Runnable) new ae(this), true);
    }

    public void setAppName(String str, boolean z) {
        if (this.an) {
            if (!z) {
                c(str);
            } else if (this.f != null) {
                this.f.execute(new d(this, str), z);
            }
        }
    }

    public void setAutoStartEnabled(boolean z, boolean z2) {
        if (this.an) {
            this.f.execute(new m(this, z), z2);
        }
    }

    public synchronized void setAutoStartLabel(String str, String str2) {
        if (this.an) {
            this.ad.put(str, str2);
        }
    }

    public synchronized void setAutoStartLabels(HashMap<String, String> map) {
        if (this.an && map != null) {
            this.ad.putAll(Utils.mapOfStrings(map));
        }
    }

    public void setCacheFlushingInterval(long j, boolean z) {
        if (!this.an || this.f == null || this.af == j) {
            return;
        }
        this.f.execute(new s(this, j), z);
    }

    public void setCacheMaxBatchFiles(int i, boolean z) {
        if (!this.an || this.f == null || this.a == null) {
            return;
        }
        this.f.execute(new o(this, i), z);
    }

    public void setCacheMaxFlushesInARow(int i, boolean z) {
        if (!this.an || this.f == null || this.a == null) {
            return;
        }
        this.f.execute(new p(this, i), z);
    }

    public void setCacheMaxMeasurements(int i, boolean z) {
        if (!this.an || this.f == null || this.a == null) {
            return;
        }
        this.f.execute(new n(this, i), z);
    }

    public void setCacheMeasurementExpiry(int i, boolean z) {
        if (!this.an || this.f == null || this.a == null) {
            return;
        }
        this.f.execute(new r(this, i), z);
    }

    public void setCacheMinutesToRetry(int i, boolean z) {
        if (!this.an || this.f == null || this.a == null) {
            return;
        }
        this.f.execute(new q(this, i), z);
    }

    public void setCurrentActivityName(String str) {
        this.w = str;
    }

    public void setCustomerC2(String str, boolean z) {
        if (!this.an || str == null || str.length() == 0) {
            return;
        }
        if (!z) {
            d(str);
        } else if (this.f != null) {
            this.f.execute(new g(this, str), z);
        }
    }

    public void setDebug(boolean z) {
        if (this.an) {
            this.f.execute((Runnable) new k(this, z), true);
        }
    }

    public synchronized void setEnabled(boolean z) {
        this.f.execute((Runnable) new z(this, z), true);
    }

    public void setErrorHandlingEnabled(boolean z) {
        if (this.an) {
            this.ag = z;
            if (z) {
                Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
            } else if (Thread.getDefaultUncaughtExceptionHandler() != this.ah) {
                Thread.setDefaultUncaughtExceptionHandler(this.ah);
            }
        }
    }

    public void setKeepAliveEnabled(boolean z, boolean z2) {
        if (this.an) {
            if (!z2) {
                c(z);
            } else if (this.f != null) {
                this.f.execute(new b(this, z), z2);
            }
        }
    }

    public void setLabel(String str, String str2, boolean z) {
        if (this.an) {
            if (!z) {
                b(str, str2);
            } else if (this.f != null) {
                this.f.execute(new f(this, str, str2), z);
            }
        }
    }

    public synchronized void setLabels(HashMap<String, String> map, boolean z) {
        if (this.an && map != null && this.f != null) {
            this.f.execute(new e(this, map), z);
        }
    }

    public void setMeasurementLabelOrder(String[] strArr, boolean z) {
        if (!this.an || this.f == null || strArr == this.al || strArr == null || strArr.length <= 0) {
            return;
        }
        this.f.execute(new t(this, strArr), z);
    }

    public synchronized void setOfflineURL(String str) {
        if (this.an && str != null && str.length() != 0 && this.f != null) {
            this.f.execute((Runnable) new ag(this, str), true);
        }
    }

    public synchronized void setPixelURL(String str, boolean z) {
        if (this.an && str != null && str.length() != 0) {
            if (!z) {
                a(str);
            } else if (this.f != null) {
                this.f.execute(new af(this, str), z);
            }
        }
    }

    public void setPublisherSecret(String str, boolean z) {
        if (!this.an || str == null || str.length() == 0 || this.f == null) {
            return;
        }
        this.f.execute(new c(this, str), z);
    }

    public void setSecure(boolean z, boolean z2) {
        if (this.an) {
            if (!z2) {
                this.ai = z;
            } else if (this.f != null) {
                this.f.execute(new j(this, z), z2);
            }
        }
    }

    protected void t() {
        b(true);
    }

    protected OfflineMeasurementsCache u() {
        return this.a;
    }

    public synchronized void update() {
        update(true);
    }

    public synchronized void update(boolean z) {
        if (this.an) {
            if (this.f.containsTask(this.j)) {
                this.f.removeEnqueuedTask(this.j);
                this.j.run();
                this.j = null;
            }
            a(z);
            b(z);
        }
    }

    protected synchronized void v() {
        if (this.an) {
            w();
            if (this.k >= 60000) {
                this.i = new x(this);
                this.f.execute(this.i, this.k, true, this.k);
            }
        }
    }

    protected synchronized void w() {
        if (this.an && this.i != null) {
            this.f.removeEnqueuedTask(this.i);
            this.i = null;
        }
    }

    protected void x() {
    }

    protected void y() {
    }

    boolean z() {
        return this.ab == null || this.am.isPublisherSecretEmpty() || this.Z == null || this.Z.length() == 0;
    }
}
