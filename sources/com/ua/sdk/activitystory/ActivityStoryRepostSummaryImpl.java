package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityStoryRepostSummaryImpl implements ActivityStoryRepostSummary {
    public static Parcelable.Creator<ActivityStoryRepostSummaryImpl> CREATOR = new Parcelable.Creator<ActivityStoryRepostSummaryImpl>() {
        public ActivityStoryRepostSummaryImpl createFromParcel(Parcel source) {
            return new ActivityStoryRepostSummaryImpl(source);
        }

        public ActivityStoryRepostSummaryImpl[] newArray(int size) {
            return new ActivityStoryRepostSummaryImpl[size];
        }
    };
    @SerializedName("items")
    ArrayList<ActivityStory> mItems;
    @SerializedName("reposted")
    Boolean mReposted;
    @SerializedName("count")
    Integer mTotalCount;

    public int getTotalCount() {
        if (this.mTotalCount != null) {
            return this.mTotalCount.intValue();
        }
        return 0;
    }

    public boolean isReposted() {
        if (this.mReposted != null) {
            return this.mReposted.booleanValue();
        }
        return false;
    }

    public List<ActivityStory> getItems() {
        if (this.mItems == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.mItems);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mTotalCount);
        dest.writeValue(this.mReposted);
        dest.writeList(this.mItems);
    }

    public ActivityStoryRepostSummaryImpl() {
    }

    private ActivityStoryRepostSummaryImpl(Parcel in) {
        this.mTotalCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mReposted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mItems = new ArrayList<>(5);
        in.readList(this.mItems, ActivityStory.class.getClassLoader());
        if (this.mItems.isEmpty()) {
            this.mItems = null;
        }
    }
}
