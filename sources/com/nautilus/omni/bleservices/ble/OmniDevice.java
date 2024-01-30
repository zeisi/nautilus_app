package com.nautilus.omni.bleservices.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConnectionState;
import java.util.Observable;

public class OmniDevice extends Observable implements Parcelable {
    public static final Parcelable.Creator<OmniDevice> CREATOR = new Parcelable.Creator<OmniDevice>() {
        public OmniDevice createFromParcel(Parcel in) {
            return new OmniDevice(in);
        }

        public OmniDevice[] newArray(int size) {
            return new OmniDevice[size];
        }
    };
    public static final String mfgNameStr = "Nautilus, Inc.";
    private boolean haveBLE = false;
    private String mAddress;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothGatt mBluetoothGhatt;
    private String mDeviceName;
    private OmniConnectionState mDeviceStatus = OmniConnectionState.DISCONNECTED;
    private OmniDeviceType mDeviceType;
    private String mFirmwareRev;
    private String mHardwareRev;
    private String mManufacturerName;
    private String mModelNumber;
    private String mSoftwareVersion;
    private boolean paringInProcess = false;

    public boolean isParingInProcess() {
        return this.paringInProcess;
    }

    public void setParingInProcess(boolean paringInProcess2) {
        this.paringInProcess = paringInProcess2;
    }

    public OmniDevice(OmniDeviceType deviceType) {
        this.mDeviceType = deviceType;
        this.mBluetoothDevice = null;
        this.mBluetoothGhatt = null;
        this.mManufacturerName = mfgNameStr;
    }

    public OmniConnectionState getDeviceStatus() {
        return this.mDeviceStatus;
    }

    public void setDeviceStatus(OmniConnectionState deviceStatus) {
        this.mDeviceStatus = deviceStatus;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.mBluetoothDevice = bluetoothDevice;
    }

    public BluetoothGatt getBluetoothGhatt() {
        return this.mBluetoothGhatt;
    }

    public void setBluetoothGhatt(BluetoothGatt bluetoothGhatt) {
        this.mBluetoothGhatt = bluetoothGhatt;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        this.mDeviceName = deviceName;
    }

    public String getModelNumber() {
        return this.mModelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.mModelNumber = modelNumber;
    }

    public String getFirmwareRev() {
        return this.mFirmwareRev;
    }

    public void setFirmwareRev(String firmwareRev) {
        this.mFirmwareRev = firmwareRev;
    }

    public String getHardwareRev() {
        return this.mHardwareRev;
    }

    public void setHardwareRev(String hardwareRev) {
        this.mHardwareRev = hardwareRev;
    }

    public String getManufacturerName() {
        return this.mManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.mManufacturerName = manufacturerName;
    }

    public String getSWVersion() {
        return this.mSoftwareVersion;
    }

    public void setSWVersion(String softwareVersion) {
        this.mSoftwareVersion = softwareVersion;
    }

    public boolean isHaveBLE() {
        return this.haveBLE;
    }

    public void setHaveBLE(boolean gotBLE) {
        this.haveBLE = gotBLE;
    }

    private void triggerObservers() {
        setChanged();
        notifyObservers(this);
    }

    public OmniDeviceType getDeviceType() {
        return this.mDeviceType;
    }

    public void setDeviceType(OmniDeviceType deviceType) {
        this.mDeviceType = deviceType;
    }

    public OmniDevice(Parcel in) {
        this.mBluetoothDevice = (BluetoothDevice) in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.mManufacturerName = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mBluetoothDevice, 1);
        dest.writeString(this.mManufacturerName);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof OmniDevice) || this.mAddress == null) {
            return false;
        }
        OmniDevice device = (OmniDevice) o;
        if (device.getAddress() == null || !device.getAddress().equalsIgnoreCase(this.mAddress)) {
            return false;
        }
        return true;
    }
}
