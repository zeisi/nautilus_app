package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityStoryReplySummaryImpl implements ActivityStoryReplySummary {
    public static Parcelable.Creator<ActivityStoryReplySummaryImpl> CREATOR = new Parcelable.Creator<ActivityStoryReplySummaryImpl>() {
        public ActivityStoryReplySummaryImpl createFromParcel(Parcel source) {
            return new ActivityStoryReplySummaryImpl(source);
        }

        public ActivityStoryReplySummaryImpl[] newArray(int size) {
            return new ActivityStoryReplySummaryImpl[size];
        }
    };
    @SerializedName("items")
    ArrayList<ActivityStory> mItems;
    @SerializedName("replied")
    Boolean mReplied;
    @SerializedName("reply_id")
    String mReplyId;
    @SerializedName("count")
    Integer mTotalCount;

    public int getTotalCount() {
        if (this.mTotalCount != null) {
            return this.mTotalCount.intValue();
        }
        return 0;
    }

    public boolean isReplied() {
        if (this.mReplied != null) {
            return this.mReplied.booleanValue();
        }
        return false;
    }

    public EntityRef<ActivityStory> getReplyRef() {
        if (this.mReplyId != null) {
            return new LinkEntityRef(this.mReplyId, (String) null);
        }
        return null;
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
        dest.writeValue(this.mReplied);
        dest.writeString(this.mReplyId);
        dest.writeList(this.mItems);
    }

    public ActivityStoryReplySummaryImpl() {
    }

    private ActivityStoryReplySummaryImpl(Parcel in) {
        this.mTotalCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mReplied = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mReplyId = in.readString();
        this.mItems = new ArrayList<>(5);
        in.readList(this.mItems, ActivityStory.class.getClassLoader());
        if (this.mItems.isEmpty()) {
            this.mItems = null;
        }
    }
}
