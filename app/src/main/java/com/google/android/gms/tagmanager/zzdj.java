package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
class zzdj {
    private static zzbw<zzag.zza> zza(zzbw<zzag.zza> zzbwVar) {
        try {
            return new zzbw<>(zzdf.zzQ(zzfl(zzdf.zzg(zzbwVar.getObject()))), zzbwVar.zzDh());
        } catch (UnsupportedEncodingException e) {
            zzbg.zzb("Escape URI: unsupported encoding", e);
            return zzbwVar;
        }
    }

    private static zzbw<zzag.zza> zza(zzbw<zzag.zza> zzbwVar, int i) {
        if (!zzn(zzbwVar.getObject())) {
            zzbg.e("Escaping can only be applied to strings.");
            return zzbwVar;
        }
        switch (i) {
            case 12:
                break;
            default:
                zzbg.e("Unsupported Value Escaping: " + i);
                break;
        }
        return zzbwVar;
    }

    static zzbw<zzag.zza> zza(zzbw<zzag.zza> zzbwVar, int... iArr) {
        for (int i : iArr) {
            zzbwVar = zza(zzbwVar, i);
        }
        return zzbwVar;
    }

    static String zzfl(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }

    private static boolean zzn(zzag.zza zzaVar) {
        return zzdf.zzl(zzaVar) instanceof String;
    }
}
