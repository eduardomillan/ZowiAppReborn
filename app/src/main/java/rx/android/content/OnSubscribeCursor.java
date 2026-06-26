package rx.android.content;

import android.database.Cursor;
import rx.Observable;
import rx.Subscriber;

/* JADX INFO: loaded from: classes.dex */
final class OnSubscribeCursor implements Observable.OnSubscribe<Cursor> {
    private final Cursor cursor;

    OnSubscribeCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super Cursor> subscriber) {
        while (!subscriber.isUnsubscribed() && this.cursor.moveToNext()) {
            try {
                subscriber.onNext(this.cursor);
            } finally {
                if (!this.cursor.isClosed()) {
                    this.cursor.close();
                }
            }
        }
        subscriber.onCompleted();
    }
}
