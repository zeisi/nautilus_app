package com.ua.sdk.activitystory.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryActor;

public class ActivityStorySiteActorImpl implements ActivityStoryActor {
    public static Parcelable.Creator<ActivityStorySiteActorImpl> CREATOR = new Parcelable.Creator<ActivityStorySiteActorImpl>() {
        public ActivityStorySiteActorImpl createFromParcel(Parcel source) {
            return new ActivityStorySiteActorImpl(source);
        }

        public ActivityStorySiteActorImpl[] newArray(int size) {
            return new ActivityStorySiteActorImpl[size];
        }
    };
    @SerializedName("id")
    String mId;

    public ActivityStoryActor.Type getType() {
        return ActivityStoryActor.Type.SITE;
    }

    public String getId() {
        return this.mId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
    }

    public ActivityStorySiteActorImpl() {
    }

    private ActivityStorySiteActorImpl(Parcel in) {
        this.mId = in.readString();
    }
}
