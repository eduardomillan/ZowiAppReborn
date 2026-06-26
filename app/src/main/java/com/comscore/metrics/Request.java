package com.comscore.metrics;

import android.content.Context;
import android.os.Build;
import androidx.core.os.EnvironmentCompat;
import com.comscore.analytics.Core;
import com.comscore.measurement.Measurement;
import com.comscore.utils.CSLog;
import com.comscore.utils.Connectivity;
import com.comscore.utils.Constants;
import com.comscore.utils.Date;
import com.comscore.utils.Permissions;
import com.comscore.utils.Storage;
import com.comscore.utils.TransmissionMode;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
public class Request {
    public static final boolean REDIRECTION_SUPPORTED;
    protected URL a = process();
    protected Proxy b;
    private Measurement c;
    private Core d;
    private Storage e;

    static {
        int i = Build.VERSION.SDK_INT;
        REDIRECTION_SUPPORTED = i < 11 || i > 13;
    }

    public Request(Core core, Measurement measurement) {
        this.d = core;
        this.e = core.getStorage();
        this.c = measurement;
    }

    private static Proxy a(String str) {
        int i;
        int iIndexOf = str.indexOf(58);
        if (iIndexOf != -1) {
            String strSubstring = str.substring(0, iIndexOf);
            i = Integer.parseInt(str.substring(iIndexOf + 1));
            str = strSubstring;
        } else {
            i = 80;
        }
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
    }

    private boolean c() throws Throwable {
        d();
        boolean zA = a();
        if (!zA) {
            e();
        }
        return zA;
    }

    private void d() {
        TransmissionMode offlineTransmissionMode = this.d.getOfflineTransmissionMode();
        if (offlineTransmissionMode == TransmissionMode.DEFAULT || ((offlineTransmissionMode == TransmissionMode.WIFIONLY && Connectivity.isConnectedWiFi(this.d.getAppContext())) || offlineTransmissionMode == TransmissionMode.PIGGYBACK)) {
            this.d.getOfflineCache().flush();
        }
    }

    private void e() {
        CSLog.e(this, "Measurement was not transmitted: " + this.c.retrieveLabelsAsString(this.d.getMeasurementLabelOrder()));
        this.d.getOfflineCache().saveEvent(this.c);
    }

