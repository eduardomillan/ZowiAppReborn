package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.internal.zzag;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class zzj extends zzdd {
    private static final String ID = com.google.android.gms.internal.zzad.ARBITRARY_PIXEL.toString();
    private static final String URL = com.google.android.gms.internal.zzae.URL.toString();
    private static final String zzaVJ = com.google.android.gms.internal.zzae.ADDITIONAL_PARAMS.toString();
    private static final String zzaVK = com.google.android.gms.internal.zzae.UNREPEATABLE.toString();
    static final String zzaVL = "gtm_" + ID + "_unrepeatable";
    private static final Set<String> zzaVM = new HashSet();
    private final Context mContext;
    private final zza zzaVN;

    public interface zza {
        zzar zzCp();
    }

    public zzj(final Context context) {
        this(context, new zza() { // from class: com.google.android.gms.tagmanager.zzj.1
            @Override // com.google.android.gms.tagmanager.zzj.zza
            public zzar zzCp() {
                return zzz.zzaO(context);
            }
        });
    }

    zzj(Context context, zza zzaVar) {
        super(ID, URL);
        this.zzaVN = zzaVar;
        this.mContext = context;
    }

    private synchronized boolean zzew(String str) {
        boolean z = true;
        synchronized (this) {
            if (!zzey(str)) {
                if (zzex(str)) {
                    zzaVM.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    @Override // com.google.android.gms.tagmanager.zzdd
    public void zzK(Map<String, zzag.zza> map) {
        String strZzg = map.get(zzaVK) != null ? zzdf.zzg(map.get(zzaVK)) : null;
        if (strZzg == null || !zzew(strZzg)) {
            Uri.Builder builderBuildUpon = Uri.parse(zzdf.zzg(map.get(URL))).buildUpon();
            zzag.zza zzaVar = map.get(zzaVJ);
            if (zzaVar != null) {
                Object objZzl = zzdf.zzl(zzaVar);
                if (!(objZzl instanceof List)) {
                    zzbg.e("ArbitraryPixel: additional params not a list: not sending partial hit: " + builderBuildUpon.build().toString());
                    return;
                }
                for (Object obj : (List) objZzl) {
                    if (!(obj instanceof Map)) {
                        zzbg.e("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + builderBuildUpon.build().toString());
                        return;
                    }
                    for (Map.Entry entry : ((Map) obj).entrySet()) {
                        builderBuildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
            String string = builderBuildUpon.build().toString();
            this.zzaVN.zzCp().zzeN(string);
            zzbg.v("ArbitraryPixel: url = " + string);
            if (strZzg != null) {
                synchronized (zzj.class) {
                    zzaVM.add(strZzg);
                    zzcv.zzb(this.mContext, zzaVL, strZzg, "true");
                }
            }
        }
    }

    boolean zzex(String str) {
        return this.mContext.getSharedPreferences(zzaVL, 0).contains(str);
    }

    boolean zzey(String str) {
        return zzaVM.contains(str);
    }
}
