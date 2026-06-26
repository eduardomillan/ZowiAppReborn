package com.google.android.gms.ads.internal.overlay;

import com.google.android.gms.internal.zzid;

/* JADX INFO: loaded from: classes.dex */
class zzq implements Runnable {
    private boolean mCancelled = false;
    private zzk zzCo;

    zzq(zzk zzkVar) {
        this.zzCo = zzkVar;
    }

    public void cancel() {
        this.mCancelled = true;
        zzid.zzIE.removeCallbacks(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.mCancelled) {
            return;
        }
        this.zzCo.zzeX();
        zzfg();
    }

    public void zzfg() {
        zzid.zzIE.postDelayed(this, 250L);
    }
}
