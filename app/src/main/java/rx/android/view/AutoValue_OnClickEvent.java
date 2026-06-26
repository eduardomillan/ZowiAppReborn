package rx.android.view;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_OnClickEvent extends OnClickEvent {
    private final View view;

    AutoValue_OnClickEvent(View view) {
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
    }

    @Override // rx.android.view.OnClickEvent
    public View view() {
        return this.view;
    }

    public String toString() {
        return "OnClickEvent{view=" + this.view + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof OnClickEvent) {
            OnClickEvent that = (OnClickEvent) o;
            return this.view.equals(that.view());
        }
        return false;
    }

    public int hashCode() {
        int h = 1 * 1000003;
        return h ^ this.view.hashCode();
    }
}
