package com.google.android.gms.analytics.internal;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
class zzah extends zzd {
    private static final byte[] zzPy = Droid2InoConstants.NEW_LINE_CHARACTER.getBytes();
    private final String zzIa;
    private final zzaj zzPx;

    private class zza {
        private ByteArrayOutputStream zzPA = new ByteArrayOutputStream();
        private int zzPz;

        public zza() {
        }

        public byte[] getPayload() {
            return this.zzPA.toByteArray();
        }

        public boolean zzj(zzab zzabVar) {
            com.google.android.gms.common.internal.zzx.zzw(zzabVar);
            if (this.zzPz + 1 > zzah.this.zziv().zzjM()) {
                return false;
            }
            String strZza = zzah.this.zza(zzabVar, false);
            if (strZza == null) {
                zzah.this.zziu().zza(zzabVar, "Error formatting hit");
                return true;
            }
            byte[] bytes = strZza.getBytes();
            int length = bytes.length;
            if (length > zzah.this.zziv().zzjE()) {
                zzah.this.zziu().zza(zzabVar, "Hit size exceeds the maximum size limit");
                return true;
            }
            if (this.zzPA.size() > 0) {
                length++;
            }
            if (length + this.zzPA.size() > zzah.this.zziv().zzjG()) {
                return false;
            }
            try {
                if (this.zzPA.size() > 0) {
                    this.zzPA.write(zzah.zzPy);
                }
                this.zzPA.write(bytes);
                this.zzPz++;
                return true;
            } catch (IOException e) {
                zzah.this.zze("Failed to write payload when batching hits", e);
                return true;
            }
        }

        public int zzkN() {
            return this.zzPz;
        }
    }

