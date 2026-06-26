package com.google.android.gms.ads.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zziz;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zze {
    private zza zzoI;
    private boolean zzoJ;
    private boolean zzoK;

    public interface zza {
        void zzq(String str);
    }

    @zzgr
    public static class zzb implements zza {
        private final zzhs.zza zzoL;
        private final zziz zzoM;

        public zzb(zzhs.zza zzaVar, zziz zzizVar) {
            this.zzoL = zzaVar;
            this.zzoM = zzizVar;
        }

        @Override // com.google.android.gms.ads.internal.zze.zza
        public void zzq(String str) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("An auto-clicking creative is blocked");
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.path("//pagead2.googlesyndication.com/pagead/gen_204");
            builder.appendQueryParameter("id", "gmob-apps-blocked-navigation");
            if (!TextUtils.isEmpty(str)) {
                builder.appendQueryParameter("navigationURL", str);
            }
            if (this.zzoL != null && this.zzoL.zzHD != null && !TextUtils.isEmpty(this.zzoL.zzHD.zzEP)) {
                builder.appendQueryParameter("debugDialog", this.zzoL.zzHD.zzEP);
            }
            zzp.zzbv().zzc(this.zzoM.getContext(), this.zzoM.zzhh().zzJu, builder.toString());
        }
    }

    public zze() {
        this.zzoK = zzby.zzus.get().booleanValue();
    }

    public zze(boolean z) {
        this.zzoK = z;
    }

    public void recordClick() {
        this.zzoJ = true;
    }

    public void zza(zza zzaVar) {
        this.zzoI = zzaVar;
    }

    public boolean zzbe() {
        return !this.zzoK || this.zzoJ;
    }

    public void zzp(String str) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Action was blocked because no click was detected.");
        if (this.zzoI != null) {
            this.zzoI.zzq(str);
        }
    }
}
