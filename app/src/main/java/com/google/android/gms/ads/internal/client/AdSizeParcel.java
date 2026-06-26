package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.Parcel;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class AdSizeParcel implements SafeParcelable {
    public static final zzi CREATOR = new zzi();
    public final int height;
    public final int heightPixels;
    public final int versionCode;
    public final int width;
    public final int widthPixels;
    public final String zzte;
    public final boolean zztf;
    public final AdSizeParcel[] zztg;
    public final boolean zzth;
    public final boolean zzti;

    public AdSizeParcel() {
        this(4, "interstitial_mb", 0, 0, true, 0, 0, null, false, false);
    }

    AdSizeParcel(int versionCode, String formatString, int height, int heightPixels, boolean isInterstitial, int width, int widthPixels, AdSizeParcel[] supportedAdSizes, boolean isNative, boolean isFluid) {
        this.versionCode = versionCode;
        this.zzte = formatString;
        this.height = height;
        this.heightPixels = heightPixels;
        this.zztf = isInterstitial;
        this.width = width;
        this.widthPixels = widthPixels;
        this.zztg = supportedAdSizes;
        this.zzth = isNative;
        this.zzti = isFluid;
    }

    public AdSizeParcel(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    public AdSizeParcel(Context context, AdSize[] adSizes) {
        int i;
        AdSize adSize = adSizes[0];
        this.versionCode = 4;
        this.zztf = false;
        this.zzti = adSize.isFluid();
        if (this.zzti) {
            this.width = AdSize.BANNER.getWidth();
            this.height = AdSize.BANNER.getHeight();
        } else {
            this.width = adSize.getWidth();
            this.height = adSize.getHeight();
        }
        boolean z = this.width == -1;
        boolean z2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (z) {
            if (zzl.zzcF().zzS(context) && zzl.zzcF().zzT(context)) {
                this.widthPixels = zza(displayMetrics) - zzl.zzcF().zzU(context);
            } else {
                this.widthPixels = zza(displayMetrics);
            }
            double d = this.widthPixels / displayMetrics.density;
            int i2 = (int) d;
            i = d - ((double) ((int) d)) >= 0.01d ? i2 + 1 : i2;
        } else {
            int i3 = this.width;
            this.widthPixels = zzl.zzcF().zza(displayMetrics, this.width);
            i = i3;
        }
        int iZzc = z2 ? zzc(displayMetrics) : this.height;
        this.heightPixels = zzl.zzcF().zza(displayMetrics, iZzc);
        if (z || z2) {
            this.zzte = i + "x" + iZzc + "_as";
        } else if (this.zzti) {
            this.zzte = "320x50_mb";
        } else {
            this.zzte = adSize.toString();
        }
        if (adSizes.length > 1) {
            this.zztg = new AdSizeParcel[adSizes.length];
            for (int i4 = 0; i4 < adSizes.length; i4++) {
                this.zztg[i4] = new AdSizeParcel(context, adSizes[i4]);
            }
        } else {
            this.zztg = null;
        }
        this.zzth = false;
    }

    public AdSizeParcel(AdSizeParcel adSize, AdSizeParcel[] supportedAdSizes) {
        this(4, adSize.zzte, adSize.height, adSize.heightPixels, adSize.zztf, adSize.width, adSize.widthPixels, supportedAdSizes, adSize.zzth, adSize.zzti);
    }

    public static int zza(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int zzb(DisplayMetrics displayMetrics) {
        return (int) (zzc(displayMetrics) * displayMetrics.density);
    }

    private static int zzc(DisplayMetrics displayMetrics) {
        int i = (int) (displayMetrics.heightPixels / displayMetrics.density);
        if (i <= 400) {
            return 32;
        }
        return i <= 720 ? 50 : 90;
    }

    public static AdSizeParcel zzcC() {
        return new AdSizeParcel(4, "reward_mb", 0, 0, false, 0, 0, null, false, false);
    }

    public static AdSizeParcel zzs(Context context) {
        return new AdSizeParcel(4, "320x50_mb", 0, 0, false, 0, 0, null, true, false);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzi.zza(this, out, flags);
    }

    public AdSize zzcD() {
        return com.google.android.gms.ads.zza.zza(this.width, this.height, this.zzte);
    }
}
