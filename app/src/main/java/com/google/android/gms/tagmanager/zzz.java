package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
public class zzz implements zzar {
    private static final Object zzaVD = new Object();
    private static zzz zzaWQ;
    private String zzaWR;
    private String zzaWS;
    private zzas zzaWT;
    private zzcd zzaWg;

    private zzz(Context context) {
        this(zzat.zzaQ(context), new zzcs());
    }

    zzz(zzas zzasVar, zzcd zzcdVar) {
        this.zzaWT = zzasVar;
        this.zzaWg = zzcdVar;
    }

    public static zzar zzaO(Context context) {
        zzz zzzVar;
        synchronized (zzaVD) {
            if (zzaWQ == null) {
                zzaWQ = new zzz(context);
            }
            zzzVar = zzaWQ;
        }
        return zzzVar;
    }

    @Override // com.google.android.gms.tagmanager.zzar
    public boolean zzeN(String str) {
        if (!this.zzaWg.zzkF()) {
            zzbg.zzaH("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        if (this.zzaWR != null && this.zzaWS != null) {
            try {
                str = this.zzaWR + "?" + this.zzaWS + "=" + URLEncoder.encode(str, "UTF-8");
                zzbg.v("Sending wrapped url hit: " + str);
            } catch (UnsupportedEncodingException e) {
                zzbg.zzd("Error wrapping URL for testing.", e);
                return false;
            }
        }
        this.zzaWT.zzeR(str);
        return true;
    }
}
