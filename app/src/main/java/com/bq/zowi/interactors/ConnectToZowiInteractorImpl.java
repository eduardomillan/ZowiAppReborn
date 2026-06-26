package com.bq.zowi.interactors;

import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.KitonNetworkController;
import com.bq.zowi.controllers.ZowiDataController;
import com.bq.zowi.models.ConnectionSuccessData;
import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel;
import com.bq.zowi.rx.RetryWithDelay;
import com.bq.zowi.utils.Grove;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class ConnectToZowiInteractorImpl implements ConnectToZowiInteractor {
    private static final int DATA_RECEPTION_AFTER_CONNECTION_TIMEOUT = 3000;
    private static final int RECONNECTION_RETRIES = 3;
    private static final int RECONNECTION_RETRIES_DELAY = 1000;
    private static final int WAIT_AFTER_DISCONNECT = 1000;
    private BTAdapterController btAdapterController;
    private BTConnectionController btConnectionController;
    private KitonNetworkController kitonNetworkController;
    private ZowiDataController zowiDataController;

    public ConnectToZowiInteractorImpl(BTConnectionController btConnectionController, BTAdapterController btAdapterController, ZowiDataController zowiDataController, KitonNetworkController kitonNetworkController) {
        this.btConnectionController = btConnectionController;
        this.btAdapterController = btAdapterController;
        this.zowiDataController = zowiDataController;
        this.kitonNetworkController = kitonNetworkController;
    }

    @Override // com.bq.zowi.interactors.ConnectToZowiInteractor
    public Single<ConnectionSuccessData> connectToZowiAndRetrieveData(final String deviceAddress) {
        return connectToZowi(deviceAddress, true).flatMap(new Func1<Void, Single<? extends ConnectionSuccessData>>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.2
            @Override // rx.functions.Func1
            public Single<? extends ConnectionSuccessData> call(Void aVoid) {
                return Single.zip(ConnectToZowiInteractorImpl.this.zowiDataController.waitForZowiNameReception(), ConnectToZowiInteractorImpl.this.zowiDataController.waitForZowiAppIdReception(), ConnectToZowiInteractorImpl.this.zowiDataController.waitForBatteryLevelReception(), new Func3<String, String, Float, ConnectionSuccessData>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.2.1
                    @Override // rx.functions.Func3
                    public ConnectionSuccessData call(String receivedZowiName, String receivedZowiAppId, Float receivedBatteryLevel) {
                        return new ConnectionSuccessData(receivedZowiName, receivedZowiAppId, receivedBatteryLevel.floatValue());
                    }
                }).timeout(3000L, TimeUnit.MILLISECONDS, Single.just(null));
            }
        }).doOnSuccess(new Action1<ConnectionSuccessData>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.1
            @Override // rx.functions.Action1
            public void call(ConnectionSuccessData connectionSuccessData) {
                String zowiAppId = null;
                if (connectionSuccessData != null) {
                    zowiAppId = connectionSuccessData.getZowiAppId();
                }
                ConnectToZowiInteractorImpl.this.kitonNetworkController.sendIsAliveToKiton(deviceAddress, zowiAppId).subscribeOn(Schedulers.io()).subscribe(new SingleSubscriber<KitonIsAliveResponseNetworkModel>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.1.1
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
        });
    }

    @Override // com.bq.zowi.interactors.ConnectToZowiInteractor
    public Single<Void> connectToZowi(final String deviceAddress, final boolean isDuplexConnection) {
        return this.btAdapterController.enableBluetooth().flatMap(new Func1<Void, Single<Void>>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.4
            @Override // rx.functions.Func1
            public Single<Void> call(Void aVoid) {
                Grove.d("Bluetooth enabled. Start connecting.", new Object[0]);
                return ConnectToZowiInteractorImpl.this.btConnectionController.connect(ConnectToZowiInteractorImpl.this.btAdapterController.getRemoteDevice(deviceAddress), isDuplexConnection);
            }
        }).toObservable().retryWhen(new RetryWithDelay(3, 1000)).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.3
            private boolean alreadyTriedDisablingBluetooth = false;

            @Override // rx.functions.Func1
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.3.1
                    @Override // rx.functions.Func1
                    public Observable<?> call(Throwable throwable) {
                        if (!AnonymousClass3.this.alreadyTriedDisablingBluetooth) {
                            AnonymousClass3.this.alreadyTriedDisablingBluetooth = true;
                            Grove.d(throwable, "ConnectToZowi error. Retried several times and still failing. Trying disabling and re-enabling bluetooth.", new Object[0]);
                            return ConnectToZowiInteractorImpl.this.btAdapterController.disableBluetooth().toObservable();
                        }
                        Grove.d(throwable, "Already tried disabling and re-enabling bluetooth. Nothing else to do. Just fail.", new Object[0]);
                        return Observable.error(throwable);
                    }
                });
            }
        }).toSingle();
    }

    @Override // com.bq.zowi.interactors.ConnectToZowiInteractor
    public Single<Void> disconnectFromZowi(String deviceAddress) {
        return Observable.zip(Observable.create(new Observable.OnSubscribe<Void>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.5
            @Override // rx.functions.Action1
            public void call(Subscriber<? super Void> subscriber) {
                ConnectToZowiInteractorImpl.this.btConnectionController.stopConnection();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        }), Observable.timer(1000L, TimeUnit.MILLISECONDS), new Func2<Void, Long, Void>() { // from class: com.bq.zowi.interactors.ConnectToZowiInteractorImpl.6
            @Override // rx.functions.Func2
            public Void call(Void aVoid, Long aLong) {
                return null;
            }
        }).toSingle();
    }
}
