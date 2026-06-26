package rx.android.view;

import android.view.View;
import rx.Observable;
import rx.android.internal.Assertions;
import rx.android.schedulers.AndroidSchedulers;

/* JADX INFO: loaded from: classes.dex */
public final class ViewObservable {
    private ViewObservable() {
        throw new AssertionError("No instances");
    }

    public static Observable<OnClickEvent> clicks(View view) {
        return clicks(view, false);
    }

    public static Observable<OnClickEvent> clicks(View view, boolean emitInitialValue) {
        return Observable.create(new OnSubscribeViewClick(view, emitInitialValue));
    }

    public static <T> Observable<T> bindView(View view, Observable<T> source) {
        if (view == null || source == null) {
            throw new IllegalArgumentException("View and Observable must be given");
        }
        Assertions.assertUiThread();
        return source.takeUntil(Observable.create(new OnSubscribeViewDetachedFromWindowFirst(view))).observeOn(AndroidSchedulers.mainThread());
    }
}
