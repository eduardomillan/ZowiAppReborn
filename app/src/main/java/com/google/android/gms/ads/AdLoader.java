package com.google.android.gms.ads;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.ads.internal.client.zzc;
import com.google.android.gms.ads.internal.client.zzd;
import com.google.android.gms.ads.internal.client.zzh;
import com.google.android.gms.ads.internal.client.zzp;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzdb;
import com.google.android.gms.internal.zzdc;
import com.google.android.gms.internal.zzdd;
import com.google.android.gms.internal.zzde;
import com.google.android.gms.internal.zzel;

/* JADX INFO: loaded from: classes.dex */
public class AdLoader {
    private final Context mContext;
    private final zzh zznL;
    private final zzp zznM;

    public static class Builder {
        private final Context mContext;
        private final zzq zznN;

        Builder(Context context, zzq builder) {
            this.mContext = context;
            this.zznN = builder;
        }

        public Builder(Context context, String adUnitID) {
            this(context, zzd.zza(context, adUnitID, new zzel()));
        }

        public AdLoader build() {
            try {
                return new AdLoader(this.mContext, this.zznN.zzbk());
            } catch (RemoteException e) {
                zzb.zzb("Failed to build AdLoader.", e);
                return null;
            }
        }

        public Builder forAppInstallAd(NativeAppInstallAd.OnAppInstallAdLoadedListener listener) {
            try {
                this.zznN.zza(new zzdb(listener));
            } catch (RemoteException e) {
                zzb.zzd("Failed to add app install ad listener", e);
            }
            return this;
        }

        public Builder forContentAd(NativeContentAd.OnContentAdLoadedListener listener) {
            try {
                this.zznN.zza(new zzdc(listener));
            } catch (RemoteException e) {
                zzb.zzd("Failed to add content ad listener", e);
            }
            return this;
        }

        public Builder forCustomTemplateAd(String templateId, NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener adLoadedListener, NativeCustomTemplateAd.OnCustomClickListener customClickListener) {
            try {
                this.zznN.zza(templateId, new zzde(adLoadedListener), customClickListener == null ? null : new zzdd(customClickListener));
            } catch (RemoteException e) {
                zzb.zzd("Failed to add custom template ad listener", e);
            }
            return this;
        }

        public Builder withAdListener(AdListener listener) {
            try {
                this.zznN.zzb(new zzc(listener));
            } catch (RemoteException e) {
                zzb.zzd("Failed to set AdListener.", e);
            }
            return this;
        }

        public Builder withNativeAdOptions(NativeAdOptions options) {
            try {
                this.zznN.zza(new NativeAdOptionsParcel(options));
            } catch (RemoteException e) {
                zzb.zzd("Failed to specify native ad options", e);
            }
            return this;
        }
    }

    AdLoader(Context context, zzp adLoader) {
        this(context, adLoader, zzh.zzcB());
    }

    AdLoader(Context context, zzp adLoader, zzh parcelFactory) {
        this.mContext = context;
        this.zznM = adLoader;
        this.zznL = parcelFactory;
    }

    private void zza(zzy zzyVar) {
        try {
            this.zznM.zzf(this.zznL.zza(this.mContext, zzyVar));
        } catch (RemoteException e) {
            zzb.zzb("Failed to load ad.", e);
        }
    }

    public String getMediationAdapterClassName() {
        try {
            return this.zznM.getMediationAdapterClassName();
        } catch (RemoteException e) {
            zzb.zzd("Failed to get the mediation adapter class name.", e);
            return null;
        }
    }

    public boolean isLoading() {
        try {
            return this.zznM.isLoading();
        } catch (RemoteException e) {
            zzb.zzd("Failed to check if ad is loading.", e);
            return false;
        }
    }

    public void loadAd(AdRequest adRequest) {
        zza(adRequest.zzaF());
    }

    public void loadAd(PublisherAdRequest publisherAdRequest) {
        zza(publisherAdRequest.zzaF());
    }
}
