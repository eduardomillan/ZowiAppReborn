package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
class zzat extends Thread implements zzas {
    private static zzat zzaXf;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile boolean zzNZ;
    private final LinkedBlockingQueue<Runnable> zzaXe;
    private volatile zzau zzaXg;

    private zzat(Context context) {
        super("GAThread");
        this.zzaXe = new LinkedBlockingQueue<>();
        this.zzNZ = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static zzat zzaQ(Context context) {
        if (zzaXf == null) {
            zzaXf = new zzat(context);
        }
        return zzaXf;
    }

    private String zzd(Throwable th) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (!this.mClosed) {
            try {
                try {
                    Runnable runnableTake = this.zzaXe.take();
                    if (!this.zzNZ) {
                        runnableTake.run();
                    }
                } catch (InterruptedException e) {
                    zzbg.zzaG(e.toString());
                }
            } catch (Throwable th) {
                zzbg.e("Error on Google TagManager Thread: " + zzd(th));
                zzbg.e("Google TagManager is shutting down.");
                this.zzNZ = true;
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.zzas
    public void zzeR(String str) {
        zzj(str, System.currentTimeMillis());
    }

    void zzj(final String str, final long j) {
        zzk(new Runnable() { // from class: com.google.android.gms.tagmanager.zzat.1
            @Override // java.lang.Runnable
            public void run() {
                if (zzat.this.zzaXg == null) {
                    zzcu zzcuVarZzDG = zzcu.zzDG();
                    zzcuVarZzDG.zza(zzat.this.mContext, this);
                    zzat.this.zzaXg = zzcuVarZzDG.zzDJ();
                }
                zzat.this.zzaXg.zzg(j, str);
            }
        });
    }

    @Override // com.google.android.gms.tagmanager.zzas
    public void zzk(Runnable runnable) {
        this.zzaXe.add(runnable);
    }
}
