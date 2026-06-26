package com.google.android.gms.ads.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzp;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzcx;
import com.google.android.gms.internal.zzcy;
import com.google.android.gms.internal.zzcz;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zzmi;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzi extends zzp.zza {
    private final Context mContext;
    private final com.google.android.gms.ads.internal.client.zzo zzoT;
    private final zzcw zzoU;
    private final zzcx zzoV;
    private final zzmi<String, zzcz> zzoW;
    private final zzmi<String, zzcy> zzoX;
    private final NativeAdOptionsParcel zzoY;
    private final zzem zzox;
    private final String zzpa;
    private final VersionInfoParcel zzpb;
    private WeakReference<zzn> zzpc;
    private Object zzpd = new Object();
    private final List<String> zzoZ = zzbi();

    zzi(Context context, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel, com.google.android.gms.ads.internal.client.zzo zzoVar, zzcw zzcwVar, zzcx zzcxVar, zzmi<String, zzcz> zzmiVar, zzmi<String, zzcy> zzmiVar2, NativeAdOptionsParcel nativeAdOptionsParcel) {
        this.mContext = context;
        this.zzpa = str;
        this.zzox = zzemVar;
        this.zzpb = versionInfoParcel;
        this.zzoT = zzoVar;
        this.zzoV = zzcxVar;
        this.zzoU = zzcwVar;
        this.zzoW = zzmiVar;
        this.zzoX = zzmiVar2;
        this.zzoY = nativeAdOptionsParcel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> zzbi() {
        ArrayList arrayList = new ArrayList();
        if (this.zzoV != null) {
            arrayList.add("1");
        }
        if (this.zzoU != null) {
            arrayList.add("2");
        }
        if (this.zzoW.size() > 0) {
            arrayList.add("3");
        }
        return arrayList;
    }

    @Override // com.google.android.gms.ads.internal.client.zzp
    public String getMediationAdapterClassName() {
        synchronized (this.zzpd) {
            if (this.zzpc == null) {
                return null;
            }
            zzn zznVar = this.zzpc.get();
            return zznVar != null ? zznVar.getMediationAdapterClassName() : null;
        }
    }

    @Override // com.google.android.gms.ads.internal.client.zzp
    public boolean isLoading() {
        synchronized (this.zzpd) {
            if (this.zzpc == null) {
                return false;
            }
            zzn zznVar = this.zzpc.get();
            return zznVar != null ? zznVar.isLoading() : false;
        }
    }

    protected void runOnUiThread(Runnable runnable) {
        zzid.zzIE.post(runnable);
    }

    protected zzn zzbj() {
        return new zzn(this.mContext, AdSizeParcel.zzs(this.mContext), this.zzpa, this.zzox, this.zzpb);
    }

    @Override // com.google.android.gms.ads.internal.client.zzp
    public void zzf(final AdRequestParcel adRequestParcel) {
        runOnUiThread(new Runnable() { // from class: com.google.android.gms.ads.internal.zzi.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (zzi.this.zzpd) {
                    zzn zznVarZzbj = zzi.this.zzbj();
                    zzi.this.zzpc = new WeakReference(zznVarZzbj);
                    zznVarZzbj.zzb(zzi.this.zzoU);
                    zznVarZzbj.zzb(zzi.this.zzoV);
                    zznVarZzbj.zza(zzi.this.zzoW);
                    zznVarZzbj.zza(zzi.this.zzoT);
                    zznVarZzbj.zzb(zzi.this.zzoX);
                    zznVarZzbj.zza(zzi.this.zzbi());
                    zznVarZzbj.zzb(zzi.this.zzoY);
                    zznVarZzbj.zzb(adRequestParcel);
                }
            }
        });
    }
}