    protected HttpURLConnection a(URL url) {
        HttpURLConnection httpURLConnection = this.b != null ? (HttpURLConnection) url.openConnection(this.b) : (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Connection", "Close");
        return httpURLConnection;
    }

    protected URL a(URL url, int i, String str) {
        switch (i) {
            case 300:
            case 301:
            case 302:
            case 303:
            case 305:
                if (str != null) {
                    if (i != 305) {
                        URL url2 = new URL(url, str);
                        if (!url.getProtocol().equals(url2.getProtocol())) {
                        }
                    } else {
                        int length = str.startsWith(new StringBuilder().append(url.getProtocol()).append(':').toString()) ? url.getProtocol().length() + 1 : 0;
                        if (str.startsWith("//", length)) {
                            length += 2;
                        }
                        this.b = a(str.substring(length));
                    }
                }
                break;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean a() throws java.lang.Throwable {
        /*
            r7 = this;
            r2 = 0
            r1 = 0
            java.net.URL r0 = r7.a     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> La8
            boolean r3 = r7.b()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> La8
            if (r3 == 0) goto L5d
            java.net.HttpURLConnection r1 = r7.a(r0)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> La8
            int r0 = r1.getResponseCode()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
        L12:
            java.lang.String r3 = "Content-Type"
            java.lang.String r3 = r1.getHeaderField(r3)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            java.lang.String r4 = "Content-Length"
            java.lang.String r4 = r1.getHeaderField(r4)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            r5 = 200(0xc8, float:2.8E-43)
            if (r0 != r5) goto L3c
            boolean r5 = com.comscore.utils.Utils.isNotEmpty(r3)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            if (r5 == 0) goto L3c
            java.lang.String r5 = "image/"
            int r3 = r3.indexOf(r5)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            if (r3 != 0) goto L3c
            boolean r3 = com.comscore.utils.Utils.isNotEmpty(r4)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            if (r3 == 0) goto L3c
            int r3 = java.lang.Integer.parseInt(r4)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            if (r3 > 0) goto L4c
        L3c:
            r3 = 204(0xcc, float:2.86E-43)
            if (r0 != r3) goto L56
            boolean r0 = com.comscore.utils.Utils.isNotEmpty(r4)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            if (r0 == 0) goto L56
            int r0 = java.lang.Integer.parseInt(r4)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            if (r0 != 0) goto L56
        L4c:
            r2 = 1
            com.comscore.analytics.Core r0 = r7.d     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            com.comscore.applications.KeepAlive r0 = r0.getKeepAlive()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            r0.reset()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
        L56:
            r0 = r2
            if (r1 == 0) goto L5c
            r1.disconnect()
        L5c:
            return r0
        L5d:
            r3 = r2
            r5 = r0
            r0 = r2
        L60:
            if (r5 == 0) goto L12
            r4 = 5
            if (r3 >= r4) goto L12
            java.net.HttpURLConnection r1 = r7.a(r5)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            r0 = 0
            r1.setInstanceFollowRedirects(r0)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            int r4 = r1.getResponseCode()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            java.lang.String r0 = "Location"
            java.lang.String r0 = r1.getHeaderField(r0)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            java.net.URL r5 = r7.a(r5, r4, r0)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lb2
            int r0 = r3 + 1
            r3 = r0
            r0 = r4
            goto L60
        L80:
            r0 = move-exception
            r6 = r0
            r0 = r2
            r2 = r1
            r1 = r6
        L85:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laf
            r3.<init>()     // Catch: java.lang.Throwable -> Laf
            java.lang.String r4 = "Exception sending measurement:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r4 = r1.getLocalizedMessage()     // Catch: java.lang.Throwable -> Laf
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Laf
            com.comscore.utils.CSLog.e(r7, r3)     // Catch: java.lang.Throwable -> Laf
            com.comscore.utils.CSLog.printStackTrace(r1)     // Catch: java.lang.Throwable -> Laf
            if (r2 == 0) goto L5c
            r2.disconnect()
            goto L5c
        La8:
            r0 = move-exception
        La9:
            if (r1 == 0) goto Lae
            r1.disconnect()
        Lae:
            throw r0
        Laf:
            r0 = move-exception
            r1 = r2
            goto La9
        Lb2:
            r0 = move-exception
            r6 = r0
            r0 = r2
            r2 = r1
            r1 = r6
            goto L85
        */
        throw new UnsupportedOperationException("Method not decompiled: com.comscore.metrics.Request.a():boolean");
    }

    public Boolean availableConnection() {
        String str;
        try {
            Context appContext = this.d.getAppContext();
            boolean z = true;
            if (Connectivity.isEmulator()) {
                str = "emu";
            } else if (Connectivity.isConnectedWiFi(appContext)) {
                str = "wifi";
            } else if (Connectivity.isConnectedMobile(appContext)) {
                str = "wwan";
            } else if (Connectivity.isConnectBluetooth(appContext)) {
                str = "bth";
            } else if (Connectivity.isConnectEthernet(appContext)) {
                str = "eth";
            } else {
                str = EnvironmentCompat.MEDIA_UNKNOWN;
                z = false;
            }
            this.c.setLabel(Constants.NETWORK_TYPE_LABEL_NAME, str);
            return Boolean.valueOf(z);
        } catch (NullPointerException e) {
            return false;
        }
    }

    protected boolean b() {
        return REDIRECTION_SUPPORTED;
    }

    public URL process() {
        return process(this.c.getPixelURL());
    }

    public URL process(String str) {
        String str2;
        String strReplace;
        availableConnection();
        String strConcat = str.concat(this.c.retrieveLabelsAsString(this.d.getMeasurementLabelOrder()));
        if (strConcat.length() <= 4096 || strConcat.indexOf("&") <= 0) {
            str2 = strConcat;
        } else {
            int iLastIndexOf = strConcat.substring(0, 4088).lastIndexOf("&");
            try {
                strReplace = URLEncoder.encode(strConcat.substring(iLastIndexOf + 1), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                strReplace = "0";
            }
            str2 = strConcat.substring(0, iLastIndexOf) + "&ns_cut=" + strReplace;
        }
        try {
            return new URL(str2.length() > 4096 ? str2.substring(0, 4096) : str2);
        } catch (MalformedURLException e2) {
            return null;
        }
    }

    public boolean send() {
        Context appContext = this.d.getAppContext();
        boolean zBooleanValue = Permissions.check(appContext, "android.permission.ACCESS_NETWORK_STATE").booleanValue();
        TransmissionMode liveTransmissionMode = this.d.getLiveTransmissionMode();
        this.e.set(Constants.LAST_MEASUREMENT_PROCESSED_TIMESTAMP_KEY, String.valueOf(Date.unixTime()));
        switch (a.a[liveTransmissionMode.ordinal()]) {
            case 1:
                e();
                break;
            case 2:
                e();
                break;
            case 3:
                if (zBooleanValue && !availableConnection().booleanValue()) {
                    e();
                }
                break;
            case 4:
                if (zBooleanValue && !Connectivity.isEmulator() && !Connectivity.isConnectedWiFi(appContext) && !Connectivity.isConnectEthernet(appContext)) {
                    e();
                }
                break;
            case 5:
                if (zBooleanValue && !Connectivity.isEmulator() && !Connectivity.isConnectedWiFi(appContext) && !Connectivity.isDataConnected(appContext)) {
                    e();
                }
                break;
        }
        return false;
    }
}
