package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzlc;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.internal.zzmp;
import com.google.android.gms.internal.zzqz;
import com.google.android.gms.internal.zzra;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.tagmanager.zzbf;
import com.google.android.gms.tagmanager.zzcb;
import com.google.android.gms.tagmanager.zzo;

/* JADX INFO: loaded from: classes.dex */
public class zzp extends zzlc<ContainerHolder> {
    private final Context mContext;
    private final String zzaVQ;
    private long zzaVV;
    private final TagManager zzaWc;
    private final zzd zzaWf;
    private final zzcd zzaWg;
    private final int zzaWh;
    private zzf zzaWi;
    private zzra zzaWj;
    private volatile zzo zzaWk;
    private volatile boolean zzaWl;
    private zzaf.zzj zzaWm;
    private String zzaWn;
    private zze zzaWo;
    private zza zzaWp;
    private final Looper zzaaO;
    private final zzmn zzpW;

    /* JADX INFO: renamed from: com.google.android.gms.tagmanager.zzp$1, reason: invalid class name */
    class AnonymousClass1 {
    }

    interface zza {
        boolean zzb(Container container);
    }

    private class zzb implements zzbf<zzqz.zza> {
        private zzb() {
        }

        /* synthetic */ zzb(zzp zzpVar, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zzCC() {
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
        public void zzH(zzqz.zza zzaVar) {
            zzaf.zzj zzjVar;
            if (zzaVar.zzbaj != null) {
                zzjVar = zzaVar.zzbaj;
            } else {
                zzaf.zzf zzfVar = zzaVar.zziR;
                zzjVar = new zzaf.zzj();
                zzjVar.zziR = zzfVar;
                zzjVar.zziQ = null;
                zzjVar.zziS = zzfVar.version;
            }
            zzp.this.zza(zzjVar, zzaVar.zzbai, true);
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zza(zzbf.zza zzaVar) {
            if (zzp.this.zzaWl) {
                return;
            }
            zzp.this.zzU(0L);
        }
    }

    private class zzc implements zzbf<zzaf.zzj> {
        private zzc() {
        }

        /* synthetic */ zzc(zzp zzpVar, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zzCC() {
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zza(zzbf.zza zzaVar) {
            synchronized (zzp.this) {
                if (!zzp.this.isReady()) {
                    if (zzp.this.zzaWk != null) {
                        zzp.this.zzb(zzp.this.zzaWk);
                    } else {
                        zzp.this.zzb(zzp.this.zzb(Status.zzabe));
                    }
                }
            }
            zzp.this.zzU(3600000L);
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public void zzH(zzaf.zzj zzjVar) {
            synchronized (zzp.this) {
                if (zzjVar.zziR == null) {
                    if (zzp.this.zzaWm.zziR == null) {
                        zzbg.e("Current resource is null; network resource is also null");
                        zzp.this.zzU(3600000L);
                        return;
                    }
                    zzjVar.zziR = zzp.this.zzaWm.zziR;
                }
                zzp.this.zza(zzjVar, zzp.this.zzpW.currentTimeMillis(), false);
                zzbg.v("setting refresh time to current time: " + zzp.this.zzaVV);
                if (!zzp.this.zzCB()) {
                    zzp.this.zza(zzjVar);
                }
            }
        }
    }

    private class zzd implements zzo.zza {
        private zzd() {
        }

        /* synthetic */ zzd(zzp zzpVar, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.google.android.gms.tagmanager.zzo.zza
        public String zzCv() {
            return zzp.this.zzCv();
        }

        @Override // com.google.android.gms.tagmanager.zzo.zza
        public void zzCx() {
            if (zzp.this.zzaWg.zzkF()) {
                zzp.this.zzU(0L);
            }
        }

        @Override // com.google.android.gms.tagmanager.zzo.zza
        public void zzeE(String str) {
            zzp.this.zzeE(str);
        }
    }

    interface zze extends Releasable {
        void zza(zzbf<zzaf.zzj> zzbfVar);

        void zzeH(String str);

        void zzf(long j, String str);
    }

    interface zzf extends Releasable {
        void zzCD();

        void zza(zzbf<zzqz.zza> zzbfVar);

        void zzb(zzqz.zza zzaVar);

        zzrb.zzc zzjs(int i);
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzf zzfVar, zze zzeVar, zzra zzraVar, zzmn zzmnVar, zzcd zzcdVar) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.zzaWc = tagManager;
        this.zzaaO = looper == null ? Looper.getMainLooper() : looper;
        this.zzaVQ = str;
        this.zzaWh = i;
        this.zzaWi = zzfVar;
        this.zzaWo = zzeVar;
        this.zzaWj = zzraVar;
        this.zzaWf = new zzd(this, null);
        this.zzaWm = new zzaf.zzj();
        this.zzpW = zzmnVar;
        this.zzaWg = zzcdVar;
        if (zzCB()) {
            zzeE(zzcb.zzDm().zzDo());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzs zzsVar) {
        this(context, tagManager, looper, str, i, new zzcn(context, str), new zzcm(context, str, zzsVar), new zzra(context), zzmp.zzqt(), new zzbe(30, 900000L, 5000L, "refreshing", zzmp.zzqt()));
        this.zzaWj.zzfm(zzsVar.zzCE());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean zzCB() {
        zzcb zzcbVarZzDm = zzcb.zzDm();
        return (zzcbVarZzDm.zzDn() == zzcb.zza.CONTAINER || zzcbVarZzDm.zzDn() == zzcb.zza.CONTAINER_DEBUG) && this.zzaVQ.equals(zzcbVarZzDm.getContainerId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void zzU(long j) {
        if (this.zzaWo == null) {
            zzbg.zzaH("Refresh requested, but no network load scheduler.");
        } else {
            this.zzaWo.zzf(j, this.zzaWm.zziS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void zza(zzaf.zzj zzjVar) {
        if (this.zzaWi != null) {
            zzqz.zza zzaVar = new zzqz.zza();
            zzaVar.zzbai = this.zzaVV;
            zzaVar.zziR = new zzaf.zzf();
            zzaVar.zzbaj = zzjVar;
            this.zzaWi.zzb(zzaVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void zza(zzaf.zzj zzjVar, long j, boolean z) {
        if (z) {
            if (!this.zzaWl) {
            }
        }
        if (!isReady() || this.zzaWk == null) {
        }
        this.zzaWm = zzjVar;
        this.zzaVV = j;
        zzU(Math.max(0L, Math.min(43200000L, (this.zzaVV + 43200000) - this.zzpW.currentTimeMillis())));
        Container container = new Container(this.mContext, this.zzaWc.getDataLayer(), this.zzaVQ, j, zzjVar);
        if (this.zzaWk == null) {
            this.zzaWk = new zzo(this.zzaWc, this.zzaaO, container, this.zzaWf);
        } else {
            this.zzaWk.zza(container);
        }
        if (!isReady() && this.zzaWp.zzb(container)) {
            zzb(this.zzaWk);
        }
    }

    private void zzar(final boolean z) {
        AnonymousClass1 anonymousClass1 = null;
        this.zzaWi.zza(new zzb(this, anonymousClass1));
        this.zzaWo.zza(new zzc(this, anonymousClass1));
        zzrb.zzc zzcVarZzjs = this.zzaWi.zzjs(this.zzaWh);
        if (zzcVarZzjs != null) {
            this.zzaWk = new zzo(this.zzaWc, this.zzaaO, new Container(this.mContext, this.zzaWc.getDataLayer(), this.zzaVQ, 0L, zzcVarZzjs), this.zzaWf);
        }
        this.zzaWp = new zza() { // from class: com.google.android.gms.tagmanager.zzp.3
            @Override // com.google.android.gms.tagmanager.zzp.zza
            public boolean zzb(Container container) {
                return z ? container.getLastRefreshTime() + 43200000 >= zzp.this.zzpW.currentTimeMillis() : !container.isDefault();
            }
        };
        if (zzCB()) {
            this.zzaWo.zzf(0L, "");
        } else {
            this.zzaWi.zzCD();
        }
    }

    public void zzCA() {
        zzar(true);
    }

    synchronized String zzCv() {
        return this.zzaWn;
    }

    public void zzCy() {
        zzrb.zzc zzcVarZzjs = this.zzaWi.zzjs(this.zzaWh);
        if (zzcVarZzjs != null) {
            zzb(new zzo(this.zzaWc, this.zzaaO, new Container(this.mContext, this.zzaWc.getDataLayer(), this.zzaVQ, 0L, zzcVarZzjs), new zzo.zza() { // from class: com.google.android.gms.tagmanager.zzp.2
                @Override // com.google.android.gms.tagmanager.zzo.zza
                public String zzCv() {
                    return zzp.this.zzCv();
                }

                @Override // com.google.android.gms.tagmanager.zzo.zza
                public void zzCx() {
                    zzbg.zzaH("Refresh ignored: container loaded as default only.");
                }

                @Override // com.google.android.gms.tagmanager.zzo.zza
                public void zzeE(String str) {
                    zzp.this.zzeE(str);
                }
            }));
        } else {
            zzbg.e("Default was requested, but no default container was found");
            zzb(zzb(new Status(10, "Default was requested, but no default container was found", null)));
        }
        this.zzaWo = null;
        this.zzaWi = null;
    }

    public void zzCz() {
        zzar(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.zzlc
    /* JADX INFO: renamed from: zzbf, reason: merged with bridge method [inline-methods] */
    public ContainerHolder zzb(Status status) {
        if (this.zzaWk != null) {
            return this.zzaWk;
        }
        if (status == Status.zzabe) {
            zzbg.e("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    synchronized void zzeE(String str) {
        this.zzaWn = str;
        if (this.zzaWo != null) {
            this.zzaWo.zzeH(str);
        }
    }
}
