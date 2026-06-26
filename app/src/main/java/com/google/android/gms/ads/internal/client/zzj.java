package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.internal.client.zzu;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzj extends zzu.zza {
    private final AppEventListener zztj;

    public zzj(AppEventListener appEventListener) {
        this.zztj = appEventListener;
    }

    @Override // com.google.android.gms.ads.internal.client.zzu
    public void onAppEvent(String name, String info) {
        this.zztj.onAppEvent(name, info);
    }
}