    zzah(zzf zzfVar) {
        super(zzfVar);
        this.zzIa = zza("GoogleAnalytics", zze.VERSION, Build.VERSION.RELEASE, zzam.zza(Locale.getDefault()), Build.MODEL, Build.ID);
        this.zzPx = new zzaj(zzfVar.zzit());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int zza(java.net.URL r6, byte[] r7) throws java.lang.Throwable {
        /*
            r5 = this;
            r1 = 0
            com.google.android.gms.common.internal.zzx.zzw(r6)
            com.google.android.gms.common.internal.zzx.zzw(r7)
            java.lang.String r0 = "POST bytes, url"
            int r2 = r7.length
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r5.zzb(r0, r2, r6)
            boolean r0 = r5.zziD()
            if (r0 == 0) goto L21
            java.lang.String r0 = "Post payload\n"
            java.lang.String r2 = new java.lang.String
            r2.<init>(r7)
            r5.zza(r0, r2)
        L21:
            java.net.HttpURLConnection r2 = r5.zzc(r6)     // Catch: java.io.IOException -> L64 java.lang.Throwable -> L7e
            r0 = 1
            r2.setDoOutput(r0)     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            int r0 = r7.length     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r2.setFixedLengthStreamingMode(r0)     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r2.connect()     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            java.io.OutputStream r1 = r2.getOutputStream()     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r1.write(r7)     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r5.zzb(r2)     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            int r0 = r2.getResponseCode()     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r3 = 200(0xc8, float:2.8E-43)
            if (r0 != r3) goto L49
            com.google.android.gms.analytics.internal.zzb r3 = r5.zzhP()     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r3.zzip()     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
        L49:
            java.lang.String r3 = "POST status"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            r5.zzb(r3, r4)     // Catch: java.lang.Throwable -> L92 java.io.IOException -> L94
            if (r1 == 0) goto L57
            r1.close()     // Catch: java.io.IOException -> L5d
        L57:
            if (r2 == 0) goto L5c
            r2.disconnect()
        L5c:
            return r0
        L5d:
            r1 = move-exception
            java.lang.String r3 = "Error closing http post connection output stream"
            r5.zze(r3, r1)
            goto L57
        L64:
            r0 = move-exception
            r2 = r1
        L66:
            java.lang.String r3 = "Network POST connection error"
            r5.zzd(r3, r0)     // Catch: java.lang.Throwable -> L92
            r0 = 0
            if (r1 == 0) goto L71
            r1.close()     // Catch: java.io.IOException -> L77
        L71:
            if (r2 == 0) goto L5c
            r2.disconnect()
            goto L5c
        L77:
            r1 = move-exception
            java.lang.String r3 = "Error closing http post connection output stream"
            r5.zze(r3, r1)
            goto L71
        L7e:
            r0 = move-exception
            r2 = r1
        L80:
            if (r1 == 0) goto L85
            r1.close()     // Catch: java.io.IOException -> L8b
        L85:
            if (r2 == 0) goto L8a
            r2.disconnect()
        L8a:
            throw r0
        L8b:
            r1 = move-exception
            java.lang.String r3 = "Error closing http post connection output stream"
            r5.zze(r3, r1)
            goto L85
        L92:
            r0 = move-exception
            goto L80
        L94:
            r0 = move-exception
            goto L66
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzah.zza(java.net.URL, byte[]):int");
    }

    private static String zza(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", str, str2, str3, str4, str5, str6);
    }

    private void zza(StringBuilder sb, String str, String str2) throws UnsupportedEncodingException {
        if (sb.length() != 0) {
            sb.append('&');
        }
        sb.append(URLEncoder.encode(str, "UTF-8"));
        sb.append('=');
        sb.append(URLEncoder.encode(str2, "UTF-8"));
    }

    private int zzb(URL url) {
        int responseCode;
        com.google.android.gms.common.internal.zzx.zzw(url);
        zzb("GET request", url);
        HttpURLConnection httpURLConnectionZzc = null;
        try {
            try {
                httpURLConnectionZzc = zzc(url);
                httpURLConnectionZzc.connect();
                zzb(httpURLConnectionZzc);
                responseCode = httpURLConnectionZzc.getResponseCode();
                if (responseCode == 200) {
                    zzhP().zzip();
                }
                zzb("GET status", Integer.valueOf(responseCode));
            } catch (IOException e) {
                zzd("Network GET connection error", e);
                responseCode = 0;
                if (httpURLConnectionZzc != null) {
                    httpURLConnectionZzc.disconnect();
                }
            }
            return responseCode;
        } finally {
            if (httpURLConnectionZzc != null) {
                httpURLConnectionZzc.disconnect();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int zzb(java.net.URL r9, byte[] r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzah.zzb(java.net.URL, byte[]):int");
    }

    private URL zzb(zzab zzabVar, String str) {
        try {
            return new URL(zzabVar.zzkC() ? zziv().zzjO() + zziv().zzjQ() + "?" + str : zziv().zzjP() + zziv().zzjQ() + "?" + str);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private void zzb(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
            do {
            } while (inputStream.read(new byte[1024]) > 0);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    zze("Error closing http connection input stream", e);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    zze("Error closing http connection input stream", e2);
                }
            }
            throw th;
        }
    }

    private boolean zzg(zzab zzabVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        String strZza = zza(zzabVar, !zzabVar.zzkC());
        if (strZza == null) {
            zziu().zza(zzabVar, "Error formatting hit for upload");
            return true;
        }
        if (strZza.length() <= zziv().zzjD()) {
            URL urlZzb = zzb(zzabVar, strZza);
            if (urlZzb != null) {
                return zzb(urlZzb) == 200;
            }
            zzbe("Failed to build collect GET endpoint url");
            return false;
        }
        String strZza2 = zza(zzabVar, false);
        if (strZza2 == null) {
            zziu().zza(zzabVar, "Error formatting hit for POST upload");
            return true;
        }
        byte[] bytes = strZza2.getBytes();
        if (bytes.length > zziv().zzjF()) {
            zziu().zza(zzabVar, "Hit payload exceeds size limit");
            return true;
        }
        URL urlZzh = zzh(zzabVar);
        if (urlZzh != null) {
            return zza(urlZzh, bytes) == 200;
        }
        zzbe("Failed to build collect POST endpoint url");
        return false;
    }

    private static byte[] zzg(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private URL zzh(zzab zzabVar) {
        try {
            return new URL(zzabVar.zzkC() ? zziv().zzjO() + zziv().zzjQ() : zziv().zzjP() + zziv().zzjQ());
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private String zzi(zzab zzabVar) {
        return String.valueOf(zzabVar.zzkz());
    }

    private URL zzkL() {
        try {
            return new URL(zziv().zzjO() + zziv().zzjR());
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    String zza(zzab zzabVar, boolean z) {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : zzabVar.zzn().entrySet()) {
                String key = entry.getKey();
                if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key) && !"z".equals(key) && !"_gmsv".equals(key)) {
                    zza(sb, key, entry.getValue());
                }
            }
            zza(sb, "ht", String.valueOf(zzabVar.zzkA()));
            zza(sb, "qt", String.valueOf(zzit().currentTimeMillis() - zzabVar.zzkA()));
            if (zziv().zzjA()) {
                zza(sb, "_gmsv", zze.VERSION);
            }
            if (z) {
                long jZzkD = zzabVar.zzkD();
                zza(sb, "z", jZzkD != 0 ? String.valueOf(jZzkD) : zzi(zzabVar));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    List<Long> zza(List<zzab> list, boolean z) {
        com.google.android.gms.common.internal.zzx.zzaa(!list.isEmpty());
        zza("Uploading batched hits. compression, count", Boolean.valueOf(z), Integer.valueOf(list.size()));
        zza zzaVar = new zza();
        ArrayList arrayList = new ArrayList();
        for (zzab zzabVar : list) {
            if (!zzaVar.zzj(zzabVar)) {
                break;
            }
            arrayList.add(Long.valueOf(zzabVar.zzkz()));
        }
        if (zzaVar.zzkN() == 0) {
            return arrayList;
        }
        URL urlZzkL = zzkL();
        if (urlZzkL == null) {
            zzbe("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        int iZzb = z ? zzb(urlZzkL, zzaVar.getPayload()) : zza(urlZzkL, zzaVar.getPayload());
        if (200 == iZzb) {
            zza("Batched upload completed. Hits batched", Integer.valueOf(zzaVar.zzkN()));
            return arrayList;
        }
        zza("Network error uploading hits. status code", Integer.valueOf(iZzb));
        if (zziv().zzjU().contains(Integer.valueOf(iZzb))) {
            zzbd("Server instructed the client to stop batching");
            this.zzPx.start();
        }
        return Collections.emptyList();
    }

    HttpURLConnection zzc(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setDefaultUseCaches(false);
        httpURLConnection.setConnectTimeout(zziv().zzkd());
        httpURLConnection.setReadTimeout(zziv().zzke());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("User-Agent", this.zzIa);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        zza("Network initialized. User agent", this.zzIa);
    }

    public boolean zzkK() {
        NetworkInfo activeNetworkInfo;
        zzis();
        zziE();
        try {
            activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException e) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzba("No network connectivity");
        return false;
    }

    public List<Long> zzm(List<zzab> list) {
        boolean z;
        boolean z2 = true;
        zzis();
        zziE();
        com.google.android.gms.common.internal.zzx.zzw(list);
        if (zziv().zzjU().isEmpty() || !this.zzPx.zzv(zziv().zzjN() * 1000)) {
            z2 = false;
            z = false;
        } else {
            z = zziv().zzjS() != zzm.NONE;
            if (zziv().zzjT() != zzo.GZIP) {
                z2 = false;
            }
        }
        return z ? zza(list, z2) : zzn(list);
    }

    List<Long> zzn(List<zzab> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (zzab zzabVar : list) {
            if (!zzg(zzabVar)) {
                break;
            }
            arrayList.add(Long.valueOf(zzabVar.zzkz()));
            if (arrayList.size() >= zziv().zzjL()) {
                break;
            }
        }
        return arrayList;
    }
}
