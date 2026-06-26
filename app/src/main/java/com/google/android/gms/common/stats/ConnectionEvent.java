package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class ConnectionEvent extends zzf implements SafeParcelable {
    public static final Parcelable.Creator<ConnectionEvent> CREATOR = new zza();
    final int mVersionCode;
    private final long zzahn;
    private int zzaho;
    private final String zzahp;
    private final String zzahq;
    private final String zzahr;
    private final String zzahs;
    private final String zzaht;
    private final String zzahu;
    private final long zzahv;
    private final long zzahw;
    private long zzahx;

    ConnectionEvent(int versionCode, long timeMillis, int eventType, String callingProcess, String callingService, String targetProcess, String targetService, String stackTrace, String connKey, long elapsedRealtime, long heapAlloc) {
        this.mVersionCode = versionCode;
        this.zzahn = timeMillis;
        this.zzaho = eventType;
        this.zzahp = callingProcess;
        this.zzahq = callingService;
        this.zzahr = targetProcess;
        this.zzahs = targetService;
        this.zzahx = -1L;
        this.zzaht = stackTrace;
        this.zzahu = connKey;
        this.zzahv = elapsedRealtime;
        this.zzahw = heapAlloc;
    }

    public ConnectionEvent(long timeMillis, int eventType, String callingProcess, String callingService, String targetProcess, String targetService, String stackTrace, String connKey, long elapsedRealtime, long heapAlloc) {
        this(1, timeMillis, eventType, callingProcess, callingService, targetProcess, targetService, stackTrace, connKey, elapsedRealtime, heapAlloc);
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
        zza.zza(this, out, flags);
    }

    public String zzpX() {
        return this.zzahp;
    }

    public String zzpY() {
        return this.zzahq;
    }

    public String zzpZ() {
        return this.zzahr;
    }

    public String zzqa() {
        return this.zzahs;
    }

    public String zzqb() {
        return this.zzaht;
    }

    public String zzqc() {
        return this.zzahu;
    }

    @Override // com.google.android.gms.common.stats.zzf
    public long zzqd() {
        return this.zzahx;
    }

    public long zzqe() {
        return this.zzahw;
    }

    public long zzqf() {
        return this.zzahv;
    }

    @Override // com.google.android.gms.common.stats.zzf
    public String zzqg() {
        return "\t" + zzpX() + "/" + zzpY() + "\t" + zzpZ() + "/" + zzqa() + "\t" + (this.zzaht == null ? "" : this.zzaht) + "\t" + zzqe();
    }
}
