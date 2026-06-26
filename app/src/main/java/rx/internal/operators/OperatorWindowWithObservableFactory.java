package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorWindowWithObservableFactory<T, U> implements Observable.Operator<Observable<T>, T> {
    static final Object NEXT_SUBJECT = new Object();
    static final NotificationLite<Object> nl = NotificationLite.instance();
    final Func0<? extends Observable<? extends U>> otherFactory;

    public OperatorWindowWithObservableFactory(Func0<? extends Observable<? extends U>> otherFactory) {
        this.otherFactory = otherFactory;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super Observable<T>> child) {
        SourceSubscriber<T, U> sub = new SourceSubscriber<>(child, this.otherFactory);
        child.add(sub);
        sub.replaceWindow();
        return sub;
    }

    static final class SourceSubscriber<T, U> extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        Observer<T> consumer;
        boolean emitting;
        final Func0<? extends Observable<? extends U>> otherFactory;
        Observable<T> producer;
        List<Object> queue;
        final Object guard = new Object();
        final SerialSubscription ssub = new SerialSubscription();

        public SourceSubscriber(Subscriber<? super Observable<T>> child, Func0<? extends Observable<? extends U>> otherFactory) {
            this.child = new SerializedSubscriber(child);
            this.otherFactory = otherFactory;
            add(this.ssub);
        }

        @Override // rx.Subscriber
        public void onStart() {
            request(Long.MAX_VALUE);
        }

        /* JADX WARN: Removed duplicated region for block: B:55:0x0068  */
        @Override // rx.Observer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onNext(T r7) {
            /*
                r6 = this;
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                boolean r3 = r6.emitting     // Catch: java.lang.Throwable -> L49
                if (r3 == 0) goto L19
                java.util.List<java.lang.Object> r3 = r6.queue     // Catch: java.lang.Throwable -> L49
                if (r3 != 0) goto L12
                java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L49
                r3.<init>()     // Catch: java.lang.Throwable -> L49
                r6.queue = r3     // Catch: java.lang.Throwable -> L49
            L12:
                java.util.List<java.lang.Object> r3 = r6.queue     // Catch: java.lang.Throwable -> L49
                r3.add(r7)     // Catch: java.lang.Throwable -> L49
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L49
            L18:
                return
            L19:
                java.util.List<java.lang.Object> r0 = r6.queue     // Catch: java.lang.Throwable -> L49
                r3 = 0
                r6.queue = r3     // Catch: java.lang.Throwable -> L49
                r3 = 1
                r6.emitting = r3     // Catch: java.lang.Throwable -> L49
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L49
                r1 = 1
                r2 = 0
            L24:
                r6.drain(r0)     // Catch: java.lang.Throwable -> L65
                if (r1 == 0) goto L2d
                r1 = 0
                r6.emitValue(r7)     // Catch: java.lang.Throwable -> L65
            L2d:
                java.lang.Object r4 = r6.guard     // Catch: java.lang.Throwable -> L65
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L65
                java.util.List<java.lang.Object> r0 = r6.queue     // Catch: java.lang.Throwable -> L62
                r3 = 0
                r6.queue = r3     // Catch: java.lang.Throwable -> L62
                if (r0 != 0) goto L4c
                r3 = 0
                r6.emitting = r3     // Catch: java.lang.Throwable -> L62
                r2 = 1
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L62
                if (r2 != 0) goto L18
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                r3 = 0
                r6.emitting = r3     // Catch: java.lang.Throwable -> L46
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L46
                goto L18
            L46:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L46
                throw r3
            L49:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L49
                throw r3
            L4c:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L62
                rx.Subscriber<? super rx.Observable<T>> r3 = r6.child     // Catch: java.lang.Throwable -> L65
                boolean r3 = r3.isUnsubscribed()     // Catch: java.lang.Throwable -> L65
                if (r3 == 0) goto L24
                if (r2 != 0) goto L18
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                r3 = 0
                r6.emitting = r3     // Catch: java.lang.Throwable -> L5f
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L5f
                goto L18
            L5f:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L5f
                throw r3
            L62:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L62
                throw r3     // Catch: java.lang.Throwable -> L65
            L65:
                r3 = move-exception
                if (r2 != 0) goto L6f
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                r5 = 0
                r6.emitting = r5     // Catch: java.lang.Throwable -> L70
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L70
            L6f:
                throw r3
            L70:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L70
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithObservableFactory.SourceSubscriber.onNext(java.lang.Object):void");
        }

        /* JADX WARN: Multi-variable type inference failed */
        void drain(List<Object> queue) {
            if (queue != null) {
                for (Object o : queue) {
                    if (o == OperatorWindowWithObservableFactory.NEXT_SUBJECT) {
                        replaceSubject();
                    } else if (OperatorWindowWithObservableFactory.nl.isError(o)) {
                        error(OperatorWindowWithObservableFactory.nl.getError(o));
                        return;
                    } else {
                        if (OperatorWindowWithObservableFactory.nl.isCompleted(o)) {
                            complete();
                            return;
                        }
                        emitValue(o);
                    }
                }
            }
        }

        void replaceSubject() {
            Observer<T> s = this.consumer;
            if (s != null) {
                s.onCompleted();
            }
            createNewWindow();
            this.child.onNext(this.producer);
        }

        void createNewWindow() {
            BufferUntilSubscriber<T> bus = BufferUntilSubscriber.create();
            this.consumer = bus;
            this.producer = bus;
            try {
                Observable<? extends U> other = this.otherFactory.call();
                BoundarySubscriber<T, U> bs = new BoundarySubscriber<>(this.child, this);
                this.ssub.set(bs);
                other.unsafeSubscribe(bs);
            } catch (Throwable e) {
                this.child.onError(e);
                unsubscribe();
            }
        }

        void emitValue(T t) {
            Observer<T> s = this.consumer;
            if (s != null) {
                s.onNext(t);
            }
        }

        @Override // rx.Observer
        public void onError(Throwable e) {
            synchronized (this.guard) {
                if (this.emitting) {
                    this.queue = Collections.singletonList(OperatorWindowWithObservableFactory.nl.error(e));
                    return;
                }
                this.queue = null;
                this.emitting = true;
                error(e);
            }
        }

        @Override // rx.Observer
        public void onCompleted() {
            synchronized (this.guard) {
                if (this.emitting) {
                    if (this.queue == null) {
                        this.queue = new ArrayList();
                    }
                    this.queue.add(OperatorWindowWithObservableFactory.nl.completed());
                    return;
                }
                List<Object> localQueue = this.queue;
                this.queue = null;
                this.emitting = true;
                try {
                    drain(localQueue);
                    complete();
                } catch (Throwable e) {
                    error(e);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:55:0x006a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void replaceWindow() {
            /*
                r6 = this;
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                boolean r3 = r6.emitting     // Catch: java.lang.Throwable -> L4b
                if (r3 == 0) goto L1b
                java.util.List<java.lang.Object> r3 = r6.queue     // Catch: java.lang.Throwable -> L4b
                if (r3 != 0) goto L12
                java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L4b
                r3.<init>()     // Catch: java.lang.Throwable -> L4b
                r6.queue = r3     // Catch: java.lang.Throwable -> L4b
            L12:
                java.util.List<java.lang.Object> r3 = r6.queue     // Catch: java.lang.Throwable -> L4b
                java.lang.Object r5 = rx.internal.operators.OperatorWindowWithObservableFactory.NEXT_SUBJECT     // Catch: java.lang.Throwable -> L4b
                r3.add(r5)     // Catch: java.lang.Throwable -> L4b
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4b
            L1a:
                return
            L1b:
                java.util.List<java.lang.Object> r0 = r6.queue     // Catch: java.lang.Throwable -> L4b
                r3 = 0
                r6.queue = r3     // Catch: java.lang.Throwable -> L4b
                r3 = 1
                r6.emitting = r3     // Catch: java.lang.Throwable -> L4b
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4b
                r1 = 1
                r2 = 0
            L26:
                r6.drain(r0)     // Catch: java.lang.Throwable -> L67
                if (r1 == 0) goto L2f
                r1 = 0
                r6.replaceSubject()     // Catch: java.lang.Throwable -> L67
            L2f:
                java.lang.Object r4 = r6.guard     // Catch: java.lang.Throwable -> L67
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L67
                java.util.List<java.lang.Object> r0 = r6.queue     // Catch: java.lang.Throwable -> L64
                r3 = 0
                r6.queue = r3     // Catch: java.lang.Throwable -> L64
                if (r0 != 0) goto L4e
                r3 = 0
                r6.emitting = r3     // Catch: java.lang.Throwable -> L64
                r2 = 1
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L64
                if (r2 != 0) goto L1a
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                r3 = 0
                r6.emitting = r3     // Catch: java.lang.Throwable -> L48
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L48
                goto L1a
            L48:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L48
                throw r3
            L4b:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4b
                throw r3
            L4e:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L64
                rx.Subscriber<? super rx.Observable<T>> r3 = r6.child     // Catch: java.lang.Throwable -> L67
                boolean r3 = r3.isUnsubscribed()     // Catch: java.lang.Throwable -> L67
                if (r3 == 0) goto L26
                if (r2 != 0) goto L1a
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                r3 = 0
                r6.emitting = r3     // Catch: java.lang.Throwable -> L61
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L61
                goto L1a
            L61:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L61
                throw r3
            L64:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L64
                throw r3     // Catch: java.lang.Throwable -> L67
            L67:
                r3 = move-exception
                if (r2 != 0) goto L71
                java.lang.Object r4 = r6.guard
                monitor-enter(r4)
                r5 = 0
                r6.emitting = r5     // Catch: java.lang.Throwable -> L72
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L72
            L71:
                throw r3
            L72:
                r3 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L72
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithObservableFactory.SourceSubscriber.replaceWindow():void");
        }

        void complete() {
            Observer<T> s = this.consumer;
            this.consumer = null;
            this.producer = null;
            if (s != null) {
                s.onCompleted();
            }
            this.child.onCompleted();
            unsubscribe();
        }

        void error(Throwable e) {
            Observer<T> s = this.consumer;
            this.consumer = null;
            this.producer = null;
            if (s != null) {
                s.onError(e);
            }
            this.child.onError(e);
            unsubscribe();
        }
    }

    static final class BoundarySubscriber<T, U> extends Subscriber<U> {
        boolean done;
        final SourceSubscriber<T, U> sub;

        public BoundarySubscriber(Subscriber<?> child, SourceSubscriber<T, U> sub) {
            this.sub = sub;
        }

        @Override // rx.Subscriber
        public void onStart() {
            request(Long.MAX_VALUE);
        }

        @Override // rx.Observer
        public void onNext(U t) {
            if (!this.done) {
                this.done = true;
                this.sub.replaceWindow();
            }
        }

        @Override // rx.Observer
        public void onError(Throwable e) {
            this.sub.onError(e);
        }

        @Override // rx.Observer
        public void onCompleted() {
            if (!this.done) {
                this.done = true;
                this.sub.onCompleted();
            }
        }
    }
}
