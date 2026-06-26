package rx.subjects;

import rx.Observable;
import rx.Observer;
import rx.annotations.Experimental;

/* JADX INFO: loaded from: classes.dex */
public abstract class Subject<T, R> extends Observable<R> implements Observer<T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    public abstract boolean hasObservers();

    protected Subject(Observable.OnSubscribe<R> onSubscribe) {
        super(onSubscribe);
    }

    public final SerializedSubject<T, R> toSerialized() {
        return getClass() == SerializedSubject.class ? (SerializedSubject) this : new SerializedSubject<>(this);
    }

    @Deprecated
    @Experimental
    public boolean hasThrowable() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Experimental
    public boolean hasCompleted() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Experimental
    public Throwable getThrowable() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Experimental
    public boolean hasValue() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Experimental
    public T getValue() {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    @Experimental
    public Object[] getValues() {
        Object[] values = getValues(EMPTY_ARRAY);
        if (values == EMPTY_ARRAY) {
            return new Object[0];
        }
        return values;
    }

    @Deprecated
    @Experimental
    public T[] getValues(T[] a) {
        throw new UnsupportedOperationException();
    }
}
