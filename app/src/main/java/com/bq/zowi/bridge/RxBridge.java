package com.bq.zowi.bridge;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Conversion utilities between RxJava1 and RxJava2 types.
 * Used by interactor bridges to delegate core (RxJava2) implementations
 * while presenting RxJava1 interfaces to existing presenters.
 */
public final class RxBridge {

    private RxBridge() {
    }

    public static <T> rx.Single<T> toRxSingle(Single<T> source) {
        return rx.Single.create(subscriber -> source.subscribe(
            value -> subscriber.onSuccess(value),
            error -> subscriber.onError(error)
        ));
    }

    public static rx.Single<Void> toRxSingleVoid(Completable source) {
        return rx.Single.create(subscriber -> source.subscribe(
            () -> subscriber.onSuccess(null),
            error -> subscriber.onError(error)
        ));
    }

    public static <T> rx.Observable<T> toRxObservable(Observable<T> source) {
        return rx.Observable.create(subscriber -> source.subscribe(
            value -> subscriber.onNext(value),
            error -> subscriber.onError(error),
            () -> subscriber.onCompleted()
        ));
    }
}
