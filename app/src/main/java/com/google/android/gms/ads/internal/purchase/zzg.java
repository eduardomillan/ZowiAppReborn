package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzfv;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzg extends zzfv.zza implements ServiceConnection {
    private Context mContext;
    zzb zzCD;
    private String zzCJ;
    private zzf zzCN;
    private boolean zzCT;
    private int zzCU;
    private Intent zzCV;

    public zzg(Context context, String str, boolean z, int i, Intent intent, zzf zzfVar) {
        this.zzCT = false;
        this.zzCJ = str;
        this.zzCU = i;
        this.zzCV = intent;
        this.zzCT = z;
        this.mContext = context;
        this.zzCN = zzfVar;
    }

    @Override // com.google.android.gms.internal.zzfv
    public void finishPurchase() {
        int iZzd = zzp.zzbF().zzd(this.zzCV);
        if (this.zzCU == -1 && iZzd == 0) {
            this.zzCD = new zzb(this.mContext);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
            com.google.android.gms.common.stats.zzb.zzqh().zza(this.mContext, intent, this, 1);
        }
    }

    @Override // com.google.android.gms.internal.zzfv
    public String getProductId() {
        return this.zzCJ;
    }

    @Override // com.google.android.gms.internal.zzfv
    public Intent getPurchaseData() {
        return this.zzCV;
    }

    @Override // com.google.android.gms.internal.zzfv
    public int getResultCode() {
        return this.zzCU;
    }

    @Override // com.google.android.gms.internal.zzfv
    public boolean isVerified() {
        return this.zzCT;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName name, IBinder service) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("In-app billing service connected.");
        this.zzCD.zzN(service);
        String strZzap = zzp.zzbF().zzap(zzp.zzbF().zze(this.zzCV));
        if (strZzap == null) {
            return;
        }
        if (this.zzCD.zzi(this.mContext.getPackageName(), strZzap) == 0) {
            zzh.zzw(this.mContext).zza(this.zzCN);
        }
        com.google.android.gms.common.stats.zzb.zzqh().zza(this.mContext, this);
        this.zzCD.destroy();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName name) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("In-app billing service disconnected.");
        this.zzCD.destroy();
    }
}
