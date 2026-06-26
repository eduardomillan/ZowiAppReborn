package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzdi implements zzdk {
    private void zzb(zziz zzizVar, Map<String, String> map) {
        String str = map.get("label");
        String str2 = map.get("start_label");
        String str3 = map.get("timestamp");
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No label given for CSI tick.");
            return;
        }
        if (TextUtils.isEmpty(str3)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No timestamp given for CSI tick.");
            return;
        }
        try {
            long jZzc = zzc(Long.parseLong(str3));
            if (TextUtils.isEmpty(str2)) {
                str2 = "native:view_load";
            }
            zzizVar.zzhn().zza(str, str2, jZzc);
        } catch (NumberFormatException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Malformed timestamp for CSI tick.", e);
        }
    }

    private long zzc(long j) {
        return (j - com.google.android.gms.ads.internal.zzp.zzbz().currentTimeMillis()) + com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime();
    }

    private void zzc(zziz zzizVar, Map<String, String> map) {
        String str = map.get("value");
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No value given for CSI experiment.");
            return;
        }
        zzcg zzcgVarZzdm = zzizVar.zzhn().zzdm();
        if (zzcgVarZzdm == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No ticker for WebView, dropping experiment ID.");
        } else {
            zzcgVarZzdm.zze("e", str);
        }
    }

    private void zzd(zziz zzizVar, Map<String, String> map) {
        String str = map.get("name");
        String str2 = map.get("value");
        if (TextUtils.isEmpty(str2)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No value given for CSI extra.");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No name given for CSI extra.");
            return;
        }
        zzcg zzcgVarZzdm = zzizVar.zzhn().zzdm();
        if (zzcgVarZzdm == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("No ticker for WebView, dropping extra parameter.");
        } else {
            zzcgVarZzdm.zze(str, str2);
        }
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        String str = map.get("action");
        if ("tick".equals(str)) {
            zzb(zzizVar, map);
        } else if ("experiment".equals(str)) {
            zzc(zzizVar, map);
        } else if ("extra".equals(str)) {
            zzd(zzizVar, map);
        }
    }
}
