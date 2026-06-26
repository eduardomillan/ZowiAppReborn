package rx.android.widget;

import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_OnTextChangeEvent extends OnTextChangeEvent {
    private final CharSequence text;
    private final TextView view;

    AutoValue_OnTextChangeEvent(TextView view, CharSequence text) {
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
        if (text == null) {
            throw new NullPointerException("Null text");
        }
        this.text = text;
    }

    @Override // rx.android.widget.OnTextChangeEvent
    public TextView view() {
        return this.view;
    }

    @Override // rx.android.widget.OnTextChangeEvent
    public CharSequence text() {
        return this.text;
    }

    public String toString() {
        return "OnTextChangeEvent{view=" + this.view + ", text=" + ((Object) this.text) + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnTextChangeEvent)) {
            return false;
        }
        OnTextChangeEvent that = (OnTextChangeEvent) o;
        return this.view.equals(that.view()) && this.text.equals(that.text());
    }

    public int hashCode() {
        int h = 1 * 1000003;
        return ((h ^ this.view.hashCode()) * 1000003) ^ this.text.hashCode();
    }
}
