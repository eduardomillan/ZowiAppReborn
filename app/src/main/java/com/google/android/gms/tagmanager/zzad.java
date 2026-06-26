package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzad extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.ENCODE.toString();
    private static final String zzaWU = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaWV = com.google.android.gms.internal.zzae.NO_PADDING.toString();
    private static final String zzaWW = com.google.android.gms.internal.zzae.INPUT_FORMAT.toString();
    private static final String zzaWX = com.google.android.gms.internal.zzae.OUTPUT_FORMAT.toString();

    public zzad() {
        super(ID, zzaWU);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        byte[] bArrDecode;
        String strEncodeToString;
        zzag.zza zzaVar = map.get(zzaWU);
        if (zzaVar == null || zzaVar == zzdf.zzDX()) {
            return zzdf.zzDX();
        }
        String strZzg = zzdf.zzg(zzaVar);
        zzag.zza zzaVar2 = map.get(zzaWW);
        String strZzg2 = zzaVar2 == null ? "text" : zzdf.zzg(zzaVar2);
        zzag.zza zzaVar3 = map.get(zzaWX);
        String strZzg3 = zzaVar3 == null ? "base16" : zzdf.zzg(zzaVar3);
        zzag.zza zzaVar4 = map.get(zzaWV);
        int i = (zzaVar4 == null || !zzdf.zzk(zzaVar4).booleanValue()) ? 2 : 3;
        try {
            if ("text".equals(strZzg2)) {
                bArrDecode = strZzg.getBytes();
            } else if ("base16".equals(strZzg2)) {
                bArrDecode = zzk.zzez(strZzg);
            } else if ("base64".equals(strZzg2)) {
                bArrDecode = Base64.decode(strZzg, i);
            } else {
                if (!"base64url".equals(strZzg2)) {
                    zzbg.e("Encode: unknown input format: " + strZzg2);
                    return zzdf.zzDX();
                }
                bArrDecode = Base64.decode(strZzg, i | 8);
            }
            if ("base16".equals(strZzg3)) {
                strEncodeToString = zzk.zzi(bArrDecode);
            } else if ("base64".equals(strZzg3)) {
                strEncodeToString = Base64.encodeToString(bArrDecode, i);
            } else {
                if (!"base64url".equals(strZzg3)) {
                    zzbg.e("Encode: unknown output format: " + strZzg3);
                    return zzdf.zzDX();
                }
                strEncodeToString = Base64.encodeToString(bArrDecode, i | 8);
            }
            return zzdf.zzQ(strEncodeToString);
        } catch (IllegalArgumentException e) {
            zzbg.e("Encode: invalid input:");
            return zzdf.zzDX();
        }
    }
}
