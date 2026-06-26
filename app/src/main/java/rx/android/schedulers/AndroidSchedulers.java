package rx.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import rx.Scheduler;

/* JADX INFO: loaded from: classes.dex */
public final class AndroidSchedulers {
    private static final Scheduler MAIN_THREAD_SCHEDULER = new HandlerThreadScheduler(new Handler(Looper.getMainLooper()));

    private AndroidSchedulers() {
        throw new AssertionError("No instances");
    }

    public static Scheduler handlerThread(Handler handler) {
        return new HandlerThreadScheduler(handler);
    }

    public static Scheduler mainThread() {
        return MAIN_THREAD_SCHEDULER;
    }
}
