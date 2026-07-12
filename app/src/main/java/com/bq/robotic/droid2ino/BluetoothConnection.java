package com.bq.robotic.droid2ino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.bq.zowi.R;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothConnection {
    private static final String LOG_TAG = "BluetoothConnection";
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private Context mContext;
    private final Handler mHandler;
    private boolean isDuplexConnection = true;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private int mState = 0;

    public BluetoothConnection(Context context, Handler handler) {
        this.mHandler = handler;
        this.mContext = context;
    }

    private synchronized void setState(int state) {
        this.mState = state;
        this.mHandler.obtainMessage(1, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        return this.mState;
    }

    public boolean isDuplexConnection() {
        return this.isDuplexConnection;
    }

    public void setDuplexConnection(boolean isDuplexConnection) {
        this.isDuplexConnection = isDuplexConnection;
    }

    public synchronized void start() {
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        setState(1);
        if (this.mAcceptThread == null) {
            this.mAcceptThread = new AcceptThread();
            this.mAcceptThread.start();
        }
    }

    public synchronized void connect(BluetoothDevice device) {
        if (this.mState == 2 && this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        this.mConnectThread = new ConnectThread(device);
        this.mConnectThread.start();
        setState(2);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread != null) {
            this.mAcceptThread.cancel();
            this.mAcceptThread = null;
        }
        this.mConnectedThread = new ConnectedThread(socket);
        this.mConnectedThread.start();
        Message msg = this.mHandler.obtainMessage(4);
        Bundle bundle = new Bundle();
        bundle.putString(Droid2InoConstants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        setState(3);
    }

    public synchronized void stop() {
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread != null) {
            this.mAcceptThread.cancel();
            this.mAcceptThread = null;
        }
        setState(0);
    }

    public void write(byte[] out) {
        synchronized (this) {
            if (this.mState == 3) {
                ConnectedThread r = this.mConnectedThread;
                r.write(out);
            }
        }
    }

    public OutputStream getBTOutputStream() {
        synchronized (this) {
            if (this.mState != 3) {
                return null;
            }
            ConnectedThread r = this.mConnectedThread;
            return r.getMmOutStream();
        }
    }

    public InputStream getBTInputStream() {
        synchronized (this) {
            if (this.mState != 3) {
                return null;
            }
            ConnectedThread r = this.mConnectedThread;
            return r.getMmInStream();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectionFailed() {
        Message msg = this.mHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putString(Droid2InoConstants.TOAST, this.mContext.getString(R.string.connecting_bluetooth_error));
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectionLost() {
        Message msg = this.mHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putString(Droid2InoConstants.TOAST, this.mContext.getString(R.string.connection_lost_error));
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        start();
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = BluetoothConnection.this.mAdapter.listenUsingRfcommWithServiceRecord(Droid2InoConstants.SOCKET_NAME, Droid2InoConstants.MY_UUID);
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "Socket listen() failed", e);
            }
            this.mmServerSocket = tmp;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName(Droid2InoConstants.ACCEPT_THREAD_NAME);
            if (this.mmServerSocket != null) {
                while (BluetoothConnection.this.mState != 3) {
                    try {
                        BluetoothSocket socket = this.mmServerSocket.accept();
                        if (socket != null) {
                            synchronized (BluetoothConnection.this) {
                                switch (BluetoothConnection.this.mState) {
                                    case 0:
                                    case 3:
                                        try {
                                            socket.close();
                                        } catch (IOException e) {
                                            Log.e(BluetoothConnection.LOG_TAG, "Could not close unwanted socket", e);
                                        }
                                        break;
                                    case 1:
                                    case 2:
                                        BluetoothConnection.this.connected(socket, socket.getRemoteDevice());
                                        break;
                                }
                            }
                        }
                    } catch (IOException e2) {
                        Log.e(BluetoothConnection.LOG_TAG, "Socket accept() failed", e2);
                        return;
                    } catch (Exception e3) {
                        Log.e(BluetoothConnection.LOG_TAG, "Some type error", e3);
                        return;
                    }
                }
                return;
            }
            Log.e(BluetoothConnection.LOG_TAG, "mmServerSocket in run of AcceptThread = null");
        }

        public void cancel() {
            try {
                if (this.mmServerSocket != null) {
                    this.mmServerSocket.close();
                }
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "Socket close() of server failed", e);
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        private final BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device) {
            this.mmDevice = device;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(Droid2InoConstants.MY_UUID);
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "Socket create() failed", e);
            }
            this.mmSocket = tmp;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.i(BluetoothConnection.LOG_TAG, "BEGIN mConnectThread");
            setName(Droid2InoConstants.CONNECT_THREAD_NAME);
            BluetoothConnection.this.mAdapter.cancelDiscovery();
            if (this.mmSocket == null) {
                Log.e(BluetoothConnection.LOG_TAG, "mmSocket in run of ConnectThread = null");
                return;
            }
            try {
                this.mmSocket.connect();
                synchronized (BluetoothConnection.this) {
                    BluetoothConnection.this.mConnectThread = null;
                }
                BluetoothConnection.this.connected(this.mmSocket, this.mmDevice);
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "error connecting the socket in run method of connect thread: " + e);
                try {
                    this.mmSocket.close();
                } catch (IOException e2) {
                    Log.e(BluetoothConnection.LOG_TAG, "unable to close() socket during connection failure", e2);
                }
                BluetoothConnection.this.connectionFailed();
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;
        private final StringBuffer readMessage;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(BluetoothConnection.LOG_TAG, "create ConnectedThread");
            this.mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            this.readMessage = new StringBuffer();
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "temp sockets not created", e);
            }
            this.mmInStream = tmpIn;
            this.mmOutStream = tmpOut;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.i(BluetoothConnection.LOG_TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            while (BluetoothConnection.this.isDuplexConnection) {
                try {
                    int bytes = this.mmInStream.read(buffer);
                    this.readMessage.append(new String(buffer, 0, bytes));
                    int startIndex = this.readMessage.indexOf(Droid2InoConstants.START_READ_DELIMITER);
                    int endIndex = this.readMessage.indexOf(Droid2InoConstants.END_READ_DELIMITER);
                    Log.d(BluetoothConnection.LOG_TAG, "readMessage: " + ((Object) this.readMessage));
                    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                        String message = this.readMessage.substring(startIndex + 2, endIndex);
                        BluetoothConnection.this.mHandler.obtainMessage(2, bytes, -1, message).sendToTarget();
                        this.readMessage.delete(0, endIndex + 1);
                    }
                } catch (IOException e) {
                    Log.e(BluetoothConnection.LOG_TAG, "disconnected", e);
                    BluetoothConnection.this.connectionLost();
                    BluetoothConnection.this.start();
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.mmOutStream.write(buffer);
                BluetoothConnection.this.mHandler.obtainMessage(3, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
                Log.e(BluetoothConnection.LOG_TAG, "close() of connect socket failed", e);
            }
        }

        public InputStream getMmInStream() {
            return this.mmInStream;
        }

        public OutputStream getMmOutStream() {
            return this.mmOutStream;
        }
    }
}
