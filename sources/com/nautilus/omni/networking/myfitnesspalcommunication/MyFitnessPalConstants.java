package com.nautilus.omni.networking.myfitnesspalcommunication;

import com.nautilus.omni.model.dto.OmniMachineType;

public class MyFitnessPalConstants {
    public static final String GOOGLE_PLAY_MARKET = "http://play.google.com/store/apps/details?id=";
    public static final String MFP_ACCESS_TOKEN_KEY = "access_token";
    public static final String MFP_ACTION_KEY = "action";
    public static final String MFP_ACTION_VALUE = "log_cardio_exercise";
    public static final String MFP_AVG_HEART_RATE_KEY = "avg_heart_rate";
    public static final String MFP_CLIENT_ID = "nautilus-trainer-2";
    public static final String MFP_DURATION_KEY = "duration";
    public static final String MFP_ENERGY_EXPENDED_KEY = "energy_expended";
    public static final String MFP_EXERCISE_ID = "134026252709229";
    public static final String MFP_EXERCISE_ID_KEY = "exercise_id";
    public static final String MFP_PACKAGE = "com.myfitnesspal.android";
    public static final int MFP_REQUEST_CODE = 2;
    public static final String MFP_START_TIME_KEY = "start_time";
    public static final String MFP_STATUS_UPDATE_MSG_KEY = "status_update_message";
    public static final String MFP_UNITS_KEY = "units";
    public static final String MFP_UNITS_VALUE = "US";
    public static final String MFP_USER_ID_KEY = "user_id";
    public static final String MY_FITNESS_PAL_APP_ID = "8645126e40f7d632b44e";

    public static final String getMFPExerciseID(OmniMachineType machineType) {
        if (machineType.equals(OmniMachineType.MY14_BIKE_ELLIPTICAL) || machineType.equals(OmniMachineType.MY16_BIKE_ELLIPTICAL) || machineType.equals(OmniMachineType.ELLIPTICAL_E628_INTERNATIONAL) || machineType.equals(OmniMachineType.MY17_ELLIPTICAL_E618) || machineType.equals(OmniMachineType.MY17_ELLIPTICAL_E616)) {
            return "133476467519469";
        }
        if (machineType.equals(OmniMachineType.MY14_TREADMILL) || machineType.equals(OmniMachineType.MY16_TREADMILL) || machineType.equals(OmniMachineType.MY16_INTERNATIONAL_TREADMILL) || machineType.equals(OmniMachineType.MY17_TREADMILL_T618) || machineType.equals(OmniMachineType.MY17_TREADMILL_T616)) {
            return "133478619213677";
        }
        if (machineType.equals(OmniMachineType.MY16_INTERNATIONAL_RECUMBENT_BIKE) || machineType.equals(OmniMachineType.UPRIGHT_BIKE_UR628_INTERNATIONAL) || machineType.equals(OmniMachineType.MY17_BIKE_B618) || machineType.equals(OmniMachineType.MY17_UPRIGHT_BIKE_UR616)) {
            return "133478648541165";
        }
        return "133478619213677";
    }
}
