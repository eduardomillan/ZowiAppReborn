package com.bq.zowi.controllers;

import com.bq.zowi.controllers.ZowiDataController;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.commands.DataRequestCommand;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import java.util.List;
import java.util.Vector;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class ZowiDataControllerImpl implements ZowiDataController {
    private BTConnectionController connectionController;
    private final List<ZowiDataController.OnBatteryLevelReceivedListener> onBatteryLevelReceivedListeners = new Vector();
    private final List<ZowiDataController.OnDistanceLevelReceivedListener> onDistanceLevelReceivedListeners = new Vector();
    private final List<ZowiDataController.OnNoiseLevelReceivedListener> onNoiseLevelReceivedListeners = new Vector();
    private final List<ZowiDataController.OnZowiAppIdReceivedListener> onZowiAppIdReceivedListeners = new Vector();
    private final List<ZowiDataController.OnZowiNameReceivedListener> onZowiNameReceivedListeners = new Vector();
    private Subscription receivedMessageSubscription;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private Scheduler uiScheduler;

    public ZowiDataControllerImpl(BTConnectionController connectionController, SendCommandToZowiInteractor sendCommandToZowiInteractor, Scheduler uiScheduler) {
        this.connectionController = connectionController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
        this.uiScheduler = uiScheduler;
        addZowiReceivedMessageListener();
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<Float> getBatteryLevel() {
        return Single.create(new Single.OnSubscribe<Float>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.1
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Float> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveBatteryLevel(singleSubscriber);
                ZowiDataControllerImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new DataRequestCommand(Command.Action.BATTERY)).subscribe(new CommandSingleSubscriber());
            }
        });
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<Float> waitForBatteryLevelReception() {
        return Single.create(new Single.OnSubscribe<Float>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.2
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Float> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveBatteryLevel(singleSubscriber);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveBatteryLevel(final SingleSubscriber<? super Float> singleSubscriber) {
        synchronized (this.onBatteryLevelReceivedListeners) {
            this.onBatteryLevelReceivedListeners.add(new ZowiDataController.OnBatteryLevelReceivedListener() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.3
                @Override // com.bq.zowi.controllers.ZowiDataController.OnBatteryLevelReceivedListener
                public void onBatteryLevelReceived(float batteryLevel) {
                    singleSubscriber.onSuccess(Float.valueOf(batteryLevel));
                }
            });
        }
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<Integer> getDistanceLevel() {
        return Single.create(new Single.OnSubscribe<Integer>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveDistanceLevel(singleSubscriber);
                ZowiDataControllerImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new DataRequestCommand(Command.Action.DISTANCE)).subscribe(new CommandSingleSubscriber());
            }
        });
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<Integer> waitForDistanceLevelReception() {
        return Single.create(new Single.OnSubscribe<Integer>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.5
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveDistanceLevel(singleSubscriber);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveDistanceLevel(final SingleSubscriber<? super Integer> singleSubscriber) {
        synchronized (this.onDistanceLevelReceivedListeners) {
            this.onDistanceLevelReceivedListeners.add(new ZowiDataController.OnDistanceLevelReceivedListener() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.6
                @Override // com.bq.zowi.controllers.ZowiDataController.OnDistanceLevelReceivedListener
                public void onDistanceLevelReceived(int distanceLevel) {
                    singleSubscriber.onSuccess(Integer.valueOf(distanceLevel));
                }
            });
        }
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<Integer> getNoiseLevel() {
        return Single.create(new Single.OnSubscribe<Integer>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.7
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveNoiseLevel(singleSubscriber);
                ZowiDataControllerImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new DataRequestCommand(Command.Action.NOISE)).subscribe(new CommandSingleSubscriber());
            }
        });
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<Integer> waitForNoiseLevelReception() {
        return Single.create(new Single.OnSubscribe<Integer>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.8
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveNoiseLevel(singleSubscriber);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveNoiseLevel(final SingleSubscriber<? super Integer> singleSubscriber) {
        synchronized (this.onNoiseLevelReceivedListeners) {
            this.onNoiseLevelReceivedListeners.add(new ZowiDataController.OnNoiseLevelReceivedListener() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.9
                @Override // com.bq.zowi.controllers.ZowiDataController.OnNoiseLevelReceivedListener
                public void onNoiseLevelReceived(int noiseLevel) {
                    singleSubscriber.onSuccess(Integer.valueOf(noiseLevel));
                }
            });
        }
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<String> getZowiAppId() {
        return Single.create(new Single.OnSubscribe<String>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.10
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveZowiAppId(singleSubscriber);
                ZowiDataControllerImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new DataRequestCommand(Command.Action.APP_ID)).subscribe(new CommandSingleSubscriber());
            }
        });
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<String> waitForZowiAppIdReception() {
        return Single.create(new Single.OnSubscribe<String>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.11
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveZowiAppId(singleSubscriber);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveZowiAppId(final SingleSubscriber<? super String> singleSubscriber) {
        synchronized (this.onZowiAppIdReceivedListeners) {
            this.onZowiAppIdReceivedListeners.add(new ZowiDataController.OnZowiAppIdReceivedListener() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.12
                @Override // com.bq.zowi.controllers.ZowiDataController.OnZowiAppIdReceivedListener
                public void onZowiAppIdReceived(String zowiAppId) {
                    singleSubscriber.onSuccess(zowiAppId);
                }
            });
        }
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<String> getZowiName() {
        return Single.create(new Single.OnSubscribe<String>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.13
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveZowiName(singleSubscriber);
                ZowiDataControllerImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new DataRequestCommand(Command.Action.NAME)).subscribe(new CommandSingleSubscriber());
            }
        });
    }

    @Override // com.bq.zowi.controllers.ZowiDataController
    public Single<String> waitForZowiNameReception() {
        return Single.create(new Single.OnSubscribe<String>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.14
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                ZowiDataControllerImpl.this.receiveZowiName(singleSubscriber);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveZowiName(final SingleSubscriber<? super String> singleSubscriber) {
        synchronized (this.onZowiNameReceivedListeners) {
            this.onZowiNameReceivedListeners.add(new ZowiDataController.OnZowiNameReceivedListener() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.15
                @Override // com.bq.zowi.controllers.ZowiDataController.OnZowiNameReceivedListener
                public void onZowiNameReceived(String zowiName) {
                    singleSubscriber.onSuccess(zowiName);
                }
            });
        }
    }

    private void addZowiReceivedMessageListener() {
        if (this.receivedMessageSubscription == null || this.receivedMessageSubscription.isUnsubscribed()) {
            this.receivedMessageSubscription = this.connectionController.getReceivedMessageObservable().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe((Subscriber<? super String>) new Subscriber<String>() { // from class: com.bq.zowi.controllers.ZowiDataControllerImpl.16
                @Override // rx.Observer
                public void onCompleted() {
                }

                @Override // rx.Observer
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override // rx.Observer
                public void onNext(String receivedMessage) {
                    char receivedMessageType = receivedMessage.charAt(0);
                    if (receivedMessage.length() >= 2) {
                        String receivedMessageBody = receivedMessage.substring(2);
                        switch (receivedMessageType) {
                            case 'B':
                                synchronized (ZowiDataControllerImpl.this.onBatteryLevelReceivedListeners) {
                                    for (ZowiDataController.OnBatteryLevelReceivedListener listener : ZowiDataControllerImpl.this.onBatteryLevelReceivedListeners) {
                                        listener.onBatteryLevelReceived(Float.valueOf(receivedMessageBody).floatValue());
                                    }
                                    ZowiDataControllerImpl.this.onBatteryLevelReceivedListeners.clear();
                                    break;
                                }
                            case 'D':
                                synchronized (ZowiDataControllerImpl.this.onDistanceLevelReceivedListeners) {
                                    for (ZowiDataController.OnDistanceLevelReceivedListener listener2 : ZowiDataControllerImpl.this.onDistanceLevelReceivedListeners) {
                                        listener2.onDistanceLevelReceived(Integer.valueOf(receivedMessageBody).intValue());
                                    }
                                    ZowiDataControllerImpl.this.onDistanceLevelReceivedListeners.clear();
                                    break;
                                }
                            case 'E':
                                synchronized (ZowiDataControllerImpl.this.onZowiNameReceivedListeners) {
                                    for (ZowiDataController.OnZowiNameReceivedListener listener3 : ZowiDataControllerImpl.this.onZowiNameReceivedListeners) {
                                        listener3.onZowiNameReceived(receivedMessageBody);
                                    }
                                    ZowiDataControllerImpl.this.onZowiNameReceivedListeners.clear();
                                    break;
                                }
                            case 'I':
                                synchronized (ZowiDataControllerImpl.this.onZowiAppIdReceivedListeners) {
                                    for (ZowiDataController.OnZowiAppIdReceivedListener listener4 : ZowiDataControllerImpl.this.onZowiAppIdReceivedListeners) {
                                        listener4.onZowiAppIdReceived(receivedMessageBody);
                                    }
                                    ZowiDataControllerImpl.this.onZowiAppIdReceivedListeners.clear();
                                    break;
                                }
                            case 'N':
                                synchronized (ZowiDataControllerImpl.this.onNoiseLevelReceivedListeners) {
                                    for (ZowiDataController.OnNoiseLevelReceivedListener listener5 : ZowiDataControllerImpl.this.onNoiseLevelReceivedListeners) {
                                        listener5.onNoiseLevelReceived(Integer.valueOf(receivedMessageBody).intValue());
                                    }
                                    ZowiDataControllerImpl.this.onNoiseLevelReceivedListeners.clear();
                                    break;
                                }
                            default:
                                return;
                        }
                    }
                }
            });
        }
    }

    private void removeZowiReceivedMessageListener() {
        if (this.receivedMessageSubscription != null && !this.receivedMessageSubscription.isUnsubscribed()) {
            this.receivedMessageSubscription.unsubscribe();
        }
    }
}
