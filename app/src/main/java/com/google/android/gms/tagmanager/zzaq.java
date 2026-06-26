package com.google.android.gms.tagmanager;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
class zzaq {
    private final long zzPg;
    private final long zzaXb;
    private final long zzaXc;
    private String zzaXd;

    zzaq(long j, long j2, long j3) {
        this.zzaXb = j;
        this.zzPg = j2;
        this.zzaXc = j3;
    }

    long zzCV() {
        return this.zzaXb;
    }

    long zzCW() {
        return this.zzaXc;
    }

    String zzCX() {
        return this.zzaXd;
    }

    void zzeQ(String str) {
        if (str == null || TextUtils.isEmpty(str.trim())) {
            return;
        }
        this.zzaXd = str;
    }
}
