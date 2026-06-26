package rx.android.widget;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: loaded from: classes.dex */
public abstract class OnItemClickEvent {
    public abstract long id();

    public abstract AdapterView<?> parent();

    public abstract int position();

    public abstract View view();

    public static OnItemClickEvent create(AdapterView<?> parent, View view, int position, long id) {
        return new AutoValue_OnItemClickEvent(parent, view, position, id);
    }
}
