package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class zzas implements zzaq {
    private zzrx zznG;
    private byte[] zznH;
    private final int zznI;

    public zzas(int i) {
        this.zznI = i;
        reset();
    }

    @Override // com.google.android.gms.internal.zzaq
    public void reset() {
        this.zznH = new byte[this.zznI];
        this.zznG = zzrx.zzC(this.zznH);
    }

    @Override // com.google.android.gms.internal.zzaq
    public byte[] zzac() throws IOException {
        int iZzFD = this.zznG.zzFD();
        if (iZzFD < 0) {
            throw new IOException();
        }
        if (iZzFD == 0) {
            return this.zznH;
        }
        byte[] bArr = new byte[this.zznH.length - iZzFD];
        System.arraycopy(this.zznH, 0, bArr, 0, bArr.length);
        return bArr;
    }

    @Override // com.google.android.gms.internal.zzaq
    public void zzb(int i, long j) throws IOException {
        this.zznG.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.zzaq
    public void zzb(int i, String str) throws IOException {
        this.zznG.zzb(i, str);
    }
}
