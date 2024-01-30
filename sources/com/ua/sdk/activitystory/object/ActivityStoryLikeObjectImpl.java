package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.activitystory.ActivityStoryLikeObject;
import com.ua.sdk.activitystory.ActivityStoryObject;

public class ActivityStoryLikeObjectImpl implements ActivityStoryLikeObject {
    public static Parcelable.Creator<ActivityStoryLikeObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryLikeObjectImpl>() {
        public ActivityStoryLikeObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryLikeObjectImpl(source);
        }

        public ActivityStoryLikeObjectImpl[] newArray(int size) {
            return new ActivityStoryLikeObjectImpl[size];
        }
    };

    public ActivityStoryLikeObjectImpl() {
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.LIKE;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private ActivityStoryLikeObjectImpl(Parcel in) {
    }
}
