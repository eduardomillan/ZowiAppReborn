package com.bq.zowi.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/* JADX INFO: loaded from: classes.dex */
public class ToastUtils {
    private static final int LONG_DELAY = 3500;
    private static final int SHORT_DELAY = 2000;
    private static boolean isAnyToastVisible = false;

    public static void showNonOverlappingToast(Context context, int stringResId, int duration) {
        if (!isAnyToastVisible) {
            if (duration != 0 && duration != 1) {
                throw new IllegalArgumentException("Duration " + duration + " is not valid.");
            }
            long durationInMillis = duration == 0 ? 2000L : 3500L;
            isAnyToastVisible = true;
            Toast.makeText(context, stringResId, duration).show();
            new Handler().postDelayed(new Runnable() { // from class: com.bq.zowi.utils.ToastUtils.1
                @Override // java.lang.Runnable
                public void run() {
                    boolean unused = ToastUtils.isAnyToastVisible = false;
                }
            }, durationInMillis);
        }
    }
}
