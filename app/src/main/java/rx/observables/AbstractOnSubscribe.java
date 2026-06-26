package rx.observables;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.annotations.Experimental;
import rx.exceptions.CompositeException;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;
import rx.internal.operators.BackpressureUtils;

/* JADX INFO: loaded from: classes.dex */
@Experimental
public abstract class AbstractOnSubscribe<T, S> implements Observable.OnSubscribe<T> {
    private static final Func1<Object, Object> NULL_FUNC1 = new Func1<Object, Object>() { // from class: rx.observables.AbstractOnSubscribe.1
        @Override // rx.functions.Func1
        public Object call(Object t1) {
            return null;
        }
    };

    protected abstract void next(SubscriptionState<T, S> subscriptionState);

    protected S onSubscribe(Subscriber<? super T> subscriber) {
        return null;
    }

    protected void onTerminated(S state) {
    }

    @Override // rx.functions.Action1
    public final void call(Subscriber<? super T> subscriber) {
        S custom = onSubscribe(subscriber);
        SubscriptionState<T, S> state = new SubscriptionState<>(subscriber, custom);
        subscriber.add(new SubscriptionCompleter(state));
        subscriber.setProducer(new SubscriptionProducer(state));
    }

    public final Observable<T> toObservable() {
        return Observable.create(this);
    }

    public static <T, S> AbstractOnSubscribe<T, S> create(Action1<SubscriptionState<T, S>> next) {
        Func1<? super Subscriber<? super T>, ? extends S> nullFunc = NULL_FUNC1;
        return create(next, nullFunc, Actions.empty());
    }

    public static <T, S> AbstractOnSubscribe<T, S> create(Action1<SubscriptionState<T, S>> next, Func1<? super Subscriber<? super T>, ? extends S> onSubscribe) {
        return create(next, onSubscribe, Actions.empty());
    }

    public static <T, S> AbstractOnSubscribe<T, S> create(Action1<SubscriptionState<T, S>> next, Func1<? super Subscriber<? super T>, ? extends S> onSubscribe, Action1<? super S> onTerminated) {
        return new LambdaOnSubscribe(next, onSubscribe, onTerminated);
    }

    private static final class LambdaOnSubscribe<T, S> extends AbstractOnSubscribe<T, S> {
        final Action1<SubscriptionState<T, S>> next;
        final Func1<? super Subscriber<? super T>, ? extends S> onSubscribe;
        final Action1<? super S> onTerminated;

        @Override // rx.observables.AbstractOnSubscribe, rx.functions.Action1
        public /* bridge */ /* synthetic */ void call(Object x0) {
            super.call((Subscriber) x0);
        }

        private LambdaOnSubscribe(Action1<SubscriptionState<T, S>> next, Func1<? super Subscriber<? super T>, ? extends S> onSubscribe, Action1<? super S> onTerminated) {
            this.next = next;
            this.onSubscribe = onSubscribe;
            this.onTerminated = onTerminated;
        }

        @Override // rx.observables.AbstractOnSubscribe
        protected S onSubscribe(Subscriber<? super T> subscriber) {
            return this.onSubscribe.call(subscriber);
        }

        @Override // rx.observables.AbstractOnSubscribe
        protected void onTerminated(S state) {
            this.onTerminated.call(state);
        }

        @Override // rx.observables.AbstractOnSubscribe
        protected void next(SubscriptionState<T, S> state) {
            this.next.call(state);
        }
    }

    private static final class SubscriptionCompleter<T, S> extends AtomicBoolean implements Subscription {
        private static final long serialVersionUID = 7993888274897325004L;
        private final SubscriptionState<T, S> state;

        private SubscriptionCompleter(SubscriptionState<T, S> state) {
            this.state = state;
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return get();
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (compareAndSet(false, true)) {
                this.state.free();
            }
        }
    }

    private static final class SubscriptionProducer<T, S> implements Producer {
        final SubscriptionState<T, S> state;

        private SubscriptionProducer(SubscriptionState<T, S> state) {
            this.state = state;
        }

        @Override // rx.Producer
        public void request(long n) {
            if (n <= 0 || BackpressureUtils.getAndAddRequest(((SubscriptionState) this.state).requestCount, n) != 0) {
                return;
            }
            if (n == Long.MAX_VALUE) {
                while (!((SubscriptionState) this.state).subscriber.isUnsubscribed() && doNext()) {
                }
            } else if (!((SubscriptionState) this.state).subscriber.isUnsubscribed()) {
                while (doNext() && ((SubscriptionState) this.state).requestCount.decrementAndGet() > 0 && !((SubscriptionState) this.state).subscriber.isUnsubscribed()) {
                }
            }
        }

