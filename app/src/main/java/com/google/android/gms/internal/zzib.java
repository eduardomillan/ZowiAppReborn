package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzib {

    private static abstract class zza extends zzhz {
        private zza() {
        }

        @Override // com.google.android.gms.internal.zzhz
        public void onStop() {
        }
    }

    public interface zzb {
        void zzd(Bundle bundle);
    }

    public static Future zza(final Context context, final int i) {
        return new zza() { // from class: com.google.android.gms.internal.zzib.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.android.gms.internal.zzhz
            public void zzbn() {
                SharedPreferences.Editor editorEdit = zzib.zzv(context).edit();
                editorEdit.putInt("webview_cache_version", i);
                editorEdit.apply();
            }
        }.zzfu();
    }

    public static Future zza(final Context context, final zzb zzbVar) {
        return new zza() { // from class: com.google.android.gms.internal.zzib.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.android.gms.internal.zzhz
            public void zzbn() {
                SharedPreferences sharedPreferencesZzv = zzib.zzv(context);
                Bundle bundle = new Bundle();
                bundle.putBoolean("use_https", sharedPreferencesZzv.getBoolean("use_https", true));
                if (zzbVar != null) {
                    zzbVar.zzd(bundle);
                }
            }
        }.zzfu();
    }

    public static Future zza(final Context context, final boolean z) {
        return new zza() { // from class: com.google.android.gms.internal.zzib.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.android.gms.internal.zzhz
            public void zzbn() {
                SharedPreferences.Editor editorEdit = zzib.zzv(context).edit();
                editorEdit.putBoolean("use_https", z);
                editorEdit.apply();
            }
        }.zzfu();
    }

    public static Future zzb(final Context context, final zzb zzbVar) {
        return new zza() { // from class: com.google.android.gms.internal.zzib.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.android.gms.internal.zzhz
            public void zzbn() {
                SharedPreferences sharedPreferencesZzv = zzib.zzv(context);
                Bundle bundle = new Bundle();
                bundle.putInt("webview_cache_version", sharedPreferencesZzv.getInt("webview_cache_version", 0));
                if (zzbVar != null) {
                    zzbVar.zzd(bundle);
                }
            }
        }.zzfu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SharedPreferences zzv(Context context) {
        return context.getSharedPreferences("admob", 0);
    }
}
