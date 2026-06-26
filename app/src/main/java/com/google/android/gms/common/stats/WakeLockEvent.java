package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class WakeLockEvent extends zzf implements SafeParcelable {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzh();
    private final long mTimeout;
    final int mVersionCode;
    private final long zzahn;
    private int zzaho;
    private final long zzahv;
    private long zzahx;
    private final String zzaia;
    private final int zzaib;
    private final List<String> zzaic;
    private final String zzaid;
    private int zzaie;
    private final String zzaif;
    private final String zzaig;
    private final float zzaih;

    WakeLockEvent(int versionCode, long timeMillis, int eventType, String wakelockName, int wakelockType, List<String> callingPackages, String eventKey, long elapsedRealtime, int deviceState, String secondaryWakeLockName, String hostPackageName, float beginPowerPercentage, long timeout) {
        this.mVersionCode = versionCode;
        this.zzahn = timeMillis;
        this.zzaho = eventType;
        this.zzaia = wakelockName;
        this.zzaif = secondaryWakeLockName;
        this.zzaib = wakelockType;
        this.zzahx = -1L;
        this.zzaic = callingPackages;
        this.zzaid = eventKey;
        this.zzahv = elapsedRealtime;
        this.zzaie = deviceState;
        this.zzaig = hostPackageName;
        this.zzaih = beginPowerPercentage;
        this.mTimeout = timeout;
    }

    public WakeLockEvent(long timeMillis, int eventType, String wakelockName, int wakelockType, List<String> callingPackages, String eventKey, long elapsedRealtime, int deviceState, String secondaryWakeLockName, String hostPackageName, float beginPowerPercentage, long timeout) {
        this(1, timeMillis, eventType, wakelockName, wakelockType, callingPackages, eventKey, elapsedRealtime, deviceState, secondaryWakeLockName, hostPackageName, beginPowerPercentage, timeout);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.stats.zzf
    public int getEventType() {
        return this.zzaho;
    }

    @Override // com.google.android.gms.common.stats.zzf
    public long getTimeMillis() {
        return this.zzahn;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzh.zza(this, out, flags);
    }

    public String zzqc() {
        return this.zzaid;
    }

    @Override // com.google.android.gms.common.stats.zzf
    public long zzqd() {
        return this.zzahx;
    }

    public long zzqf() {
        return this.zzahv;
    }

    @Override // com.google.android.gms.common.stats.zzf
    public String zzqg() {
        return "\t" + zzqj() + "\t" + zzql() + "\t" + (zzqm() == null ? "" : TextUtils.join(",", zzqm())) + "\t" + zzqn() + "\t" + (zzqk() == null ? "" : zzqk()) + "\t" + (zzqo() == null ? "" : zzqo()) + "\t" + zzqp();
    }

    public String zzqj() {
        return this.zzaia;
    }

    public String zzqk() {
        return this.zzaif;
    }

    public int zzql() {
        return this.zzaib;
    }

    public List<String> zzqm() {
        return this.zzaic;
    }

    public int zzqn() {
        return this.zzaie;
    }

    public String zzqo() {
        return this.zzaig;
    }

    public float zzqp() {
        return this.zzaih;
    }

    public long zzqq() {
        return this.mTimeout;
    }
}
