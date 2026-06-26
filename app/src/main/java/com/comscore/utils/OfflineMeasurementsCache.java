package com.comscore.utils;

import android.content.Context;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.comscore.analytics.Core;
import com.comscore.applications.ApplicationMeasurement;
import com.comscore.applications.EventType;
import com.comscore.measurement.Measurement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/* JADX INFO: loaded from: classes.dex */
public class OfflineMeasurementsCache {
    protected final Core a;
    private int b;
    private int c;
    private int d;
    private long e;
    private long f;
    private String g;
    private final String h;
    private ArrayList<String> i;
    private String j;
    private int k;
    private long l;
    private long m;

    public OfflineMeasurementsCache(Core core) {
        this(core, Constants.CACHE_FILENAME);
    }

    protected OfflineMeasurementsCache(Core core, String str) {
        this.g = null;
        this.i = null;
        this.j = null;
        this.k = 0;
        this.l = 0L;
        this.m = 0L;
        this.a = core;
        this.h = str;
        setCacheMaxMeasurements(2000);
        setCacheMaxBatchFiles(100);
        setCacheMaxPosts(10);
        setCacheWaitMinutes(30);
        setCacheMeasurementExpiry(31);
        e();
    }

    private void a(int i) {
        Storage storage = this.a.getStorage();
        if (storage.has(Constants.CACHE_DROPPED_MEASUREMENTS).booleanValue()) {
            i += Integer.valueOf(storage.get(Constants.CACHE_DROPPED_MEASUREMENTS)).intValue();
        }
        storage.set(Constants.CACHE_DROPPED_MEASUREMENTS, String.valueOf(i));
    }

    private void a(String str) {
        Storage storage = this.a.getStorage();
        if (storage.has(str).booleanValue()) {
            a(Integer.valueOf(storage.get(str)).intValue());
        }
    }

    private void a(String str, boolean z) {
        if (str != null) {
            if (z) {
                a(str);
            }
            FileUtils.deleteFile(this.a, str);
            this.i.remove(str);
        }
    }

    private boolean a(long j) {
        return ((((this.f * 24) * 60) * 60) * 1000) - (Date.unixTime() - j) <= 0;
    }

    private boolean a(String str, String str2) {
        HttpClient httpClient = Connectivity.getHttpClient();
        HttpPost httpPost = new HttpPost(str2);
        try {
            StringEntity stringEntity = new StringEntity(str, "UTF-8");
            stringEntity.setContentType("text/xml");
            httpPost.setHeader("User-Agent", System.getProperty("http.agent"));
            httpPost.setEntity(stringEntity);
            CSLog.d(this, "Sending POST request");
            HttpResponse httpResponseExecute = httpClient.execute(httpPost);
            int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
            CSLog.d(this, "Response:" + statusCode);
            CSLog.d(this, "Cache flushed");
            String string = EntityUtils.toString(httpResponseExecute.getEntity());
            if (statusCode == 200 && Utils.isNotEmpty(string)) {
                if (string.startsWith(Constants.RESPONSE_MASK)) {
                    return true;
                }
            }
        } catch (SSLException e) {
            CSLog.e(this, e.getMessage());
            this.a.allowOfflineTransmission(TransmissionMode.DISABLED, true);
            clear();
        } catch (Exception e2) {
            CSLog.e(this, "Exception in flush:" + e2.getLocalizedMessage());
            CSLog.printStackTrace(e2);
        }
        return false;
    }

    private static String[] a(String[] strArr, int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException();
        }
        int length = strArr.length;
        if (i < 0 || i > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = i2 - i;
        int iMin = Math.min(i3, length - i);
        String[] strArr2 = new String[i3];
        System.arraycopy(strArr, i, strArr2, 0, iMin);
        return strArr2;
    }

    private void b(String str) {
        CSLog.d(this, "Creating new cache batch file");
        String str2 = this.h + XMLBuilder.getLabelFromEvent(str, "ns_ts");
        FileUtils.writeEvent(this.a, str2, 0, str);
        if (this.i == null) {
            this.i = new ArrayList<>();
        }
        this.i.add(str2);
        c();
    }

    private boolean b() {
        if (d().booleanValue() && !isEmpty() && this.a.getCustomerC2() != null) {
            if (this.k < this.d) {
                return true;
            }
            long jUnixTime = ((this.e * 1000) * 60) - (Date.unixTime() - this.m);
            if (jUnixTime <= 0) {
                this.k = 0;
                this.m = 0L;
                return true;
            }
            CSLog.d(this, "Max flushes in a row (" + this.d + ") reached. Waiting " + ((jUnixTime / 1000.0d) / 60.0d) + " minutes");
        }
        return false;
    }

