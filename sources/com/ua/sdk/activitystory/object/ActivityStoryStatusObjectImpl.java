package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryStatusObject;
import com.ua.sdk.privacy.Privacy;

public class ActivityStoryStatusObjectImpl implements ActivityStoryStatusObject {
    public static Parcelable.Creator<ActivityStoryStatusObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryStatusObjectImpl>() {
        public ActivityStoryStatusObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryStatusObjectImpl(source);
        }

        public ActivityStoryStatusObjectImpl[] newArray(int size) {
            return new ActivityStoryStatusObjectImpl[size];
        }
    };
    @SerializedName("privacy")
    Privacy mPrivacy;
    @SerializedName("text")
    String mText;

    public ActivityStoryStatusObjectImpl(String text, Privacy privacy) {
        this.mText = text;
        this.mPrivacy = privacy;
    }

    public ActivityStoryStatusObjectImpl() {
    }

    public String getText() {
        return this.mText;
    }

    public Privacy getPrivacy() {
        return this.mPrivacy;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.STATUS;
    }

    public void setPrivacy(Privacy privacy) {
        this.mPrivacy = privacy;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mText);
        dest.writeParcelable(this.mPrivacy, flags);
    }

    protected ActivityStoryStatusObjectImpl(Parcel in) {
        this.mText = in.readString();
        this.mPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
    }
}
