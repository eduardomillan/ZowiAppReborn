package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

/* JADX INFO: loaded from: classes.dex */
class zzcx implements zzac {
    private final Context zzaYU;
    private final String zzaZl = zza("GoogleTagManager", "4.00", Build.VERSION.RELEASE, zzc(Locale.getDefault()), Build.MODEL, Build.ID);
    private final HttpClient zzaZm;
    private zza zzaZn;

    public interface zza {
        void zza(zzaq zzaqVar);

        void zzb(zzaq zzaqVar);

        void zzc(zzaq zzaqVar);
    }

    zzcx(HttpClient httpClient, Context context, zza zzaVar) {
        this.zzaYU = context.getApplicationContext();
        this.zzaZm = httpClient;
        this.zzaZn = zzaVar;
    }

    private void zza(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        int iAvailable;
        StringBuffer stringBuffer = new StringBuffer();
        for (Header header : httpEntityEnclosingRequest.getAllHeaders()) {
            stringBuffer.append(header.toString()).append(Droid2InoConstants.NEW_LINE_CHARACTER);
        }
        stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append(Droid2InoConstants.NEW_LINE_CHARACTER);
        if (httpEntityEnclosingRequest.getEntity() != null) {
            try {
                InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                if (content != null && (iAvailable = content.available()) > 0) {
                    byte[] bArr = new byte[iAvailable];
                    content.read(bArr);
                    stringBuffer.append("POST:\n");
                    stringBuffer.append(new String(bArr)).append(Droid2InoConstants.NEW_LINE_CHARACTER);
                }
            } catch (IOException e) {
                zzbg.v("Error Writing hit to log...");
            }
        }
        zzbg.v(stringBuffer.toString());
    }

    static String zzc(Locale locale) {
        if (locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(locale.getLanguage().toLowerCase());
        if (locale.getCountry() != null && locale.getCountry().length() != 0) {
            sb.append("-").append(locale.getCountry().toLowerCase());
        }
        return sb.toString();
    }

    private HttpEntityEnclosingRequest zzd(URL url) {
        BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        URISyntaxException e;
        try {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", url.toURI().toString());
        } catch (URISyntaxException e2) {
            basicHttpEntityEnclosingRequest = null;
            e = e2;
        }
        try {
            basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.zzaZl);
        } catch (URISyntaxException e3) {
            e = e3;
            zzbg.zzaH("Exception sending hit: " + e.getClass().getSimpleName());
            zzbg.zzaH(e.getMessage());
        }
        return basicHttpEntityEnclosingRequest;
    }

    @Override // com.google.android.gms.tagmanager.zzac
    public boolean zzCO() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzaYU.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzbg.v("...no network connectivity");
        return false;
    }

    String zza(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", str, str2, str3, str4, str5, str6);
    }

    URL zzd(zzaq zzaqVar) {
        try {
            return new URL(zzaqVar.zzCX());
        } catch (MalformedURLException e) {
            zzbg.e("Error trying to parse the GTM url.");
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzac
    public void zzz(List<zzaq> list) {
        boolean z;
        int iMin = Math.min(list.size(), 40);
        boolean z2 = true;
        int i = 0;
        while (i < iMin) {
            zzaq zzaqVar = list.get(i);
            URL urlZzd = zzd(zzaqVar);
            if (urlZzd == null) {
                zzbg.zzaH("No destination: discarding hit.");
                this.zzaZn.zzb(zzaqVar);
                z = z2;
            } else {
                HttpEntityEnclosingRequest httpEntityEnclosingRequestZzd = zzd(urlZzd);
                if (httpEntityEnclosingRequestZzd == null) {
                    this.zzaZn.zzb(zzaqVar);
                    z = z2;
                } else {
                    HttpHost httpHost = new HttpHost(urlZzd.getHost(), urlZzd.getPort(), urlZzd.getProtocol());
                    httpEntityEnclosingRequestZzd.addHeader("Host", httpHost.toHostString());
                    zza(httpEntityEnclosingRequestZzd);
                    if (z2) {
                        try {
                            zzbl.zzaS(this.zzaYU);
                            z2 = false;
                        } catch (ClientProtocolException e) {
                            zzbg.zzaH("ClientProtocolException sending hit; discarding hit...");
                            this.zzaZn.zzb(zzaqVar);
                            z = z2;
                        } catch (IOException e2) {
                            zzbg.zzaH("Exception sending hit: " + e2.getClass().getSimpleName());
                            zzbg.zzaH(e2.getMessage());
                            this.zzaZn.zzc(zzaqVar);
                            z = z2;
                        }
                    }
                    HttpResponse httpResponseExecute = this.zzaZm.execute(httpHost, httpEntityEnclosingRequestZzd);
                    int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                    HttpEntity entity = httpResponseExecute.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                    if (statusCode != 200) {
                        zzbg.zzaH("Bad response: " + httpResponseExecute.getStatusLine().getStatusCode());
                        this.zzaZn.zzc(zzaqVar);
                    } else {
                        this.zzaZn.zza(zzaqVar);
                    }
                    z = z2;
                }
            }
            i++;
            z2 = z;
        }
    }
}
