package com.google.android.gms.tagmanager;

import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;

/* JADX INFO: loaded from: classes.dex */
public final class InstallReferrerReceiver extends CampaignTrackingReceiver {
    @Override // com.google.android.gms.analytics.CampaignTrackingReceiver
    protected void zzaS(String str) {
        zzax.zzeS(str);
    }

    @Override // com.google.android.gms.analytics.CampaignTrackingReceiver
    protected Class<? extends CampaignTrackingService> zzhJ() {
        return InstallReferrerService.class;
    }
}
