package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.annotation.TargetApi;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class InternalHelperKK {
    InternalHelperKK() {
    }

    @TargetApi(19)
    public static void clearViewPropertyAnimatorUpdateListener(View view) {
        view.animate().setUpdateListener(null);
    }
}
