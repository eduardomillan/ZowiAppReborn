package com.bq.zowi.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.utils.Grove;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class BTAdapterControllerImpl implements BTAdapterController {
    private BluetoothAdapter bluetoothAdapter;
    private Subscriber<? super BluetoothDevice> bluetoothDeviceFoundSubscriber;
    private Context context;
    private SingleSubscriber<? super Void> disableBluetoothSubscriber;
    private SingleSubscriber<? super Void> enableBluetoothSubscriber;
    private final BroadcastReceiver enableBluetoothChangeBroadcastReceiver = new BroadcastReceiver() { // from class: com.bq.zowi.controllers.BTAdapterControllerImpl.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int bluetoothAdapterState = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                if (bluetoothAdapterState == 12) {
                    Grove.d("Bluetooth enabled!", new Object[0]);
                    BTAdapterControllerImpl.this.enableBluetoothSubscriber.onSuccess(null);
                    context.unregisterReceiver(this);
                } else if (bluetoothAdapterState == 10) {
                    Grove.d("Bluetooth could not be enabled.", new Object[0]);
                    BTAdapterControllerImpl.this.enableBluetoothSubscriber.onError(new BTAdapterController.BluetoothNotEnabledException());
                    context.unregisterReceiver(this);
                }
            }
        }
    };
    private final BroadcastReceiver disableBluetoothChangeBroadcastReceiver = new BroadcastReceiver() { // from class: com.bq.zowi.controllers.BTAdapterControllerImpl.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int bluetoothAdapterState = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                if (bluetoothAdapterState == 10) {
                    Grove.d("Bluetooth disabled!", new Object[0]);
                    BTAdapterControllerImpl.this.disableBluetoothSubscriber.onSuccess(null);
                    context.unregisterReceiver(this);
                } else if (bluetoothAdapterState == 12) {
                    Grove.d("Bluetooth could not be disabled.", new Object[0]);
                    BTAdapterControllerImpl.this.disableBluetoothSubscriber.onError(new BTAdapterController.BluetoothNotDisabledException());
                    context.unregisterReceiver(this);
                }
            }
        }
    };
    private final BroadcastReceiver discoveryBroadcastReceiver = new BroadcastReceiver() { // from class: com.bq.zowi.controllers.BTAdapterControllerImpl.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BTAdapterControllerImpl.this.bluetoothDeviceFoundSubscriber.onNext(bluetoothDevice);
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                BTAdapterControllerImpl.this.bluetoothDeviceFoundSubscriber.onCompleted();
                context.unregisterReceiver(this);
            }
        }
    };

    public BTAdapterControllerImpl(Context context, BluetoothAdapter bluetoothAdapter) {
        this.context = context;
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override // com.bq.zowi.controllers.BTAdapterController
    public Single<Void> enableBluetooth() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.BTAdapterControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                Grove.d("Enabling bluetooth...", new Object[0]);
                if (BTAdapterControllerImpl.this.bluetoothAdapter != null) {
                    BTAdapterControllerImpl.this.enableBluetoothSubscriber = singleSubscriber;
                    if (!BTAdapterControllerImpl.this.bluetoothAdapter.isEnabled()) {
                        BTAdapterControllerImpl.this.registerEnableBluetoothChangeReceivers();
                        BTAdapterControllerImpl.this.bluetoothAdapter.enable();
                        return;
                    } else {
                        Grove.d("Bluetooth was already enabled. Skipping.", new Object[0]);
                        BTAdapterControllerImpl.this.enableBluetoothSubscriber.onSuccess(null);
                        return;
                    }
                }
                singleSubscriber.onError(new IllegalStateException("Bluetooth is not supported on this hardware platform"));
            }
        });
    }

    @Override // com.bq.zowi.controllers.BTAdapterController
    public Single<Void> disableBluetooth() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.BTAdapterControllerImpl.5
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                Grove.d("Disabling bluetooth...", new Object[0]);
                if (BTAdapterControllerImpl.this.bluetoothAdapter != null) {
                    BTAdapterControllerImpl.this.disableBluetoothSubscriber = singleSubscriber;
                    if (BTAdapterControllerImpl.this.bluetoothAdapter.isEnabled()) {
                        BTAdapterControllerImpl.this.registerDisableBluetoothChangeReceivers();
                        BTAdapterControllerImpl.this.bluetoothAdapter.disable();
                        return;
                    } else {
                        Grove.d("Bluetooth was already disabled. Skipping.", new Object[0]);
                        BTAdapterControllerImpl.this.disableBluetoothSubscriber.onSuccess(null);
                        return;
                    }
                }
                singleSubscriber.onError(new IllegalStateException("Bluetooth is not supported on this hardware platform"));
            }
        });
    }

    @Override // com.bq.zowi.controllers.BTAdapterController
    public Observable<BluetoothDevice> startBTDiscovery() {
        return Observable.create(new Observable.OnSubscribe<BluetoothDevice>() { // from class: com.bq.zowi.controllers.BTAdapterControllerImpl.6
            @Override // rx.functions.Action1
            public void call(Subscriber<? super BluetoothDevice> subscriber) {
                BTAdapterControllerImpl.this.bluetoothDeviceFoundSubscriber = subscriber;
                BTAdapterControllerImpl.this.bluetoothAdapter.cancelDiscovery();
                BTAdapterControllerImpl.this.registerDiscoveryReceivers();
                BTAdapterControllerImpl.this.bluetoothAdapter.startDiscovery();
            }
        });
    }

    @Override // com.bq.zowi.controllers.BTAdapterController
    public void stopBTDiscovery() {
        if (this.bluetoothAdapter.isDiscovering()) {
            this.bluetoothAdapter.cancelDiscovery();
        }
        if (this.bluetoothDeviceFoundSubscriber != null && !this.bluetoothDeviceFoundSubscriber.isUnsubscribed()) {
            this.bluetoothDeviceFoundSubscriber.onCompleted();
        }
    }

    @Override // com.bq.zowi.controllers.BTAdapterController
    public BluetoothDevice getRemoteDevice(String address) {
        return this.bluetoothAdapter.getRemoteDevice(address);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerEnableBluetoothChangeReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.context.registerReceiver(this.enableBluetoothChangeBroadcastReceiver, filter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDisableBluetoothChangeReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.context.registerReceiver(this.disableBluetoothChangeBroadcastReceiver, filter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDiscoveryReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        filter.addAction("android.bluetooth.device.action.FOUND");
        filter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.context.registerReceiver(this.discoveryBroadcastReceiver, filter);
    }
}
