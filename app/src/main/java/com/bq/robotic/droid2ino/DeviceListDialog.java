package com.bq.robotic.droid2ino;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.bq.robotic.droid2ino.utils.DeviceListDialogStyle;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class DeviceListDialog extends Dialog {
    private static final String LOG_TAG = "DeviceListActivity";
    private BluetoothAdapter mBtAdapter;
    private AdapterView.OnItemClickListener mDeviceClickListener;
    private DeviceListDialogStyle mDialogStyle;
    private DialogListener mListener;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private final BroadcastReceiver mReceiver;

    public DeviceListDialog(Context context, DialogListener listener) {
        super(context);
        this.mDeviceClickListener = new AdapterView.OnItemClickListener() { // from class: com.bq.robotic.droid2ino.DeviceListDialog.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                DeviceListDialog.this.mBtAdapter.cancelDiscovery();
                String info = ((TextView) v).getText().toString();
                String address = info.substring(info.length() - 17);
                Intent intent = new Intent();
                intent.putExtra(Droid2InoConstants.EXTRA_DEVICE_ADDRESS, address);
                Bundle values = new Bundle();
                values.putString(Droid2InoConstants.EXTRA_DEVICE_ADDRESS, address);
                if (DeviceListDialog.this.mListener != null) {
                    DeviceListDialog.this.mListener.onComplete(values);
                }
                DeviceListDialog.this.dismiss();
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.bq.robotic.droid2ino.DeviceListDialog.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.bluetooth.device.action.FOUND".equals(action)) {
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    ListView pairedListView = (ListView) DeviceListDialog.this.findViewById(R.id.paired_devices);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, 0);
                    p.weight = 1.0f;
                    pairedListView.setLayoutParams(p);
                    if (device.getBondState() != 12) {
                        DeviceListDialog.this.mNewDevicesArrayAdapter.add(device.getName() + Droid2InoConstants.NEW_LINE_CHARACTER + device.getAddress());
                        return;
                    }
                    return;
                }
                if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                    TextView dialogTitle = (TextView) DeviceListDialog.this.findViewById(R.id.dialog_title);
                    dialogTitle.setText(R.string.select_device);
                }
            }
        };
        this.mListener = listener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(5);
        requestWindowFeature(1);
        setContentView(R.layout.device_list);
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.robotic.droid2ino.DeviceListDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DeviceListDialog.this.doDiscovery();
                v.setVisibility(8);
            }
        });
        TextView emptyPairedDevicesListItem = (TextView) findViewById(R.id.paired_devices_empty_item);
        TextView emptyNewDevicesListItem = (TextView) findViewById(R.id.new_devices_empty_item);
        this.mDialogStyle = new DeviceListDialogStyle((TextView) findViewById(R.id.dialog_title), (TextView) findViewById(R.id.title_paired_devices), (TextView) findViewById(R.id.title_new_devices));
        this.mPairedDevicesArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.device_name);
        this.mNewDevicesArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.device_name);
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter((ListAdapter) this.mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(this.mDeviceClickListener);
        pairedListView.setEmptyView(emptyPairedDevicesListItem);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter((ListAdapter) this.mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(this.mDeviceClickListener);
        newDevicesListView.setEmptyView(emptyNewDevicesListItem);
        emptyNewDevicesListItem.setVisibility(8);
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.FOUND");
        getContext().registerReceiver(this.mReceiver, filter);
        IntentFilter filter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        getContext().registerReceiver(this.mReceiver, filter2);
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = this.mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(0);
            for (BluetoothDevice device : pairedDevices) {
                this.mPairedDevicesArrayAdapter.add(device.getName() + Droid2InoConstants.NEW_LINE_CHARACTER + device.getAddress());
            }
        }
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        if (this.mBtAdapter != null) {
            this.mBtAdapter.cancelDiscovery();
        }
        getContext().unregisterReceiver(this.mReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDiscovery() {
        Log.d(LOG_TAG, "doDiscovery()");
        TextView dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogTitle.setText(R.string.scanning);
        findViewById(R.id.title_new_devices).setVisibility(0);
        if (this.mBtAdapter.isDiscovering()) {
            this.mBtAdapter.cancelDiscovery();
        }
        this.mBtAdapter.startDiscovery();
    }

    public DeviceListDialogStyle getDialogStyle() {
        return this.mDialogStyle;
    }
}
