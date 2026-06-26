package com.google.android.gms.internal;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class zznb {
    private static final Pattern zzaio = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzcA(String str) {
        return str == null || com.google.android.gms.common.internal.zze.zzaeL.zzb(str);
    }
}
