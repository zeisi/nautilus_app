package com.ua.sdk.recorder.datasource.sensor.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.ua.sdk.UaLog;
import com.ua.sdk.recorder.RecorderContext;
import com.ua.sdk.recorder.SensorStatus;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BaseGattCallback;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothClient;
import java.util.Timer;
import java.util.TimerTask;

@TargetApi(18)
public class BluetoothConnection implements BluetoothClient {
    private static final int SCAN_RETRY_COUNT_MAX = 3;
    /* access modifiers changed from: private */
    public BaseGattCallback baseGattCallback;
    private BluetoothAdapter bluetoothAdapter;
    /* access modifiers changed from: private */
    public BluetoothGatt bluetoothGatt;
    private BluetoothManager bluetoothManager;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public String deviceAddress;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    protected BluetoothClient.BluetoothClientListener listener;
    private MyBluetoothStateReceiver myBluetoothStateReceiver = new MyBluetoothStateReceiver();
    private MyLeScanCallbacks myLeScanCallbacks = new MyLeScanCallbacks();
    /* access modifiers changed from: private */
    public MyStopScanRunnable myStopScanRunnable = new MyStopScanRunnable();
    /* access modifiers changed from: private */
    public int retryCount = 0;
    /* access modifiers changed from: private */
    public Timer timer;

    static /* synthetic */ int access$308(BluetoothConnection x0) {
        int i = x0.retryCount;
        x0.retryCount = i + 1;
        return i;
    }

    public BluetoothConnection(BaseGattCallback baseGattCallback2) {
        this.baseGattCallback = baseGattCallback2;
    }

    public void connect(BluetoothClient.BluetoothClientListener listener2, String deviceAddress2, Context context2) {
        this.listener = listener2;
        this.deviceAddress = deviceAddress2;
        this.context = context2;
        this.timer = new Timer("BluetoothClientTimer");
        this.baseGattCallback.setClientListener(listener2);
        context2.registerReceiver(this.myBluetoothStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        attemptConnect();
    }

    public void configure(RecorderContext recorderContext) {
        if (this.bluetoothManager == null) {
            this.bluetoothManager = (BluetoothManager) recorderContext.getApplicationContext().getSystemService("bluetooth");
        }
        this.baseGattCallback.configure(recorderContext);
    }

    /* access modifiers changed from: private */
    public void attemptConnect() {
        if (this.listener != null) {
            this.listener.onConnectionStatusChanged(SensorStatus.CONNECTING);
        }
        if (initializeAdapter()) {
            startScan();
        }
    }

    public void disconnect() {
        UaLog.debug("Client has disconnected from device");
        this.baseGattCallback.disconnect();
        if (this.timer != null) {
            this.handler.removeCallbacks(this.myStopScanRunnable);
            stopScan();
            this.timer.cancel();
        }
        this.context.unregisterReceiver(this.myBluetoothStateReceiver);
        if (this.bluetoothGatt != null) {
            this.bluetoothGatt.disconnect();
            this.bluetoothGatt.close();
            this.bluetoothGatt = null;
        }
        if (this.listener != null) {
            this.listener.onConnectionStatusChanged(SensorStatus.DISCONNECTED);
        }
    }

    public void startSegment() {
        this.baseGattCallback.startSegment();
    }

    public void stopSegment() {
    }

    private boolean initializeAdapter() {
        this.bluetoothAdapter = this.bluetoothManager.getAdapter();
        if (this.bluetoothAdapter != null) {
            return true;
        }
        UaLog.error("Unable to get the bluetooth adapter.");
        this.listener.onConnectionStatusChanged(SensorStatus.DISCONNECTED);
        return false;
    }

    /* access modifiers changed from: private */
    public void startScan() {
        if (this.bluetoothAdapter != null && this.bluetoothAdapter.isEnabled()) {
            this.bluetoothAdapter.startLeScan(this.myLeScanCallbacks);
            this.handler.postDelayed(this.myStopScanRunnable, 10000);
        }
    }

    /* access modifiers changed from: private */
    public void stopScan() {
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.myLeScanCallbacks);
        }
    }

    private class MyLeScanCallbacks implements BluetoothAdapter.LeScanCallback {
        private MyLeScanCallbacks() {
        }

        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (device.getAddress().equals(BluetoothConnection.this.deviceAddress)) {
                UaLog.debug("Device found! Connecting to server the server now");
                int unused = BluetoothConnection.this.retryCount = 0;
                BluetoothConnection.this.handler.removeCallbacks(BluetoothConnection.this.myStopScanRunnable);
                BluetoothConnection.this.stopScan();
                if (BluetoothConnection.this.bluetoothGatt != null) {
                    BluetoothConnection.this.bluetoothGatt.disconnect();
                    BluetoothConnection.this.bluetoothGatt.close();
                    BluetoothGatt unused2 = BluetoothConnection.this.bluetoothGatt = null;
                }
                BluetoothConnection.this.baseGattCallback.setConnectionLostListener(new MyConnectionLostListener());
                new Thread(new Runnable() {
                    public void run() {
                        BluetoothGatt unused = BluetoothConnection.this.bluetoothGatt = device.connectGatt(BluetoothConnection.this.context, false, BluetoothConnection.this.baseGattCallback);
                    }
                }).start();
            }
        }
    }

    protected class MyStopScanRunnable extends TimerTask {
        protected MyStopScanRunnable() {
        }

        public void run() {
            BluetoothConnection.this.stopScan();
            UaLog.error("Unable to find device with address " + BluetoothConnection.this.deviceAddress);
            BluetoothConnection.access$308(BluetoothConnection.this);
            if (BluetoothConnection.this.retryCount <= 3) {
                UaLog.error("retry scan number " + BluetoothConnection.this.retryCount);
                BluetoothConnection.this.startScan();
                return;
            }
            int unused = BluetoothConnection.this.retryCount = 0;
            UaLog.debug("we will attempt to reconnect in 60 seconds");
            BluetoothConnection.this.timer.schedule(new MyReconnectTask(), 60000);
        }
    }

    protected class MyReconnectTask extends TimerTask {
        protected MyReconnectTask() {
        }

        public void run() {
            BluetoothConnection.this.stopScan();
            BluetoothConnection.this.attemptConnect();
        }
    }

    private class MyConnectionLostListener implements BaseGattCallback.ConnectionLostListener {
        private MyConnectionLostListener() {
        }

        public void onScheduleReconnect(long delay) {
            BluetoothConnection.this.timer.schedule(new MyReconnectTask(), delay);
        }
    }

    private class MyBluetoothStateReceiver extends BroadcastReceiver {
        private MyBluetoothStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED") && intent.getExtras().getInt("android.bluetooth.adapter.extra.STATE") == 12) {
                BluetoothConnection.this.startScan();
            }
        }
    }
}
