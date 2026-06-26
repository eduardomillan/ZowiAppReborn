package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.request.zzj;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzis;
import com.google.android.gms.internal.zzja;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzgt extends zzj.zza {
    private static zzgt zzFA;
    private static final Object zzpy = new Object();
    private final Context mContext;
    private final zzgs zzFB;
    private final zzbr zzFC;
    private final zzdz zzrf;

    zzgt(Context context, zzbr zzbrVar, zzgs zzgsVar) {
        this.mContext = context;
        this.zzFB = zzgsVar;
        this.zzFC = zzbrVar;
        this.zzrf = new zzdz(context.getApplicationContext() != null ? context.getApplicationContext() : context, new VersionInfoParcel(8115000, 8115000, true), zzbrVar.zzdc(), new zzdz.zzb<zzbb>() { // from class: com.google.android.gms.internal.zzgt.5
            @Override // com.google.android.gms.internal.zzdz.zzb
            /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
            public void zzc(zzbb zzbbVar) {
                zzbbVar.zza("/log", zzdj.zzxv);
            }
        }, new zzdz.zzc());
    }

    private static AdResponseParcel zza(final Context context, final zzdz zzdzVar, final zzbr zzbrVar, zzgs zzgsVar, final AdRequestInfoParcel adRequestInfoParcel) {
        String string;
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Starting ad request from service.");
        zzby.initialize(context);
        final zzcg zzcgVar = new zzcg(zzby.zzuQ.get().booleanValue(), "load_ad", adRequestInfoParcel.zzqn.zzte);
        if (adRequestInfoParcel.versionCode > 10 && adRequestInfoParcel.zzEF != -1) {
            zzcgVar.zza(zzcgVar.zzb(adRequestInfoParcel.zzEF), "cts");
        }
        zzce zzceVarZzdn = zzcgVar.zzdn();
        zzgsVar.zzFv.init();
        zzgy zzgyVarZzC = com.google.android.gms.ads.internal.zzp.zzbB().zzC(context);
        if (zzgyVarZzC.zzGE == -1) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Device is offline.");
            return new AdResponseParcel(2);
        }
        String string2 = adRequestInfoParcel.versionCode >= 7 ? adRequestInfoParcel.zzEC : UUID.randomUUID().toString();
        final zzgv zzgvVar = new zzgv(string2, adRequestInfoParcel.applicationInfo.packageName);
        if (adRequestInfoParcel.zzEn.extras != null && (string = adRequestInfoParcel.zzEn.extras.getString("_ad")) != null) {
            return zzgu.zza(context, adRequestInfoParcel, string);
        }
        Location locationZzd = zzgsVar.zzFv.zzd(250L);
        String strZzc = zzgsVar.zzFw.zzc(context, adRequestInfoParcel.zzEo.packageName);
        JSONObject jSONObjectZza = zzgu.zza(context, adRequestInfoParcel, zzgyVarZzC, zzgsVar.zzFy.zzD(context), locationZzd, zzbrVar, strZzc, zzgsVar.zzFx.zzax(adRequestInfoParcel.zzEp), zzgsVar.zzFu.zza(adRequestInfoParcel));
        if (adRequestInfoParcel.versionCode < 7) {
            try {
                jSONObjectZza.put("request_id", string2);
            } catch (JSONException e) {
            }
        }
        if (jSONObjectZza == null) {
            return new AdResponseParcel(0);
        }
        final String string3 = jSONObjectZza.toString();
        zzcgVar.zza(zzceVarZzdn, "arc");
        final zzce zzceVarZzdn2 = zzcgVar.zzdn();
        if (zzby.zzum.get().booleanValue()) {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgt.1
                @Override // java.lang.Runnable
                public void run() {
                    zzdz.zzd zzdVarZzdO = zzdzVar.zzdO();
                    zzgvVar.zzb(zzdVarZzdO);
                    zzcgVar.zza(zzceVarZzdn2, "rwc");
                    final zzce zzceVarZzdn3 = zzcgVar.zzdn();
                    zzdVarZzdO.zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzgt.1.1
                        @Override // com.google.android.gms.internal.zzis.zzc
                        /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
                        public void zzc(zzbe zzbeVar) {
                            zzcgVar.zza(zzceVarZzdn3, "jsf");
                            zzcgVar.zzdo();
                            zzbeVar.zza("/invalidRequest", zzgvVar.zzFR);
                            zzbeVar.zza("/loadAdURL", zzgvVar.zzFS);
                            try {
                                zzbeVar.zza("AFMA_buildAdURL", string3);
                            } catch (Exception e2) {
                                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error requesting an ad url", e2);
                            }
                        }
                    }, new zzis.zza() { // from class: com.google.android.gms.internal.zzgt.1.2
                        @Override // com.google.android.gms.internal.zzis.zza
                        public void run() {
                        }
                    });
                }
            });
        } else {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgt.2
                @Override // java.lang.Runnable
                public void run() {
                    zziz zzizVarZza = com.google.android.gms.ads.internal.zzp.zzbw().zza(context, new AdSizeParcel(), false, false, null, adRequestInfoParcel.zzqj);
                    if (com.google.android.gms.ads.internal.zzp.zzby().zzgu()) {
                        zzizVarZza.clearCache(true);
                    }
                    zzizVarZza.getWebView().setWillNotDraw(true);
                    zzgvVar.zze(zzizVarZza);
                    zzcgVar.zza(zzceVarZzdn2, "rwc");
                    zzja.zza zzaVarZza = zzgt.zza(string3, zzcgVar, zzcgVar.zzdn());
                    zzja zzjaVarZzhe = zzizVarZza.zzhe();
                    zzjaVarZzhe.zza("/invalidRequest", zzgvVar.zzFR);
                    zzjaVarZzhe.zza("/loadAdURL", zzgvVar.zzFS);
                    zzjaVarZzhe.zza("/log", zzdj.zzxv);
                    zzjaVarZzhe.zza(zzaVarZza);
                    com.google.android.gms.ads.internal.util.client.zzb.zzaF("Loading the JS library.");
                    zzizVarZza.loadUrl(zzbrVar.zzdc());
                }
            });
        }
        try {
            zzgx zzgxVar = zzgvVar.zzfS().get(10L, TimeUnit.SECONDS);
            if (zzgxVar == null) {
                return new AdResponseParcel(0);
            }
            if (zzgxVar.getErrorCode() != -2) {
                return new AdResponseParcel(zzgxVar.getErrorCode());
            }
            if (zzcgVar.zzdq() != null) {
                zzcgVar.zza(zzcgVar.zzdq(), "rur");
            }
            AdResponseParcel adResponseParcelZza = zza(adRequestInfoParcel, context, adRequestInfoParcel.zzqj.zzJu, zzgxVar.getUrl(), zzgxVar.zzfW() ? zzgsVar.zzFt.zzaw(adRequestInfoParcel.zzEo.packageName) : null, strZzc, zzgxVar, zzcgVar, zzgsVar);
            if (adResponseParcelZza.zzEW == 1) {
                zzgsVar.zzFw.clearToken(context, adRequestInfoParcel.zzEo.packageName);
            }
            zzcgVar.zza(zzceVarZzdn, "tts");
            adResponseParcelZza.zzEY = zzcgVar.zzdp();
            return adResponseParcelZza;
        } catch (Exception e2) {
            return new AdResponseParcel(0);
        } finally {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgt.3
                @Override // java.lang.Runnable
                public void run() {
                    zzgvVar.zzfT();
                    if (zzgvVar.zzfR() != null) {
                        zzgvVar.zzfR().release();
                    }
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x018f, code lost:
    
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Received error HTTP response code: " + r9);
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01ab, code lost:
    
        r2.disconnect();
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01ae, code lost:
    
        if (r21 == null) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01b0, code lost:
    
        r21.zzFz.zzfZ();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.ads.internal.request.AdResponseParcel zza(com.google.android.gms.ads.internal.request.AdRequestInfoParcel r13, android.content.Context r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, com.google.android.gms.internal.zzgx r19, com.google.android.gms.internal.zzcg r20, com.google.android.gms.internal.zzgs r21) {
        /*
            Method dump skipped, instruction units count: 467
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgt.zza(com.google.android.gms.ads.internal.request.AdRequestInfoParcel, android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.google.android.gms.internal.zzgx, com.google.android.gms.internal.zzcg, com.google.android.gms.internal.zzgs):com.google.android.gms.ads.internal.request.AdResponseParcel");
    }

    public static zzgt zza(Context context, zzbr zzbrVar, zzgs zzgsVar) {
        zzgt zzgtVar;
        synchronized (zzpy) {
            if (zzFA == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zzFA = new zzgt(context, zzbrVar, zzgsVar);
            }
            zzgtVar = zzFA;
        }
        return zzgtVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzja.zza zza(final String str, final zzcg zzcgVar, final zzce zzceVar) {
        return new zzja.zza() { // from class: com.google.android.gms.internal.zzgt.4
            @Override // com.google.android.gms.internal.zzja.zza
            public void zza(zziz zzizVar, boolean z) {
                zzcgVar.zza(zzceVar, "jsf");
                zzcgVar.zzdo();
                zzizVar.zza("AFMA_buildAdURL", str);
            }
        };
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (com.google.android.gms.ads.internal.util.client.zzb.zzN(2)) {
            com.google.android.gms.ads.internal.util.client.zzb.v("Http Response: {\n  URL:\n    " + str + "\n  Headers:");
            if (map != null) {
                for (String str3 : map.keySet()) {
                    com.google.android.gms.ads.internal.util.client.zzb.v("    " + str3 + ":");
                    Iterator<String> it = map.get(str3).iterator();
                    while (it.hasNext()) {
                        com.google.android.gms.ads.internal.util.client.zzb.v("      " + it.next());
                    }
                }
            }
            com.google.android.gms.ads.internal.util.client.zzb.v("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += 1000) {
                    com.google.android.gms.ads.internal.util.client.zzb.v(str2.substring(i2, Math.min(str2.length(), i2 + 1000)));
                }
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.v("    null");
            }
            com.google.android.gms.ads.internal.util.client.zzb.v("  Response Code:\n    " + i + "\n}");
        }
    }

    @Override // com.google.android.gms.ads.internal.request.zzj
    public void zza(final AdRequestInfoParcel adRequestInfoParcel, final com.google.android.gms.ads.internal.request.zzk zzkVar) {
        com.google.android.gms.ads.internal.zzp.zzby().zzb(this.mContext, adRequestInfoParcel.zzqj);
        zzid.zzb(new Runnable() { // from class: com.google.android.gms.internal.zzgt.6
            @Override // java.lang.Runnable
            public void run() {
                AdResponseParcel adResponseParcel;
                try {
                    adResponseParcel = zzgt.this.zze(adRequestInfoParcel);
                } catch (Exception e) {
                    com.google.android.gms.ads.internal.zzp.zzby().zzc(e, true);
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not fetch ad response due to an Exception.", e);
                    adResponseParcel = null;
                }
                if (adResponseParcel == null) {
                    adResponseParcel = new AdResponseParcel(0);
                }
                try {
                    zzkVar.zzb(adResponseParcel);
                } catch (RemoteException e2) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to forward ad response.", e2);
                }
            }
        });
    }

    @Override // com.google.android.gms.ads.internal.request.zzj
    public AdResponseParcel zze(AdRequestInfoParcel adRequestInfoParcel) {
        return zza(this.mContext, this.zzrf, this.zzFC, this.zzFB, adRequestInfoParcel);
    }
}
