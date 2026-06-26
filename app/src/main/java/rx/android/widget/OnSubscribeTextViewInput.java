package rx.android.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.functions.Action0;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeTextViewInput implements Observable.OnSubscribe<OnTextChangeEvent> {
    private final boolean emitInitialValue;
    private final TextView input;

    public OnSubscribeTextViewInput(TextView input, boolean emitInitialValue) {
        this.input = input;
        this.emitInitialValue = emitInitialValue;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super OnTextChangeEvent> observer) {
        Assertions.assertUiThread();
        final TextWatcher watcher = new SimpleTextWatcher() { // from class: rx.android.widget.OnSubscribeTextViewInput.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // rx.android.widget.OnSubscribeTextViewInput.SimpleTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                observer.onNext(OnTextChangeEvent.create(OnSubscribeTextViewInput.this.input));
            }
        };
        Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() { // from class: rx.android.widget.OnSubscribeTextViewInput.2
            @Override // rx.functions.Action0
            public void call() {
                OnSubscribeTextViewInput.this.input.removeTextChangedListener(watcher);
            }
        });
        if (this.emitInitialValue) {
            observer.onNext(OnTextChangeEvent.create(this.input));
        }
        this.input.addTextChangedListener(watcher);
        observer.add(subscription);
    }

    private static class SimpleTextWatcher implements TextWatcher {
        private SimpleTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }
    }
}
