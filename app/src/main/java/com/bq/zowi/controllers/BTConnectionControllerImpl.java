package com.bq.zowi.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.bq.robotic.protocolSTK500v1.Logger;
import com.bq.robotic.protocolSTK500v1.STK500v1;
import com.bq.zowi.utils.Grove;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/* JADX INFO: loaded from: classes.dex */
public class BTConnectionControllerImpl implements BTConnectionController {
    private BluetoothAdapter bluetoothAdapter;
    private ZowiBluetoothConnection bluetoothConnection;
    private String connectedDeviceName;
    private PublishSubject<String> receivedMessagePublishSubject;
    private SessionController sessionController;
    private Scheduler uiScheduler;
    private boolean isSendingHexToZowi = false;
    private final String PIN = "1234";
    private final BroadcastReceiver disconnectedReceiver = new BroadcastReceiver() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action) && device.getAddress().equals(BTConnectionControllerImpl.this.sessionController.loadActiveZowiDeviceAddress())) {
                BTConnectionControllerImpl.this.bluetoothConnection.stop();
                BTConnectionControllerImpl.this.connectionStatusBehaviorSubject.onNext(0);
            }
        }
    };
    private BehaviorSubject<Integer> connectionStatusBehaviorSubject = BehaviorSubject.create();

    public BTConnectionControllerImpl(Context context, BluetoothAdapter bluetoothAdapter, SessionController sessionController, Scheduler uiScheduler) {
        this.bluetoothConnection = new ZowiBluetoothConnection(context, new ZowiConnectionMessageHandler());
        this.bluetoothAdapter = bluetoothAdapter;
        this.sessionController = sessionController;
        this.uiScheduler = uiScheduler;
        this.connectionStatusBehaviorSubject.onNext(0);
        this.receivedMessagePublishSubject = PublishSubject.create();
        IntentFilter disconnectedFilter = new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED");
        context.registerReceiver(this.disconnectedReceiver, disconnectedFilter);
    }

    private Observable<Integer> connectWithStates(final BluetoothDevice bluetoothDevice, final boolean enabledReadingIncomingData) {
        return Observable.create(new Observable.OnSubscribe<Integer>() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.2
            @Override // rx.functions.Action1
            public void call(Subscriber<? super Integer> subscriber) {
                if (BTConnectionControllerImpl.this.bluetoothConnection.getState() == 2) {
                    Grove.d("Skipping new connection attempt as there is already one in progress", new Object[0]);
                    subscriber.onCompleted();
                } else {
                    Grove.d("New connection attempt", new Object[0]);
                    BTConnectionControllerImpl.this.connectionStatusBehaviorSubject.onNext(0);
                    BTConnectionControllerImpl.this.bluetoothConnection.connect(bluetoothDevice, enabledReadingIncomingData);
                    subscriber.onCompleted();
                }
            }
        }).concatWith(this.connectionStatusBehaviorSubject);
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public Single<Void> connect(BluetoothDevice bluetoothDevice, boolean enabledReadingIncomingData) {
        return connectWithStates(bluetoothDevice, enabledReadingIncomingData).skipWhile(new Func1<Integer, Boolean>() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.5
            @Override // rx.functions.Func1
            public Boolean call(Integer connectionStatus) {
                return Boolean.valueOf(connectionStatus.intValue() != 2);
            }
        }).filter(new Func1<Integer, Boolean>() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.4
            @Override // rx.functions.Func1
            public Boolean call(Integer connectionStatus) {
                return Boolean.valueOf(connectionStatus.intValue() != 2);
            }
        }).map(new Func1<Integer, Void>() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.3
            @Override // rx.functions.Func1
            public Void call(Integer connectionStatus) {
                if (connectionStatus.intValue() != 3) {
                    throw new IllegalStateException("Conection state is different from STATE_CONNECTED");
                }
                return null;
            }
        }).first().toSingle();
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public Observable<Integer> getConnectionStatusObservable() {
        return this.connectionStatusBehaviorSubject;
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public Observable<String> getReceivedMessageObservable() {
        return this.receivedMessagePublishSubject;
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public void stopConnection() {
        this.bluetoothConnection.stop();
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public void enabledReadingIncomingData() {
        this.bluetoothConnection.enabledReadingIncomingData();
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public boolean isConnected() {
        if (this.bluetoothAdapter != null && this.bluetoothConnection.getState() == 3) {
            return true;
        }
        Grove.d("Mobile device not connected to BT device", new Object[0]);
        return false;
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public boolean isSendingHexToZowi() {
        return this.isSendingHexToZowi;
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public OutputStream getBTOutputStream() {
        return this.bluetoothConnection.getBTOutputStream();
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public InputStream getBTInputStream() {
        return this.bluetoothConnection.getBTInputStream();
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public void sendMessage(String message) {
        if (isConnected() && message.length() > 0) {
            byte[] send = message.getBytes();
            this.bluetoothConnection.write(send);
        }
    }

    @Override // com.bq.zowi.controllers.BTConnectionController
    public Observable<Integer> sendHexToZowi(final InputStream hexInputStream) {
        return Observable.create(new Observable.OnSubscribe<Integer>() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.7
            @Override // rx.functions.Action1
            public void call(final Subscriber<? super Integer> subscriber) throws Throwable {
                BTConnectionControllerImpl.this.isSendingHexToZowi = true;
                Logger log = new Logger() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.7.1
                    private final String STK500v1_LOGGER_TAG = "STK500v1";

                    @Override // com.bq.robotic.protocolSTK500v1.Logger
                    public void makeToast(String s) {
                        Grove.tag("STK500v1").d(s, new Object[0]);
                    }

                    @Override // com.bq.robotic.protocolSTK500v1.Logger
                    public void printToConsole(String s) {
                        Grove.tag("STK500v1").d(s, new Object[0]);
                    }

                    @Override // com.bq.robotic.protocolSTK500v1.Logger
                    public void logcat(String s, String s1) {
                        Grove.tag("STK500v1").d(s + " " + s1, new Object[0]);
                    }
                };
                BufferedReader br = null;
                StringBuilder hexData = new StringBuilder();
                try {
                    try {
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(hexInputStream));
                        while (true) {
                            try {
                                String line = br2.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    hexData.append(line);
                                }
                            } catch (FileNotFoundException e) {
                                e = e;
                                br = br2;
                                Grove.d("onSendClicked: " + e, "e");
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            } catch (IOException e3) {
                                e = e3;
                                br = br2;
                                Grove.d("onSendClicked: " + e, "e");
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                br = br2;
                                if (br != null) {
                                    try {
                                        br.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        if (br2 != null) {
                            try {
                                br2.close();
                                br = br2;
                            } catch (IOException e6) {
                                e6.printStackTrace();
                                br = br2;
                            }
                        } else {
                            br = br2;
                        }
                    } catch (FileNotFoundException e7) {
                        e = e7;
                    } catch (IOException e8) {
                        e = e8;
                    }
                    if (hexData.length() <= 0) {
                        subscriber.onError(new Exception("HEX data length is = 0. Invalid HEX data."));
                        return;
                    }
                    String hexDataProcessed = hexData.toString().replace(":", "3A");
                    byte[] binaryFile = new byte[hexDataProcessed.length() / 2];
                    for (int i = 0; i < hexDataProcessed.length(); i += 2) {
                        Integer iHex = Integer.valueOf(Integer.parseInt(hexDataProcessed.substring(i, i + 2), 16));
                        binaryFile[i / 2] = iHex.byteValue();
                    }
                    OutputStream outStream = BTConnectionControllerImpl.this.getBTOutputStream();
                    InputStream inputStream = BTConnectionControllerImpl.this.getBTInputStream();
                    boolean result = false;
                    Subscription progressSubscription = null;
                    try {
                        final STK500v1 p = new STK500v1(outStream, inputStream, log, binaryFile);
                        progressSubscription = Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.7.2
                            @Override // rx.functions.Action1
                            public void call(SingleSubscriber<? super Void> innerSubscriber) {
                                while (p.getProgress() < 100) {
                                    try {
                                        subscriber.onNext(Integer.valueOf(p.getProgress()));
                                        Thread.sleep(100L);
                                    } catch (InterruptedException e9) {
                                        e9.printStackTrace();
                                        return;
                                    }
                                }
                                innerSubscriber.onSuccess(null);
                            }
                        }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe();
                        result = p.programUsingOptiboot(false, 128);
                    } catch (Exception e9) {
                        Grove.d("Programming: " + e9, "e");
                    }
                    if (progressSubscription != null) {
                        progressSubscription.unsubscribe();
                    }
                    Grove.d("Protocol code stopped with result: " + result, "d");
                    if (result) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception("Something went wrong while programming"));
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }).doOnTerminate(new Action0() { // from class: com.bq.zowi.controllers.BTConnectionControllerImpl.6
            @Override // rx.functions.Action0
            public void call() {
                BTConnectionControllerImpl.this.isSendingHexToZowi = false;
            }
        });
    }

    private class ZowiConnectionMessageHandler extends Handler {
        private ZowiConnectionMessageHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    switch (msg.arg1) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            BTConnectionControllerImpl.this.connectionStatusBehaviorSubject.onNext(Integer.valueOf(msg.arg1));
                            Grove.d("Connectivity changed  : " + msg.arg1, new Object[0]);
                            break;
                    }
                    break;
                case 2:
                    String readMessage = (String) msg.obj;
                    Grove.d("Read message : " + readMessage, new Object[0]);
                    BTConnectionControllerImpl.this.receivedMessagePublishSubject.onNext(readMessage);
                    break;
                case 3:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    Grove.d("WriteMessage message : " + writeMessage, new Object[0]);
                    break;
                case 4:
                    BTConnectionControllerImpl.this.connectedDeviceName = msg.getData().getString(Droid2InoConstants.DEVICE_NAME);
                    Grove.d("Connected to: " + BTConnectionControllerImpl.this.connectedDeviceName, new Object[0]);
                    break;
                case 5:
                    Grove.d("Toast message received: " + msg.getData().getString(Droid2InoConstants.TOAST), new Object[0]);
                    break;
            }
        }
    }
}
