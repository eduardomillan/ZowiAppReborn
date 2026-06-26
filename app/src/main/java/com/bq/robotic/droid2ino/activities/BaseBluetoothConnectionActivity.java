package com.bq.robotic.droid2ino.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;
import com.bq.robotic.droid2ino.BluetoothConnection;
import com.bq.robotic.droid2ino.DeviceListDialog;
import com.bq.robotic.droid2ino.DialogListener;
import com.bq.robotic.droid2ino.R;
import com.bq.robotic.droid2ino.utils.DeviceListDialogStyle;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseBluetoothConnectionActivity extends ActionBarActivity {
    private static final String LOG_TAG = "BaseConnectActivity";
    private BroadcastReceiver bluetoothDisconnectReceiver;
    private IntentFilter disconnectBluetoothFilter;
    protected String mConnectedDeviceName = null;
    protected BluetoothAdapter mBluetoothAdapter = null;
    protected BluetoothConnection mBluetoothConnection = null;
    protected boolean wasEnableBluetoothAllowed = false;
    protected boolean deviceConnectionWasRequested = false;
    private boolean wasBluetoothEnabled = false;
    private final Handler mHandler = new Handler() { // from class: com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    switch (msg.arg1) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            BaseBluetoothConnectionActivity.this.onConnectionStatusUpdate(msg.arg1);
                            break;
                    }
                    break;
                case 2:
                    String readMessage = (String) msg.obj;
                    BaseBluetoothConnectionActivity.this.onNewMessage(readMessage);
                    break;
                case 3:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    BaseBluetoothConnectionActivity.this.onWriteSuccess(writeMessage);
                    break;
                case 4:
                    BaseBluetoothConnectionActivity.this.mConnectedDeviceName = msg.getData().getString(Droid2InoConstants.DEVICE_NAME);
                    Toast.makeText(BaseBluetoothConnectionActivity.this.getApplicationContext(), BaseBluetoothConnectionActivity.this.getString(R.string.connected_to) + BaseBluetoothConnectionActivity.this.mConnectedDeviceName, 0).show();
                    break;
                case 5:
                    Toast.makeText(BaseBluetoothConnectionActivity.this.getApplicationContext(), msg.getData().getString(Droid2InoConstants.TOAST), 0).show();
                    break;
            }
        }
    };

    public abstract void onNewMessage(String str);

    @Override // android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter != null) {
            if (savedInstanceState != null) {
                this.wasEnableBluetoothAllowed = savedInstanceState.getBoolean(Droid2InoConstants.WAS_BLUETOOTH_ALLOWED_KEY);
            }
            if (this.mBluetoothAdapter.isEnabled()) {
                this.wasBluetoothEnabled = true;
            }
            this.bluetoothDisconnectReceiver = new DisconnectBluetoothBroadcastReceiver();
            this.disconnectBluetoothFilter = new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED");
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.bluetooth_not_available, 1).show();
            return;
        }
        registerReceiver(this.bluetoothDisconnectReceiver, this.disconnectBluetoothFilter);
        if (!this.mBluetoothAdapter.isEnabled() && this.wasEnableBluetoothAllowed) {
            this.mBluetoothAdapter.enable();
            setupSession();
        } else if (this.mBluetoothConnection == null) {
            setupSession();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.mBluetoothAdapter != null) {
            unregisterReceiver(this.bluetoothDisconnectReceiver);
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        if (this.mBluetoothAdapter != null) {
            stopApp();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(Droid2InoConstants.WAS_BLUETOOTH_ALLOWED_KEY, this.wasEnableBluetoothAllowed);
    }

    @Override // android.app.Activity
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.wasEnableBluetoothAllowed = savedInstanceState.getBoolean(Droid2InoConstants.WAS_BLUETOOTH_ALLOWED_KEY);
    }

    protected void stopApp() {
        stopBluetoothConnection();
        if (this.mBluetoothAdapter.isEnabled() && !this.wasBluetoothEnabled) {
            this.mBluetoothAdapter.disable();
        }
    }

    protected void stopBluetoothConnection() {
        if (this.mBluetoothConnection != null) {
            this.mBluetoothConnection.stop();
        }
    }

    protected void enableBluetooth() {
        if (this.wasEnableBluetoothAllowed) {
            this.mBluetoothAdapter.enable();
            setupSession();
        } else {
            Intent enableIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
            startActivityForResult(enableIntent, 3);
        }
    }

    protected void setupSession() {
        this.mBluetoothConnection = new BluetoothConnection(this, this.mHandler);
    }

    protected void ensureDiscoverable() {
        if (this.mBluetoothAdapter.getScanMode() != 23) {
            Intent discoverableIntent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
            discoverableIntent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 300);
            startActivity(discoverableIntent);
        }
    }

    protected void sendMessage(String message) {
        if (isConnected() && message.length() > 0) {
            byte[] send = message.getBytes();
            this.mBluetoothConnection.write(send);
        }
    }

    protected void sendMessage(byte[] messageBuffer) {
        if (isConnected() && messageBuffer.length > 0) {
            this.mBluetoothConnection.write(messageBuffer);
        }
    }

    protected boolean isConnected() {
        if (this.mBluetoothAdapter != null && this.mBluetoothConnection.getState() == 3) {
            return true;
        }
        Toast.makeText(this, R.string.not_connected, 0).show();
        return false;
    }

    protected boolean isConnectedWithoutToast() {
        return (this.mBluetoothAdapter == null || this.mBluetoothConnection == null || this.mBluetoothConnection.getState() != 3) ? false : true;
    }

    private DeviceListDialog deviceListDialog(DialogListener listener) {
        DeviceListDialog deviceDialog = new DeviceListDialog(this, listener);
        deviceDialog.show();
        return deviceDialog;
    }

    protected void requestDeviceConnection() {
        if (this.mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.bluetooth_not_available, 1).show();
        } else if (this.mBluetoothAdapter.isEnabled()) {
            DeviceListDialog deviceDialog = deviceListDialog(new DialogListener() { // from class: com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity.1
                @Override // com.bq.robotic.droid2ino.DialogListener
                public void onComplete(Bundle values) {
                    BaseBluetoothConnectionActivity.this.connectDevice(values);
                }

                @Override // com.bq.robotic.droid2ino.DialogListener
                public void onCancel() {
                }
            });
            onDeviceListDialogStyleObtained(deviceDialog.getDialogStyle());
        } else {
            this.deviceConnectionWasRequested = true;
            enableBluetooth();
        }
    }

    private void showListDialog() {
        if (this.mBluetoothAdapter.isEnabled()) {
            DeviceListDialog deviceDialog = deviceListDialog(new DialogListener() { // from class: com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity.2
                @Override // com.bq.robotic.droid2ino.DialogListener
                public void onComplete(Bundle values) {
                    BaseBluetoothConnectionActivity.this.connectDevice(values);
                }

                @Override // com.bq.robotic.droid2ino.DialogListener
                public void onCancel() {
                }
            });
            onDeviceListDialogStyleObtained(deviceDialog.getDialogStyle());
            this.deviceConnectionWasRequested = false;
            return;
        }
        this.deviceConnectionWasRequested = false;
    }

    protected void onDeviceListDialogStyleObtained(DeviceListDialogStyle deviceListDialogStyle) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectDevice(Bundle values) {
        String address = values.getString(Droid2InoConstants.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
        this.mBluetoothConnection.connect(device);
    }

    private void connectDevice(Intent data) {
        String address = data.getExtras().getString(Droid2InoConstants.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
        this.mBluetoothConnection.connect(device);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    connectDevice(data);
                }
                break;
            case 3:
                if (resultCode == -1) {
                    this.wasEnableBluetoothAllowed = true;
                    setupSession();
                    if (this.deviceConnectionWasRequested) {
                        showListDialog();
                    }
                } else {
                    Log.d(LOG_TAG, "BT not enabled");
                    this.wasEnableBluetoothAllowed = false;
                    Toast.makeText(this, R.string.bt_not_enabled, 0).show();
                    if (this.deviceConnectionWasRequested) {
                        showListDialog();
                    }
                }
                break;
        }
    }

    public void onConnectionStatusUpdate(int connectionState) {
        Log.d(LOG_TAG, "Connectivity changed  : " + connectionState);
    }

    public void onWriteSuccess(String message) {
        Log.d(LOG_TAG, "Response message : " + message);
    }

    public class DisconnectBluetoothBroadcastReceiver extends BroadcastReceiver {
        public DisconnectBluetoothBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                Log.i(BaseBluetoothConnectionActivity.LOG_TAG, "The connection was lost. The Bluetooth device was disconnected.");
                BaseBluetoothConnectionActivity.this.mBluetoothConnection.stop();
            }
        }
    }
}
