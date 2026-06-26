package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzia extends Handler {
    public zzia(Looper looper) {
        super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) throws Exception {
        try {
            super.handleMessage(msg);
        } catch (Exception e) {
            com.google.android.gms.ads.internal.zzp.zzby().zzc(e, false);
            throw e;
        }
    }
}
