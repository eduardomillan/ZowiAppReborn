package com.google.android.gms.internal;

import android.content.Context;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.google.android.gms.internal.zzm;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzih {
    private static zzl zzIZ;
    private static final Object zzpy = new Object();
    public static final zza<Void> zzJa = new zza() { // from class: com.google.android.gms.internal.zzih.1
        @Override // com.google.android.gms.internal.zzih.zza
        /* JADX INFO: renamed from: zzgL, reason: merged with bridge method [inline-methods] */
        public Void zzfF() {
            return null;
        }

        @Override // com.google.android.gms.internal.zzih.zza
        /* JADX INFO: renamed from: zzi, reason: merged with bridge method [inline-methods] */
        public Void zzh(InputStream inputStream) {
            return null;
        }
    };

    public interface zza<T> {
        T zzfF();

        T zzh(InputStream inputStream);
    }

    private static class zzb<T> extends zzk<InputStream> {
        private final zza<T> zzJe;
        private final zzm.zzb<T> zzaG;

        public zzb(String str, final zza<T> zzaVar, final zzm.zzb<T> zzbVar) {
            super(0, str, new zzm.zza() { // from class: com.google.android.gms.internal.zzih.zzb.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.android.gms.internal.zzm.zza
                public void zze(zzr zzrVar) {
                    zzbVar.zzb(zzaVar.zzfF());
                }
            });
            this.zzJe = zzaVar;
            this.zzaG = zzbVar;
        }

        @Override // com.google.android.gms.internal.zzk
        protected zzm<InputStream> zza(zzi zziVar) {
            return zzm.zza(new ByteArrayInputStream(zziVar.data), zzx.zzb(zziVar));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzk
        /* JADX INFO: renamed from: zzj, reason: merged with bridge method [inline-methods] */
        public void zza(InputStream inputStream) {
            this.zzaG.zzb(this.zzJe.zzh(inputStream));
        }
    }

    private class zzc<T> extends zzin<T> implements zzm.zzb<T> {
        private zzc() {
        }

        @Override // com.google.android.gms.internal.zzm.zzb
        public void zzb(T t) {
            super.zzf(t);
        }
    }

    public zzih(Context context) {
        zzIZ = zzP(context);
    }

    private static zzl zzP(Context context) {
        zzl zzlVar;
        synchronized (zzpy) {
            if (zzIZ == null) {
                zzIZ = zzac.zza(context.getApplicationContext());
            }
            zzlVar = zzIZ;
        }
        return zzlVar;
    }

    public <T> zziq<T> zza(String str, zza<T> zzaVar) {
        zzc zzcVar = new zzc();
        zzIZ.zze(new zzb(str, zzaVar, zzcVar));
        return zzcVar;
    }

    public zziq<String> zza(final String str, final Map<String, String> map) {
        final zzc zzcVar = new zzc();
        zzIZ.zze(new zzab(str, zzcVar, new zzm.zza() { // from class: com.google.android.gms.internal.zzih.2
            @Override // com.google.android.gms.internal.zzm.zza
            public void zze(zzr zzrVar) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Failed to load URL: " + str + Droid2InoConstants.NEW_LINE_CHARACTER + zzrVar.toString());
                zzcVar.zzb(null);
            }
        }) { // from class: com.google.android.gms.internal.zzih.3
            @Override // com.google.android.gms.internal.zzk
            public Map<String, String> getHeaders() throws com.google.android.gms.internal.zza {
                return map == null ? super.getHeaders() : map;
            }
        });
        return zzcVar;
    }
}
