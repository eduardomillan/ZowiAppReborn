package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel;
import java.util.Date;
import rx.Single;
import rx.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public class KitonNetworkControllerImpl implements KitonNetworkController {
    public static final String KITON_LAST_IS_ALIVE_PARAMS = "kitonLastIsAliveParams";
    public static final String KITON_LAST_IS_ALIVE_TIMESTAMP = "kitonLastIsAliveTimestamp";
    private static final long TIME_BETWEEN_ISALIVES_MILLIS = 86400000;
    private static final String UNKNOWN_FW_COMPILATION = "custom";
    private KitonNetworkService kitonNetworkService;
    private SharedPreferences sharedPreferences;

    public KitonNetworkControllerImpl(KitonNetworkService kitonNetworkService, SharedPreferences sharedPreferences) {
        this.kitonNetworkService = kitonNetworkService;
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.bq.zowi.controllers.KitonNetworkController
    public Single<KitonIsAliveResponseNetworkModel> sendIsAliveToKiton(String deviceAddress, @Nullable String zowiAppId) {
        final String curatedDeviceAddress = macAddressToKitonFormat(deviceAddress);
        final String curatedZowiAppId = zowiAppIdToKitonFwCompilation(zowiAppId);
        if (enoughTimePassedSinceLastSending() || currentParamsAreDifferentFromLastSent(curatedDeviceAddress, curatedZowiAppId)) {
            return this.kitonNetworkService.isAlive(curatedDeviceAddress, curatedZowiAppId).toSingle().doOnSuccess(new Action1<KitonIsAliveResponseNetworkModel>() { // from class: com.bq.zowi.controllers.KitonNetworkControllerImpl.1
                @Override // rx.functions.Action1
                public void call(KitonIsAliveResponseNetworkModel kitonIsAliveResponseNetworkModel) {
                    KitonNetworkControllerImpl.this.setLastIsAliveSentTimestamp();
                    KitonNetworkControllerImpl.this.setLastIsAliveSentParams(curatedDeviceAddress, curatedZowiAppId);
                }
            });
        }
        return Single.error(new IllegalStateException("Skipping Kiton isAlive. Enough time passed since last sending: " + enoughTimePassedSinceLastSending() + ". Params differ from last params sent: " + currentParamsAreDifferentFromLastSent(curatedDeviceAddress, curatedZowiAppId)));
    }

    private String macAddressToKitonFormat(String macAddress) {
        return macAddress.replaceAll(":", "");
    }

    private String zowiAppIdToKitonFwCompilation(@Nullable String zowiAppId) {
        if (zowiAppId == null) {
            return UNKNOWN_FW_COMPILATION;
        }
        return zowiAppId;
    }

    private long getLastIsAliveSentTimestamp() {
        return this.sharedPreferences.getLong(KITON_LAST_IS_ALIVE_TIMESTAMP, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLastIsAliveSentTimestamp() {
        this.sharedPreferences.edit().putLong(KITON_LAST_IS_ALIVE_TIMESTAMP, new Date().getTime()).commit();
    }

    private String getLastIsAliveSentParams() {
        return this.sharedPreferences.getString(KITON_LAST_IS_ALIVE_PARAMS, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLastIsAliveSentParams(String curatedDeviceAddress, String curatedZowiAppId) {
        this.sharedPreferences.edit().putString(KITON_LAST_IS_ALIVE_PARAMS, curatedDeviceAddress + "|" + curatedZowiAppId).commit();
    }

    private boolean enoughTimePassedSinceLastSending() {
        return getLastIsAliveSentTimestamp() == -1 || new Date().getTime() - getLastIsAliveSentTimestamp() > 86400000;
    }

    private boolean currentParamsAreDifferentFromLastSent(String curatedDeviceAddress, String curatedZowiAppId) {
        return getLastIsAliveSentParams() == null || !getLastIsAliveSentParams().equals(new StringBuilder().append(curatedDeviceAddress).append("|").append(curatedZowiAppId).toString());
    }
}
