package com.google.android.gms.internal;

import android.view.View;
import android.webkit.WebChromeClient;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzjh extends zzjf {
    public zzjh(zziz zzizVar) {
        super(zzizVar);
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback customViewCallback) {
        zza(view, requestedOrientation, customViewCallback);
    }
}
