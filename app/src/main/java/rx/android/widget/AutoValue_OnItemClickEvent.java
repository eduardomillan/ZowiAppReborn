package rx.android.widget;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_OnItemClickEvent extends OnItemClickEvent {
    private final long id;
    private final AdapterView<?> parent;
    private final int position;
    private final View view;

    AutoValue_OnItemClickEvent(AdapterView<?> parent, View view, int position, long id) {
        if (parent == null) {
            throw new NullPointerException("Null parent");
        }
        this.parent = parent;
        if (view == null) {
            throw new NullPointerException("Null view");
        }
        this.view = view;
        this.position = position;
        this.id = id;
    }

    @Override // rx.android.widget.OnItemClickEvent
    public AdapterView<?> parent() {
        return this.parent;
    }

    @Override // rx.android.widget.OnItemClickEvent
    public View view() {
        return this.view;
    }

    @Override // rx.android.widget.OnItemClickEvent
    public int position() {
        return this.position;
    }

    @Override // rx.android.widget.OnItemClickEvent
    public long id() {
        return this.id;
    }

    public String toString() {
        return "OnItemClickEvent{parent=" + this.parent + ", view=" + this.view + ", position=" + this.position + ", id=" + this.id + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnItemClickEvent)) {
            return false;
        }
        OnItemClickEvent that = (OnItemClickEvent) o;
        return this.parent.equals(that.parent()) && this.view.equals(that.view()) && this.position == that.position() && this.id == that.id();
    }

    public int hashCode() {
        int h = 1 * 1000003;
        return (int) (((long) ((((((h ^ this.parent.hashCode()) * 1000003) ^ this.view.hashCode()) * 1000003) ^ this.position) * 1000003)) ^ ((this.id >>> 32) ^ this.id));
    }
}
