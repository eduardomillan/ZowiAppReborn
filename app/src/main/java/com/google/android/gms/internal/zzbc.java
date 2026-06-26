package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbb;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzbc {
    /* JADX INFO: Access modifiers changed from: private */
    public zzbb zza(Context context, VersionInfoParcel versionInfoParcel, final zzin<zzbb> zzinVar, zzan zzanVar) {
        final zzbd zzbdVar = new zzbd(context, versionInfoParcel, zzanVar);
        zzbdVar.zza(new zzbb.zza() { // from class: com.google.android.gms.internal.zzbc.2
            @Override // com.google.android.gms.internal.zzbb.zza
            public void zzcj() {
                zzinVar.zzf(zzbdVar);
            }
        });
        return zzbdVar;
    }

    public Future<zzbb> zza(final Context context, final VersionInfoParcel versionInfoParcel, final String str, final zzan zzanVar) {
        final zzin zzinVar = new zzin();
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzbc.1
            @Override // java.lang.Runnable
            public void run() {
                zzbc.this.zza(context, versionInfoParcel, (zzin<zzbb>) zzinVar, zzanVar).zzt(str);
            }
        });
        return zzinVar;
    }
}
