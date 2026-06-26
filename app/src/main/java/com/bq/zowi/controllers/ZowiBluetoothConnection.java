package com.bq.zowi.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.bq.zowi.R;
import com.bq.zowi.utils.Grove;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class ZowiBluetoothConnection {
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private Context context;
    private final Handler handler;
    private volatile boolean isReadingIncomingDataEnabled = true;
    private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private int state = 0;

    public ZowiBluetoothConnection(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
    }

    private synchronized void setState(int state) {
        this.state = state;
        this.handler.obtainMessage(1, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        return this.state;
    }

    public boolean isReadingIncomingDataEnabled() {
        return this.isReadingIncomingDataEnabled;
    }

    public void enabledReadingIncomingData() {
        this.isReadingIncomingDataEnabled = true;
        this.connectedThread.semaphore.release();
    }

    public synchronized void start() {
        if (this.connectThread != null) {
            this.connectThread.cancel();
            this.connectThread = null;
        }
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
        }
        setState(1);
    }

    public synchronized void connect(BluetoothDevice device, boolean enabledReadingIncomingData) {
        if (this.state == 2 && this.connectThread != null) {
            this.connectThread.cancel();
            this.connectThread = null;
        }
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
        }
        this.connectThread = new ConnectThread(device, enabledReadingIncomingData);
        this.connectThread.start();
        setState(2);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, boolean enabledReadingIncomingData) {
        if (this.connectThread != null) {
            this.connectThread.cancel();
            this.connectThread = null;
        }
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
        }
        this.connectedThread = new ConnectedThread(socket, enabledReadingIncomingData);
        this.connectedThread.start();
        Message msg = this.handler.obtainMessage(4);
        Bundle bundle = new Bundle();
        bundle.putString(Droid2InoConstants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        this.handler.sendMessage(msg);
        setState(3);
    }

    public synchronized void stop() {
        if (this.connectThread != null) {
            this.connectThread.cancel();
            this.connectThread = null;
        }
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
        }
        setState(0);
    }

    public void write(byte[] out) {
        synchronized (this) {
            if (this.state == 3) {
                ConnectedThread r = this.connectedThread;
                r.write(out);
            }
        }
    }

    public OutputStream getBTOutputStream() {
        synchronized (this) {
            if (this.state != 3) {
                return null;
            }
            ConnectedThread r = this.connectedThread;
            return r.getOutputStream();
        }
    }

    public InputStream getBTInputStream() {
        synchronized (this) {
            if (this.state != 3) {
                return null;
            }
            ConnectedThread r = this.connectedThread;
            return r.getInputStream();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectionFailed() {
        Message msg = this.handler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putString(Droid2InoConstants.TOAST, this.context.getString(R.string.connecting_bluetooth_error));
        msg.setData(bundle);
        this.handler.sendMessage(msg);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectionLost() {
        Message msg = this.handler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putString(Droid2InoConstants.TOAST, this.context.getString(R.string.connection_lost_error));
        msg.setData(bundle);
        this.handler.sendMessage(msg);
        start();
    }

    private class ConnectThread extends Thread {
        private final BluetoothDevice btDevice;
        private BluetoothSocket btSocket;
        private final boolean enabledReadingIncomingData;

        public ConnectThread(BluetoothDevice device, boolean enabledReadingIncomingData) {
            this.btDevice = device;
            this.enabledReadingIncomingData = enabledReadingIncomingData;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(Droid2InoConstants.MY_UUID);
            } catch (IOException e) {
                Grove.d("Socket create() failed", e);
            }
            this.btSocket = tmp;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Grove.i("BEGIN connectThread", new Object[0]);
            setName(Droid2InoConstants.CONNECT_THREAD_NAME);
            ZowiBluetoothConnection.this.adapter.cancelDiscovery();
            if (this.btSocket == null) {
                Grove.d("btSocket in run of ConnectThread = null", new Object[0]);
                return;
            }
            try {
                this.btSocket.connect();
            } catch (IOException e) {
                Grove.d(e.getMessage(), new Object[0]);
                try {
                    Grove.d("trying fallback...", new Object[0]);
                    this.btSocket = (BluetoothSocket) this.btDevice.getClass().getMethod("createRfcommSocket", Integer.TYPE).invoke(this.btDevice, 1);
                    this.btSocket.connect();
                    Grove.d("Connected using fallback", new Object[0]);
                } catch (Exception e2) {
                    Grove.d("error connecting the socket in run method of connect thread: " + e2, new Object[0]);
                    try {
                        this.btSocket.close();
                    } catch (IOException e3) {
                        Grove.d("unable to close() socket during connection failure", e3);
                    }
                    ZowiBluetoothConnection.this.connectionFailed();
                    return;
                }
            }
            synchronized (ZowiBluetoothConnection.this) {
                ZowiBluetoothConnection.this.connectThread = null;
            }
            ZowiBluetoothConnection.this.connected(this.btSocket, this.btDevice, this.enabledReadingIncomingData);
        }

        public void cancel() {
            try {
                this.btSocket.close();
            } catch (IOException e) {
                Grove.d("close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream btInputStream;
        private final OutputStream btOutputStream;
        private final BluetoothSocket btSocket;
        private final boolean enabledReadingIncomingData;
        private final StringBuffer readMessage;
        Semaphore semaphore = new Semaphore(0);

        public ConnectedThread(BluetoothSocket socket, boolean enabledReadingIncomingData) {
            Grove.d("create ConnectedThread", new Object[0]);
            this.btSocket = socket;
            this.enabledReadingIncomingData = enabledReadingIncomingData;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            this.readMessage = new StringBuffer();
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Grove.d("temp sockets not created", e);
            }
            this.btInputStream = tmpIn;
            this.btOutputStream = tmpOut;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Grove.i("BEGIN connectedThread", new Object[0]);
            byte[] buffer = new byte[1024];
            Pattern pattern = Pattern.compile("&&([^%&]+)%%");
            Pattern cleanPattern = Pattern.compile("(\r\n)");
            if (!this.enabledReadingIncomingData) {
                this.semaphore.acquireUninterruptibly();
            }
            while (true) {
                try {
                    int bytes = this.btInputStream.read(buffer);
                    this.readMessage.append(new String(buffer, 0, bytes));
                    Grove.d("readMessage buffer: " + ((Object) this.readMessage), new Object[0]);
                    Matcher m = pattern.matcher(this.readMessage);
                    while (m.find()) {
                        String message = m.group(1);
                        ZowiBluetoothConnection.this.handler.obtainMessage(2, bytes, -1, message).sendToTarget();
                        this.readMessage.delete(this.readMessage.indexOf(message) - 2, this.readMessage.indexOf(message) + message.length() + 2);
                    }
                    Matcher m2 = cleanPattern.matcher(this.readMessage);
                    while (m2.find()) {
                        String message2 = m2.group(1);
                        this.readMessage.delete(this.readMessage.indexOf(message2), this.readMessage.indexOf(message2) + message2.length());
                    }
                } catch (IOException e) {
                    Grove.d("disconnected", e);
                    ZowiBluetoothConnection.this.connectionLost();
                    ZowiBluetoothConnection.this.start();
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.btOutputStream.write(buffer);
                ZowiBluetoothConnection.this.handler.obtainMessage(3, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Grove.d("Exception during write", e);
            }
        }

        public void cancel() {
            try {
                this.btSocket.close();
            } catch (IOException e) {
                Grove.d("close() of connect socket failed", e);
            }
        }

        public InputStream getInputStream() {
            return this.btInputStream;
        }

        public OutputStream getOutputStream() {
            return this.btOutputStream;
        }
    }
}
