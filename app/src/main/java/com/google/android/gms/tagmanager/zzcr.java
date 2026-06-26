package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzcr extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.SDK_VERSION.toString();

    public zzcr() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        return zzdf.zzQ(Integer.valueOf(Build.VERSION.SDK_INT));
    }
}
