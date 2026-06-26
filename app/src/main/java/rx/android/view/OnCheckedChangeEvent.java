package rx.android.view;

import android.widget.CompoundButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class OnCheckedChangeEvent {
    public abstract boolean value();

    public abstract CompoundButton view();

    public static OnCheckedChangeEvent create(CompoundButton view) {
        return create(view, view.isChecked());
    }

    public static OnCheckedChangeEvent create(CompoundButton view, boolean value) {
        return new AutoValue_OnCheckedChangeEvent(view, value);
    }
}
