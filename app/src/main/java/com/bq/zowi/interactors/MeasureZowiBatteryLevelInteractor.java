package com.bq.zowi.interactors;

import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface MeasureZowiBatteryLevelInteractor {
    public static final float BATTERY_LEVEL_LOWER_THRESHOLD = 50.0f;

    Single<Boolean> manageZowiBatteryLevel(float f);

    Single<Boolean> measureAndManageZowiBatteryLevel();
}
