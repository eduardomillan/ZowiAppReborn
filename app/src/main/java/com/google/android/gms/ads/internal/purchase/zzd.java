package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzfr;
import com.google.android.gms.internal.zzgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzd extends zzfr.zza {
    private Context mContext;
    private String zzCJ;
    private ArrayList<String> zzCK;
    private String zzqV;

    public zzd(String str, ArrayList<String> arrayList, Context context, String str2) {
        this.zzCJ = str;
        this.zzCK = arrayList;
        this.zzqV = str2;
        this.mContext = context;
    }

    @Override // com.google.android.gms.internal.zzfr
    public String getProductId() {
        return this.zzCJ;
    }

    @Override // com.google.android.gms.internal.zzfr
    public void recordPlayBillingResolution(int billingResponseCode) {
        if (billingResponseCode == 0) {
            zzfn();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("google_play_status", String.valueOf(billingResponseCode));
        map.put("sku", this.zzCJ);
        map.put("status", String.valueOf(zzy(billingResponseCode)));
        LinkedList linkedList = new LinkedList();
        Iterator<String> it = this.zzCK.iterator();
        while (it.hasNext()) {
            linkedList.add(zza(it.next(), map));
        }
        zzp.zzbv().zza(this.mContext, this.zzqV, linkedList);
    }

    @Override // com.google.android.gms.internal.zzfr
    public void recordResolution(int resolution) {
        if (resolution == 1) {
            zzfn();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(resolution));
        map.put("sku", this.zzCJ);
        LinkedList linkedList = new LinkedList();
        Iterator<String> it = this.zzCK.iterator();
        while (it.hasNext()) {
            linkedList.add(zza(it.next(), map));
        }
        zzp.zzbv().zza(this.mContext, this.zzqV, linkedList);
    }

    protected String zza(String str, HashMap<String, String> map) {
        String str2;
        String packageName = this.mContext.getPackageName();
        try {
            str2 = this.mContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Error to retrieve app version", e);
            str2 = "";
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - zzp.zzby().zzgn().zzgx();
        for (String str3 : map.keySet()) {
            str = str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", str3), String.format("$1%s$2", map.get(str3)));
        }
        return str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "sessionid"), String.format("$1%s$2", zzp.zzby().getSessionId())).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "appid"), String.format("$1%s$2", packageName)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "osversion"), String.format("$1%s$2", String.valueOf(Build.VERSION.SDK_INT))).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "sdkversion"), String.format("$1%s$2", this.zzqV)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "appversion"), String.format("$1%s$2", str2)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "timestamp"), String.format("$1%s$2", String.valueOf(jElapsedRealtime))).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "[^@]+"), String.format("$1%s$2", "")).replaceAll("@@", "@");
    }

    void zzfn() {
        try {
            this.mContext.getClassLoader().loadClass("com.google.ads.conversiontracking.IAPConversionReporter").getDeclaredMethod("reportWithProductId", Context.class, String.class, String.class, Boolean.TYPE).invoke(null, this.mContext, this.zzCJ, "", true);
        } catch (ClassNotFoundException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (NoSuchMethodException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (Exception e3) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to report a conversion.", e3);
        }
    }

    protected int zzy(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        return i == 4 ? 3 : 0;
    }
}
