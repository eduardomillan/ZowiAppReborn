package rx.android.lifecycle;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/* JADX INFO: loaded from: classes.dex */
public class LifecycleObservable {
    private static final Func1<LifecycleEvent, LifecycleEvent> ACTIVITY_LIFECYCLE = new Func1<LifecycleEvent, LifecycleEvent>() { // from class: rx.android.lifecycle.LifecycleObservable.4
        @Override // rx.functions.Func1
        public LifecycleEvent call(LifecycleEvent lastEvent) {
            if (lastEvent == null) {
                throw new NullPointerException("Cannot bind to null LifecycleEvent.");
            }
            switch (AnonymousClass6.$SwitchMap$rx$android$lifecycle$LifecycleEvent[lastEvent.ordinal()]) {
                case 1:
                    return LifecycleEvent.DESTROY;
                case 2:
                    return LifecycleEvent.STOP;
                case 3:
                    return LifecycleEvent.PAUSE;
                case 4:
                    return LifecycleEvent.STOP;
                case 5:
                    return LifecycleEvent.DESTROY;
                case 6:
                    throw new IllegalStateException("Cannot bind to Activity lifecycle when outside of it.");
                case 7:
                case 8:
                case 9:
                case 10:
                    throw new IllegalStateException("Cannot bind to " + lastEvent + " for an Activity.");
                default:
                    throw new UnsupportedOperationException("Binding to LifecycleEvent " + lastEvent + " not yet implemented");
            }
        }
    };
    private static final Func1<LifecycleEvent, LifecycleEvent> FRAGMENT_LIFECYCLE = new Func1<LifecycleEvent, LifecycleEvent>() { // from class: rx.android.lifecycle.LifecycleObservable.5
        @Override // rx.functions.Func1
        public LifecycleEvent call(LifecycleEvent lastEvent) {
            if (lastEvent == null) {
                throw new NullPointerException("Cannot bind to null LifecycleEvent.");
            }
            switch (AnonymousClass6.$SwitchMap$rx$android$lifecycle$LifecycleEvent[lastEvent.ordinal()]) {
                case 1:
                    return LifecycleEvent.DESTROY;
                case 2:
                    return LifecycleEvent.STOP;
                case 3:
                    return LifecycleEvent.PAUSE;
                case 4:
                    return LifecycleEvent.STOP;
                case 5:
                    return LifecycleEvent.DESTROY_VIEW;
                case 6:
                    return LifecycleEvent.DETACH;
                case 7:
                    return LifecycleEvent.DETACH;
                case 8:
                    return LifecycleEvent.DESTROY_VIEW;
                case 9:
                    return LifecycleEvent.DESTROY;
                case 10:
                    throw new IllegalStateException("Cannot bind to Fragment lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to LifecycleEvent " + lastEvent + " not yet implemented");
            }
        }
    };

    private LifecycleObservable() {
        throw new AssertionError("No instances");
    }

    public static <T> Observable<T> bindUntilLifecycleEvent(Observable<LifecycleEvent> observable, Observable<T> observable2, final LifecycleEvent lifecycleEvent) {
        if (observable == null || observable2 == null) {
            throw new IllegalArgumentException("Lifecycle and Observable must be given");
        }
        return (Observable<T>) observable2.lift(new OperatorSubscribeUntil(observable.takeFirst(new Func1<LifecycleEvent, Boolean>() { // from class: rx.android.lifecycle.LifecycleObservable.1
            @Override // rx.functions.Func1
            public Boolean call(LifecycleEvent lifecycleEvent2) {
                return Boolean.valueOf(lifecycleEvent2 == lifecycleEvent);
            }
        })));
    }

    public static <T> Observable<T> bindActivityLifecycle(Observable<LifecycleEvent> lifecycle, Observable<T> source) {
        return bindLifecycle(lifecycle, source, ACTIVITY_LIFECYCLE);
    }

    public static <T> Observable<T> bindFragmentLifecycle(Observable<LifecycleEvent> lifecycle, Observable<T> source) {
        return bindLifecycle(lifecycle, source, FRAGMENT_LIFECYCLE);
    }

    private static <T> Observable<T> bindLifecycle(Observable<LifecycleEvent> observable, Observable<T> observable2, Func1<LifecycleEvent, LifecycleEvent> func1) {
        if (observable == null || observable2 == null) {
            throw new IllegalArgumentException("Lifecycle and Observable must be given");
        }
        Observable<LifecycleEvent> observableShare = observable.share();
        return (Observable<T>) observable2.lift(new OperatorSubscribeUntil(Observable.combineLatest(observableShare.take(1).map(func1), observableShare.skip(1), new Func2<LifecycleEvent, LifecycleEvent, Boolean>() { // from class: rx.android.lifecycle.LifecycleObservable.3
            @Override // rx.functions.Func2
            public Boolean call(LifecycleEvent bindUntilEvent, LifecycleEvent lifecycleEvent) {
                return Boolean.valueOf(lifecycleEvent == bindUntilEvent);
            }
        }).takeFirst(new Func1<Boolean, Boolean>() { // from class: rx.android.lifecycle.LifecycleObservable.2
            @Override // rx.functions.Func1
            public Boolean call(Boolean shouldComplete) {
                return shouldComplete;
            }
        })));
    }

    /* JADX INFO: renamed from: rx.android.lifecycle.LifecycleObservable$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$rx$android$lifecycle$LifecycleEvent = new int[LifecycleEvent.values().length];

        static {
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.STOP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.ATTACH.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.CREATE_VIEW.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.DESTROY_VIEW.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$rx$android$lifecycle$LifecycleEvent[LifecycleEvent.DETACH.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }
}
