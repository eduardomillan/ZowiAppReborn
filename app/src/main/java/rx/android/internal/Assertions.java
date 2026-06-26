package rx.android.internal;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public final class Assertions {
    private Assertions() {
        throw new AssertionError("No instances");
    }

    public static void assertUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Observers must subscribe from the main UI thread, but was " + Thread.currentThread());
        }
    }
}
