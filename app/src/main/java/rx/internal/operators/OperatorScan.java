package rx.internal.operators;

import android.Manifest;
import java.util.Queue;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.internal.util.atomic.SpscLinkedAtomicQueue;
import rx.internal.util.unsafe.SpscLinkedQueue;
import rx.internal.util.unsafe.UnsafeAccess;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorScan<R, T> implements Observable.Operator<R, T> {
    private static final Object NO_INITIAL_VALUE = new Object();
    private final Func2<R, ? super T, R> accumulator;
    private final Func0<R> initialValueFactory;

    public OperatorScan(final R initialValue, Func2<R, ? super T, R> accumulator) {
        this((Func0) new Func0<R>() { // from class: rx.internal.operators.OperatorScan.1
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public R call() {
                return (R) initialValue;
            }
        }, (Func2) accumulator);
    }

    public OperatorScan(Func0<R> initialValueFactory, Func2<R, ? super T, R> accumulator) {
        this.initialValueFactory = initialValueFactory;
        this.accumulator = accumulator;
    }

    public OperatorScan(Func2<R, ? super T, R> accumulator) {
        this(NO_INITIAL_VALUE, accumulator);
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super R> subscriber) {
        final R rCall = this.initialValueFactory.call();
        if (rCall == NO_INITIAL_VALUE) {
            return new Subscriber<T>(subscriber) { // from class: rx.internal.operators.OperatorScan.2
                boolean once;
                R value;

                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // rx.Observer
                public void onNext(T t) {
                    T t2;
                    if (!this.once) {
                        this.once = true;
                        t2 = (R) t;
                    } else {
                        try {
                            t2 = (R) OperatorScan.this.accumulator.call(this.value, t);
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            subscriber.onError(OnErrorThrowable.addValueAsLastCause(th, t));
                            return;
                        }
                    }
                    this.value = (R) t2;
                    subscriber.onNext(t2);
                }

                @Override // rx.Observer
                public void onError(Throwable e) {
                    subscriber.onError(e);
                }

                @Override // rx.Observer
                public void onCompleted() {
                    subscriber.onCompleted();
                }
            };
        }
        final InitialProducer initialProducer = new InitialProducer(rCall, subscriber);
        Subscriber<T> subscriber2 = new Subscriber<T>() { // from class: rx.internal.operators.OperatorScan.3
            private R value;

            {
                this.value = (R) rCall;
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // rx.Observer
            public void onNext(T t) {
                try {
                    R r = (R) OperatorScan.this.accumulator.call(this.value, t);
                    this.value = r;
                    initialProducer.onNext(r);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onError(OnErrorThrowable.addValueAsLastCause(th, t));
                }
            }

            @Override // rx.Observer
            public void onError(Throwable e) {
                initialProducer.onError(e);
            }

            @Override // rx.Observer
            public void onCompleted() {
                initialProducer.onCompleted();
            }

            @Override // rx.Subscriber
            public void setProducer(Producer producer) {
                initialProducer.setProducer(producer);
            }
        };
        subscriber.add(subscriber2);
        subscriber.setProducer(initialProducer);
        return subscriber2;
    }

    static final class InitialProducer<R> implements Producer, Observer<R> {
        final Subscriber<? super R> child;
        volatile boolean done;
        boolean emitting;
        Throwable error;
        boolean missed;
        Producer missedProducer;
        long missedRequested;
        Producer producer;
        final Queue<Object> queue;
        long requested;

        public InitialProducer(R initialValue, Subscriber<? super R> child) {
            Queue<Object> q;
            this.child = child;
            if (UnsafeAccess.isUnsafeAvailable()) {
                q = new SpscLinkedQueue<>();
            } else {
                q = new SpscLinkedAtomicQueue<>();
            }
            this.queue = q;
            q.offer(NotificationLite.instance().next(initialValue));
        }

        @Override // rx.Producer
        public void request(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("n >= required but it was " + n);
            }
            if (n != 0) {
                synchronized (this) {
                    if (this.emitting) {
                        long mr = this.missedRequested;
                        long mu = mr + n;
                        if (mu < 0) {
                            mu = Long.MAX_VALUE;
                        }
                        this.missedRequested = mu;
                    } else {
                        this.emitting = true;
                        long r = this.requested;
                        long u = r + n;
                        if (u < 0) {
                            u = Long.MAX_VALUE;
                        }
                        this.requested = u;
                        Producer p = this.producer;
                        if (p != null) {
                            p.request(n);
                        }
                        emitLoop();
                    }
                }
            }
        }

        @Override // rx.Observer
        public void onNext(R t) {
            this.queue.offer(NotificationLite.instance().next(t));
            emit();
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<? super R> child) {
            if (child.isUnsubscribed()) {
                return true;
            }
            if (d) {
                Throwable err = this.error;
                if (err != null) {
                    child.onError(err);
                    return true;
                }
                if (empty) {
                    child.onCompleted();
                    return true;
                }
            }
            return false;
        }

        @Override // rx.Observer
        public void onError(Throwable e) {
            this.error = e;
            this.done = true;
            emit();
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.done = true;
            emit();
        }

        public void setProducer(Producer p) {
            if (p == null) {
                throw new NullPointerException();
            }
            synchronized (this) {
                if (this.emitting) {
                    this.missedProducer = p;
                    return;
                }
                this.emitting = true;
                this.producer = p;
                long r = this.requested;
                if (r != 0) {
                    p.request(r);
                }
                emitLoop();
            }
        }

        void emit() {
            synchronized (this) {
                if (this.emitting) {
                    this.missed = true;
                } else {
                    this.emitting = true;
                    emitLoop();
                }
            }
        }

        void emitLoop() {
            Producer producer;
            long j;
            Subscriber<? super R> subscriber = this.child;
            Queue<Object> queue = this.queue;
            NotificationLite notificationLiteInstance = NotificationLite.instance();
            long j2 = this.requested;
            while (true) {
                boolean z = j2 == Long.MAX_VALUE;
                if (!checkTerminated(this.done, queue.isEmpty(), subscriber)) {
                    while (j2 != 0) {
                        boolean z2 = this.done;
                        Object objPoll = queue.poll();
                        boolean z3 = objPoll == null;
                        if (!checkTerminated(z2, z3, subscriber)) {
                            if (z3) {
                                break;
                            }
                            Manifest manifest = (R) notificationLiteInstance.getValue(objPoll);
                            try {
                                subscriber.onNext(manifest);
                                if (!z) {
                                    j2--;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                subscriber.onError(OnErrorThrowable.addValueAsLastCause(th, manifest));
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    if (!z) {
                        this.requested = j2;
                    }
                    synchronized (this) {
                        producer = this.missedProducer;
                        j = this.missedRequested;
                        if (!this.missed && producer == null && j == 0) {
                            this.emitting = false;
                            return;
                        } else {
                            this.missed = false;
                            this.missedProducer = null;
                            this.missedRequested = 0L;
                        }
                    }
                    if (j != 0 && !z) {
                        long j3 = j2 + j;
                        if (j3 < 0) {
                            j3 = Long.MAX_VALUE;
                        }
                        this.requested = j3;
                        j2 = j3;
                    }
                    if (producer != null) {
                        this.producer = producer;
                        if (j2 != 0) {
                            producer.request(j2);
                        }
                    } else {
                        Producer producer2 = this.producer;
                        if (producer2 != null && j != 0) {
                            producer2.request(j);
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }
}
