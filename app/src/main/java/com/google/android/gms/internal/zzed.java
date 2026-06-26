package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzed {
    public final String zzyM;
    public final String zzyN;
    public final List<String> zzyO;
    public final String zzyP;
    public final String zzyQ;
    public final List<String> zzyR;
    public final List<String> zzyS;
    public final String zzyT;
    public final List<String> zzyU;
    public final List<String> zzyV;

    public zzed(JSONObject jSONObject) throws JSONException {
        this.zzyN = jSONObject.getString("id");
        JSONArray jSONArray = jSONObject.getJSONArray("adapters");
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        this.zzyO = Collections.unmodifiableList(arrayList);
        this.zzyP = jSONObject.optString("allocation_id", null);
        this.zzyR = com.google.android.gms.ads.internal.zzp.zzbH().zza(jSONObject, "clickurl");
        this.zzyS = com.google.android.gms.ads.internal.zzp.zzbH().zza(jSONObject, "imp_urls");
        this.zzyU = com.google.android.gms.ads.internal.zzp.zzbH().zza(jSONObject, "video_start_urls");
        this.zzyV = com.google.android.gms.ads.internal.zzp.zzbH().zza(jSONObject, "video_complete_urls");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("ad");
        this.zzyM = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.toString() : null;
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("data");
        this.zzyT = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.toString() : null;
        this.zzyQ = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optString("class_name") : null;
    }
}
