package com.google.android.gms.internal;

import android.support.v7.internal.widget.ActivityChooserView;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class zzph extends com.google.android.gms.measurement.zze<zzph> {
    private int zzaLA;
    private String zzaLB;
    private String zzaLC;
    private boolean zzaLD;
    private boolean zzaLE;
    private boolean zzaLF;
    private String zzaLy;
    private int zzaLz;

    public zzph() {
        this(false);
    }

    public zzph(boolean z) {
        this(z, zzyL());
    }

    public zzph(boolean z, int i) {
        com.google.android.gms.common.internal.zzx.zzbI(i);
        this.zzaLz = i;
        this.zzaLE = z;
    }

    static int zzyL() {
        UUID uuidRandomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (uuidRandomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        int mostSignificantBits = (int) (uuidRandomUUID.getMostSignificantBits() & 2147483647L);
        if (mostSignificantBits != 0) {
            return mostSignificantBits;
        }
        Log.e("GAv4", "UUID.randomUUID() returned 0.");
        return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    private void zzyP() {
        if (this.zzaLF) {
            throw new IllegalStateException("ScreenViewInfo is immutable");
        }
    }

    public void setScreenName(String screenName) {
        zzyP();
        this.zzaLy = screenName;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("screenName", this.zzaLy);
        map.put("interstitial", Boolean.valueOf(this.zzaLD));
        map.put("automatic", Boolean.valueOf(this.zzaLE));
        map.put("screenId", Integer.valueOf(this.zzaLz));
        map.put("referrerScreenId", Integer.valueOf(this.zzaLA));
        map.put("referrerScreenName", this.zzaLB);
        map.put("referrerUri", this.zzaLC);
        return zzB(map);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzph zzphVar) {
        if (!TextUtils.isEmpty(this.zzaLy)) {
            zzphVar.setScreenName(this.zzaLy);
        }
        if (this.zzaLz != 0) {
            zzphVar.zzib(this.zzaLz);
        }
        if (this.zzaLA != 0) {
            zzphVar.zzic(this.zzaLA);
        }
        if (!TextUtils.isEmpty(this.zzaLB)) {
            zzphVar.zzdT(this.zzaLB);
        }
        if (!TextUtils.isEmpty(this.zzaLC)) {
            zzphVar.zzdU(this.zzaLC);
        }
        if (this.zzaLD) {
            zzphVar.zzam(this.zzaLD);
        }
        if (this.zzaLE) {
            zzphVar.zzal(this.zzaLE);
        }
    }

    public void zzal(boolean z) {
        zzyP();
        this.zzaLE = z;
    }

    public void zzam(boolean z) {
        zzyP();
        this.zzaLD = z;
    }

    public void zzdT(String str) {
        zzyP();
        this.zzaLB = str;
    }

    public void zzdU(String str) {
        zzyP();
        if (TextUtils.isEmpty(str)) {
            this.zzaLC = null;
        } else {
            this.zzaLC = str;
        }
    }

    public void zzib(int i) {
        zzyP();
        this.zzaLz = i;
    }

    public void zzic(int i) {
        zzyP();
        this.zzaLA = i;
    }

    public String zzyM() {
        return this.zzaLy;
    }

    public int zzyN() {
        return this.zzaLz;
    }

    public String zzyO() {
        return this.zzaLC;
    }
}
