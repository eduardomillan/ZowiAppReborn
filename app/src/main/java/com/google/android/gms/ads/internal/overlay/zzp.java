package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcc;
import com.google.android.gms.internal.zzce;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzig;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class zzp {
    private final Context mContext;
    private final String zzBY;
    private final VersionInfoParcel zzBZ;
    private final zzce zzCa;
    private final zzcg zzCb;
    private final long[] zzCd;
    private final String[] zzCe;
    private zzce zzCf;
    private zzce zzCg;
    private zzce zzCh;
    private zzce zzCi;
    private boolean zzCj;
    private zzi zzCk;
    private boolean zzCl;
    private boolean zzCm;
    private final zzig zzCc = new zzig.zzb().zza("min_1", Double.MIN_VALUE, 1.0d).zza("1_5", 1.0d, 5.0d).zza("5_10", 5.0d, 10.0d).zza("10_20", 10.0d, 20.0d).zza("20_30", 20.0d, 30.0d).zza("30_max", 30.0d, Double.MAX_VALUE).zzgK();
    private long zzCn = -1;

    public zzp(Context context, VersionInfoParcel versionInfoParcel, String str, zzcg zzcgVar, zzce zzceVar) {
        this.mContext = context;
        this.zzBZ = versionInfoParcel;
        this.zzBY = str;
        this.zzCb = zzcgVar;
        this.zzCa = zzceVar;
        String str2 = zzby.zzuF.get();
        if (str2 == null) {
            this.zzCe = new String[0];
            this.zzCd = new long[0];
            return;
        }
        String[] strArrSplit = TextUtils.split(str2, ",");
        this.zzCe = new String[strArrSplit.length];
        this.zzCd = new long[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            try {
                this.zzCd[i] = Long.parseLong(strArrSplit[i]);
            } catch (NumberFormatException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Unable to parse frame hash target time number.", e);
                this.zzCd[i] = -1;
            }
        }
    }

    private void zzc(zzi zziVar) {
        long jLongValue = zzby.zzuG.get().longValue();
        long currentPosition = zziVar.getCurrentPosition();
        for (int i = 0; i < this.zzCe.length; i++) {
            if (this.zzCe[i] == null && jLongValue > Math.abs(currentPosition - this.zzCd[i])) {
                this.zzCe[i] = zza((TextureView) zziVar);
                return;
            }
        }
    }

    private void zzfd() {
        if (this.zzCh != null && this.zzCi == null) {
            zzcc.zza(this.zzCb, this.zzCh, "vff");
            zzcc.zza(this.zzCb, this.zzCa, "vtt");
            this.zzCi = zzcc.zzb(this.zzCb);
        }
        long jNanoTime = com.google.android.gms.ads.internal.zzp.zzbz().nanoTime();
        if (this.zzCj && this.zzCm && this.zzCn != -1) {
            this.zzCc.zza(TimeUnit.SECONDS.toNanos(1L) / (jNanoTime - this.zzCn));
        }
        this.zzCm = this.zzCj;
        this.zzCn = jNanoTime;
    }

    public void onStop() {
        if (!zzby.zzuE.get().booleanValue() || this.zzCl) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("type", "native-player-metrics");
        bundle.putString("request", this.zzBY);
        bundle.putString("player", this.zzCk.zzer());
        for (zzig.zza zzaVar : this.zzCc.getBuckets()) {
            bundle.putString("fps_c_" + zzaVar.name, Integer.toString(zzaVar.count));
            bundle.putString("fps_p_" + zzaVar.name, Double.toString(zzaVar.zzIV));
        }
        for (int i = 0; i < this.zzCd.length; i++) {
            String str = this.zzCe[i];
            if (str != null) {
                bundle.putString("fh_" + Long.valueOf(this.zzCd[i]), str);
            }
        }
        com.google.android.gms.ads.internal.zzp.zzbv().zza(this.mContext, this.zzBZ.zzJu, "gmob-apps", bundle, true);
        this.zzCl = true;
    }

    String zza(TextureView textureView) {
        long j;
        Bitmap bitmap = textureView.getBitmap(8, 8);
        long j2 = 0;
        long j3 = 63;
        int i = 0;
        while (i < 8) {
            int i2 = 0;
            long j4 = j2;
            while (true) {
                j = j3;
                int i3 = i2;
                if (i3 < 8) {
                    int pixel = bitmap.getPixel(i3, i);
                    j4 |= (Color.green(pixel) + (Color.blue(pixel) + Color.red(pixel)) > 128 ? 1L : 0L) << ((int) j);
                    i2 = i3 + 1;
                    j3 = j - 1;
                }
            }
            i++;
            j3 = j;
            j2 = j4;
        }
        return String.format("%016X", Long.valueOf(j2));
    }

    public void zza(zzi zziVar) {
        zzcc.zza(this.zzCb, this.zzCa, "vpc");
        this.zzCf = zzcc.zzb(this.zzCb);
        this.zzCk = zziVar;
    }

    public void zzb(zzi zziVar) {
        zzfd();
        zzc(zziVar);
    }

    public void zzeR() {
        if (this.zzCf == null || this.zzCg != null) {
            return;
        }
        zzcc.zza(this.zzCb, this.zzCf, "vfr");
        this.zzCg = zzcc.zzb(this.zzCb);
    }

    public void zzfe() {
        this.zzCj = true;
        if (this.zzCg == null || this.zzCh != null) {
            return;
        }
        zzcc.zza(this.zzCb, this.zzCg, "vfp");
        this.zzCh = zzcc.zzb(this.zzCb);
    }

    public void zzff() {
        this.zzCj = false;
    }
}
