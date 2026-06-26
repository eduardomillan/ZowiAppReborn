package com.google.android.gms.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/* JADX INFO: loaded from: classes.dex */
class zzrd implements zzrf {
    private HttpClient zzaD;

    zzrd() {
    }

    private InputStream zza(HttpClient httpClient, HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            com.google.android.gms.tagmanager.zzbg.v("Success response");
            return httpResponse.getEntity().getContent();
        }
        String str = "Bad response: " + statusCode;
        if (statusCode == 404) {
            throw new FileNotFoundException(str);
        }
        throw new IOException(str);
    }

    private void zza(HttpClient httpClient) {
        if (httpClient == null || httpClient.getConnectionManager() == null) {
            return;
        }
        httpClient.getConnectionManager().shutdown();
    }

    @Override // com.google.android.gms.internal.zzrf
    public void close() {
        zza(this.zzaD);
    }

    HttpClient zzEs() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
        return new DefaultHttpClient(basicHttpParams);
    }

    @Override // com.google.android.gms.internal.zzrf
    public InputStream zzft(String str) throws IOException {
        this.zzaD = zzEs();
        return zza(this.zzaD, this.zzaD.execute(new HttpGet(str)));
    }
}
