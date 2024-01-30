package com.ua.sdk.activitystory.target;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryTarget;

public class ActivityStoryUnknownTarget implements ActivityStoryTarget {
    public static Parcelable.Creator<ActivityStoryUnknownTarget> CREATOR = new Parcelable.Creator<ActivityStoryUnknownTarget>() {
        public ActivityStoryUnknownTarget createFromParcel(Parcel source) {
            return new ActivityStoryUnknownTarget(source);
        }

        public ActivityStoryUnknownTarget[] newArray(int size) {
            return new ActivityStoryUnknownTarget[size];
        }
    };
    @SerializedName("id")
    String id;
    @SerializedName("type")
    ActivityStoryTarget.Type type;

    public ActivityStoryUnknownTarget() {
    }

    private ActivityStoryUnknownTarget(Parcel in) {
        this.id = in.readString();
        this.type = (ActivityStoryTarget.Type) in.readValue(ActivityStoryTarget.Type.class.getClassLoader());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public ActivityStoryTarget.Type getType() {
        return this.type;
    }

    public void setType(ActivityStoryTarget.Type type2) {
        this.type = type2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.type);
    }
}
