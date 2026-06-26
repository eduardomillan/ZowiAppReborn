package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
public final class zzsa implements Cloneable {
    private static final zzsb zzbin = new zzsb();
    private int mSize;
    private boolean zzbio;
    private int[] zzbip;
    private zzsb[] zzbiq;

    zzsa() {
        this(10);
    }

    zzsa(int i) {
        this.zzbio = false;
        int iIdealIntArraySize = idealIntArraySize(i);
        this.zzbip = new int[iIdealIntArraySize];
        this.zzbiq = new zzsb[iIdealIntArraySize];
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.zzbip;
        zzsb[] zzsbVarArr = this.zzbiq;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            zzsb zzsbVar = zzsbVarArr[i3];
            if (zzsbVar != zzbin) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    zzsbVarArr[i2] = zzsbVar;
                    zzsbVarArr[i3] = null;
                }
                i2++;
            }
        }
        this.zzbio = false;
        this.mSize = i2;
    }

    private int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++) {
            if (need <= (1 << i) - 12) {
                return (1 << i) - 12;
            }
        }
        return need;
    }

    private int idealIntArraySize(int need) {
        return idealByteArraySize(need * 4) / 4;
    }

    private boolean zza(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean zza(zzsb[] zzsbVarArr, zzsb[] zzsbVarArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!zzsbVarArr[i2].equals(zzsbVarArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    private int zzlT(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzbip[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else {
                if (i5 <= i) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return i2 ^ (-1);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzsa)) {
            return false;
        }
        zzsa zzsaVar = (zzsa) o;
        if (size() != zzsaVar.size()) {
            return false;
        }
        return zza(this.zzbip, zzsaVar.zzbip, this.mSize) && zza(this.zzbiq, zzsaVar.zzbiq, this.mSize);
    }

    public int hashCode() {
        if (this.zzbio) {
            gc();
        }
        int iHashCode = 17;
        for (int i = 0; i < this.mSize; i++) {
            iHashCode = (((iHashCode * 31) + this.zzbip[i]) * 31) + this.zzbiq[i].hashCode();
        }
        return iHashCode;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    int size() {
        if (this.zzbio) {
            gc();
        }
        return this.mSize;
    }

    /* JADX INFO: renamed from: zzFH, reason: merged with bridge method [inline-methods] */
    public final zzsa clone() {
        int size = size();
        zzsa zzsaVar = new zzsa(size);
        System.arraycopy(this.zzbip, 0, zzsaVar.zzbip, 0, size);
        for (int i = 0; i < size; i++) {
            if (this.zzbiq[i] != null) {
                zzsaVar.zzbiq[i] = this.zzbiq[i].clone();
            }
        }
        zzsaVar.mSize = size;
        return zzsaVar;
    }

    void zza(int i, zzsb zzsbVar) {
        int iZzlT = zzlT(i);
        if (iZzlT >= 0) {
            this.zzbiq[iZzlT] = zzsbVar;
            return;
        }
        int iZzlT2 = iZzlT ^ (-1);
        if (iZzlT2 < this.mSize && this.zzbiq[iZzlT2] == zzbin) {
            this.zzbip[iZzlT2] = i;
            this.zzbiq[iZzlT2] = zzsbVar;
            return;
        }
        if (this.zzbio && this.mSize >= this.zzbip.length) {
            gc();
            iZzlT2 = zzlT(i) ^ (-1);
        }
        if (this.mSize >= this.zzbip.length) {
            int iIdealIntArraySize = idealIntArraySize(this.mSize + 1);
            int[] iArr = new int[iIdealIntArraySize];
            zzsb[] zzsbVarArr = new zzsb[iIdealIntArraySize];
            System.arraycopy(this.zzbip, 0, iArr, 0, this.zzbip.length);
            System.arraycopy(this.zzbiq, 0, zzsbVarArr, 0, this.zzbiq.length);
            this.zzbip = iArr;
            this.zzbiq = zzsbVarArr;
        }
        if (this.mSize - iZzlT2 != 0) {
            System.arraycopy(this.zzbip, iZzlT2, this.zzbip, iZzlT2 + 1, this.mSize - iZzlT2);
            System.arraycopy(this.zzbiq, iZzlT2, this.zzbiq, iZzlT2 + 1, this.mSize - iZzlT2);
        }
        this.zzbip[iZzlT2] = i;
        this.zzbiq[iZzlT2] = zzsbVar;
        this.mSize++;
    }

    zzsb zzlR(int i) {
        int iZzlT = zzlT(i);
        if (iZzlT < 0 || this.zzbiq[iZzlT] == zzbin) {
            return null;
        }
        return this.zzbiq[iZzlT];
    }

    zzsb zzlS(int i) {
        if (this.zzbio) {
            gc();
        }
        return this.zzbiq[i];
    }
}
