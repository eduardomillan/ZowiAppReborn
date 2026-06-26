package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class zzcu extends zzct {
    private static final Object zzaYT = new Object();
    private static zzcu zzaZe;
    private Handler handler;
    private Context zzaYU;
    private zzau zzaYV;
    private volatile zzas zzaYW;
    private zzbl zzaZc;
    private int zzaYX = 1800000;
    private boolean zzaYY = true;
    private boolean zzaYZ = false;
    private boolean connected = true;
    private boolean zzaZa = true;
    private zzav zzaZb = new zzav() { // from class: com.google.android.gms.tagmanager.zzcu.1
        @Override // com.google.android.gms.tagmanager.zzav
        public void zzas(boolean z) {
            zzcu.this.zzd(z, zzcu.this.connected);
        }
    };
    private boolean zzaZd = false;

    private zzcu() {
    }

    public static zzcu zzDG() {
        if (zzaZe == null) {
            zzaZe = new zzcu();
        }
        return zzaZe;
    }

    private void zzDH() {
        this.zzaZc = new zzbl(this);
        this.zzaZc.zzaR(this.zzaYU);
    }

    private void zzDI() {
        this.handler = new Handler(this.zzaYU.getMainLooper(), new Handler.Callback() { // from class: com.google.android.gms.tagmanager.zzcu.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message msg) {
                if (1 == msg.what && zzcu.zzaYT.equals(msg.obj)) {
                    zzcu.this.dispatch();
                    if (zzcu.this.zzaYX > 0 && !zzcu.this.zzaZd) {
                        zzcu.this.handler.sendMessageDelayed(zzcu.this.handler.obtainMessage(1, zzcu.zzaYT), zzcu.this.zzaYX);
                    }
                }
                return true;
            }
        });
        if (this.zzaYX > 0) {
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaYT), this.zzaYX);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzct
    public synchronized void dispatch() {
        if (this.zzaYZ) {
            this.zzaYW.zzk(new Runnable() { // from class: com.google.android.gms.tagmanager.zzcu.3
                @Override // java.lang.Runnable
                public void run() {
                    zzcu.this.zzaYV.dispatch();
                }
            });
        } else {
            zzbg.v("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zzaYY = true;
        }
    }

    synchronized zzau zzDJ() {
        if (this.zzaYV == null) {
            if (this.zzaYU == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzaYV = new zzby(this.zzaZb, this.zzaYU);
        }
        if (this.handler == null) {
            zzDI();
        }
        this.zzaYZ = true;
        if (this.zzaYY) {
            dispatch();
            this.zzaYY = false;
        }
        if (this.zzaZc == null && this.zzaZa) {
            zzDH();
        }
        return this.zzaYV;
    }

    synchronized void zza(Context context, zzas zzasVar) {
        if (this.zzaYU == null) {
            this.zzaYU = context.getApplicationContext();
            if (this.zzaYW == null) {
                this.zzaYW = zzasVar;
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.zzct
    synchronized void zzat(boolean z) {
        zzd(this.zzaZd, z);
    }

    synchronized void zzd(boolean z, boolean z2) {
        if (this.zzaZd != z || this.connected != z2) {
            if ((z || !z2) && this.zzaYX > 0) {
                this.handler.removeMessages(1, zzaYT);
            }
            if (!z && z2 && this.zzaYX > 0) {
                this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaYT), this.zzaYX);
            }
            zzbg.v("PowerSaveMode " + ((z || !z2) ? "initiated." : "terminated."));
            this.zzaZd = z;
            this.connected = z2;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzct
    synchronized void zzio() {
        if (!this.zzaZd && this.connected && this.zzaYX > 0) {
            this.handler.removeMessages(1, zzaYT);
            this.handler.sendMessage(this.handler.obtainMessage(1, zzaYT));
        }
    }
}
