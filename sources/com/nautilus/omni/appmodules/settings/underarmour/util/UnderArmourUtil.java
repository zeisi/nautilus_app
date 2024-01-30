package com.nautilus.omni.appmodules.settings.underarmour.util;

import com.nautilus.omni.model.dto.OmniMachineType;
import com.nautilus.underarmourconnection.services.workouts.ActivityType;

public class UnderArmourUtil {
    public static String getWorkoutName(OmniMachineType machineType) {
        if (machineType.equals(OmniMachineType.MY14_BIKE_ELLIPTICAL) || machineType.equals(OmniMachineType.MY16_BIKE_ELLIPTICAL) || machineType.equals(OmniMachineType.ELLIPTICAL_E628_INTERNATIONAL) || machineType.equals(OmniMachineType.MY17_ELLIPTICAL_E618) || machineType.equals(OmniMachineType.MY17_ELLIPTICAL_E616)) {
            return UnderArmourConstants.ELLIPTICAL_NAME;
        }
        if (machineType.equals(OmniMachineType.MY14_TREADMILL) || machineType.equals(OmniMachineType.MY16_TREADMILL) || machineType.equals(OmniMachineType.MY16_INTERNATIONAL_TREADMILL) || machineType.equals(OmniMachineType.MY17_TREADMILL_T618) || machineType.equals(OmniMachineType.MY17_TREADMILL_T616)) {
            return UnderArmourConstants.TREADMILL_NAME;
        }
        if (machineType.equals(OmniMachineType.MY16_INTERNATIONAL_RECUMBENT_BIKE) || machineType.equals(OmniMachineType.MY17_BIKE_B618)) {
            return UnderArmourConstants.RECUMBENT_BIKE_NAME;
        }
        return UnderArmourConstants.UPRIGHT_BIKE_NAME;
    }

    public static String getActivityType(OmniMachineType machineType) {
        if (machineType.equals(OmniMachineType.MY14_BIKE_ELLIPTICAL) || machineType.equals(OmniMachineType.MY16_BIKE_ELLIPTICAL) || machineType.equals(OmniMachineType.ELLIPTICAL_E628_INTERNATIONAL) || machineType.equals(OmniMachineType.MY17_ELLIPTICAL_E618) || machineType.equals(OmniMachineType.MY17_ELLIPTICAL_E616)) {
            return ActivityType.ELLIPTICAL_MACHINE;
        }
        if (machineType.equals(OmniMachineType.MY14_TREADMILL) || machineType.equals(OmniMachineType.MY16_TREADMILL) || machineType.equals(OmniMachineType.MY16_INTERNATIONAL_TREADMILL) || machineType.equals(OmniMachineType.MY17_TREADMILL_T618) || machineType.equals(OmniMachineType.MY17_TREADMILL_T616)) {
            return ActivityType.TREADMILL_GENERAL;
        }
        if (machineType.equals(OmniMachineType.MY16_INTERNATIONAL_RECUMBENT_BIKE) || machineType.equals(OmniMachineType.MY17_BIKE_B618)) {
            return ActivityType.RECUMBENT_BIKE;
        }
        return ActivityType.STATIONARY_BIKE;
    }
}
