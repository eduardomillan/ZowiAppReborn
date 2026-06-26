package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.internal.zzrf;
import com.google.android.gms.internal.zzrg;
import com.google.android.gms.tagmanager.zzbf;
import com.google.android.gms.tagmanager.zzcb;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class zzcl implements Runnable {
    private final Context mContext;
    private final String zzaVQ;
    private volatile String zzaWn;
    private final zzrg zzaYi;
    private final String zzaYj;
    private zzbf<zzaf.zzj> zzaYk;
    private volatile zzs zzaYl;
    private volatile String zzaYm;

    zzcl(Context context, String str, zzrg zzrgVar, zzs zzsVar) {
        this.mContext = context;
        this.zzaYi = zzrgVar;
        this.zzaVQ = str;
        this.zzaYl = zzsVar;
        this.zzaYj = "/r?id=" + str;
        this.zzaWn = this.zzaYj;
        this.zzaYm = null;
    }

    public zzcl(Context context, String str, zzs zzsVar) {
        this(context, str, new zzrg(), zzsVar);
    }

    private boolean zzDp() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzbg.v("...no network connectivity");
        return false;
    }

    private void zzDq() {
        if (!zzDp()) {
            this.zzaYk.zza(zzbf.zza.NOT_AVAILABLE);
            return;
        }
        zzbg.v("Start loading resource from network ...");
        String strZzDr = zzDr();
        zzrf zzrfVarZzEt = this.zzaYi.zzEt();
        try {
            try {
                InputStream inputStreamZzft = zzrfVarZzEt.zzft(strZzDr);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    zzrb.zzb(inputStreamZzft, byteArrayOutputStream);
                    zzaf.zzj zzjVarZzd = zzaf.zzj.zzd(byteArrayOutputStream.toByteArray());
                    zzbg.v("Successfully loaded supplemented resource: " + zzjVarZzd);
                    if (zzjVarZzd.zziR == null && zzjVarZzd.zziQ.length == 0) {
                        zzbg.v("No change for container: " + this.zzaVQ);
                    }
                    this.zzaYk.zzH(zzjVarZzd);
                    zzrfVarZzEt.close();
                    zzbg.v("Load resource from network finished.");
                } catch (IOException e) {
                    zzbg.zzd("Error when parsing downloaded resources from url: " + strZzDr + " " + e.getMessage(), e);
                    this.zzaYk.zza(zzbf.zza.SERVER_ERROR);
                    zzrfVarZzEt.close();
                }
            } catch (FileNotFoundException e2) {
                zzbg.zzaH("No data is retrieved from the given url: " + strZzDr + ". Make sure container_id: " + this.zzaVQ + " is correct.");
                this.zzaYk.zza(zzbf.zza.SERVER_ERROR);
                zzrfVarZzEt.close();
            } catch (IOException e3) {
                zzbg.zzd("Error when loading resources from url: " + strZzDr + " " + e3.getMessage(), e3);
                this.zzaYk.zza(zzbf.zza.IO_ERROR);
                zzrfVarZzEt.close();
            }
        } catch (Throwable th) {
            zzrfVarZzEt.close();
            throw th;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.zzaYk == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.zzaYk.zzCC();
        zzDq();
    }

    String zzDr() {
        String str = this.zzaYl.zzCE() + this.zzaWn + "&v=a65833898";
        if (this.zzaYm != null && !this.zzaYm.trim().equals("")) {
            str = str + "&pv=" + this.zzaYm;
        }
        return zzcb.zzDm().zzDn().equals(zzcb.zza.CONTAINER_DEBUG) ? str + "&gtm_debug=x" : str;
    }

    void zza(zzbf<zzaf.zzj> zzbfVar) {
        this.zzaYk = zzbfVar;
    }

    void zzeH(String str) {
        if (str == null) {
            this.zzaWn = this.zzaYj;
        } else {
            zzbg.zzaF("Setting CTFE URL path: " + str);
            this.zzaWn = str;
        }
    }

    void zzeW(String str) {
        zzbg.zzaF("Setting previous container version: " + str);
        this.zzaYm = str;
    }
}
