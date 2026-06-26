package com.google.android.gms.internal;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzbu<T> {
    private final String zzue;
    private final T zzuf;

    private zzbu(String str, T t) {
        this.zzue = str;
        this.zzuf = t;
        com.google.android.gms.ads.internal.zzp.zzbD().zza(this);
    }

    public static zzbu<String> zzP(String str) {
        zzbu<String> zzbuVarZzc = zzc(str, (String) null);
        com.google.android.gms.ads.internal.zzp.zzbD().zzb(zzbuVarZzc);
        return zzbuVarZzc;
    }

    public static zzbu<String> zzQ(String str) {
        zzbu<String> zzbuVarZzc = zzc(str, (String) null);
        com.google.android.gms.ads.internal.zzp.zzbD().zzc(zzbuVarZzc);
        return zzbuVarZzc;
    }

    public static zzbu<Integer> zza(String str, int i) {
        return new zzbu<Integer>(str, Integer.valueOf(i)) { // from class: com.google.android.gms.internal.zzbu.2
            @Override // com.google.android.gms.internal.zzbu
            /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
            public Integer zza(SharedPreferences sharedPreferences) {
                return Integer.valueOf(sharedPreferences.getInt(getKey(), zzde().intValue()));
            }
        };
    }

    public static zzbu<Boolean> zza(String str, Boolean bool) {
        return new zzbu<Boolean>(str, bool) { // from class: com.google.android.gms.internal.zzbu.1
            @Override // com.google.android.gms.internal.zzbu
            /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
            public Boolean zza(SharedPreferences sharedPreferences) {
                return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), zzde().booleanValue()));
            }
        };
    }

    public static zzbu<Long> zzb(String str, long j) {
        return new zzbu<Long>(str, Long.valueOf(j)) { // from class: com.google.android.gms.internal.zzbu.3
            @Override // com.google.android.gms.internal.zzbu
            /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
            public Long zza(SharedPreferences sharedPreferences) {
                return Long.valueOf(sharedPreferences.getLong(getKey(), zzde().longValue()));
            }
        };
    }

    public static zzbu<String> zzc(String str, String str2) {
        return new zzbu<String>(str, str2) { // from class: com.google.android.gms.internal.zzbu.4
            @Override // com.google.android.gms.internal.zzbu
            /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
            public String zza(SharedPreferences sharedPreferences) {
                return sharedPreferences.getString(getKey(), zzde());
            }
        };
    }

    public T get() {
        return (T) com.google.android.gms.ads.internal.zzp.zzbE().zzd(this);
    }

    public String getKey() {
        return this.zzue;
    }

    protected abstract T zza(SharedPreferences sharedPreferences);

    public T zzde() {
        return this.zzuf;
    }
}
