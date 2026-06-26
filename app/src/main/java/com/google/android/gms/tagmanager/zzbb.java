package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class zzbb extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.LANGUAGE.toString();

    public zzbb() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ String zzCT() {
        return super.zzCT();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ Set zzCU() {
        return super.zzCU();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        String language;
        Locale locale = Locale.getDefault();
        if (locale != null && (language = locale.getLanguage()) != null) {
            return zzdf.zzQ(language.toLowerCase());
        }
        return zzdf.zzDX();
    }
}
