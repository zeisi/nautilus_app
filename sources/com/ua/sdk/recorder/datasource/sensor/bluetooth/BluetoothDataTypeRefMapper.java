package com.ua.sdk.recorder.datasource.sensor.bluetooth;

import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.recorder.data.BluetoothServiceType;
import java.util.ArrayList;
import java.util.List;

public class BluetoothDataTypeRefMapper {
    public static List<DataTypeRef> getDataTypeRefFromService(BluetoothServiceType serviceType) {
        List<DataTypeRef> dataTypeRefs = new ArrayList<>();
        switch (serviceType) {
            case HEART_RATE:
                dataTypeRefs.add(BaseDataTypes.TYPE_HEART_RATE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_ENERGY_EXPENDED.getRef());
                break;
            case RUN_SPEED_CADENCE:
                dataTypeRefs.add(BaseDataTypes.TYPE_SPEED.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_RUN_CADENCE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_STRIDE_LENGTH.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_DISTANCE.getRef());
                break;
            case BIKE_POWER:
                dataTypeRefs.add(BaseDataTypes.TYPE_CYCLING_POWER.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_CYCLING_POWER_BALANCE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_ACCUMULATED_TORQUE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_WHEEL_REVOLUTIONS.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_CRANK_REVOLUTIONS.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_EXTREME_FORCES.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_EXTREME_TORQUE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_EXTREME_ANGLES.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_TOP_DEAD_SPOT_ANGLE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_BOTTOM_DEAD_SPOT_ANGLE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_ENERGY_EXPENDED.getRef());
                break;
            case BIKE_SPEED_CADENCE:
                dataTypeRefs.add(BaseDataTypes.TYPE_WHEEL_REVOLUTIONS.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_CRANK_REVOLUTIONS.getRef());
                break;
            case ARMOUR_39:
                dataTypeRefs.add(BaseDataTypes.TYPE_STEPS.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_ENERGY_EXPENDED.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_WILLPOWER.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_POSTURE.getRef());
                dataTypeRefs.add(BaseDataTypes.TYPE_RUN_CADENCE.getRef());
                break;
        }
        return dataTypeRefs;
    }
}
