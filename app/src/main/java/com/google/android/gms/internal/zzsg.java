package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class zzsg {
    final int tag;
    final byte[] zzbiw;

    zzsg(int i, byte[] bArr) {
        this.tag = i;
        this.zzbiw = bArr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzsg)) {
            return false;
        }
        zzsg zzsgVar = (zzsg) o;
        return this.tag == zzsgVar.tag && Arrays.equals(this.zzbiw, zzsgVar.zzbiw);
    }

    public int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbiw);
    }

    int zzB() {
        return 0 + zzrx.zzlO(this.tag) + this.zzbiw.length;
    }

    void zza(zzrx zzrxVar) throws IOException {
        zzrxVar.zzlN(this.tag);
        zzrxVar.zzF(this.zzbiw);
    }
}
