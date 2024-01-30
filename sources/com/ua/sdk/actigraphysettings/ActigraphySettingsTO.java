package com.ua.sdk.actigraphysettings;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.List;

public class ActigraphySettingsTO extends ApiTransferObject {
    @SerializedName("recorder_priorities")
    RecorderPrioritiesTransferObject recorderPriorities;

    class RecorderPrioritiesTransferObject {
        @SerializedName("activity")
        List<String> activity;
        @SerializedName("sleep")
        List<String> sleep;

        RecorderPrioritiesTransferObject() {
        }
    }

    public static ActigraphySettingsImpl fromTransferObject(ActigraphySettingsTO to) {
        if (to == null) {
            return null;
        }
        ActigraphySettingsImpl actigraphySettings = new ActigraphySettingsImpl();
        if (to.recorderPriorities.sleep != null) {
            actigraphySettings.setSleepPriority(to.recorderPriorities.sleep);
        }
        if (to.recorderPriorities.activity == null) {
            return actigraphySettings;
        }
        actigraphySettings.setActivityPriority(to.recorderPriorities.activity);
        return actigraphySettings;
    }
}
