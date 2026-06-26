package rx.android.app;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import rx.Observable;
import rx.android.internal.Assertions;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public final class AppObservable {
    private static final Func1<Activity, Boolean> ACTIVITY_VALIDATOR;
    private static final Func1<Fragment, Boolean> FRAGMENTV4_VALIDATOR;
    private static final Func1<android.app.Fragment, Boolean> FRAGMENT_VALIDATOR;
    public static final boolean USES_SUPPORT_FRAGMENTS;

    private AppObservable() {
        throw new AssertionError("No instances");
    }

    static {
        boolean supportFragmentsAvailable = false;
        try {
            Class.forName("android.support.v4.app.Fragment");
            supportFragmentsAvailable = true;
        } catch (ClassNotFoundException e) {
        }
        USES_SUPPORT_FRAGMENTS = supportFragmentsAvailable;
        ACTIVITY_VALIDATOR = new Func1<Activity, Boolean>() { // from class: rx.android.app.AppObservable.1
            @Override // rx.functions.Func1
            public Boolean call(Activity activity) {
                return Boolean.valueOf(!activity.isFinishing());
            }
        };
        FRAGMENT_VALIDATOR = new Func1<android.app.Fragment, Boolean>() { // from class: rx.android.app.AppObservable.2
            @Override // rx.functions.Func1
            public Boolean call(android.app.Fragment fragment) {
                return Boolean.valueOf(fragment.isAdded() && !fragment.getActivity().isFinishing());
            }
        };
        FRAGMENTV4_VALIDATOR = new Func1<Fragment, Boolean>() { // from class: rx.android.app.AppObservable.3
            @Override // rx.functions.Func1
            public Boolean call(Fragment fragment) {
                return Boolean.valueOf(fragment.isAdded() && !fragment.getActivity().isFinishing());
            }
        };
    }

    public static <T> Observable<T> bindActivity(Activity activity, Observable<T> observable) {
        Assertions.assertUiThread();
        return (Observable<T>) observable.observeOn(AndroidSchedulers.mainThread()).lift(new OperatorConditionalBinding(activity, ACTIVITY_VALIDATOR));
    }

    public static <T> Observable<T> bindFragment(Object obj, Observable<T> observable) {
        Assertions.assertUiThread();
        Observable<T> observableObserveOn = observable.observeOn(AndroidSchedulers.mainThread());
        if (USES_SUPPORT_FRAGMENTS && (obj instanceof Fragment)) {
            return (Observable<T>) observableObserveOn.lift(new OperatorConditionalBinding((Fragment) obj, FRAGMENTV4_VALIDATOR));
        }
        if (Build.VERSION.SDK_INT >= 11 && (obj instanceof android.app.Fragment)) {
            return (Observable<T>) observableObserveOn.lift(new OperatorConditionalBinding((android.app.Fragment) obj, FRAGMENT_VALIDATOR));
        }
        throw new IllegalArgumentException("Target fragment is neither a native nor support library Fragment");
    }
}
