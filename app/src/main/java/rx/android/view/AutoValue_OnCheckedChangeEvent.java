package rx.android.view;

import android.widget.CompoundButton;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_OnCheckedChangeEvent extends OnCheckedChangeEvent {
    private final boolean value;
    private final CompoundButton view;

    AutoValue_OnCheckedChangeEvent(CompoundButton view, boolean value) {
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
        this.value = value;
    }

    @Override // rx.android.view.OnCheckedChangeEvent
    public CompoundButton view() {
        return this.view;
    }

    @Override // rx.android.view.OnCheckedChangeEvent
    public boolean value() {
        return this.value;
    }

    public String toString() {
        return "OnCheckedChangeEvent{view=" + this.view + ", value=" + this.value + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnCheckedChangeEvent)) {
            return false;
        }
        OnCheckedChangeEvent that = (OnCheckedChangeEvent) o;
        return this.view.equals(that.view()) && this.value == that.value();
    }

    public int hashCode() {
        int h = 1 * 1000003;
        return ((h ^ this.view.hashCode()) * 1000003) ^ (this.value ? 1231 : 1237);
    }
}
