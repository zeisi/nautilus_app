package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.activitystory.ActivityStoryAdObject;
import com.ua.sdk.activitystory.ActivityStoryObject;

public class ActivityStoryAdObjectImpl implements ActivityStoryAdObject {
    public static Parcelable.Creator<ActivityStoryAdObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryAdObjectImpl>() {
        public ActivityStoryAdObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryAdObjectImpl(source);
        }

        public ActivityStoryAdObjectImpl[] newArray(int size) {
            return new ActivityStoryAdObjectImpl[size];
        }
    };

    public ActivityStoryAdObjectImpl() {
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.AD;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private ActivityStoryAdObjectImpl(Parcel in) {
    }
}
