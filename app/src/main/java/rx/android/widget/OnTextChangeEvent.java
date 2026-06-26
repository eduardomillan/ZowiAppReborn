package rx.android.widget;

import android.text.SpannableString;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class OnTextChangeEvent {
    public abstract CharSequence text();

    public abstract TextView view();

    public static OnTextChangeEvent create(TextView view) {
        return create(view, new SpannableString(view.getText()));
    }

    public static OnTextChangeEvent create(TextView view, CharSequence text) {
        return new AutoValue_OnTextChangeEvent(view, text);
    }
}