        protected boolean doNext() {
            try {
                if (!this.state.use()) {
                    return false;
                }
                int p = this.state.phase();
                ((SubscriptionState) this.state).parent.next(this.state);
                if (!this.state.verify()) {
                    throw new IllegalStateException("No event produced or stop called @ Phase: " + p + " -> " + this.state.phase() + ", Calls: " + this.state.calls());
                }
                if (this.state.accept() || this.state.stopRequested()) {
                    this.state.terminate();
                    return false;
                }
                SubscriptionState.access$708(this.state);
                this.state.free();
                return true;
            } catch (Throwable t) {
                this.state.terminate();
                ((SubscriptionState) this.state).subscriber.onError(t);
                return false;
            } finally {
                this.state.free();
            }
        }
    }

    public static final class SubscriptionState<T, S> {
        private long calls;
        private boolean hasCompleted;
        private boolean hasOnNext;
        private final AtomicInteger inUse;
        private final AbstractOnSubscribe<T, S> parent;
        private int phase;
        private final AtomicLong requestCount;
        private final S state;
        private boolean stopRequested;
        private final Subscriber<? super T> subscriber;
        private Throwable theException;
        private T theValue;

        static /* synthetic */ long access$708(SubscriptionState x0) {
            long j = x0.calls;
            x0.calls = 1 + j;
            return j;
        }

        private SubscriptionState(AbstractOnSubscribe<T, S> parent, Subscriber<? super T> subscriber, S state) {
            this.parent = parent;
            this.subscriber = subscriber;
            this.state = state;
            this.requestCount = new AtomicLong();
            this.inUse = new AtomicInteger(1);
        }

        public S state() {
            return this.state;
        }

        public int phase() {
            return this.phase;
        }

        public void phase(int newPhase) {
            this.phase = newPhase;
        }

        public void advancePhase() {
            advancePhaseBy(1);
        }

        public void advancePhaseBy(int amount) {
            this.phase += amount;
        }

        public long calls() {
            return this.calls;
        }

        public void onNext(T value) {
            if (this.hasOnNext) {
                throw new IllegalStateException("onNext not consumed yet!");
            }
            if (this.hasCompleted) {
                throw new IllegalStateException("Already terminated", this.theException);
            }
            this.theValue = value;
            this.hasOnNext = true;
        }

        public void onError(Throwable e) {
            if (e == null) {
                throw new NullPointerException("e != null required");
            }
            if (this.hasCompleted) {
                throw new IllegalStateException("Already terminated", this.theException);
            }
            this.theException = e;
            this.hasCompleted = true;
        }

        public void onCompleted() {
            if (this.hasCompleted) {
                throw new IllegalStateException("Already terminated", this.theException);
            }
            this.hasCompleted = true;
        }

        public void stop() {
            this.stopRequested = true;
        }

        protected boolean accept() {
            if (this.hasOnNext) {
                T value = this.theValue;
                this.theValue = null;
                this.hasOnNext = false;
                try {
                    this.subscriber.onNext(value);
                } catch (Throwable t) {
                    this.hasCompleted = true;
                    Throwable e = this.theException;
                    this.theException = null;
                    if (e == null) {
                        this.subscriber.onError(t);
                        return true;
                    }
                    this.subscriber.onError(new CompositeException(Arrays.asList(t, e)));
                    return true;
                }
            }
            if (!this.hasCompleted) {
                return false;
            }
            Throwable e2 = this.theException;
            this.theException = null;
            if (e2 != null) {
                this.subscriber.onError(e2);
                return true;
            }
            this.subscriber.onCompleted();
            return true;
        }

        protected boolean verify() {
            return this.hasOnNext || this.hasCompleted || this.stopRequested;
        }

        protected boolean stopRequested() {
            return this.stopRequested;
        }

        protected boolean use() {
            int i = this.inUse.get();
            if (i == 0) {
                return false;
            }
            if (i == 1 && this.inUse.compareAndSet(1, 2)) {
                return true;
            }
            throw new IllegalStateException("This is not reentrant nor threadsafe!");
        }

        protected void free() {
            int i = this.inUse.get();
            if (i > 0 && this.inUse.decrementAndGet() == 0) {
                this.parent.onTerminated(this.state);
            }
        }

        protected void terminate() {
            int i;
            do {
                i = this.inUse.get();
                if (i <= 0) {
                    return;
                }
            } while (!this.inUse.compareAndSet(i, 0));
            this.parent.onTerminated(this.state);
        }
    }
}
