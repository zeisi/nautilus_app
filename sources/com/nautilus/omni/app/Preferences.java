package com.nautilus.omni.app;

import android.content.SharedPreferences;

public class Preferences {
    public static final String AWARD_TYPES_FIRST_UPDATE_KEY = "AWARD_TYPES_FIRST_UPDATE_KEY";
    public static final int AWARD_TYPES_VERSION = 1;
    public static final String AWARD_TYPES_VERSION_KEY = "AWARD_TYPES_VERSION";
    public static final String DATABASE_VERSION = "DATABASE_VERSION";
    public static final int DEFAULT_AWARD_TYPES_VERSION = 1;
    public static final String FIRST_RUN = "FIRST_RUN";
    public static final String GOALS_ENABLED = "GOALS_ENABLED";
    public static final String GOOGLE_FIT_ACCOUNT_NAME = "GOOGLE_FIT_ACCOUNT_NAME";
    public static final String GOOGLE_FIT_AVG_HEART_RATE = "GOOGLE_FIT_AVG_HEART_RATE";
    public static final String GOOGLE_FIT_CALORIES = "GOOGLE_FIT_CALORIES";
    public static final String GOOGLE_FIT_DISTANCE = "GOOGLE_FIT_DISTANCE";
    public static final String GOOGLE_FIT_RPM = "GOOGLE_FIT_RPM";
    public static final String GOOGLE_FIT_SYNC = "GOOGLE_FIT_SYNC";
    public static final String GOOGLE_FIT_TIME = "GOOGLE_FIT_TIME";
    public static final String HAS_OMNI_TRAINER_DEVICE_SET = "HAS_OMNI_TRAINER_DEVICE_SET";
    public static final String IS_CONNECTION_WIZARD_STARTED_FROM_DEVICE_SCREEN = "IS_CONNECTION_WIZARD_STARTED_FROM_DEVICE_SCREEN";
    public static final String IS_GOOGLE_FIT_CONNECTED = "IS_GOOGLE_FIT_CONNECTED";
    public static final String IS_GOOGLE_FIT_SYNC_IN_PROGRESS = "IS_GOOGLE_FIT_SYNC_IN_PROGRESS";
    public static final String IS_MFP_SYNC_IN_PROGRESS = "IS_MFP_SYNC_IN_PROGRESS";
    public static final String IS_MY_FITNESS_PAL_CONNECTED = "MY_FITNESS_PAL_CONNECTED";
    public static final String IS_SYNC_IN_ERROR_STATE = "IS_SYNC_IN_ERROR_STATE";
    public static final String IS_UNDER_ARMOUR_SYNC_IN_PROGRESS = "IS_UNDER_ARMOUR_SYNC_IN_PROGRESS";
    public static final int METRIC_UNITS = 1;
    public static final String MY_FITNESS_PAL_DOWNLOAD = "MY_FITNESS_PAL_DOWNLOAD";
    public static final String MY_FITNESS_PAL_REFRESH_TOKEN = "MY_FITNESS_PAL_REFRESH_TOKEN";
    public static final String MY_FITNESS_PAL_TOKEN = "MY_FITNESS_PAL_TOKEN";
    public static final String MY_FITNESS_USER_ID = "MY_FITNESS_USER_ID";
    public static final String NAVIGATION_FROM_HELP = "NAVIGATION_FROM_SETTINGS";
    public static final String NAVIGATION_FROM_SETTINGS = "NAVIGATION_FROM_SETTINGS";
    public static final String OMNI_TRAINER_PREFERENCES = "OMNI_TRAINER_PREFERENCES";
    public static final String SHOW_DEVICE_SCREEN_FROM_HELP = "SHOW_DEVICE_SCREEN_FROM_HELP";
    public static final String SHOW_DEVICE_VIEW = "SHOW_DEVICE_VIEW";
    public static final String SHOW_HOME_SCREEN = "SHOW_HOME_SCREEN";
    public static final String SHOW_HOME_SCREEN_FROM_HELP = "SHOW_HOME_SCREEN_FROM_HELP";
    public static final String SHOW_TIME_GOAL_UPDATED_DIALOG = "SHOW_TIME_GOAL_UPDATED_DIALOG";
    public static final String SYNC_FROM_CONNECTION_WIZARD = "SYNC_FROM_CONNECTION_WIZARD";
    public static final String SYNC_FROM_DEVICE_VIEW = "SYNC_FROM_DEVICE_VIEW";
    public static final String SYNC_IN_PROGRESS = "SYNC_IN_PROGRESS";
    public static final String SYNC_TOASTS_SHOWED = "SYNC_TOASTS_SHOWED";
    public static final String TIME_GOAL_VALUES_ALREADY_UPDATED = "TIME_GOAL_VALUES_ALREADY_UPDATED";
    public static final String TUTORIAL = "TUTORIAL";
    public static final String UNITS_SETTINGS = "UNITS_SETTINGS";
    public static final String USER_INDEX = "USER_INDEX";
    public static final int US_UNITS = 0;
    public static final String WON_AWARDS = "WON_AWARDS";

    public static void resetPreferences(SharedPreferences appPreferences) {
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putInt(USER_INDEX, -1);
        editor.putBoolean(HAS_OMNI_TRAINER_DEVICE_SET, false);
        editor.putBoolean(GOALS_ENABLED, false);
        editor.putBoolean(IS_MY_FITNESS_PAL_CONNECTED, false);
        editor.putBoolean(WON_AWARDS, false);
        editor.putBoolean(IS_SYNC_IN_ERROR_STATE, false);
        editor.putBoolean(SHOW_DEVICE_VIEW, false);
        editor.putBoolean(SHOW_DEVICE_SCREEN_FROM_HELP, false);
        editor.putBoolean(SHOW_HOME_SCREEN, false);
        editor.putBoolean(SHOW_HOME_SCREEN_FROM_HELP, false);
        editor.putBoolean("NAVIGATION_FROM_SETTINGS", false);
        editor.putBoolean("NAVIGATION_FROM_SETTINGS", false);
        editor.putBoolean("SYNC_FROM_CONNECTION_WIZARD", false);
        editor.putBoolean(SYNC_TOASTS_SHOWED, false);
        editor.commit();
    }
}
