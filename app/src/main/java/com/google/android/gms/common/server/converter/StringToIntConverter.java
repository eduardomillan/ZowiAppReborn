package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class StringToIntConverter implements SafeParcelable, FastJsonResponse.zza<String, Integer> {
    public static final zzb CREATOR = new zzb();
    private final int mVersionCode;
    private final HashMap<String, Integer> zzagP;
    private final HashMap<Integer, String> zzagQ;
    private final ArrayList<Entry> zzagR;

    public static final class Entry implements SafeParcelable {
        public static final zzc CREATOR = new zzc();
        final int versionCode;
        final String zzagS;
        final int zzagT;

        Entry(int versionCode, String stringValue, int intValue) {
            this.versionCode = versionCode;
            this.zzagS = stringValue;
            this.zzagT = intValue;
        }

        Entry(String stringValue, int intValue) {
            this.versionCode = 1;
            this.zzagS = stringValue;
            this.zzagT = intValue;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            zzc zzcVar = CREATOR;
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            zzc zzcVar = CREATOR;
            zzc.zza(this, out, flags);
        }
    }

    public StringToIntConverter() {
        this.mVersionCode = 1;
        this.zzagP = new HashMap<>();
        this.zzagQ = new HashMap<>();
        this.zzagR = null;
    }

    StringToIntConverter(int versionCode, ArrayList<Entry> serializedMap) {
        this.mVersionCode = versionCode;
        this.zzagP = new HashMap<>();
        this.zzagQ = new HashMap<>();
        this.zzagR = null;
        zzb(serializedMap);
    }

    private void zzb(ArrayList<Entry> arrayList) {
        for (Entry entry : arrayList) {
            zzi(entry.zzagS, entry.zzagT);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        zzb zzbVar = CREATOR;
        return 0;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzb zzbVar = CREATOR;
        zzb.zza(this, out, flags);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.zza
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public String convertBack(Integer num) {
        String str = this.zzagQ.get(num);
        return (str == null && this.zzagP.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public StringToIntConverter zzi(String str, int i) {
        this.zzagP.put(str, Integer.valueOf(i));
        this.zzagQ.put(Integer.valueOf(i), str);
        return this;
    }

    ArrayList<Entry> zzpA() {
        ArrayList<Entry> arrayList = new ArrayList<>();
        for (String str : this.zzagP.keySet()) {
            arrayList.add(new Entry(str, this.zzagP.get(str).intValue()));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.zza
    public int zzpB() {
        return 7;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.zza
    public int zzpC() {
        return 0;
    }
}
