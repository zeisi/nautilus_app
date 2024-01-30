package com.ua.sdk.activitystory.target;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryTarget;

public class ActivityStoryGroupTarget implements ActivityStoryTarget {
    public static Parcelable.Creator<ActivityStoryGroupTarget> CREATOR = new Parcelable.Creator<ActivityStoryGroupTarget>() {
        public ActivityStoryGroupTarget createFromParcel(Parcel source) {
            return new ActivityStoryGroupTarget(source);
        }

        public ActivityStoryGroupTarget[] newArray(int size) {
            return new ActivityStoryGroupTarget[size];
        }
    };
    @SerializedName("id")
    String id;
    @SerializedName("type")
    ActivityStoryTarget.Type type;

    public String getId() {
        return this.id;
    }

    public ActivityStoryTarget.Type getType() {
        return ActivityStoryTarget.Type.GROUP;
    }

    public ActivityStoryGroupTarget() {
    }

    public ActivityStoryGroupTarget(String id2) {
        this.id = id2;
        this.type = ActivityStoryTarget.Type.GROUP;
    }

    private ActivityStoryGroupTarget(Parcel in) {
        this.id = in.readString();
        this.type = (ActivityStoryTarget.Type) in.readValue(ActivityStoryTarget.Type.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.type);
    }
}
