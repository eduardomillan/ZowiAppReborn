package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzd implements DataLayer.zzb {
    private final Context context;

    public zzd(Context context) {
        this.context = context;
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.zzb
    public void zzJ(Map<String, Object> map) {
        String queryParameter;
        Object obj;
        Object obj2 = map.get("gtm.url");
        Object obj3 = (obj2 == null && (obj = map.get("gtm")) != null && (obj instanceof Map)) ? ((Map) obj).get("url") : obj2;
        if (obj3 == null || !(obj3 instanceof String) || (queryParameter = Uri.parse((String) obj3).getQueryParameter("referrer")) == null) {
            return;
        }
        zzax.zzo(this.context, queryParameter);
    }
}
