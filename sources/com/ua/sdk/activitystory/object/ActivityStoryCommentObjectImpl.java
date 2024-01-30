package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryCommentObject;
import com.ua.sdk.activitystory.ActivityStoryObject;

public class ActivityStoryCommentObjectImpl implements ActivityStoryCommentObject {
    public static Parcelable.Creator<ActivityStoryCommentObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryCommentObjectImpl>() {
        public ActivityStoryCommentObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryCommentObjectImpl(source);
        }

        public ActivityStoryCommentObjectImpl[] newArray(int size) {
            return new ActivityStoryCommentObjectImpl[size];
        }
    };
    @SerializedName("text")
    String mText;

    public ActivityStoryCommentObjectImpl(String text) {
        this.mText = text;
    }

    public ActivityStoryCommentObjectImpl() {
    }

    public String getText() {
        return this.mText;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.COMMENT;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mText);
    }

    private ActivityStoryCommentObjectImpl(Parcel in) {
        this.mText = in.readString();
    }
}
