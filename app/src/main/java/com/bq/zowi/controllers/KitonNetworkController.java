package com.bq.zowi.controllers;

import android.support.annotation.Nullable;
import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface KitonNetworkController {
    Single<KitonIsAliveResponseNetworkModel> sendIsAliveToKiton(String str, @Nullable String str2);
}
