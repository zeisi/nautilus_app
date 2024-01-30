package com.ua.sdk.actigraphysettings;

import com.google.android.gms.fitness.FitnessActivities;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;

public class ActigraphySettingsRequestTO extends ApiTransferObject {
    @SerializedName("recorder_type_key")
    String recorderTypeKey;
    @SerializedName("qs_type")
    String type;

    public static ActigraphySettingsRequestTO toTransferObject(ActigraphySettings actigraphySettings) {
        ActigraphySettingsRequestTO to = new ActigraphySettingsRequestTO();
        if (actigraphySettings.getActivityRecorderPriorities() != null && actigraphySettings.getActivityRecorderPriorities().get(0) != null) {
            to.type = "activity";
            to.recorderTypeKey = actigraphySettings.getActivityRecorderPriorities().get(0);
        } else if (!(actigraphySettings.getSleepRecorderPriorities() == null || actigraphySettings.getSleepRecorderPriorities().get(0) == null)) {
            to.type = FitnessActivities.SLEEP;
            to.recorderTypeKey = actigraphySettings.getSleepRecorderPriorities().get(0);
        }
        return to;
    }
}