    private int c(String str) {
        Storage storage = this.a.getStorage();
        if (str != null) {
            return storage.has(str).booleanValue() ? Integer.valueOf(storage.get(str)).intValue() : FileUtils.readCachedEvents(this.a.getAppContext(), str).length;
        }
        return 0;
    }

    private void c() {
        this.l = 0L;
        if (this.j != null) {
            this.j = null;
        }
    }

    private Boolean d() {
        Context appContext = this.a.getAppContext();
        if (!Connectivity.isEmulator() && Permissions.check(appContext, "android.permission.ACCESS_NETWORK_STATE").booleanValue() && !Connectivity.isConnectedWiFi(appContext) && !Connectivity.isConnectedMobile(appContext)) {
            return false;
        }
        return true;
    }

    private String[] d(String str) {
        String[] cachedEvents = FileUtils.readCachedEvents(this.a.getAppContext(), str);
        boolean z = false;
        int i = 0;
        while (i < cachedEvents.length) {
            try {
                long j = Long.parseLong(XMLBuilder.getLabelFromEvent(cachedEvents[i], "ns_ts"));
                z = !a(j);
                if (z) {
                    CSLog.d(this, "Valid timestamp found: " + j);
                    break;
                }
                continue;
            } catch (NumberFormatException e) {
            }
            i++;
        }
        if (z) {
            a(i);
            return a(cachedEvents, i, cachedEvents.length);
        }
        CSLog.d(this, "All events in the file " + str + " are expired");
        a(str, true);
        return null;
    }

    private long e(String str) {
        return Long.valueOf(str.substring(this.h.length())).longValue();
    }

    private void e() {
        List<String> listF = f();
        for (int size = listF.size() - 1; size >= 0; size--) {
            if (a(e(listF.get(size)))) {
                CSLog.d(this, "Deleting expired cache file " + listF.get(size));
                a(listF.get(size), true);
            }
        }
    }

    private List<String> f() {
        if (this.i == null) {
            this.i = FileUtils.getFileList(this.a.getAppContext());
        }
        return this.i;
    }

    private String g() {
        if (this.i == null || this.i.size() <= 0) {
            return null;
        }
        return this.i.get(0);
    }

    private String h() {
        if (this.i == null || this.i.size() <= 0) {
            return null;
        }
        return this.i.get(this.i.size() - 1);
    }

    protected String a() {
        StringBuilder sb;
        boolean z;
        if (this.g != null) {
            sb = new StringBuilder(this.g);
        } else {
            sb = new StringBuilder(this.a.isSecure() ? Constants.OFFLINE_RECEIVER_URL_SECURE : Constants.OFFLINE_RECEIVER_URL);
        }
        if (sb.indexOf("?") == -1) {
            sb.append("?");
            z = false;
        } else {
            z = true;
        }
        String customerC2 = this.a.getCustomerC2();
        if (customerC2 != null && !customerC2.equals("")) {
            if (z) {
                sb.append("&");
            }
            sb.append("c2=");
            sb.append(customerC2);
            z = true;
        }
        String strMd5 = Utils.md5(String.format("JetportGotAMaskOfThe%sS.D_K-", this.a.getPublisherSecret()));
        if (strMd5 != null && !strMd5.equals("")) {
            if (z) {
                sb.append("&");
            }
            sb.append("s=");
            sb.append(strMd5);
        }
        return sb.toString().toLowerCase(new Locale("en", "US"));
    }

    public void clear() {
        if (this.a.isEnabled()) {
            List<String> listF = f();
            for (int size = listF.size(); size > 0; size--) {
                a(listF.get(size - 1), true);
            }
        }
    }

