package com.google.android.gms.analytics.internal;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Pair;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class zzai extends zzd {
    private SharedPreferences zzPC;
    private long zzPD;
    private long zzPE;
    private final zza zzPF;

    public final class zza {
        private final String mName;
        private final long zzPG;

        private zza(String str, long j) {
            com.google.android.gms.common.internal.zzx.zzcr(str);
            com.google.android.gms.common.internal.zzx.zzaa(j > 0);
            this.mName = str;
            this.zzPG = j;
        }

        private void zzkU() {
            long jCurrentTimeMillis = zzai.this.zzit().currentTimeMillis();
            SharedPreferences.Editor editorEdit = zzai.this.zzPC.edit();
            editorEdit.remove(zzkZ());
            editorEdit.remove(zzla());
            editorEdit.putLong(zzkY(), jCurrentTimeMillis);
            editorEdit.commit();
        }

        private long zzkV() {
            long jZzkX = zzkX();
            if (jZzkX == 0) {
                return 0L;
            }
            return Math.abs(jZzkX - zzai.this.zzit().currentTimeMillis());
        }

        private long zzkX() {
            return zzai.this.zzPC.getLong(zzkY(), 0L);
        }

        private String zzkY() {
            return this.mName + ":start";
        }

        private String zzkZ() {
            return this.mName + ":count";
        }

        public void zzbn(String str) {
            if (zzkX() == 0) {
                zzkU();
            }
            if (str == null) {
                str = "";
            }
            synchronized (this) {
                long j = zzai.this.zzPC.getLong(zzkZ(), 0L);
                if (j <= 0) {
                    SharedPreferences.Editor editorEdit = zzai.this.zzPC.edit();
                    editorEdit.putString(zzla(), str);
                    editorEdit.putLong(zzkZ(), 1L);
                    editorEdit.apply();
                    return;
                }
                boolean z = (UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE) < Long.MAX_VALUE / (j + 1);
                SharedPreferences.Editor editorEdit2 = zzai.this.zzPC.edit();
                if (z) {
                    editorEdit2.putString(zzla(), str);
                }
                editorEdit2.putLong(zzkZ(), j + 1);
                editorEdit2.apply();
            }
        }

        public Pair<String, Long> zzkW() {
            long jZzkV = zzkV();
            if (jZzkV < this.zzPG) {
                return null;
            }
            if (jZzkV > this.zzPG * 2) {
                zzkU();
                return null;
            }
            String string = zzai.this.zzPC.getString(zzla(), null);
            long j = zzai.this.zzPC.getLong(zzkZ(), 0L);
            zzkU();
            if (string == null || j <= 0) {
                return null;
            }
            return new Pair<>(string, Long.valueOf(j));
        }

        protected String zzla() {
            return this.mName + ":value";
        }
    }

    protected zzai(zzf zzfVar) {
        super(zzfVar);
        this.zzPE = -1L;
        this.zzPF = new zza("monitoring", zziv().zzkg());
    }

    public void zzbm(String str) {
        zzis();
        zziE();
        SharedPreferences.Editor editorEdit = this.zzPC.edit();
        if (TextUtils.isEmpty(str)) {
            editorEdit.remove("installation_campaign");
        } else {
            editorEdit.putString("installation_campaign", str);
        }
        if (editorEdit.commit()) {
            return;
        }
        zzbd("Failed to commit campaign data");
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        this.zzPC = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public long zzkO() {
        zzis();
        zziE();
        if (this.zzPD == 0) {
            long j = this.zzPC.getLong("first_run", 0L);
            if (j != 0) {
                this.zzPD = j;
            } else {
                long jCurrentTimeMillis = zzit().currentTimeMillis();
                SharedPreferences.Editor editorEdit = this.zzPC.edit();
                editorEdit.putLong("first_run", jCurrentTimeMillis);
                if (!editorEdit.commit()) {
                    zzbd("Failed to commit first run time");
                }
                this.zzPD = jCurrentTimeMillis;
            }
        }
        return this.zzPD;
    }

    public zzaj zzkP() {
        return new zzaj(zzit(), zzkO());
    }

    public long zzkQ() {
        zzis();
        zziE();
        if (this.zzPE == -1) {
            this.zzPE = this.zzPC.getLong("last_dispatch", 0L);
        }
        return this.zzPE;
    }

    public void zzkR() {
        zzis();
        zziE();
        long jCurrentTimeMillis = zzit().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzPC.edit();
        editorEdit.putLong("last_dispatch", jCurrentTimeMillis);
        editorEdit.apply();
        this.zzPE = jCurrentTimeMillis;
    }

    public String zzkS() {
        zzis();
        zziE();
        String string = this.zzPC.getString("installation_campaign", null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    public zza zzkT() {
        return this.zzPF;
    }
}
