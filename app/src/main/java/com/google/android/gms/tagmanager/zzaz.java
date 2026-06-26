package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzrb;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class zzaz {
    private static zzag.zza zzJ(Object obj) throws JSONException {
        return zzdf.zzQ(zzK(obj));
    }

    static Object zzK(Object obj) throws JSONException {
        if (obj instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        }
        if (JSONObject.NULL.equals(obj)) {
            throw new RuntimeException("JSON nulls are not supported");
        }
        if (!(obj instanceof JSONObject)) {
            return obj;
        }
        JSONObject jSONObject = (JSONObject) obj;
        HashMap map = new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, zzK(jSONObject.get(next)));
        }
        return map;
    }

    public static zzrb.zzc zzeT(String str) throws JSONException {
        zzag.zza zzaVarZzJ = zzJ(new JSONObject(str));
        zzrb.zzd zzdVarZzEc = zzrb.zzc.zzEc();
        for (int i = 0; i < zzaVarZzJ.zziW.length; i++) {
            zzdVarZzEc.zzc(zzrb.zza.zzDZ().zzb(com.google.android.gms.internal.zzae.INSTANCE_NAME.toString(), zzaVarZzJ.zziW[i]).zzb(com.google.android.gms.internal.zzae.FUNCTION.toString(), zzdf.zzfe(zzn.zzCr())).zzb(zzn.zzCs(), zzaVarZzJ.zziX[i]).zzEb());
        }
        return zzdVarZzEc.zzEf();
    }
}
