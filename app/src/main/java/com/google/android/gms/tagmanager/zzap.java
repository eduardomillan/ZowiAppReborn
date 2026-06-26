package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzap extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.HASH.toString();
    private static final String zzaWU = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaXa = com.google.android.gms.internal.zzae.ALGORITHM.toString();
    private static final String zzaWW = com.google.android.gms.internal.zzae.INPUT_FORMAT.toString();

    public zzap() {
        super(ID, zzaWU);
    }

    private byte[] zzd(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        byte[] bArrZzez;
        zzag.zza zzaVar = map.get(zzaWU);
        if (zzaVar == null || zzaVar == zzdf.zzDX()) {
            return zzdf.zzDX();
        }
        String strZzg = zzdf.zzg(zzaVar);
        zzag.zza zzaVar2 = map.get(zzaXa);
        String strZzg2 = zzaVar2 == null ? "MD5" : zzdf.zzg(zzaVar2);
        zzag.zza zzaVar3 = map.get(zzaWW);
        String strZzg3 = zzaVar3 == null ? "text" : zzdf.zzg(zzaVar3);
        if ("text".equals(strZzg3)) {
            bArrZzez = strZzg.getBytes();
        } else {
            if (!"base16".equals(strZzg3)) {
                zzbg.e("Hash: unknown input format: " + strZzg3);
                return zzdf.zzDX();
            }
            bArrZzez = zzk.zzez(strZzg);
        }
        try {
            return zzdf.zzQ(zzk.zzi(zzd(strZzg2, bArrZzez)));
        } catch (NoSuchAlgorithmException e) {
            zzbg.e("Hash: unknown algorithm: " + strZzg2);
            return zzdf.zzDX();
        }
    }
}
