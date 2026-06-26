package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzb {
    private final Context mContext;
    private final boolean zzCA;
    Object zzCz;

    public zzb(Context context) {
        this(context, true);
    }

    public zzb(Context context, boolean z) {
        this.mContext = context;
        this.zzCA = z;
    }

    public void destroy() {
        this.zzCz = null;
    }

    public void zzN(IBinder iBinder) {
        try {
            this.zzCz = this.mContext.getClassLoader().loadClass("com.android.vending.billing.IInAppBillingService$Stub").getDeclaredMethod("asInterface", IBinder.class).invoke(null, iBinder);
        } catch (Exception e) {
            if (this.zzCA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to project.");
            }
        }
    }

    public int zza(int i, String str, String str2) {
        try {
            Class<?> clsLoadClass = this.mContext.getClassLoader().loadClass("com.android.vending.billing.IInAppBillingService");
            return ((Integer) clsLoadClass.getDeclaredMethod("isBillingSupported", Integer.TYPE, String.class, String.class).invoke(clsLoadClass.cast(this.zzCz), Integer.valueOf(i), str, str2)).intValue();
        } catch (Exception e) {
            if (this.zzCA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to project.", e);
            }
            return 5;
        }
    }

    public Bundle zzb(String str, String str2, String str3) {
        try {
            Class<?> clsLoadClass = this.mContext.getClassLoader().loadClass("com.android.vending.billing.IInAppBillingService");
            return (Bundle) clsLoadClass.getDeclaredMethod("getBuyIntent", Integer.TYPE, String.class, String.class, String.class, String.class).invoke(clsLoadClass.cast(this.zzCz), 3, str, str2, "inapp", str3);
        } catch (Exception e) {
            if (this.zzCA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to project.", e);
            }
            return null;
        }
    }

    public int zzi(String str, String str2) {
        try {
            Class<?> clsLoadClass = this.mContext.getClassLoader().loadClass("com.android.vending.billing.IInAppBillingService");
            return ((Integer) clsLoadClass.getDeclaredMethod("consumePurchase", Integer.TYPE, String.class, String.class).invoke(clsLoadClass.cast(this.zzCz), 3, str, str2)).intValue();
        } catch (Exception e) {
            if (this.zzCA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to project.", e);
            }
            return 5;
        }
    }

    public Bundle zzj(String str, String str2) {
        try {
            Class<?> clsLoadClass = this.mContext.getClassLoader().loadClass("com.android.vending.billing.IInAppBillingService");
            return (Bundle) clsLoadClass.getDeclaredMethod("getPurchases", Integer.TYPE, String.class, String.class, String.class).invoke(clsLoadClass.cast(this.zzCz), 3, str, "inapp", str2);
        } catch (Exception e) {
            if (this.zzCA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to project.", e);
            }
            return null;
        }
    }
}
