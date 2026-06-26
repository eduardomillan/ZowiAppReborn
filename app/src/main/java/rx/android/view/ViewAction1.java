package rx.android.view;

import android.view.View;
import java.lang.ref.WeakReference;
import rx.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewAction1<V extends View, T> implements Action1<T> {
    private final WeakReference<V> viewReference;

    public abstract void call(V v, T t);

    public ViewAction1(V view) {
        this.viewReference = new WeakReference<>(view);
    }

    @Override // rx.functions.Action1
    public final void call(T t) {
        V view = this.viewReference.get();
        if (view != null) {
            call(view, t);
        }
    }
}
