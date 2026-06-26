package com.bq.zowi.utils;

import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

/* JADX INFO: loaded from: classes.dex */
public class AnimationUtils {
    public static void animateProgressBarToProgress(ProgressBar progressBar, int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progress);
        animation.setDuration(500L);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}
