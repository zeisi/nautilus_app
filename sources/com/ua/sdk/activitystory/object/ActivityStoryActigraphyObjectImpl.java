package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryActigraphyObject;
import com.ua.sdk.activitystory.ActivityStoryHighlight;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.privacy.Privacy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ActivityStoryActigraphyObjectImpl extends ApiTransferObject implements ActivityStoryActigraphyObject {
    public static Parcelable.Creator<ActivityStoryActigraphyObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryActigraphyObjectImpl>() {
        public ActivityStoryActigraphyObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryActigraphyObjectImpl(source);
        }

        public ActivityStoryActigraphyObjectImpl[] newArray(int size) {
            return new ActivityStoryActigraphyObjectImpl[size];
        }
    };
    @SerializedName("end_time")
    Date mEndTime;
    @SerializedName("highlights")
    List<ActivityStoryHighlight> mHighlights;
    @SerializedName("privacy")
    Privacy mPrivacy;
    @SerializedName("published")
    Date mPublishedTime;
    @SerializedName("start_time")
    Date mStartTime;
    @SerializedName("steps")
    Integer mSteps;

    public Privacy getPrivacy() {
        return this.mPrivacy;
    }

    public List<ActivityStoryHighlight> getHighlights() {
        if (this.mHighlights == null) {
            return Collections.emptyList();
        }
        return this.mHighlights;
    }

    public Date getStartTime() {
        return this.mStartTime;
    }

    public Date getEndTime() {
        return this.mEndTime;
    }

    public Integer getSteps() {
        return this.mSteps;
    }

    public Date getPublishedTime() {
        return this.mPublishedTime;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.ACTIGRAPHY;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j;
        long j2 = -1;
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mPrivacy, flags);
        dest.writeLong(this.mStartTime != null ? this.mStartTime.getTime() : -1);
        if (this.mEndTime != null) {
            j = this.mEndTime.getTime();
        } else {
            j = -1;
        }
        dest.writeLong(j);
        if (this.mPublishedTime != null) {
            j2 = this.mPublishedTime.getTime();
        }
        dest.writeLong(j2);
        dest.writeValue(this.mSteps);
        dest.writeList(this.mHighlights);
    }

    public ActivityStoryActigraphyObjectImpl() {
    }

    private ActivityStoryActigraphyObjectImpl(Parcel in) {
        super(in);
        this.mPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        long tmpMStartTime = in.readLong();
        this.mStartTime = tmpMStartTime == -1 ? null : new Date(tmpMStartTime);
        long tmpMEndTime = in.readLong();
        this.mEndTime = tmpMEndTime == -1 ? null : new Date(tmpMEndTime);
        long tmpMPublishedTime = in.readLong();
        this.mPublishedTime = tmpMPublishedTime == -1 ? null : new Date(tmpMPublishedTime);
        this.mSteps = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mHighlights = new ArrayList();
        in.readList(this.mHighlights, ActivityStoryHighlightImpl.class.getClassLoader());
        if (this.mHighlights.isEmpty()) {
            this.mHighlights = null;
        }
    }
}
