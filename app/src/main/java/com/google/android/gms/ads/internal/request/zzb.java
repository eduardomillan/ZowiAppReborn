package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.zza;
import com.google.android.gms.ads.internal.request.zzc;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzan;
import com.google.android.gms.internal.zzbk;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzid;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzb extends zzhz implements zzc.zza {
    private final Context mContext;
    AdResponseParcel zzDf;
    private Runnable zzDg;
    private final Object zzDh = new Object();
    private final zza.InterfaceC0018zza zzEe;
    private final AdRequestInfoParcel.zza zzEf;
    zzhz zzEg;
    private final zzan zzwL;
    zzee zzzA;
    private AdRequestInfoParcel zzzz;

    @zzgr
    static final class zza extends Exception {
        private final int zzDv;

        public zza(String str, int i) {
            super(str);
            this.zzDv = i;
        }

        public int getErrorCode() {
            return this.zzDv;
        }
    }

    public zzb(Context context, AdRequestInfoParcel.zza zzaVar, zzan zzanVar, zza.InterfaceC0018zza interfaceC0018zza) {
        this.zzEe = interfaceC0018zza;
        this.mContext = context;
        this.zzEf = zzaVar;
        this.zzwL = zzanVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzc(int i, String str) {
        if (i == 3 || i == -1) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG(str);
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH(str);
        }
        if (this.zzDf == null) {
            this.zzDf = new AdResponseParcel(i);
        } else {
            this.zzDf = new AdResponseParcel(i, this.zzDf.zzzc);
        }
        this.zzEe.zza(new zzhs.zza(this.zzzz, this.zzDf, this.zzzA, null, i, -1L, this.zzDf.zzEO, null));
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
        synchronized (this.zzDh) {
            if (this.zzEg != null) {
                this.zzEg.cancel();
            }
        }
    }

    zzhz zzb(AdRequestInfoParcel adRequestInfoParcel) {
        return zzc.zza(this.mContext, adRequestInfoParcel, this);
    }

    @Override // com.google.android.gms.ads.internal.request.zzc.zza
    public void zzb(AdResponseParcel adResponseParcel) {
        JSONObject jSONObject;
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Received ad response.");
        this.zzDf = adResponseParcel;
        long jElapsedRealtime = zzp.zzbz().elapsedRealtime();
        synchronized (this.zzDh) {
            this.zzEg = null;
        }
        try {
            if (this.zzDf.errorCode != -2 && this.zzDf.errorCode != -3) {
                throw new zza("There was a problem getting an ad response. ErrorCode: " + this.zzDf.errorCode, this.zzDf.errorCode);
            }
            zzfG();
            AdSizeParcel adSizeParcelZzc = this.zzzz.zzqn.zztg != null ? zzc(this.zzzz) : null;
            zzw(this.zzDf.zzEU);
            if (TextUtils.isEmpty(this.zzDf.zzES)) {
                jSONObject = null;
            } else {
                try {
                    jSONObject = new JSONObject(this.zzDf.zzES);
                } catch (Exception e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Error parsing the JSON for Active View.", e);
                    jSONObject = null;
                }
            }
            this.zzEe.zza(new zzhs.zza(this.zzzz, this.zzDf, this.zzzA, adSizeParcelZzc, -2, jElapsedRealtime, this.zzDf.zzEO, jSONObject));
            zzid.zzIE.removeCallbacks(this.zzDg);
        } catch (zza e2) {
            zzc(e2.getErrorCode(), e2.getMessage());
            zzid.zzIE.removeCallbacks(this.zzDg);
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("AdLoaderBackgroundTask started.");
        String strZzb = this.zzwL.zzab().zzb(this.mContext);
        this.zzDg = new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzb.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (zzb.this.zzDh) {
                    if (zzb.this.zzEg == null) {
                        return;
                    }
                    zzb.this.onStop();
                    zzb.this.zzc(2, "Timed out waiting for ad response.");
                }
            }
        };
        zzid.zzIE.postDelayed(this.zzDg, zzby.zzvv.get().longValue());
        this.zzzz = new AdRequestInfoParcel(this.zzEf, strZzb, zzp.zzbz().elapsedRealtime());
        synchronized (this.zzDh) {
            this.zzEg = zzb(this.zzzz);
            if (this.zzEg == null) {
                zzc(0, "Could not start the ad request service.");
                zzid.zzIE.removeCallbacks(this.zzDg);
            }
        }
    }

    protected AdSizeParcel zzc(AdRequestInfoParcel adRequestInfoParcel) throws zza {
        if (this.zzDf.zzEN == null) {
            throw new zza("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] strArrSplit = this.zzDf.zzEN.split("x");
        if (strArrSplit.length != 2) {
            throw new zza("Invalid ad size format from the ad response: " + this.zzDf.zzEN, 0);
        }
        try {
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzqn.zztg) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i3 = adSizeParcel.width == -1 ? (int) (adSizeParcel.widthPixels / f) : adSizeParcel.width;
                int i4 = adSizeParcel.height == -2 ? (int) (adSizeParcel.heightPixels / f) : adSizeParcel.height;
                if (i == i3 && i2 == i4) {
                    return new AdSizeParcel(adSizeParcel, adRequestInfoParcel.zzqn.zztg);
                }
            }
            throw new zza("The ad size from the ad response was not one of the requested sizes: " + this.zzDf.zzEN, 0);
        } catch (NumberFormatException e) {
            throw new zza("Invalid ad size number from the ad response: " + this.zzDf.zzEN, 0);
        }
    }

    protected void zzfG() throws zza {
        if (this.zzDf.errorCode == -3) {
            return;
        }
        if (TextUtils.isEmpty(this.zzDf.body)) {
            throw new zza("No fill from ad server.", 3);
        }
        zzp.zzby().zza(this.mContext, this.zzDf.zzEv);
        if (this.zzDf.zzEK) {
            try {
                this.zzzA = new zzee(this.zzDf.body);
            } catch (JSONException e) {
                throw new zza("Could not parse mediation config: " + this.zzDf.body, 0);
            }
        }
    }

    protected void zzw(boolean z) {
        zzp.zzby().zzA(z);
        zzbk zzbkVarZzE = zzp.zzby().zzE(this.mContext);
        if (zzbkVarZzE == null || zzbkVarZzE.isAlive()) {
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("start fetching content...");
        zzbkVarZzE.zzct();
    }
}
