package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzjg extends zzja {
    public zzjg(zziz zzizVar, boolean z) {
        super(zzizVar, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        try {
            if (!"mraid.js".equalsIgnoreCase(new File(url).getName())) {
                return super.shouldInterceptRequest(webView, url);
            }
            if (!(webView instanceof zziz)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Tried to intercept request from a WebView that wasn't an AdWebView.");
                return super.shouldInterceptRequest(webView, url);
            }
            zziz zzizVar = (zziz) webView;
            zzizVar.zzhe().zzeG();
            String str = zzizVar.zzaN().zztf ? zzby.zzuP.get() : zzizVar.zzhi() ? zzby.zzuO.get() : zzby.zzuN.get();
            com.google.android.gms.ads.internal.util.client.zzb.v("shouldInterceptRequest(" + str + ")");
            return zzd(zzizVar.getContext(), this.zzoM.zzhh().zzJu, str);
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not fetch MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, url);
        }
    }

    protected WebResourceResponse zzd(Context context, String str, String str2) throws ExecutionException, InterruptedException, TimeoutException, IOException {
        HashMap map = new HashMap();
        map.put("User-Agent", com.google.android.gms.ads.internal.zzp.zzbv().zzf(context, str));
        map.put("Cache-Control", "max-stale=3600");
        String str3 = new zzih(context).zza(str2, map).get(60L, TimeUnit.SECONDS);
        if (str3 == null) {
            return null;
        }
        return new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(str3.getBytes("UTF-8")));
    }
}
