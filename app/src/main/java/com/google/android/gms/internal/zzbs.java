package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class zzbs implements zzbt {
    @Override // com.google.android.gms.internal.zzbt
    public List<String> zza(AdRequestInfoParcel adRequestInfoParcel) {
        return adRequestInfoParcel.zzED == null ? Collections.emptyList() : adRequestInfoParcel.zzED;
    }
}
