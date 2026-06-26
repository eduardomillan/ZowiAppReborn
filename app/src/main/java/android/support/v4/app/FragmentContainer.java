package android.support.v4.app;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
interface FragmentContainer {
    @Nullable
    View findViewById(@IdRes int i);

    boolean hasView();
}
