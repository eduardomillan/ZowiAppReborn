package com.bq.zowi.interactors;

import com.bq.zowi.controllers.AssetController;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.KitonNetworkController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.controllers.ZowiDataController;
import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel;
import com.bq.zowi.utils.Grove;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Nullable;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class SendAppToZowiInteractorImpl implements SendAppToZowiInteractor {
    protected static final int APP_ID_RECEPTION_TIMEOUT = 6000;
    private AssetController assetController;
    private BTConnectionController btConnectionController;
    private ConnectToZowiInteractor connectToZowiInteractor;
    private KitonNetworkController kitonNetworkController;
    private SessionController sessionController;
    private ZowiDataController zowiDataController;

    public SendAppToZowiInteractorImpl(ConnectToZowiInteractor connectToZowiInteractor, BTConnectionController btConnectionController, AssetController assetController, SessionController sessionController, ZowiDataController zowiDataController, KitonNetworkController kitonNetworkController) {
        this.connectToZowiInteractor = connectToZowiInteractor;
        this.btConnectionController = btConnectionController;
        this.assetController = assetController;
        this.sessionController = sessionController;
        this.zowiDataController = zowiDataController;
        this.kitonNetworkController = kitonNetworkController;
    }

    @Override // com.bq.zowi.interactors.SendAppToZowiInteractor
    public Observable<Integer> sendAppToZowi(String appHexPath) {
        final String activeZowiAddress = this.sessionController.loadActiveZowiDeviceAddress();
        return getHexInputStream(appHexPath).flatMapObservable(new Func1<InputStream, Observable<? extends Integer>>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.1
            @Override // rx.functions.Func1
            public Observable<? extends Integer> call(InputStream hexInputStream) {
                return SendAppToZowiInteractorImpl.this.connectToZowiInteractor.connectToZowi(activeZowiAddress, false).map(new Func1<Void, Integer>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.1.4
                    @Override // rx.functions.Func1
                    public Integer call(Void aVoid) {
                        return 0;
                    }
                }).toObservable().concatWith(SendAppToZowiInteractorImpl.this.btConnectionController.sendHexToZowi(hexInputStream).subscribeOn(Schedulers.io())).concatWith(Observable.create(new Observable.OnSubscribe<Integer>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.1.3
                    @Override // rx.functions.Action1
                    public void call(Subscriber<? super Integer> subscriber) {
                        SendAppToZowiInteractorImpl.this.btConnectionController.enabledReadingIncomingData();
                        subscriber.onNext(100);
                        subscriber.onCompleted();
                        Grove.d("Send HEX to Zowi process complete. Checking installed ZowiAppId.", new Object[0]);
                    }
                })).concatWith(SendAppToZowiInteractorImpl.this.zowiDataController.waitForZowiAppIdReception().timeout(6000L, TimeUnit.MILLISECONDS, Single.create(new Single.OnSubscribe<String>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.1.2
                    @Override // rx.functions.Action1
                    public void call(SingleSubscriber<? super String> singleSubscriber) {
                        Grove.d("No installed ZowiAppId received after sending HEX to Zowi.", new Object[0]);
                        SendAppToZowiInteractorImpl.this.sendHitToKiton(activeZowiAddress, null);
                        singleSubscriber.onError(new IllegalStateException("No FW ID is returned from Zowi after programming."));
                    }
                })).toObservable().flatMap(new Func1<String, Observable<Integer>>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.1.1
                    @Override // rx.functions.Func1
                    public Observable<Integer> call(String installedZowiAppId) {
                        Grove.d("A valid ZowiAppId received after sending HEX to Zowi: " + installedZowiAppId + ". Success!", new Object[0]);
                        SendAppToZowiInteractorImpl.this.sendHitToKiton(activeZowiAddress, installedZowiAppId);
                        return Observable.just(100);
                    }
                }));
            }
        });
    }

    private Single<InputStream> getHexInputStream(final String hexPath) {
        return Single.create(new Single.OnSubscribe<InputStream>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.2
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super InputStream> subscriber) {
                InputStream inputStream = null;
                try {
                    inputStream = SendAppToZowiInteractorImpl.this.assetController.openAsset(hexPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onSuccess(inputStream);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendHitToKiton(String activeZowiAddress, @Nullable String zowiAppId) {
        this.kitonNetworkController.sendIsAliveToKiton(activeZowiAddress, zowiAppId).subscribeOn(Schedulers.io()).subscribe(new SingleSubscriber<KitonIsAliveResponseNetworkModel>() { // from class: com.bq.zowi.interactors.SendAppToZowiInteractorImpl.3
            @Override // rx.SingleSubscriber
            public void onSuccess(KitonIsAliveResponseNetworkModel value) {
                Grove.d("Kiton answered: " + value, new Object[0]);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.d(error, "Kiton isAlive error", new Object[0]);
            }
        });
    }
}
