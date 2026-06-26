package com.bq.zowi.controllers;

import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/* JADX INFO: loaded from: classes.dex */
public interface KitonNetworkService {
    @GET("/api/isAlive/{mac}/{fwCompilation}/0")
    Observable<KitonIsAliveResponseNetworkModel> isAlive(@Path("mac") String str, @Path("fwCompilation") String str2);
}
