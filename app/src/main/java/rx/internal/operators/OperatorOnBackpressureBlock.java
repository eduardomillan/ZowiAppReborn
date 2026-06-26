package rx.internal.operators;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import rx.Observable;
import rx.Subscriber;
import rx.internal.util.BackpressureDrainManager;

/* JADX INFO: loaded from: classes.dex */
public class OperatorOnBackpressureBlock<T> implements Observable.Operator<T, T> {
    final int max;

    public OperatorOnBackpressureBlock(int max) {
        this.max = max;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> child) {
        BlockingSubscriber<T> s = new BlockingSubscriber<>(this.max, child);
        s.init();
        return s;
    }

    static final class BlockingSubscriber<T> extends Subscriber<T> implements BackpressureDrainManager.BackpressureQueueCallback {
        final Subscriber<? super T> child;
        final BlockingQueue<Object> queue;
        final NotificationLite<T> nl = NotificationLite.instance();
        final BackpressureDrainManager manager = new BackpressureDrainManager(this);

        public BlockingSubscriber(int max, Subscriber<? super T> child) {
            this.queue = new ArrayBlockingQueue(max);
            this.child = child;
        }

        void init() {
            this.child.add(this);
            this.child.setProducer(this.manager);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            try {
                this.queue.put(this.nl.next(t));
                this.manager.drain();
            } catch (InterruptedException ex) {
                if (!isUnsubscribed()) {
                    onError(ex);
                }
            }
        }

        @Override // rx.Observer
        public void onError(Throwable e) {
            this.manager.terminateAndDrain(e);
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.manager.terminateAndDrain();
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public boolean accept(Object value) {
            return this.nl.accept(this.child, value);
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public void complete(Throwable exception) {
            if (exception != null) {
                this.child.onError(exception);
            } else {
                this.child.onCompleted();
            }
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public Object peek() {
            return this.queue.peek();
        }

        @Override // rx.internal.util.BackpressureDrainManager.BackpressureQueueCallback
        public Object poll() {
            return this.queue.poll();
        }
    }
}
