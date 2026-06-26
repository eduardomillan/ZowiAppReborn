package com.bq.zowi.controllers;

import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface ZowiDataController {

    public interface OnBatteryLevelReceivedListener {
        void onBatteryLevelReceived(float f);
    }

    public interface OnDistanceLevelReceivedListener {
        void onDistanceLevelReceived(int i);
    }

    public interface OnNoiseLevelReceivedListener {
        void onNoiseLevelReceived(int i);
    }

    public interface OnZowiAppIdReceivedListener {
        void onZowiAppIdReceived(String str);
    }

    public interface OnZowiNameReceivedListener {
        void onZowiNameReceived(String str);
    }

    Single<Float> getBatteryLevel();

    Single<Integer> getDistanceLevel();

    Single<Integer> getNoiseLevel();

    Single<String> getZowiAppId();

    Single<String> getZowiName();

    Single<Float> waitForBatteryLevelReception();

    Single<Integer> waitForDistanceLevelReception();

    Single<Integer> waitForNoiseLevelReception();

    Single<String> waitForZowiAppIdReception();

    Single<String> waitForZowiNameReception();
}
