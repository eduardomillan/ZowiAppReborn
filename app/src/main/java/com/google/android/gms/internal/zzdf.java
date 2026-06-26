package com.google.android.gms.internal;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzdf implements zzdk {
    private final zzdg zzxn;

    public zzdf(zzdg zzdgVar) {
        this.zzxn = zzdgVar;
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        String str = map.get("name");
        if (str == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("App event with no name parameter.");
        } else {
            this.zzxn.onAppEvent(str, map.get("info"));
        }
    }
}
