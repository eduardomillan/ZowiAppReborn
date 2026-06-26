package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzaj {
    private static void zza(DataLayer dataLayer, zzaf.zzd zzdVar) {
        for (zzag.zza zzaVar : zzdVar.zzia) {
            dataLayer.zzeI(zzdf.zzg(zzaVar));
        }
    }

    public static void zza(DataLayer dataLayer, zzaf.zzi zziVar) {
        if (zziVar.zziP == null) {
            zzbg.zzaH("supplemental missing experimentSupplemental");
            return;
        }
        zza(dataLayer, zziVar.zziP);
        zzb(dataLayer, zziVar.zziP);
        zzc(dataLayer, zziVar.zziP);
    }

    private static void zzb(DataLayer dataLayer, zzaf.zzd zzdVar) {
        for (zzag.zza zzaVar : zzdVar.zzhZ) {
            Map<String, Object> mapZzc = zzc(zzaVar);
            if (mapZzc != null) {
                dataLayer.push(mapZzc);
            }
        }
    }

    private static Map<String, Object> zzc(zzag.zza zzaVar) {
        Object objZzl = zzdf.zzl(zzaVar);
        if (objZzl instanceof Map) {
            return (Map) objZzl;
        }
        zzbg.zzaH("value: " + objZzl + " is not a map value, ignored.");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zzc(com.google.android.gms.tagmanager.DataLayer r14, com.google.android.gms.internal.zzaf.zzd r15) {
        /*
            r3 = 0
            com.google.android.gms.internal.zzaf$zzc[] r4 = r15.zzib
            int r5 = r4.length
            r2 = r3
        L5:
            if (r2 >= r5) goto Lb9
            r6 = r4[r2]
            java.lang.String r0 = r6.key
            if (r0 != 0) goto L16
            java.lang.String r0 = "GaExperimentRandom: No key"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)
        L12:
            int r0 = r2 + 1
            r2 = r0
            goto L5
        L16:
            java.lang.String r0 = r6.key
            java.lang.Object r1 = r14.get(r0)
            boolean r0 = r1 instanceof java.lang.Number
            if (r0 != 0) goto L88
            r0 = 0
        L21:
            long r8 = r6.zzhV
            long r10 = r6.zzhW
            boolean r7 = r6.zzhX
            if (r7 == 0) goto L3b
            if (r0 == 0) goto L3b
            long r12 = r0.longValue()
            int r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r7 < 0) goto L3b
            long r12 = r0.longValue()
            int r0 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r0 <= 0) goto L50
        L3b:
            int r0 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r0 > 0) goto L94
            double r0 = java.lang.Math.random()
            long r10 = r10 - r8
            double r10 = (double) r10
            double r0 = r0 * r10
            double r8 = (double) r8
            double r0 = r0 + r8
            long r0 = java.lang.Math.round(r0)
            java.lang.Long r1 = java.lang.Long.valueOf(r0)
        L50:
            java.lang.String r0 = r6.key
            r14.zzeI(r0)
            java.lang.String r0 = r6.key
            java.util.Map r1 = r14.zzk(r0, r1)
            long r8 = r6.zzhY
            r10 = 0
            int r0 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r0 <= 0) goto L84
            java.lang.String r0 = "gtm"
            boolean r0 = r1.containsKey(r0)
            if (r0 != 0) goto L9b
            java.lang.String r0 = "gtm"
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.String r8 = "lifetime"
            r7[r3] = r8
            r8 = 1
            long r10 = r6.zzhY
            java.lang.Long r6 = java.lang.Long.valueOf(r10)
            r7[r8] = r6
            java.util.Map r6 = com.google.android.gms.tagmanager.DataLayer.mapOf(r7)
            r1.put(r0, r6)
        L84:
            r14.push(r1)
            goto L12
        L88:
            r0 = r1
            java.lang.Number r0 = (java.lang.Number) r0
            long r8 = r0.longValue()
            java.lang.Long r0 = java.lang.Long.valueOf(r8)
            goto L21
        L94:
            java.lang.String r0 = "GaExperimentRandom: random range invalid"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)
            goto L12
        L9b:
            java.lang.String r0 = "gtm"
            java.lang.Object r0 = r1.get(r0)
            boolean r7 = r0 instanceof java.util.Map
            if (r7 == 0) goto Lb3
            java.util.Map r0 = (java.util.Map) r0
            java.lang.String r7 = "lifetime"
            long r8 = r6.zzhY
            java.lang.Long r6 = java.lang.Long.valueOf(r8)
            r0.put(r7, r6)
            goto L84
        Lb3:
            java.lang.String r0 = "GaExperimentRandom: gtm not a map"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)
            goto L84
        Lb9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzaj.zzc(com.google.android.gms.tagmanager.DataLayer, com.google.android.gms.internal.zzaf$zzd):void");
    }
}
