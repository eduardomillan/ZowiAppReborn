package com.bq.zowi.analytics;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import com.bq.analytics.hit.Screen;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class ZowiScreen extends Screen {
    private static final String DEVICE_MODEL;
    private static final int DEVICE_MODEL_NAME_CUSTOM_DIMENSION_ID = 1;
    private static final int DEVICE_TYPE_NAME_CUSTOM_DIMENSION_ID = 2;

    static {
        DEVICE_MODEL = Build.MODEL.startsWith(Build.MANUFACTURER) ? Build.MODEL : Build.MANUFACTURER + " - " + Build.MODEL;
    }

    public ZowiScreen(@NotNull final Context context, @NotNull String screen) {
        super(screen, new ArrayMap<Integer, String>(2) { // from class: com.bq.zowi.analytics.ZowiScreen.1
            {
                put(1, ZowiScreen.DEVICE_MODEL);
                put(2, ZowiScreen.isSmartPhone(context) ? AnalyticsUtils.CUSTOM_DIMENSION_DEVICE_TYPE_SMARTPHONE : AnalyticsUtils.CUSTOM_DIMENSION_DEVICE_TYPE_TABLET);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSmartPhone(@NonNull Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & 15;
        boolean isSmallSize = screenSize == 1;
        boolean isNormalSize = screenSize == 2;
        return isSmallSize || isNormalSize;
    }

    private static final class Builder {
        private Builder() {
        }
    }
}
