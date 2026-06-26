package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.api.Releasable;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzdv implements Releasable {
    protected zziz zzoM;

    public zzdv(zziz zzizVar) {
        this.zzoM = zzizVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String zzad(String str) {
        switch (str) {
        }
        return "internal";
    }

    public abstract void abort();

    @Override // com.google.android.gms.common.api.Releasable
    public void release() {
    }

    protected void zza(final String str, final String str2, final int i) {
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzdv.2
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("event", "precacheComplete");
                map.put("src", str);
                map.put("cachedSrc", str2);
                map.put("totalBytes", Integer.toString(i));
                zzdv.this.zzoM.zzb("onPrecacheEvent", map);
            }
        });
    }

    protected void zza(final String str, final String str2, final int i, final int i2, final boolean z) {
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzdv.1
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("event", "precacheProgress");
                map.put("src", str);
                map.put("cachedSrc", str2);
                map.put("bytesLoaded", Integer.toString(i));
                map.put("totalBytes", Integer.toString(i2));
                map.put("cacheReady", z ? "1" : "0");
                zzdv.this.zzoM.zzb("onPrecacheEvent", map);
            }
        });
    }

    protected void zza(final String str, final String str2, final String str3, final String str4) {
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzdv.3
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("event", "precacheCanceled");
                map.put("src", str);
                if (!TextUtils.isEmpty(str2)) {
                    map.put("cachedSrc", str2);
                }
                map.put("type", zzdv.this.zzad(str3));
                map.put("reason", str3);
                if (!TextUtils.isEmpty(str4)) {
                    map.put("message", str4);
                }
                zzdv.this.zzoM.zzb("onPrecacheEvent", map);
            }
        });
    }

    public abstract boolean zzab(String str);

    protected String zzac(String str) {
        return com.google.android.gms.ads.internal.client.zzl.zzcF().zzaE(str);
    }
}
