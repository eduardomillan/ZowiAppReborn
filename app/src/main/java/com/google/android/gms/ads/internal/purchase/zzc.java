package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzfw;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzc extends zzhz implements ServiceConnection {
    private Context mContext;
    private boolean zzCB;
    private zzfw zzCC;
    private zzb zzCD;
    private zzh zzCE;
    private List<zzf> zzCF;
    private zzk zzCG;
    private final Object zzpd;

    public zzc(Context context, zzfw zzfwVar, zzk zzkVar) {
        this(context, zzfwVar, zzkVar, new zzb(context), zzh.zzw(context.getApplicationContext()));
    }

    zzc(Context context, zzfw zzfwVar, zzk zzkVar, zzb zzbVar, zzh zzhVar) {
        this.zzpd = new Object();
        this.zzCB = false;
        this.zzCF = null;
        this.mContext = context;
        this.zzCC = zzfwVar;
        this.zzCG = zzkVar;
        this.zzCD = zzbVar;
        this.zzCE = zzhVar;
        this.zzCF = this.zzCE.zzg(10L);
    }

    private void zze(long j) {
        do {
            if (!zzf(j)) {
                com.google.android.gms.ads.internal.util.client.zzb.v("Timeout waiting for pending transaction to be processed.");
            }
        } while (!this.zzCB);
    }

    private boolean zzf(long j) {
        long jElapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (jElapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzpd.wait(jElapsedRealtime);
        } catch (InterruptedException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("waitWithTimeout_lock interrupted");
        }
        return true;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName name, IBinder service) {
        synchronized (this.zzpd) {
            this.zzCD.zzN(service);
            zzfm();
            this.zzCB = true;
            this.zzpd.notify();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName name) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("In-app billing service disconnected.");
        this.zzCD.destroy();
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
        synchronized (this.zzpd) {
            com.google.android.gms.common.stats.zzb.zzqh().zza(this.mContext, this);
            this.zzCD.destroy();
        }
    }

    protected void zza(final zzf zzfVar, String str, String str2) {
        final Intent intent = new Intent();
        zzp.zzbF();
        intent.putExtra("RESPONSE_CODE", 0);
        zzp.zzbF();
        intent.putExtra("INAPP_PURCHASE_DATA", str);
        zzp.zzbF();
        intent.putExtra("INAPP_DATA_SIGNATURE", str2);
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.purchase.zzc.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (zzc.this.zzCG.zza(zzfVar.zzCR, -1, intent)) {
                        zzc.this.zzCC.zza(new zzg(zzc.this.mContext, zzfVar.zzCS, true, -1, intent, zzfVar));
                    } else {
                        zzc.this.zzCC.zza(new zzg(zzc.this.mContext, zzfVar.zzCS, false, -1, intent, zzfVar));
                    }
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to verify and dispatch pending transaction");
                }
            }
        });
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        synchronized (this.zzpd) {
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
            com.google.android.gms.common.stats.zzb.zzqh().zza(this.mContext, intent, this, 1);
            zze(SystemClock.elapsedRealtime());
            com.google.android.gms.common.stats.zzb.zzqh().zza(this.mContext, this);
            this.zzCD.destroy();
        }
    }

    protected void zzfm() {
        if (this.zzCF.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        for (zzf zzfVar : this.zzCF) {
            map.put(zzfVar.zzCS, zzfVar);
        }
        String str = null;
        while (true) {
            Bundle bundleZzj = this.zzCD.zzj(this.mContext.getPackageName(), str);
            if (bundleZzj == null || zzp.zzbF().zzc(bundleZzj) != 0) {
                break;
            }
            ArrayList<String> stringArrayList = bundleZzj.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
            ArrayList<String> stringArrayList2 = bundleZzj.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
            ArrayList<String> stringArrayList3 = bundleZzj.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
            String string = bundleZzj.getString("INAPP_CONTINUATION_TOKEN");
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= stringArrayList.size()) {
                    break;
                }
                if (map.containsKey(stringArrayList.get(i2))) {
                    String str2 = stringArrayList.get(i2);
                    String str3 = stringArrayList2.get(i2);
                    String str4 = stringArrayList3.get(i2);
                    zzf zzfVar2 = (zzf) map.get(str2);
                    if (zzfVar2.zzCR.equals(zzp.zzbF().zzao(str3))) {
                        zza(zzfVar2, str3, str4);
                        map.remove(str2);
                    }
                }
                i = i2 + 1;
            }
            if (string == null || map.isEmpty()) {
                break;
            } else {
                str = string;
            }
        }
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            this.zzCE.zza((zzf) map.get((String) it.next()));
        }
    }
}
