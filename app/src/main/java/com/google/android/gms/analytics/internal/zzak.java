package com.google.android.gms.analytics.internal;

import com.google.android.gms.analytics.internal.zzq;

/* JADX INFO: loaded from: classes.dex */
public class zzak extends zzq<zzal> {

    private static class zza extends zzc implements zzq.zza<zzal> {
        private final zzal zzPI;

        public zza(zzf zzfVar) {
            super(zzfVar);
            this.zzPI = new zzal();
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzc(String str, boolean z) {
            if ("ga_autoActivityTracking".equals(str)) {
                this.zzPI.zzPL = z ? 1 : 0;
                return;
            }
            if ("ga_anonymizeIp".equals(str)) {
                this.zzPI.zzPM = z ? 1 : 0;
            } else if (!"ga_reportUncaughtExceptions".equals(str)) {
                zzd("bool configuration name not recognized", str);
            } else {
                this.zzPI.zzPN = z ? 1 : 0;
            }
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzd(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.zzPI.zzPK = i;
            } else {
                zzd("int configuration name not recognized", str);
            }
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzl(String str, String str2) {
            this.zzPI.zzPO.put(str, str2);
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        /* JADX INFO: renamed from: zzlb, reason: merged with bridge method [inline-methods] */
        public zzal zzjz() {
            return this.zzPI;
        }

        @Override // com.google.android.gms.analytics.internal.zzq.zza
        public void zzm(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.zzPI.zzLq = str2;
                return;
            }
            if (!"ga_sampleFrequency".equals(str)) {
                zzd("string configuration name not recognized", str);
                return;
            }
            try {
                this.zzPI.zzPJ = Double.parseDouble(str2);
            } catch (NumberFormatException e) {
                zzc("Error parsing ga_sampleFrequency value", str2, e);
            }
        }
    }

    public zzak(zzf zzfVar) {
        super(zzfVar, new zza(zzfVar));
    }
}
