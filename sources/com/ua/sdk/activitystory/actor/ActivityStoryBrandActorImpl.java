package com.ua.sdk.activitystory.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryActor;

public class ActivityStoryBrandActorImpl implements ActivityStoryActor {
    public static Parcelable.Creator<ActivityStoryBrandActorImpl> CREATOR = new Parcelable.Creator<ActivityStoryBrandActorImpl>() {
        public ActivityStoryBrandActorImpl createFromParcel(Parcel source) {
            return new ActivityStoryBrandActorImpl(source);
        }

        public ActivityStoryBrandActorImpl[] newArray(int size) {
            return new ActivityStoryBrandActorImpl[size];
        }
    };
    @SerializedName("id")
    String mId;

    public ActivityStoryActor.Type getType() {
        return ActivityStoryActor.Type.BRAND;
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

    public ActivityStoryBrandActorImpl() {
    }

    private ActivityStoryBrandActorImpl(Parcel in) {
        this.mId = in.readString();
    }
}
