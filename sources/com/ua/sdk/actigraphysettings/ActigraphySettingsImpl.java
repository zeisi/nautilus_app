package com.ua.sdk.actigraphysettings;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.Reference;
import java.util.List;

public class ActigraphySettingsImpl implements ActigraphySettings, Parcelable {
    public static Parcelable.Creator<ActigraphySettingsImpl> CREATOR = new Parcelable.Creator<ActigraphySettingsImpl>() {
        public ActigraphySettingsImpl createFromParcel(Parcel source) {
            return new ActigraphySettingsImpl(source);
        }

        public ActigraphySettingsImpl[] newArray(int size) {
            return new ActigraphySettingsImpl[size];
        }
    };
    private List<String> activityPriority;
    private List<String> sleepPriority;

    public List<String> getSleepRecorderPriorities() {
        return this.sleepPriority;
    }

    public List<String> getActivityRecorderPriorities() {
        return this.activityPriority;
    }

    public void setActivityPriority(List<String> activityPriority2) {
        this.activityPriority = activityPriority2;
    }

    public void setSleepPriority(List<String> sleepPriority2) {
        this.sleepPriority = sleepPriority2;
    }

    public Reference getRef() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.activityPriority);
        dest.writeStringList(this.sleepPriority);
    }

    public ActigraphySettingsImpl() {
    }

    private ActigraphySettingsImpl(Parcel in) {
        in.readStringList(this.activityPriority);
        in.readStringList(this.sleepPriority);
    }
}
