package com.google.android.gms.internal;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public class zziu {
    public static void zza(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        new zziv(view, onGlobalLayoutListener).zzgW();
    }

    public static void zza(View view, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        new zziw(view, onScrollChangedListener).zzgW();
    }
}
