package rx.android.view;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class OnClickEvent {
    public abstract View view();

    public static OnClickEvent create(View view) {
        return new AutoValue_OnClickEvent(view);
    }
}
