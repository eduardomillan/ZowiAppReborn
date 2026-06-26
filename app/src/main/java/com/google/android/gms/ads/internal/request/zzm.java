package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.zza;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzbe;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzdk;
import com.google.android.gms.internal.zzdl;
import com.google.android.gms.internal.zzdp;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzgu;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzis;
import com.google.android.gms.internal.zziz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzm extends zzhz {
    private final Context mContext;
    private final Object zzDh;
    private final zza.InterfaceC0018zza zzEe;
    private final AdRequestInfoParcel.zza zzEf;
    private zzdz.zzd zzFo;
    static final long zzFi = TimeUnit.SECONDS.toMillis(10);
    private static final Object zzpy = new Object();
    private static boolean zzFj = false;
    private static zzdz zzFk = null;
    private static zzdl zzFl = null;
    private static zzdp zzFm = null;
    private static zzdk zzFn = null;

    public static class zza implements zzdz.zzb<zzbb> {
        @Override // com.google.android.gms.internal.zzdz.zzb
        /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
        public void zzc(zzbb zzbbVar) {
            zzm.zzd(zzbbVar);
        }
    }

    public static class zzb implements zzdz.zzb<zzbb> {
        @Override // com.google.android.gms.internal.zzdz.zzb
        /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
        public void zzc(zzbb zzbbVar) {
            zzm.zzc(zzbbVar);
        }
    }

    public static class zzc implements zzdk {
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            String str = map.get("request_id");
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Invalid request: " + map.get("errors"));
            zzm.zzFm.zzZ(str);
        }
    }

    public zzm(Context context, AdRequestInfoParcel.zza zzaVar, zza.InterfaceC0018zza interfaceC0018zza) {
        super(true);
        this.zzDh = new Object();
        this.zzEe = interfaceC0018zza;
        this.mContext = context;
        this.zzEf = zzaVar;
        synchronized (zzpy) {
            if (!zzFj) {
                zzFm = new zzdp();
                zzFl = new zzdl(context.getApplicationContext(), zzaVar.zzqj);
                zzFn = new zzc();
                zzFk = new zzdz(this.mContext.getApplicationContext(), this.zzEf.zzqj, zzby.zzul.get(), new zzb(), new zza());
                zzFj = true;
            }
        }
    }

    private JSONObject zza(AdRequestInfoParcel adRequestInfoParcel, String str) {
        JSONObject jSONObjectZza;
        AdvertisingIdClient.Info advertisingIdInfo;
        Bundle bundle = adRequestInfoParcel.zzEn.extras.getBundle("sdk_less_server_data");
        String string = adRequestInfoParcel.zzEn.extras.getString("sdk_less_network_id");
        if (bundle == null || (jSONObjectZza = zzgu.zza(this.mContext, adRequestInfoParcel, zzp.zzbB().zzC(this.mContext), null, null, new zzbr(zzby.zzul.get()), null, null, new ArrayList())) == null) {
            return null;
        }
        try {
            advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException | IllegalStateException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot get advertising id info", e);
            advertisingIdInfo = null;
        }
        HashMap map = new HashMap();
        map.put("request_id", str);
        map.put("network_id", string);
        map.put("request_param", jSONObjectZza);
        map.put("data", bundle);
        if (advertisingIdInfo != null) {
            map.put("adid", advertisingIdInfo.getId());
            map.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 1 : 0));
        }
        try {
            return zzp.zzbv().zzz(map);
        } catch (JSONException e2) {
            return null;
        }
    }

    protected static void zzc(zzbb zzbbVar) {
        zzbbVar.zza("/loadAd", zzFm);
        zzbbVar.zza("/fetchHttpRequest", zzFl);
        zzbbVar.zza("/invalidRequest", zzFn);
    }

    protected static void zzd(zzbb zzbbVar) {
        zzbbVar.zzb("/loadAd", zzFm);
        zzbbVar.zzb("/fetchHttpRequest", zzFl);
        zzbbVar.zzb("/invalidRequest", zzFn);
    }

    private AdResponseParcel zzf(AdRequestInfoParcel adRequestInfoParcel) {
        final String string = UUID.randomUUID().toString();
        final JSONObject jSONObjectZza = zza(adRequestInfoParcel, string);
        if (jSONObjectZza == null) {
            return new AdResponseParcel(0);
        }
        long jElapsedRealtime = zzp.zzbz().elapsedRealtime();
        Future<JSONObject> futureZzY = zzFm.zzY(string);
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzm.2
            @Override // java.lang.Runnable
            public void run() {
                zzm.this.zzFo = zzm.zzFk.zzdO();
                zzm.this.zzFo.zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.ads.internal.request.zzm.2.1
                    @Override // com.google.android.gms.internal.zzis.zzc
                    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbe zzbeVar) {
                        try {
                            zzbeVar.zza("AFMA_getAdapterLessMediationAd", jSONObjectZza);
                        } catch (Exception e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error requesting an ad url", e);
                            zzm.zzFm.zzZ(string);
                        }
                    }
                }, new zzis.zza() { // from class: com.google.android.gms.ads.internal.request.zzm.2.2
                    @Override // com.google.android.gms.internal.zzis.zza
                    public void run() {
                        zzm.zzFm.zzZ(string);
                    }
                });
            }
        });
        try {
            JSONObject jSONObject = futureZzY.get(zzFi - (zzp.zzbz().elapsedRealtime() - jElapsedRealtime), TimeUnit.MILLISECONDS);
            if (jSONObject == null) {
                return new AdResponseParcel(-1);
            }
            AdResponseParcel adResponseParcelZza = zzgu.zza(this.mContext, adRequestInfoParcel, jSONObject.toString());
            return (adResponseParcelZza.errorCode == -3 || !TextUtils.isEmpty(adResponseParcelZza.body)) ? adResponseParcelZza : new AdResponseParcel(3);
        } catch (InterruptedException e) {
            return new AdResponseParcel(-1);
        } catch (CancellationException e2) {
            return new AdResponseParcel(-1);
        } catch (ExecutionException e3) {
            return new AdResponseParcel(0);
        } catch (TimeoutException e4) {
            return new AdResponseParcel(2);
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
        synchronized (this.zzDh) {
            com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzm.3
                @Override // java.lang.Runnable
                public void run() {
                    if (zzm.this.zzFo != null) {
                        zzm.this.zzFo.release();
                        zzm.this.zzFo = null;
                    }
                }
            });
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("SdkLessAdLoaderBackgroundTask started.");
        AdRequestInfoParcel adRequestInfoParcel = new AdRequestInfoParcel(this.zzEf, null, -1L);
        AdResponseParcel adResponseParcelZzf = zzf(adRequestInfoParcel);
        final zzhs.zza zzaVar = new zzhs.zza(adRequestInfoParcel, adResponseParcelZzf, null, null, adResponseParcelZzf.errorCode, zzp.zzbz().elapsedRealtime(), adResponseParcelZzf.zzEO, null);
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzm.1
            @Override // java.lang.Runnable
            public void run() {
                zzm.this.zzEe.zza(zzaVar);
                if (zzm.this.zzFo != null) {
                    zzm.this.zzFo.release();
                    zzm.this.zzFo = null;
                }
            }
        });
    }
}
