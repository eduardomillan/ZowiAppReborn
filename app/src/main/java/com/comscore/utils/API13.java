package com.comscore.utils;

import android.graphics.Point;
import android.os.Build;
import android.view.Display;

/* JADX INFO: loaded from: classes.dex */
public class API13 {
    public static Point getDisplaySize(Display display) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(point);
        }
        return point;
    }
}
