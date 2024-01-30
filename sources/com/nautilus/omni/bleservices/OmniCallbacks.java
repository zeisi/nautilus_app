package com.nautilus.omni.bleservices;

import android.util.SparseArray;
import com.nautilus.omni.bleservices.ble.OmniDevice;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConnectionState;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniErrorTypes;
import java.nio.ByteBuffer;

public abstract class OmniCallbacks {
    public static final String TAG = OmniCallbacks.class.getSimpleName();

    public void onFoundOmni(OmniDevice foundOmni, String uniqueID, String name, int signalStrength) {
    }

    public void omniSignalUpdate(OmniDevice foundOmni, String uniqueID, int signalStrength) {
    }

    public void onFoundMY16(OmniDevice foundOmni, ByteBuffer uniqueID, String name, int signalStrength, int consoleState, int userNum, boolean isConsoleConnectable) {
    }

    public void mY16SignalUpdate(OmniDevice foundOmni, ByteBuffer uniqueID, int signalStrength, int consoleState, int userNum, boolean isConsoleConnectable) {
    }

    public void nautilusDeviceConnectionStateChange(OmniConnectionState newConnectionState) {
    }

    public void nautilusDeviceError(OmniErrorTypes maxError) {
    }

    public void nautilusDeviceUniqueID(ByteBuffer maxUniqueID) {
    }

    public void nautilusDeviceStatus(SparseArray statusData) {
    }

    public void nvmClearFlagsStatus(boolean success) {
    }

    public void nautilusDeviceSystemData(int systemMaxUsers, int systemNumPrograms) {
    }

    public void nautilusDeviceUserData(SparseArray userData) {
    }

    public void nautilusDeviceProductData(SparseArray productData) {
    }

    public void nautilusDeviceUserWorkoutData(SparseArray userWorkoutData) {
    }

    public void nautilusDeviceUserNumberSelected(int userNumber) {
    }

    public void nautilusDeviceConsoleDisconnectWarning(int currentUser) {
    }
}
