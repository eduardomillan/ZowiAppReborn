package com.google.android.gms.internal;

import android.content.Context;
import com.comscore.utils.Constants;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzis;
import com.google.android.gms.search.SearchAuth;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdz {
    private final Context mContext;
    private final VersionInfoParcel zzpb;
    private final Object zzpd;
    private final String zzyo;
    private zzb<zzbb> zzyp;
    private zzb<zzbb> zzyq;
    private zze zzyr;
    private int zzys;

    /* JADX INFO: renamed from: com.google.android.gms.internal.zzdz$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ zze zzyt;

        /* JADX INFO: renamed from: com.google.android.gms.internal.zzdz$1$1, reason: invalid class name and collision with other inner class name */
        class C00621 implements zzbb.zza {
            final /* synthetic */ zzbb zzyv;

            C00621(zzbb zzbbVar) {
                this.zzyv = zzbbVar;
            }

            @Override // com.google.android.gms.internal.zzbb.zza
            public void zzcj() {
                zzid.zzIE.postDelayed(new Runnable() { // from class: com.google.android.gms.internal.zzdz.1.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (zzdz.this.zzpd) {
                            if (AnonymousClass1.this.zzyt.getStatus() == -1 || AnonymousClass1.this.zzyt.getStatus() == 1) {
                                return;
                            }
                            AnonymousClass1.this.zzyt.reject();
                            zzid.runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzdz.1.1.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    C00621.this.zzyv.destroy();
                                }
                            });
                            com.google.android.gms.ads.internal.util.client.zzb.v("Could not receive loaded message in a timely manner. Rejecting.");
                        }
                    }
                }, zza.zzyD);
            }
        }

        AnonymousClass1(zze zzeVar) {
            this.zzyt = zzeVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            final zzbb zzbbVarZza = zzdz.this.zza(zzdz.this.mContext, zzdz.this.zzpb);
            zzbbVarZza.zza(new C00621(zzbbVarZza));
            zzbbVarZza.zza("/jsLoaded", new zzdk() { // from class: com.google.android.gms.internal.zzdz.1.2
                @Override // com.google.android.gms.internal.zzdk
                public void zza(zziz zzizVar, Map<String, String> map) {
                    synchronized (zzdz.this.zzpd) {
                        if (AnonymousClass1.this.zzyt.getStatus() == -1 || AnonymousClass1.this.zzyt.getStatus() == 1) {
                            return;
                        }
                        zzdz.this.zzys = 0;
                        zzdz.this.zzyp.zzc(zzbbVarZza);
                        AnonymousClass1.this.zzyt.zzg(zzbbVarZza);
                        zzdz.this.zzyr = AnonymousClass1.this.zzyt;
                        com.google.android.gms.ads.internal.util.client.zzb.v("Successfully loaded JS Engine.");
                    }
                }
            });
            final zzil zzilVar = new zzil();
            zzdk zzdkVar = new zzdk() { // from class: com.google.android.gms.internal.zzdz.1.3
                @Override // com.google.android.gms.internal.zzdk
                public void zza(zziz zzizVar, Map<String, String> map) {
                    synchronized (zzdz.this.zzpd) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaG("JS Engine is requesting an update");
                        if (zzdz.this.zzys == 0) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Starting reload.");
                            zzdz.this.zzys = 2;
                            zzdz.this.zzdN();
                        }
                        zzbbVarZza.zzb("/requestReload", (zzdk) zzilVar.get());
                    }
                }
            };
            zzilVar.set(zzdkVar);
            zzbbVarZza.zza("/requestReload", zzdkVar);
            if (zzdz.this.zzyo.endsWith(".js")) {
                zzbbVarZza.zzs(zzdz.this.zzyo);
            } else if (zzdz.this.zzyo.startsWith("<html>")) {
                zzbbVarZza.zzu(zzdz.this.zzyo);
            } else {
                zzbbVarZza.zzt(zzdz.this.zzyo);
            }
            zzid.zzIE.postDelayed(new Runnable() { // from class: com.google.android.gms.internal.zzdz.1.4
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (zzdz.this.zzpd) {
                        if (AnonymousClass1.this.zzyt.getStatus() == -1 || AnonymousClass1.this.zzyt.getStatus() == 1) {
                            return;
                        }
                        AnonymousClass1.this.zzyt.reject();
                        zzid.runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzdz.1.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                zzbbVarZza.destroy();
                            }
                        });
                        com.google.android.gms.ads.internal.util.client.zzb.v("Could not receive loaded message in a timely manner. Rejecting.");
                    }
                }
            }, zza.zzyC);
        }
    }

    static class zza {
        static int zzyC = Constants.MINIMAL_AUTOUPDATE_INTERVAL;
        static int zzyD = SearchAuth.StatusCodes.AUTH_DISABLED;
    }

    public interface zzb<T> {
        void zzc(T t);
    }

    public static class zzc<T> implements zzb<T> {
        @Override // com.google.android.gms.internal.zzdz.zzb
        public void zzc(T t) {
        }
    }

    public static class zzd extends zzit<zzbe> {
        private final Object zzpd = new Object();
        private final zze zzyE;
        private boolean zzyF;

        public zzd(zze zzeVar) {
            this.zzyE = zzeVar;
        }

        public void release() {
            synchronized (this.zzpd) {
                if (this.zzyF) {
                    return;
                }
                this.zzyF = true;
                zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzdz.zzd.1
                    @Override // com.google.android.gms.internal.zzis.zzc
                    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbe zzbeVar) {
                        com.google.android.gms.ads.internal.util.client.zzb.v("Ending javascript session.");
                        ((zzbf) zzbeVar).zzck();
                    }
                }, new zzis.zzb());
                zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzdz.zzd.2
                    @Override // com.google.android.gms.internal.zzis.zzc
                    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbe zzbeVar) {
                        com.google.android.gms.ads.internal.util.client.zzb.v("Releasing engine reference.");
                        zzd.this.zzyE.zzdQ();
                    }
                }, new zzis.zza() { // from class: com.google.android.gms.internal.zzdz.zzd.3
                    @Override // com.google.android.gms.internal.zzis.zza
                    public void run() {
                        zzd.this.zzyE.zzdQ();
                    }
                });
            }
        }
    }

    public static class zze extends zzit<zzbb> {
        private final Object zzpd = new Object();
        private boolean zzyH = false;
        private int zzyI = 0;
        private zzb<zzbb> zzyq;

        public zze(zzb<zzbb> zzbVar) {
            this.zzyq = zzbVar;
        }

        public zzd zzdP() {
            final zzd zzdVar = new zzd(this);
            synchronized (this.zzpd) {
                zza(new zzis.zzc<zzbb>() { // from class: com.google.android.gms.internal.zzdz.zze.1
                    @Override // com.google.android.gms.internal.zzis.zzc
                    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbb zzbbVar) {
                        com.google.android.gms.ads.internal.util.client.zzb.v("Getting a new session for JS Engine.");
                        zzdVar.zzg(zzbbVar.zzci());
                    }
                }, new zzis.zza() { // from class: com.google.android.gms.internal.zzdz.zze.2
                    @Override // com.google.android.gms.internal.zzis.zza
                    public void run() {
                        com.google.android.gms.ads.internal.util.client.zzb.v("Rejecting reference for JS Engine.");
                        zzdVar.reject();
                    }
                });
                com.google.android.gms.common.internal.zzx.zzZ(this.zzyI >= 0);
                this.zzyI++;
            }
            return zzdVar;
        }

        protected void zzdQ() {
            synchronized (this.zzpd) {
                com.google.android.gms.common.internal.zzx.zzZ(this.zzyI >= 1);
                com.google.android.gms.ads.internal.util.client.zzb.v("Releasing 1 reference for JS Engine");
                this.zzyI--;
                zzdS();
            }
        }

        public void zzdR() {
            synchronized (this.zzpd) {
                com.google.android.gms.common.internal.zzx.zzZ(this.zzyI >= 0);
                com.google.android.gms.ads.internal.util.client.zzb.v("Releasing root reference. JS Engine will be destroyed once other references are released.");
                this.zzyH = true;
                zzdS();
            }
        }

        protected void zzdS() {
            synchronized (this.zzpd) {
                com.google.android.gms.common.internal.zzx.zzZ(this.zzyI >= 0);
                if (this.zzyH && this.zzyI == 0) {
                    com.google.android.gms.ads.internal.util.client.zzb.v("No reference is left (including root). Cleaning up engine.");
                    zza(new zzis.zzc<zzbb>() { // from class: com.google.android.gms.internal.zzdz.zze.3
                        @Override // com.google.android.gms.internal.zzis.zzc
                        /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
                        public void zzc(final zzbb zzbbVar) {
                            zzid.runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzdz.zze.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    zze.this.zzyq.zzc(zzbbVar);
                                    zzbbVar.destroy();
                                }
                            });
                        }
                    }, new zzis.zzb());
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.v("There are still references to the engine. Not destroying.");
                }
            }
        }
    }

    public zzdz(Context context, VersionInfoParcel versionInfoParcel, String str) {
        this.zzpd = new Object();
        this.zzys = 1;
        this.zzyo = str;
        this.mContext = context.getApplicationContext();
        this.zzpb = versionInfoParcel;
        this.zzyp = new zzc();
        this.zzyq = new zzc();
    }

    public zzdz(Context context, VersionInfoParcel versionInfoParcel, String str, zzb<zzbb> zzbVar, zzb<zzbb> zzbVar2) {
        this(context, versionInfoParcel, str);
        this.zzyp = zzbVar;
        this.zzyq = zzbVar2;
    }

    private zze zzdM() {
        zze zzeVar = new zze(this.zzyq);
        zzid.runOnUiThread(new AnonymousClass1(zzeVar));
        return zzeVar;
    }

    protected zzbb zza(Context context, VersionInfoParcel versionInfoParcel) {
        return new zzbd(context, versionInfoParcel, null);
    }

    protected zze zzdN() {
        final zze zzeVarZzdM = zzdM();
        zzeVarZzdM.zza(new zzis.zzc<zzbb>() { // from class: com.google.android.gms.internal.zzdz.2
            @Override // com.google.android.gms.internal.zzis.zzc
            /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
            public void zzc(zzbb zzbbVar) {
                synchronized (zzdz.this.zzpd) {
                    zzdz.this.zzys = 0;
                    if (zzdz.this.zzyr != null && zzeVarZzdM != zzdz.this.zzyr) {
                        com.google.android.gms.ads.internal.util.client.zzb.v("New JS engine is loaded, marking previous one as destroyable.");
                        zzdz.this.zzyr.zzdR();
                    }
                    zzdz.this.zzyr = zzeVarZzdM;
                }
            }
        }, new zzis.zza() { // from class: com.google.android.gms.internal.zzdz.3
            @Override // com.google.android.gms.internal.zzis.zza
            public void run() {
                synchronized (zzdz.this.zzpd) {
                    zzdz.this.zzys = 1;
                    com.google.android.gms.ads.internal.util.client.zzb.v("Failed loading new engine. Marking new engine destroyable.");
                    zzeVarZzdM.zzdR();
                }
            }
        });
        return zzeVarZzdM;
    }

    public zzd zzdO() {
        zzd zzdVarZzdP;
        synchronized (this.zzpd) {
            if (this.zzyr == null || this.zzyr.getStatus() == -1) {
                this.zzys = 2;
                this.zzyr = zzdN();
                zzdVarZzdP = this.zzyr.zzdP();
            } else if (this.zzys == 0) {
                zzdVarZzdP = this.zzyr.zzdP();
            } else if (this.zzys == 1) {
                this.zzys = 2;
                zzdN();
                zzdVarZzdP = this.zzyr.zzdP();
            } else {
                zzdVarZzdP = this.zzys == 2 ? this.zzyr.zzdP() : this.zzyr.zzdP();
            }
        }
        return zzdVarZzdP;
    }
}
