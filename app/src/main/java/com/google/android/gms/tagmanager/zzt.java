package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzt extends zzak {
    private final zza zzaWu;
    private static final String ID = com.google.android.gms.internal.zzad.FUNCTION_CALL.toString();
    private static final String zzaWt = com.google.android.gms.internal.zzae.FUNCTION_CALL_NAME.toString();
    private static final String zzaVJ = com.google.android.gms.internal.zzae.ADDITIONAL_PARAMS.toString();

    public interface zza {
        Object zzc(String str, Map<String, Object> map);
    }

    public zzt(zza zzaVar) {
        super(ID, zzaWt);
        this.zzaWu = zzaVar;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        String strZzg = zzdf.zzg(map.get(zzaWt));
        HashMap map2 = new HashMap();
        zzag.zza zzaVar = map.get(zzaVJ);
        if (zzaVar != null) {
            Object objZzl = zzdf.zzl(zzaVar);
            if (!(objZzl instanceof Map)) {
                zzbg.zzaH("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return zzdf.zzDX();
            }
            for (Map.Entry entry : ((Map) objZzl).entrySet()) {
                map2.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            return zzdf.zzQ(this.zzaWu.zzc(strZzg, map2));
        } catch (Exception e) {
            zzbg.zzaH("Custom macro/tag " + strZzg + " threw exception " + e.getMessage());
            return zzdf.zzDX();
        }
    }
}
