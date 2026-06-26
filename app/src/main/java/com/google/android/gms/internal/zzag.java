package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface zzag {

    public static final class zza extends zzry<zza> {
        private static volatile zza[] zziT;
        public int type;
        public String zziU;
        public zza[] zziV;
        public zza[] zziW;
        public zza[] zziX;
        public String zziY;
        public String zziZ;
        public long zzja;
        public boolean zzjb;
        public zza[] zzjc;
        public int[] zzjd;
        public boolean zzje;

        public zza() {
            zzR();
        }

        public static zza[] zzQ() {
            if (zziT == null) {
                synchronized (zzsc.zzbiu) {
                    if (zziT == null) {
                        zziT = new zza[0];
                    }
                }
            }
            return zziT;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) o;
            if (this.type != zzaVar.type) {
                return false;
            }
            if (this.zziU == null) {
                if (zzaVar.zziU != null) {
                    return false;
                }
            } else if (!this.zziU.equals(zzaVar.zziU)) {
                return false;
            }
            if (!zzsc.equals(this.zziV, zzaVar.zziV) || !zzsc.equals(this.zziW, zzaVar.zziW) || !zzsc.equals(this.zziX, zzaVar.zziX)) {
                return false;
            }
            if (this.zziY == null) {
                if (zzaVar.zziY != null) {
                    return false;
                }
            } else if (!this.zziY.equals(zzaVar.zziY)) {
                return false;
            }
            if (this.zziZ == null) {
                if (zzaVar.zziZ != null) {
                    return false;
                }
            } else if (!this.zziZ.equals(zzaVar.zziZ)) {
                return false;
            }
            if (this.zzja == zzaVar.zzja && this.zzjb == zzaVar.zzjb && zzsc.equals(this.zzjc, zzaVar.zzjc) && zzsc.equals(this.zzjd, zzaVar.zzjd) && this.zzje == zzaVar.zzje) {
                return (this.zzbik == null || this.zzbik.isEmpty()) ? zzaVar.zzbik == null || zzaVar.zzbik.isEmpty() : this.zzbik.equals(zzaVar.zzbik);
            }
            return false;
        }

        public int hashCode() {
            int iHashCode = 0;
            int iHashCode2 = ((((((((this.zzjb ? 1231 : 1237) + (((((this.zziZ == null ? 0 : this.zziZ.hashCode()) + (((this.zziY == null ? 0 : this.zziY.hashCode()) + (((((((((this.zziU == null ? 0 : this.zziU.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31)) * 31) + zzsc.hashCode(this.zziV)) * 31) + zzsc.hashCode(this.zziW)) * 31) + zzsc.hashCode(this.zziX)) * 31)) * 31)) * 31) + ((int) (this.zzja ^ (this.zzja >>> 32)))) * 31)) * 31) + zzsc.hashCode(this.zzjc)) * 31) + zzsc.hashCode(this.zzjd)) * 31) + (this.zzje ? 1231 : 1237)) * 31;
            if (this.zzbik != null && !this.zzbik.isEmpty()) {
                iHashCode = this.zzbik.hashCode();
            }
            return iHashCode2 + iHashCode;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        protected int zzB() {
            int iZzB = super.zzB() + zzrx.zzA(1, this.type);
            if (!this.zziU.equals("")) {
                iZzB += zzrx.zzn(2, this.zziU);
            }
            if (this.zziV != null && this.zziV.length > 0) {
                int iZzc = iZzB;
                for (int i = 0; i < this.zziV.length; i++) {
                    zza zzaVar = this.zziV[i];
                    if (zzaVar != null) {
                        iZzc += zzrx.zzc(3, zzaVar);
                    }
                }
                iZzB = iZzc;
            }
            if (this.zziW != null && this.zziW.length > 0) {
                int iZzc2 = iZzB;
                for (int i2 = 0; i2 < this.zziW.length; i2++) {
                    zza zzaVar2 = this.zziW[i2];
                    if (zzaVar2 != null) {
                        iZzc2 += zzrx.zzc(4, zzaVar2);
                    }
                }
                iZzB = iZzc2;
            }
            if (this.zziX != null && this.zziX.length > 0) {
                int iZzc3 = iZzB;
                for (int i3 = 0; i3 < this.zziX.length; i3++) {
                    zza zzaVar3 = this.zziX[i3];
                    if (zzaVar3 != null) {
                        iZzc3 += zzrx.zzc(5, zzaVar3);
                    }
                }
                iZzB = iZzc3;
            }
            if (!this.zziY.equals("")) {
                iZzB += zzrx.zzn(6, this.zziY);
            }
            if (!this.zziZ.equals("")) {
                iZzB += zzrx.zzn(7, this.zziZ);
            }
            if (this.zzja != 0) {
                iZzB += zzrx.zzd(8, this.zzja);
            }
            if (this.zzje) {
                iZzB += zzrx.zzc(9, this.zzje);
            }
            if (this.zzjd != null && this.zzjd.length > 0) {
                int iZzlJ = 0;
                for (int i4 = 0; i4 < this.zzjd.length; i4++) {
                    iZzlJ += zzrx.zzlJ(this.zzjd[i4]);
                }
                iZzB = iZzB + iZzlJ + (this.zzjd.length * 1);
            }
            if (this.zzjc != null && this.zzjc.length > 0) {
                for (int i5 = 0; i5 < this.zzjc.length; i5++) {
                    zza zzaVar4 = this.zzjc[i5];
                    if (zzaVar4 != null) {
                        iZzB += zzrx.zzc(11, zzaVar4);
                    }
                }
            }
            return this.zzjb ? iZzB + zzrx.zzc(12, this.zzjb) : iZzB;
        }

        public zza zzR() {
            this.type = 1;
            this.zziU = "";
            this.zziV = zzQ();
            this.zziW = zzQ();
            this.zziX = zzQ();
            this.zziY = "";
            this.zziZ = "";
            this.zzja = 0L;
            this.zzjb = false;
            this.zzjc = zzQ();
            this.zzjd = zzsh.zzbix;
            this.zzje = false;
            this.zzbik = null;
            this.zzbiv = -1;
            return this;
        }

        @Override // com.google.android.gms.internal.zzry, com.google.android.gms.internal.zzse
        public void zza(zzrx zzrxVar) throws IOException {
            zzrxVar.zzy(1, this.type);
            if (!this.zziU.equals("")) {
                zzrxVar.zzb(2, this.zziU);
            }
            if (this.zziV != null && this.zziV.length > 0) {
                for (int i = 0; i < this.zziV.length; i++) {
                    zza zzaVar = this.zziV[i];
                    if (zzaVar != null) {
                        zzrxVar.zza(3, zzaVar);
                    }
                }
            }
            if (this.zziW != null && this.zziW.length > 0) {
                for (int i2 = 0; i2 < this.zziW.length; i2++) {
                    zza zzaVar2 = this.zziW[i2];
                    if (zzaVar2 != null) {
                        zzrxVar.zza(4, zzaVar2);
                    }
                }
            }
            if (this.zziX != null && this.zziX.length > 0) {
                for (int i3 = 0; i3 < this.zziX.length; i3++) {
                    zza zzaVar3 = this.zziX[i3];
                    if (zzaVar3 != null) {
                        zzrxVar.zza(5, zzaVar3);
                    }
                }
            }
            if (!this.zziY.equals("")) {
                zzrxVar.zzb(6, this.zziY);
            }
            if (!this.zziZ.equals("")) {
                zzrxVar.zzb(7, this.zziZ);
            }
            if (this.zzja != 0) {
                zzrxVar.zzb(8, this.zzja);
            }
            if (this.zzje) {
                zzrxVar.zzb(9, this.zzje);
            }
            if (this.zzjd != null && this.zzjd.length > 0) {
                for (int i4 = 0; i4 < this.zzjd.length; i4++) {
                    zzrxVar.zzy(10, this.zzjd[i4]);
                }
            }
            if (this.zzjc != null && this.zzjc.length > 0) {
                for (int i5 = 0; i5 < this.zzjc.length; i5++) {
                    zza zzaVar4 = this.zzjc[i5];
                    if (zzaVar4 != null) {
                        zzrxVar.zza(11, zzaVar4);
                    }
                }
            }
            if (this.zzjb) {
                zzrxVar.zzb(12, this.zzjb);
            }
            super.zza(zzrxVar);
        }

        @Override // com.google.android.gms.internal.zzse
        /* JADX INFO: renamed from: zzl, reason: merged with bridge method [inline-methods] */
        public zza zzb(zzrw zzrwVar) throws IOException {
            int i;
            while (true) {
                int iZzFo = zzrwVar.zzFo();
                switch (iZzFo) {
                    case 0:
                        break;
                    case 8:
                        int iZzFr = zzrwVar.zzFr();
                        switch (iZzFr) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                this.type = iZzFr;
                                break;
                        }
                        break;
                    case 18:
                        this.zziU = zzrwVar.readString();
                        break;
                    case 26:
                        int iZzc = zzsh.zzc(zzrwVar, 26);
                        int length = this.zziV == null ? 0 : this.zziV.length;
                        zza[] zzaVarArr = new zza[iZzc + length];
                        if (length != 0) {
                            System.arraycopy(this.zziV, 0, zzaVarArr, 0, length);
                        }
                        while (length < zzaVarArr.length - 1) {
                            zzaVarArr[length] = new zza();
                            zzrwVar.zza(zzaVarArr[length]);
                            zzrwVar.zzFo();
                            length++;
                        }
                        zzaVarArr[length] = new zza();
                        zzrwVar.zza(zzaVarArr[length]);
                        this.zziV = zzaVarArr;
                        break;
                    case 34:
                        int iZzc2 = zzsh.zzc(zzrwVar, 34);
                        int length2 = this.zziW == null ? 0 : this.zziW.length;
                        zza[] zzaVarArr2 = new zza[iZzc2 + length2];
                        if (length2 != 0) {
                            System.arraycopy(this.zziW, 0, zzaVarArr2, 0, length2);
                        }
                        while (length2 < zzaVarArr2.length - 1) {
                            zzaVarArr2[length2] = new zza();
                            zzrwVar.zza(zzaVarArr2[length2]);
                            zzrwVar.zzFo();
                            length2++;
                        }
                        zzaVarArr2[length2] = new zza();
                        zzrwVar.zza(zzaVarArr2[length2]);
                        this.zziW = zzaVarArr2;
                        break;
                    case 42:
                        int iZzc3 = zzsh.zzc(zzrwVar, 42);
                        int length3 = this.zziX == null ? 0 : this.zziX.length;
                        zza[] zzaVarArr3 = new zza[iZzc3 + length3];
                        if (length3 != 0) {
                            System.arraycopy(this.zziX, 0, zzaVarArr3, 0, length3);
                        }
                        while (length3 < zzaVarArr3.length - 1) {
                            zzaVarArr3[length3] = new zza();
                            zzrwVar.zza(zzaVarArr3[length3]);
                            zzrwVar.zzFo();
                            length3++;
                        }
                        zzaVarArr3[length3] = new zza();
                        zzrwVar.zza(zzaVarArr3[length3]);
                        this.zziX = zzaVarArr3;
                        break;
                    case 50:
                        this.zziY = zzrwVar.readString();
                        break;
                    case 58:
                        this.zziZ = zzrwVar.readString();
                        break;
                    case 64:
                        this.zzja = zzrwVar.zzFq();
                        break;
                    case 72:
                        this.zzje = zzrwVar.zzFs();
                        break;
                    case 80:
                        int iZzc4 = zzsh.zzc(zzrwVar, 80);
                        int[] iArr = new int[iZzc4];
                        int i2 = 0;
                        int i3 = 0;
                        while (i2 < iZzc4) {
                            if (i2 != 0) {
                                zzrwVar.zzFo();
                            }
                            int iZzFr2 = zzrwVar.zzFr();
                            switch (iZzFr2) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    i = i3 + 1;
                                    iArr[i3] = iZzFr2;
                                    break;
                                default:
                                    i = i3;
                                    break;
                            }
                            i2++;
                            i3 = i;
                        }
                        if (i3 != 0) {
                            int length4 = this.zzjd == null ? 0 : this.zzjd.length;
                            if (length4 == 0 && i3 == iArr.length) {
                                this.zzjd = iArr;
                            } else {
                                int[] iArr2 = new int[length4 + i3];
                                if (length4 != 0) {
                                    System.arraycopy(this.zzjd, 0, iArr2, 0, length4);
                                }
                                System.arraycopy(iArr, 0, iArr2, length4, i3);
                                this.zzjd = iArr2;
                            }
                        }
                        break;
                    case 82:
                        int iZzlC = zzrwVar.zzlC(zzrwVar.zzFv());
                        int position = zzrwVar.getPosition();
                        int i4 = 0;
                        while (zzrwVar.zzFA() > 0) {
                            switch (zzrwVar.zzFr()) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    i4++;
                                    break;
                            }
                        }
                        if (i4 != 0) {
                            zzrwVar.zzlE(position);
                            int length5 = this.zzjd == null ? 0 : this.zzjd.length;
                            int[] iArr3 = new int[i4 + length5];
                            if (length5 != 0) {
                                System.arraycopy(this.zzjd, 0, iArr3, 0, length5);
                            }
                            while (zzrwVar.zzFA() > 0) {
                                int iZzFr3 = zzrwVar.zzFr();
                                switch (iZzFr3) {
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                    case 7:
                                    case 8:
                                    case 9:
                                    case 10:
                                    case 11:
                                    case 12:
                                    case 13:
                                    case 14:
                                    case 15:
                                    case 16:
                                    case 17:
                                        iArr3[length5] = iZzFr3;
                                        length5++;
                                        break;
                                }
                            }
                            this.zzjd = iArr3;
                        }
                        zzrwVar.zzlD(iZzlC);
                        break;
                    case 90:
                        int iZzc5 = zzsh.zzc(zzrwVar, 90);
                        int length6 = this.zzjc == null ? 0 : this.zzjc.length;
                        zza[] zzaVarArr4 = new zza[iZzc5 + length6];
                        if (length6 != 0) {
                            System.arraycopy(this.zzjc, 0, zzaVarArr4, 0, length6);
                        }
                        while (length6 < zzaVarArr4.length - 1) {
                            zzaVarArr4[length6] = new zza();
                            zzrwVar.zza(zzaVarArr4[length6]);
                            zzrwVar.zzFo();
                            length6++;
                        }
                        zzaVarArr4[length6] = new zza();
                        zzrwVar.zza(zzaVarArr4[length6]);
                        this.zzjc = zzaVarArr4;
                        break;
                    case 96:
                        this.zzjb = zzrwVar.zzFs();
                        break;
                    default:
                        if (!zza(zzrwVar, iZzFo)) {
                        }
                        break;
                }
            }
            return this;
        }
    }
}
