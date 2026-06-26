package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.reward.client.RewardedVideoAdRequestParcel;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.zzgr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzh {
    public static final zzh zztd = new zzh();

    protected zzh() {
    }

    public static zzh zzcB() {
        return zztd;
    }

    public AdRequestParcel zza(Context context, zzy zzyVar) {
        Date birthday = zzyVar.getBirthday();
        long time = birthday != null ? birthday.getTime() : -1L;
        String contentUrl = zzyVar.getContentUrl();
        int gender = zzyVar.getGender();
        Set<String> keywords = zzyVar.getKeywords();
        List listUnmodifiableList = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
        boolean zIsTestDevice = zzyVar.isTestDevice(context);
        int iZzcQ = zzyVar.zzcQ();
        Location location = zzyVar.getLocation();
        Bundle networkExtrasBundle = zzyVar.getNetworkExtrasBundle(AdMobAdapter.class);
        boolean manualImpressionsEnabled = zzyVar.getManualImpressionsEnabled();
        String publisherProvidedId = zzyVar.getPublisherProvidedId();
        SearchAdRequest searchAdRequestZzcN = zzyVar.zzcN();
        SearchAdRequestParcel searchAdRequestParcel = searchAdRequestZzcN != null ? new SearchAdRequestParcel(searchAdRequestZzcN) : null;
        Context applicationContext = context.getApplicationContext();
        return new AdRequestParcel(6, time, networkExtrasBundle, gender, listUnmodifiableList, zIsTestDevice, iZzcQ, manualImpressionsEnabled, publisherProvidedId, searchAdRequestParcel, location, contentUrl, zzyVar.zzcP(), zzyVar.getCustomTargeting(), Collections.unmodifiableList(new ArrayList(zzyVar.zzcR())), zzyVar.zzcM(), applicationContext != null ? com.google.android.gms.ads.internal.zzp.zzbv().zza(Thread.currentThread().getStackTrace(), applicationContext.getPackageName()) : null);
    }

    public RewardedVideoAdRequestParcel zza(Context context, zzy zzyVar, String str) {
        return new RewardedVideoAdRequestParcel(zza(context, zzyVar), str);
    }
}
