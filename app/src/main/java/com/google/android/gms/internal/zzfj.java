package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzfk;
import com.google.android.gms.internal.zzfl;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzfj extends com.google.android.gms.dynamic.zzg<zzfl> {
    private static final zzfj zzCp = new zzfj();

    private static final class zza extends Exception {
        public zza(String str) {
            super(str);
        }
    }

    private zzfj() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static zzfk zzb(Activity activity) {
        zzfk zzfkVarZzd;
        try {
            if (zzc(activity)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Using AdOverlay from the client jar.");
                zzfkVarZzd = new com.google.android.gms.ads.internal.overlay.zzd(activity);
            } else {
                zzfkVarZzd = zzCp.zzd(activity);
            }
            return zzfkVarZzd;
        } catch (zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH(e.getMessage());
            return null;
        }
    }

    private static boolean zzc(Activity activity) throws zza {
        Intent intent = activity.getIntent();
        if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
        }
        throw new zza("Ad overlay requires the useClientJar flag in intent extras.");
    }

    private zzfk zzd(Activity activity) {
        try {
            return zzfk.zza.zzL(zzas(activity).zze(com.google.android.gms.dynamic.zze.zzy(activity)));
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote AdOverlay.", e);
            return null;
        } catch (zzg.zza e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote AdOverlay.", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* JADX INFO: renamed from: zzK, reason: merged with bridge method [inline-methods] */
    public zzfl zzd(IBinder iBinder) {
        return zzfl.zza.zzM(iBinder);
    }
}
