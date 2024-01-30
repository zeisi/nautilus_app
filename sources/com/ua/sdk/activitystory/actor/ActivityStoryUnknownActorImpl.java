package com.ua.sdk.activitystory.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.activitystory.ActivityStoryActor;

public class ActivityStoryUnknownActorImpl implements ActivityStoryActor {
    public static Parcelable.Creator<ActivityStoryUnknownActorImpl> CREATOR = new Parcelable.Creator<ActivityStoryUnknownActorImpl>() {
        public ActivityStoryUnknownActorImpl createFromParcel(Parcel source) {
            return new ActivityStoryUnknownActorImpl(source);
        }

        public ActivityStoryUnknownActorImpl[] newArray(int size) {
            return new ActivityStoryUnknownActorImpl[size];
        }
    };

    public ActivityStoryUnknownActorImpl() {
    }

    private ActivityStoryUnknownActorImpl(Parcel in) {
    }

    public ActivityStoryActor.Type getType() {
        return ActivityStoryActor.Type.UNKNOWN;
    }

    public String getId() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
