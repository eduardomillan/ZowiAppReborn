package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzja;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzbd implements zzbb {
    private final zziz zzoM;

    public zzbd(Context context, VersionInfoParcel versionInfoParcel, zzan zzanVar) {
        this.zzoM = com.google.android.gms.ads.internal.zzp.zzbw().zza(context, new AdSizeParcel(), false, false, zzanVar, versionInfoParcel);
        this.zzoM.getWebView().setWillNotDraw(true);
    }

    private void runOnUiThread(Runnable runnable) {
        if (com.google.android.gms.ads.internal.client.zzl.zzcF().zzgT()) {
            runnable.run();
        } else {
            zzid.zzIE.post(runnable);
        }
    }

    @Override // com.google.android.gms.internal.zzbb
    public void destroy() {
        this.zzoM.destroy();
    }

    @Override // com.google.android.gms.internal.zzbb
    public void zza(com.google.android.gms.ads.internal.client.zza zzaVar, com.google.android.gms.ads.internal.overlay.zzg zzgVar, zzdg zzdgVar, com.google.android.gms.ads.internal.overlay.zzn zznVar, boolean z, zzdm zzdmVar, zzdo zzdoVar, com.google.android.gms.ads.internal.zze zzeVar, zzfi zzfiVar) {
        this.zzoM.zzhe().zzb(zzaVar, zzgVar, zzdgVar, zznVar, z, zzdmVar, zzdoVar, new com.google.android.gms.ads.internal.zze(false), zzfiVar);
    }

    @Override // com.google.android.gms.internal.zzbb
    public void zza(final zzbb.zza zzaVar) {
        this.zzoM.zzhe().zza(new zzja.zza() { // from class: com.google.android.gms.internal.zzbd.6
            @Override // com.google.android.gms.internal.zzja.zza
            public void zza(zziz zzizVar, boolean z) {
                zzaVar.zzcj();
            }
        });
    }

    @Override // com.google.android.gms.internal.zzbe
    public void zza(String str, zzdk zzdkVar) {
        this.zzoM.zzhe().zza(str, zzdkVar);
    }

    @Override // com.google.android.gms.internal.zzbe
    public void zza(final String str, final String str2) {
        runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzbd.2
            @Override // java.lang.Runnable
            public void run() {
                zzbd.this.zzoM.zza(str, str2);
            }
        });
    }

    @Override // com.google.android.gms.internal.zzbe
    public void zza(final String str, final JSONObject jSONObject) {
        runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzbd.1
            @Override // java.lang.Runnable
            public void run() {
                zzbd.this.zzoM.zza(str, jSONObject);
            }
        });
    }

    @Override // com.google.android.gms.internal.zzbe
    public void zzb(String str, zzdk zzdkVar) {
        this.zzoM.zzhe().zzb(str, zzdkVar);
    }

    @Override // com.google.android.gms.internal.zzbe
    public void zzb(String str, JSONObject jSONObject) {
        this.zzoM.zzb(str, jSONObject);
    }

    @Override // com.google.android.gms.internal.zzbb
    public zzbf zzci() {
        return new zzbg(this);
    }

    @Override // com.google.android.gms.internal.zzbb
    public void zzs(String str) {
        final String str2 = String.format("<!DOCTYPE html><html><head><script src=\"%s\"></script></head><body></body></html>", str);
        runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzbd.3
            @Override // java.lang.Runnable
            public void run() {
                zzbd.this.zzoM.loadData(str2, "text/html", "UTF-8");
            }
        });
    }

    @Override // com.google.android.gms.internal.zzbb
    public void zzt(final String str) {
        runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzbd.5
            @Override // java.lang.Runnable
            public void run() {
                zzbd.this.zzoM.loadUrl(str);
            }
        });
    }

    @Override // com.google.android.gms.internal.zzbb
    public void zzu(final String str) {
        runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzbd.4
            @Override // java.lang.Runnable
            public void run() {
                zzbd.this.zzoM.loadData(str, "text/html", "UTF-8");
            }
        });
    }
}
