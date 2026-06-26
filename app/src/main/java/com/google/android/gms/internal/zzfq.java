package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzfq implements zzfo {
    private final Context mContext;
    final Set<WebView> zzCr = Collections.synchronizedSet(new HashSet());

    public zzfq(Context context) {
        this.mContext = context;
    }

    @Override // com.google.android.gms.internal.zzfo
    public void zza(String str, final String str2, final String str3) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Fetching assets for the given html");
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzfq.1
            @Override // java.lang.Runnable
            public void run() {
                final WebView webViewZzfh = zzfq.this.zzfh();
                webViewZzfh.setWebViewClient(new WebViewClient() { // from class: com.google.android.gms.internal.zzfq.1.1
                    @Override // android.webkit.WebViewClient
                    public void onPageFinished(WebView view, String url) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Loading assets have finished");
                        zzfq.this.zzCr.remove(webViewZzfh);
                    }

                    @Override // android.webkit.WebViewClient
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Loading assets have failed.");
                        zzfq.this.zzCr.remove(webViewZzfh);
                    }
                });
                zzfq.this.zzCr.add(webViewZzfh);
                webViewZzfh.loadDataWithBaseURL(str2, str3, "text/html", "UTF-8", null);
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Fetching assets finished.");
            }
        });
    }

    public WebView zzfh() {
        WebView webView = new WebView(this.mContext);
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }
}
