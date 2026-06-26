package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface zzox {

    public static final class zza extends zzry<zza> {
        public C0092zza[] zzaCU;

        /* JADX INFO: renamed from: com.google.android.gms.internal.zzox$zza$zza, reason: collision with other inner class name */
        public static final class C0092zza extends zzry<C0092zza> {
            private static volatile C0092zza[] zzaCV;
            public int viewId;
            public String zzaCW;
            public String zzaCX;

            public C0092zza() {
                zzwe();
            }

            public static C0092zza[] zzwd() {
                if (zzaCV == null) {
                    synchronized (zzsc.zzbiu) {
                        if (zzaCV == null) {
                            zzaCV = new C0092zza[0];
                        }
                    }
                }
                return zzaCV;
            }

            public boolean equals(Object o) {
                if (o == this) {
                    return true;
                }
                if (!(o instanceof C0092zza)) {
                    return false;
                }
                C0092zza c0092zza = (C0092zza) o;
                if (this.zzaCW == null) {
                    if (c0092zza.zzaCW != null) {
                        return false;
                    }
                } else if (!this.zzaCW.equals(c0092zza.zzaCW)) {
                    return false;
                }
                if (this.zzaCX == null) {
                    if (c0092zza.zzaCX != null) {
                        return false;
                    }
                } else if (!this.zzaCX.equals(c0092zza.zzaCX)) {
                    return false;
                }
                if (this.viewId == c0092zza.viewId) {
                    return (this.zzbik == null || this.zzbik.isEmpty()) ? c0092zza.zzbik == null || c0092zza.zzbik.isEmpty() : this.zzbik.equals(c0092zza.zzbik);
                }
                return false;
            }

            public int hashCode() {
                int iHashCode = 0;
                int iHashCode2 = ((((this.zzaCX == null ? 0 : this.zzaCX.hashCode()) + (((this.zzaCW == null ? 0 : this.zzaCW.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + this.viewId) * 31;
                if (this.zzbik != null && !this.zzbik.isEmpty()) {
                    iHashCode = this.zzbik.hashCode();
                }
                return iHashCode2 + iHashCode;
            }

            @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
            protected int zzB() {
                int iZzB = super.zzB();
                if (!this.zzaCW.equals("")) {
                    iZzB += zzrx.zzn(1, this.zzaCW);
                }
                if (!this.zzaCX.equals("")) {
                    iZzB += zzrx.zzn(2, this.zzaCX);
                }
                return this.viewId != 0 ? iZzB + zzrx.zzA(3, this.viewId) : iZzB;
            }

            @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
            public void zza(zzrx zzrxVar) throws IOException {
                if (!this.zzaCW.equals("")) {
                    zzrxVar.zzb(1, this.zzaCW);
                }
                if (!this.zzaCX.equals("")) {
                    zzrxVar.zzb(2, this.zzaCX);
                }
                if (this.viewId != 0) {
                    zzrxVar.zzy(3, this.viewId);
                }
                super.zza(zzrxVar);
            }

            @Override // com.google.android.gms.internal.zzse
            /* JADX INFO: renamed from: zzq, reason: merged with bridge method [inline-methods] */
            public C0092zza zzb(zzrw zzrwVar) throws IOException {
                while (true) {
                    int iZzFo = zzrwVar.zzFo();
                    switch (iZzFo) {
                        case 0:
                            break;
                        case 10:
                            this.zzaCW = zzrwVar.readString();
                            break;
                        case 18:
                            this.zzaCX = zzrwVar.readString();
                            break;
                        case 24:
                            this.viewId = zzrwVar.zzFr();
                            break;
                        default:
                            if (!zza(zzrwVar, iZzFo)) {
                            }
                            break;
                    }
                }
                return this;
            }

            public C0092zza zzwe() {
                this.zzaCW = "";
                this.zzaCX = "";
                this.viewId = 0;
                this.zzbik = null;
                this.zzbiv = -1;
                return this;
            }
        }

        public zza() {
            zzwc();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) o;
            if (zzsc.equals(this.zzaCU, zzaVar.zzaCU)) {
                return (this.zzbik == null || this.zzbik.isEmpty()) ? zzaVar.zzbik == null || zzaVar.zzbik.isEmpty() : this.zzbik.equals(zzaVar.zzbik);
            }
            return false;
        }

        public int hashCode() {
            return ((this.zzbik == null || this.zzbik.isEmpty()) ? 0 : this.zzbik.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + zzsc.hashCode(this.zzaCU)) * 31);
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        protected int zzB() {
            int iZzB = super.zzB();
            if (this.zzaCU != null && this.zzaCU.length > 0) {
                for (int i = 0; i < this.zzaCU.length; i++) {
                    C0092zza c0092zza = this.zzaCU[i];
                    if (c0092zza != null) {
                        iZzB += zzrx.zzc(1, c0092zza);
                    }
                }
            }
            return iZzB;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        public void zza(zzrx zzrxVar) throws IOException {
            if (this.zzaCU != null && this.zzaCU.length > 0) {
                for (int i = 0; i < this.zzaCU.length; i++) {
                    C0092zza c0092zza = this.zzaCU[i];
                    if (c0092zza != null) {
                        zzrxVar.zza(1, c0092zza);
                    }
                }
            }
            super.zza(zzrxVar);
        }

        @Override // com.google.android.gms.internal.zzse
        /* JADX INFO: renamed from: zzp, reason: merged with bridge method [inline-methods] */
        public zza zzb(zzrw zzrwVar) throws IOException {
            while (true) {
                int iZzFo = zzrwVar.zzFo();
                switch (iZzFo) {
                    case 0:
                        break;
                    case 10:
                        int iZzc = zzsh.zzc(zzrwVar, 10);
                        int length = this.zzaCU == null ? 0 : this.zzaCU.length;
                        C0092zza[] c0092zzaArr = new C0092zza[iZzc + length];
                        if (length != 0) {
                            System.arraycopy(this.zzaCU, 0, c0092zzaArr, 0, length);
                        }
                        while (length < c0092zzaArr.length - 1) {
                            c0092zzaArr[length] = new C0092zza();
                            zzrwVar.zza(c0092zzaArr[length]);
                            zzrwVar.zzFo();
                            length++;
                        }
                        c0092zzaArr[length] = new C0092zza();
                        zzrwVar.zza(c0092zzaArr[length]);
                        this.zzaCU = c0092zzaArr;
                        break;
                    default:
                        if (!zza(zzrwVar, iZzFo)) {
                        }
                        break;
                }
            }
            return this;
        }

        public zza zzwc() {
            this.zzaCU = C0092zza.zzwd();
            this.zzbik = null;
            this.zzbiv = -1;
            return this;
        }
    }

    public static final class zzb extends zzry<zzb> {
        private static volatile zzb[] zzaCY;
        public String name;
        public zzd zzaCZ;

        public zzb() {
            zzwg();
        }

        public static zzb[] zzwf() {
            if (zzaCY == null) {
                synchronized (zzsc.zzbiu) {
                    if (zzaCY == null) {
                        zzaCY = new zzb[0];
                    }
                }
            }
            return zzaCY;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzb)) {
                return false;
            }
            zzb zzbVar = (zzb) o;
            if (this.name == null) {
                if (zzbVar.name != null) {
                    return false;
                }
            } else if (!this.name.equals(zzbVar.name)) {
                return false;
            }
            if (this.zzaCZ == null) {
                if (zzbVar.zzaCZ != null) {
                    return false;
                }
            } else if (!this.zzaCZ.equals(zzbVar.zzaCZ)) {
                return false;
            }
            return (this.zzbik == null || this.zzbik.isEmpty()) ? zzbVar.zzbik == null || zzbVar.zzbik.isEmpty() : this.zzbik.equals(zzbVar.zzbik);
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((this.zzaCZ == null ? 0 : this.zzaCZ.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
            if (this.zzbik != null && !this.zzbik.isEmpty()) {
                iHashCode = this.zzbik.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        protected int zzB() {
            int iZzB = super.zzB();
            if (!this.name.equals("")) {
                iZzB += zzrx.zzn(1, this.name);
            }
            return this.zzaCZ != null ? iZzB + zzrx.zzc(2, this.zzaCZ) : iZzB;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        public void zza(zzrx zzrxVar) throws IOException {
            if (!this.name.equals("")) {
                zzrxVar.zzb(1, this.name);
            }
            if (this.zzaCZ != null) {
                zzrxVar.zza(2, this.zzaCZ);
            }
            super.zza(zzrxVar);
        }

        @Override // com.google.android.gms.internal.zzse
        /* JADX INFO: renamed from: zzr, reason: merged with bridge method [inline-methods] */
        public zzb zzb(zzrw zzrwVar) throws IOException {
            while (true) {
                int iZzFo = zzrwVar.zzFo();
                switch (iZzFo) {
                    case 0:
                        break;
                    case 10:
                        this.name = zzrwVar.readString();
                        break;
                    case 18:
                        if (this.zzaCZ == null) {
                            this.zzaCZ = new zzd();
                        }
                        zzrwVar.zza(this.zzaCZ);
                        break;
                    default:
                        if (!zza(zzrwVar, iZzFo)) {
                        }
                        break;
                }
            }
            return this;
        }

        public zzb zzwg() {
            this.name = "";
            this.zzaCZ = null;
            this.zzbik = null;
            this.zzbiv = -1;
            return this;
        }
    }

    public static final class zzc extends zzry<zzc> {
        public String type;
        public zzb[] zzaDa;

        public zzc() {
            zzwh();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzc)) {
                return false;
            }
            zzc zzcVar = (zzc) o;
            if (this.type == null) {
                if (zzcVar.type != null) {
                    return false;
                }
            } else if (!this.type.equals(zzcVar.type)) {
                return false;
            }
            if (zzsc.equals(this.zzaDa, zzcVar.zzaDa)) {
                return (this.zzbik == null || this.zzbik.isEmpty()) ? zzcVar.zzbik == null || zzcVar.zzbik.isEmpty() : this.zzbik.equals(zzcVar.zzbik);
            }
            return false;
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((((this.type == null ? 0 : this.type.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzsc.hashCode(this.zzaDa)) * 31;
            if (this.zzbik != null && !this.zzbik.isEmpty()) {
                iHashCode = this.zzbik.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        protected int zzB() {
            int iZzB = super.zzB();
            if (!this.type.equals("")) {
                iZzB += zzrx.zzn(1, this.type);
            }
            if (this.zzaDa == null || this.zzaDa.length <= 0) {
                return iZzB;
            }
            int iZzc = iZzB;
            for (int i = 0; i < this.zzaDa.length; i++) {
                zzb zzbVar = this.zzaDa[i];
                if (zzbVar != null) {
                    iZzc += zzrx.zzc(2, zzbVar);
                }
            }
            return iZzc;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        public void zza(zzrx zzrxVar) throws IOException {
            if (!this.type.equals("")) {
                zzrxVar.zzb(1, this.type);
            }
            if (this.zzaDa != null && this.zzaDa.length > 0) {
                for (int i = 0; i < this.zzaDa.length; i++) {
                    zzb zzbVar = this.zzaDa[i];
                    if (zzbVar != null) {
                        zzrxVar.zza(2, zzbVar);
                    }
                }
            }
            super.zza(zzrxVar);
        }

        @Override // com.google.android.gms.internal.zzse
        /* JADX INFO: renamed from: zzs, reason: merged with bridge method [inline-methods] */
        public zzc zzb(zzrw zzrwVar) throws IOException {
            while (true) {
                int iZzFo = zzrwVar.zzFo();
                switch (iZzFo) {
                    case 0:
                        break;
                    case 10:
                        this.type = zzrwVar.readString();
                        break;
                    case 18:
                        int iZzc = zzsh.zzc(zzrwVar, 18);
                        int length = this.zzaDa == null ? 0 : this.zzaDa.length;
                        zzb[] zzbVarArr = new zzb[iZzc + length];
                        if (length != 0) {
                            System.arraycopy(this.zzaDa, 0, zzbVarArr, 0, length);
                        }
                        while (length < zzbVarArr.length - 1) {
                            zzbVarArr[length] = new zzb();
                            zzrwVar.zza(zzbVarArr[length]);
                            zzrwVar.zzFo();
                            length++;
                        }
                        zzbVarArr[length] = new zzb();
                        zzrwVar.zza(zzbVarArr[length]);
                        this.zzaDa = zzbVarArr;
                        break;
                    default:
                        if (!zza(zzrwVar, iZzFo)) {
                        }
                        break;
                }
            }
            return this;
        }

        public zzc zzwh() {
            this.type = "";
            this.zzaDa = zzb.zzwf();
            this.zzbik = null;
            this.zzbiv = -1;
            return this;
        }
    }

    public static final class zzd extends zzry<zzd> {
        public boolean zzaDb;
        public long zzaDc;
        public double zzaDd;
        public zzc zzaDe;
        public String zzagS;

        public zzd() {
            zzwi();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzd)) {
                return false;
            }
            zzd zzdVar = (zzd) o;
            if (this.zzaDb != zzdVar.zzaDb) {
                return false;
            }
            if (this.zzagS == null) {
                if (zzdVar.zzagS != null) {
                    return false;
                }
            } else if (!this.zzagS.equals(zzdVar.zzagS)) {
                return false;
            }
            if (this.zzaDc != zzdVar.zzaDc || Double.doubleToLongBits(this.zzaDd) != Double.doubleToLongBits(zzdVar.zzaDd)) {
                return false;
            }
            if (this.zzaDe == null) {
                if (zzdVar.zzaDe != null) {
                    return false;
                }
            } else if (!this.zzaDe.equals(zzdVar.zzaDe)) {
                return false;
            }
            return (this.zzbik == null || this.zzbik.isEmpty()) ? zzdVar.zzbik == null || zzdVar.zzbik.isEmpty() : this.zzbik.equals(zzdVar.zzbik);
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = (((this.zzagS == null ? 0 : this.zzagS.hashCode()) + (((this.zzaDb ? 1231 : 1237) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + ((int) (this.zzaDc ^ (this.zzaDc >>> 32)));
            long jDoubleToLongBits = Double.doubleToLongBits(this.zzaDd);
            int iHashCode3 = ((this.zzaDe == null ? 0 : this.zzaDe.hashCode()) + (((iHashCode2 * 31) + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)))) * 31)) * 31;
            if (this.zzbik != null && !this.zzbik.isEmpty()) {
                iHashCode = this.zzbik.hashCode();
            }
            return iHashCode3 + iHashCode;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        protected int zzB() {
            int iZzB = super.zzB();
            if (this.zzaDb) {
                iZzB += zzrx.zzc(1, this.zzaDb);
            }
            if (!this.zzagS.equals("")) {
                iZzB += zzrx.zzn(2, this.zzagS);
            }
            if (this.zzaDc != 0) {
                iZzB += zzrx.zzd(3, this.zzaDc);
            }
            if (Double.doubleToLongBits(this.zzaDd) != Double.doubleToLongBits(0.0d)) {
                iZzB += zzrx.zzb(4, this.zzaDd);
            }
            return this.zzaDe != null ? iZzB + zzrx.zzc(5, this.zzaDe) : iZzB;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        public void zza(zzrx zzrxVar) throws IOException {
            if (this.zzaDb) {
                zzrxVar.zzb(1, this.zzaDb);
            }
            if (!this.zzagS.equals("")) {
                zzrxVar.zzb(2, this.zzagS);
            }
            if (this.zzaDc != 0) {
                zzrxVar.zzb(3, this.zzaDc);
            }
            if (Double.doubleToLongBits(this.zzaDd) != Double.doubleToLongBits(0.0d)) {
                zzrxVar.zza(4, this.zzaDd);
            }
            if (this.zzaDe != null) {
                zzrxVar.zza(5, this.zzaDe);
            }
            super.zza(zzrxVar);
        }

        @Override // com.google.android.gms.internal.zzse
        /* JADX INFO: renamed from: zzt, reason: merged with bridge method [inline-methods] */
        public zzd zzb(zzrw zzrwVar) throws IOException {
            while (true) {
                int iZzFo = zzrwVar.zzFo();
                switch (iZzFo) {
                    case 0:
                        break;
                    case 8:
                        this.zzaDb = zzrwVar.zzFs();
                        break;
                    case 18:
                        this.zzagS = zzrwVar.readString();
                        break;
                    case 24:
                        this.zzaDc = zzrwVar.zzFq();
                        break;
                    case 33:
                        this.zzaDd = zzrwVar.readDouble();
                        break;
                    case 42:
                        if (this.zzaDe == null) {
                            this.zzaDe = new zzc();
                        }
                        zzrwVar.zza(this.zzaDe);
                        break;
                    default:
                        if (!zza(zzrwVar, iZzFo)) {
                        }
                        break;
                }
            }
            return this;
        }

        public zzd zzwi() {
            this.zzaDb = false;
            this.zzagS = "";
            this.zzaDc = 0L;
            this.zzaDd = 0.0d;
            this.zzaDe = null;
            this.zzbik = null;
            this.zzbiv = -1;
            return this;
        }
    }
}