    public synchronized boolean flush() {
        boolean zA = false;
        synchronized (this) {
            if (this.a.isEnabled()) {
                Storage storage = this.a.getStorage();
                e();
                long jUnixTime = ((this.e * 1000) * 60) - (Date.unixTime() - this.l);
                if (jUnixTime <= 0) {
                    this.l = 0L;
                    while (true) {
                        if (!b()) {
                            break;
                        }
                        String strH = null;
                        CSLog.d(this, "Cache is not empty, contains " + this.i.size() + " files");
                        if (this.j == null) {
                            strH = h();
                            CSLog.d(this, "reading events from the file " + strH);
                            String[] strArrD = d(strH);
                            if (strArrD != null && strArrD.length > 0) {
                                this.j = XMLBuilder.generateXMLRequestString(strArrD, storage.has(Constants.CACHE_DROPPED_MEASUREMENTS).booleanValue() ? storage.get(Constants.CACHE_DROPPED_MEASUREMENTS) : "0");
                            }
                        }
                        if (this.j != null && this.j.length() > 0) {
                            zA = a(this.j, a());
                            if (!zA) {
                                this.l = Date.unixTime();
                                break;
                            }
                            this.k++;
                            a(strH, false);
                            c();
                            this.m = Date.unixTime();
                            storage.remove(Constants.CACHE_DROPPED_MEASUREMENTS);
                            this.a.getStorage().set(Constants.LAST_MEASUREMENT_PROCESSED_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
                        }
                    }
                } else {
                    CSLog.d(this, "Waiting " + ((jUnixTime / 1000.0d) / 60.0d) + " minutes");
                }
            }
        }
        return zA;
    }

    public int getCacheMaxBatchFiles() {
        return this.c;
    }

    public int getCacheMaxMeasurements() {
        return this.b;
    }

    public int getCacheMaxPosts() {
        return this.d;
    }

    public long getCacheMeasurementExpiry() {
        return this.f;
    }

    public long getCacheWaitMinutes() {
        return this.e;
    }

    public int getEventCount() {
        int iC = c(h());
        return f().size() > 0 ? iC + ((r1.size() - 1) * getCacheMaxBatchFiles()) : iC;
    }

    public boolean isEmpty() {
        return getEventCount() == 0;
    }

    public void saveApplicationMeasurement(EventType eventType, HashMap<String, String> map) {
        saveApplicationMeasurement(eventType, map, false);
    }

    public void saveApplicationMeasurement(EventType eventType, HashMap<String, String> map, boolean z) {
        if (this.a.isEnabled()) {
            ApplicationMeasurement applicationMeasurementNewApplicationMeasurement = ApplicationMeasurement.newApplicationMeasurement(this.a, eventType, map, null);
            this.a.getMeasurementDispatcher().addAggregateData(applicationMeasurementNewApplicationMeasurement);
            this.a.getMeasurementDispatcher().addEventCounter(applicationMeasurementNewApplicationMeasurement);
            saveEvent(applicationMeasurementNewApplicationMeasurement, z);
        }
    }

    public void saveEvent(Measurement measurement) {
        saveEvent(measurement, false);
    }

    public void saveEvent(Measurement measurement, boolean z) {
        if (this.a.isEnabled()) {
            if (z) {
                this.a.getTaskExecutor().execute((Runnable) new c(this, measurement), true);
            } else {
                saveEvent(measurement.retrieveLabelsAsString(this.a.getMeasurementLabelOrder()));
            }
        }
    }

    public synchronized void saveEvent(String str) {
        if (this.a.isEnabled() && this.a.getOfflineTransmissionMode() != TransmissionMode.DISABLED && this.a.getCustomerC2() != null && Utils.isNotEmpty(XMLBuilder.getLabelFromEvent(str, "ns_ts"))) {
            String strH = h();
            if (strH == null) {
                b(str);
            } else if (c(strH) < getCacheMaxBatchFiles()) {
                FileUtils.writeEvent(this.a, strH, 32768, Droid2InoConstants.NEW_LINE_CHARACTER + str);
                c();
            } else {
                CSLog.d(this, "The newest cache batch file is full");
                if (f().size() >= getCacheMaxMeasurements() / getCacheMaxBatchFiles()) {
                    CSLog.d(this, "reached the cache max (" + getCacheMaxMeasurements() + ") size");
                    a(g(), true);
                }
                b(str);
            }
        }
    }

    public void setCacheMaxBatchFiles(int i) {
        if (this.a.isEnabled() && i > 0) {
            this.c = i;
        }
    }

    public void setCacheMaxMeasurements(int i) {
        if (this.a.isEnabled()) {
            this.b = i;
        }
    }

    public void setCacheMaxPosts(int i) {
        this.d = i;
    }

    public void setCacheMeasurementExpiry(int i) {
        if (this.a.isEnabled()) {
            this.f = i;
        }
    }

    public void setCacheWaitMinutes(int i) {
        if (this.a.isEnabled()) {
            this.e = i;
        }
    }

    public void setUrl(String str) {
        if (this.a.isEnabled()) {
            this.g = str;
        }
    }
}
