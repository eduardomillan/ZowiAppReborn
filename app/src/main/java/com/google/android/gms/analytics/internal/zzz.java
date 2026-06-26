package com.google.android.gms.analytics.internal;

import com.google.android.gms.analytics.internal.zzq;

/* JADX INFO: loaded from: classes.dex */
public class zzz extends zzq<zzaa> {

    private static class zza implements zzq.zza<zzaa> {
        private final zzf zzME;
        private final zzaa zzOY = new zzaa();

        public zza(zzf zzfVar) {
            this.zzME = zzfVar;
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzc(String str, boolean z) {
            if (!"ga_dryRun".equals(str)) {
                this.zzME.zziu().zzd("Bool xml configuration name not recognized", str);
            } else {
                this.zzOY.zzPd = z ? 1 : 0;
            }
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzd(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.zzOY.zzPc = i;
            } else {
                this.zzME.zziu().zzd("Int xml configuration name not recognized", str);
            }
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        /* JADX INFO: renamed from: zzkn, reason: merged with bridge method [inline-methods] */
        public zzaa zzjz() {
            return this.zzOY;
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzl(String str, String str2) {
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzm(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.zzOY.zzOZ = str2;
                return;
            }
            if ("ga_appVersion".equals(str)) {
                this.zzOY.zzPa = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.zzOY.zzPb = str2;
            } else {
                this.zzME.zziu().zzd("String xml configuration name not recognized", str);
            }
        }
    }

    public zzz(zzf zzfVar) {
        super(zzfVar, new zza(zzfVar));
    }
}
