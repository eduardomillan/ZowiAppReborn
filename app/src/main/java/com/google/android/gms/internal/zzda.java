package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzco;
import com.google.android.gms.internal.zzcp;

/* JADX INFO: loaded from: classes.dex */
public class zzda extends com.google.android.gms.dynamic.zzg<zzcp> {
    public zzda() {
        super("com.google.android.gms.ads.NativeAdViewDelegateCreatorImpl");
    }

    private zzco zzb(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        try {
            return zzco.zza.zzu(zzas(context).zza(com.google.android.gms.dynamic.zze.zzy(context), com.google.android.gms.dynamic.zze.zzy(frameLayout), com.google.android.gms.dynamic.zze.zzy(frameLayout2), 8115000));
        } catch (RemoteException | zzg.zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote NativeAdViewDelegate.", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* JADX INFO: renamed from: zzD, reason: merged with bridge method [inline-methods] */
    public zzcp zzd(IBinder iBinder) {
        return zzcp.zza.zzv(iBinder);
    }

    public zzco zza(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        zzco zzcoVarZzb;
        if (com.google.android.gms.ads.internal.client.zzl.zzcF().zzR(context) && (zzcoVarZzb = zzb(context, frameLayout, frameLayout2)) != null) {
            return zzcoVarZzb;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Using NativeAdViewDelegate from the client jar.");
        return new com.google.android.gms.ads.internal.formats.zzj(frameLayout, frameLayout2);
    }
}
