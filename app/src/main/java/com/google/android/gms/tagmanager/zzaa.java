package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzaa extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.DEVICE_ID.toString();
    private final Context mContext;

    public zzaa(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        String strZzaP = zzaP(this.mContext);
        return strZzaP == null ? zzdf.zzDX() : zzdf.zzQ(strZzaP);
    }

    protected String zzaP(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }
}
