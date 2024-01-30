package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStory;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryRepostObject;
import com.ua.sdk.privacy.Privacy;

public class ActivityStoryRepostObjectImpl extends ActivityStoryStatusObjectImpl implements ActivityStoryRepostObject {
    public static Parcelable.Creator<ActivityStoryRepostObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryRepostObjectImpl>() {
        public ActivityStoryRepostObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryRepostObjectImpl(source);
        }

        public ActivityStoryRepostObjectImpl[] newArray(int size) {
            return new ActivityStoryRepostObjectImpl[size];
        }
    };
    @SerializedName("id")
    String mId;
    @SerializedName("story")
    ActivityStory originalStory;

    public ActivityStoryRepostObjectImpl() {
    }

    public ActivityStoryRepostObjectImpl(String id, String text, Privacy privacy) {
        super(text, privacy);
        this.mId = id;
    }

    public String getId() {
        return this.mId;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.REPOST;
    }

    public ActivityStory getOriginalStory() {
        return this.originalStory;
    }

    public void setOriginalStory(ActivityStory story) {
        this.originalStory = story;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mId);
        dest.writeParcelable(this.originalStory, flags);
    }

    protected ActivityStoryRepostObjectImpl(Parcel in) {
        super(in);
        this.mId = in.readString();
        this.originalStory = (ActivityStory) in.readParcelable(ActivityStory.class.getClassLoader());
    }
}
