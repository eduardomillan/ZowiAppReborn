package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.MotionEvent;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzam;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzic;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
@zzgr
class zzh implements zzaj, Runnable {
    private final List<Object[]> zzoQ = new Vector();
    private final AtomicReference<zzaj> zzoR = new AtomicReference<>();
    CountDownLatch zzoS = new CountDownLatch(1);
    private zzq zzot;

    public zzh(zzq zzqVar) {
        this.zzot = zzqVar;
        if (com.google.android.gms.ads.internal.client.zzl.zzcF().zzgT()) {
            zzic.zza(this);
        } else {
            run();
        }
    }

    private void zzbh() {
        if (this.zzoQ.isEmpty()) {
            return;
        }
        for (Object[] objArr : this.zzoQ) {
            if (objArr.length == 1) {
                this.zzoR.get().zza((MotionEvent) objArr[0]);
            } else if (objArr.length == 3) {
                this.zzoR.get().zza(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), ((Integer) objArr[2]).intValue());
            }
        }
        this.zzoQ.clear();
    }

    private Context zzp(Context context) {
        Context applicationContext;
        return (zzby.zzuw.get().booleanValue() && (applicationContext = context.getApplicationContext()) != null) ? applicationContext : context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            zza(zzb(this.zzot.zzqj.zzJu, zzp(this.zzot.context), !zzby.zzuI.get().booleanValue() || this.zzot.zzqj.zzJx));
        } finally {
            this.zzoS.countDown();
            this.zzot = null;
        }
    }

    @Override // com.google.android.gms.internal.zzaj
    public void zza(int i, int i2, int i3) {
        zzaj zzajVar = this.zzoR.get();
        if (zzajVar == null) {
            this.zzoQ.add(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
        } else {
            zzbh();
            zzajVar.zza(i, i2, i3);
        }
    }

    @Override // com.google.android.gms.internal.zzaj
    public void zza(MotionEvent motionEvent) {
        zzaj zzajVar = this.zzoR.get();
        if (zzajVar == null) {
            this.zzoQ.add(new Object[]{motionEvent});
        } else {
            zzbh();
            zzajVar.zza(motionEvent);
        }
    }

    protected void zza(zzaj zzajVar) {
        this.zzoR.set(zzajVar);
    }

    protected zzaj zzb(String str, Context context, boolean z) {
        return zzam.zza(str, context, z);
    }

    @Override // com.google.android.gms.internal.zzaj
    public String zzb(Context context) {
        zzaj zzajVar;
        if (!zzbg() || (zzajVar = this.zzoR.get()) == null) {
            return "";
        }
        zzbh();
        return zzajVar.zzb(zzp(context));
    }

    @Override // com.google.android.gms.internal.zzaj
    public String zzb(Context context, String str) {
        zzaj zzajVar;
        if (!zzbg() || (zzajVar = this.zzoR.get()) == null) {
            return "";
        }
        zzbh();
        return zzajVar.zzb(zzp(context), str);
    }

    protected boolean zzbg() {
        try {
            this.zzoS.await();
            return true;
        } catch (InterruptedException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Interrupted during GADSignals creation.", e);
            return false;
        }
    }
}
