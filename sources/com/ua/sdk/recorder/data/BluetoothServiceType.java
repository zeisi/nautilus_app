package com.ua.sdk.recorder.data;

import com.ua.sdk.datapoint.BaseDataTypes;

public enum BluetoothServiceType {
    HEART_RATE(BaseDataTypes.ID_HEART_RATE),
    RUN_SPEED_CADENCE("run_speed_cadence"),
    BIKE_POWER("bike_power"),
    BIKE_SPEED_CADENCE("bike_speed_cadence"),
    ARMOUR_39("armour_39");
    
    private String type;

    private BluetoothServiceType(String type2) {
        this.type = type2;
    }

    public String toString() {
        return this.type;
    }

    public static BluetoothServiceType fromString(String type2) {
        if (type2 != null) {
            for (BluetoothServiceType bluetoothServiceType : values()) {
                if (bluetoothServiceType.type.equalsIgnoreCase(type2)) {
                    return bluetoothServiceType;
                }
            }
        }
        return null;
    }
}
