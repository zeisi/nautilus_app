package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class ActivityStoryList extends AbstractEntityList<ActivityStory> {
    public static Parcelable.Creator<ActivityStoryList> CREATOR = new Parcelable.Creator<ActivityStoryList>() {
        public ActivityStoryList createFromParcel(Parcel source) {
            return new ActivityStoryList(source);
        }

        public ActivityStoryList[] newArray(int size) {
            return new ActivityStoryList[size];
        }
    };
    private static final String LIST_KEY = "activity_stories";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public ActivityStoryList() {
    }

    private ActivityStoryList(Parcel in) {
        super(in);
    }
}
