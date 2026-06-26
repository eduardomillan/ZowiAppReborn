package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.internal.client.zzv;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class zzm extends zzv.zza {
    private Object zzpd = new Object();
    private final Random zzts = new Random();
    private long zztt;

    public zzm() {
        zzcL();
    }

    @Override // com.google.android.gms.ads.internal.client.zzv
    public long getValue() {
        return this.zztt;
    }

    public void zzcL() {
        synchronized (this.zzpd) {
            int i = 3;
            long jNextInt = 0;
            while (true) {
                i--;
                if (i <= 0) {
                    break;
                }
                jNextInt = ((long) this.zzts.nextInt()) + 2147483648L;
                if (jNextInt != this.zztt && jNextInt != 0) {
                    break;
                }
            }
            this.zztt = jNextInt;
        }
    }
}
