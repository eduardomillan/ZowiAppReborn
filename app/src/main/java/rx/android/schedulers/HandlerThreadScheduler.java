package rx.android.schedulers;

import android.os.Handler;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.ScheduledAction;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class HandlerThreadScheduler extends Scheduler {
    private final Handler handler;

    @Deprecated
    public HandlerThreadScheduler(Handler handler) {
        this.handler = handler;
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new InnerHandlerThreadScheduler(this.handler);
    }

    private static class InnerHandlerThreadScheduler extends Scheduler.Worker {
        private final CompositeSubscription compositeSubscription = new CompositeSubscription();
        private final Handler handler;

        public InnerHandlerThreadScheduler(Handler handler) {
            this.handler = handler;
        }

        @Override // rx.Subscription
        public void unsubscribe() throws Throwable {
            this.compositeSubscription.unsubscribe();
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.compositeSubscription.isUnsubscribed();
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
            final ScheduledAction scheduledAction = new ScheduledAction(action);
            scheduledAction.add(Subscriptions.create(new Action0() { // from class: rx.android.schedulers.HandlerThreadScheduler.InnerHandlerThreadScheduler.1
                @Override // rx.functions.Action0
                public void call() {
                    InnerHandlerThreadScheduler.this.handler.removeCallbacks(scheduledAction);
                }
            }));
            scheduledAction.addParent(this.compositeSubscription);
            this.compositeSubscription.add(scheduledAction);
            this.handler.postDelayed(scheduledAction, unit.toMillis(delayTime));
            return scheduledAction;
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action) {
            return schedule(action, 0L, TimeUnit.MILLISECONDS);
        }
    }
}
