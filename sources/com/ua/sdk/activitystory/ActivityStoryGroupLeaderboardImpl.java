package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ActivityStoryGroupLeaderboardImpl implements ActivityStoryGroupLeaderboard {
    public static Parcelable.Creator<ActivityStoryGroupLeaderboardImpl> CREATOR = new Parcelable.Creator<ActivityStoryGroupLeaderboardImpl>() {
        public ActivityStoryGroupLeaderboardImpl createFromParcel(Parcel source) {
            return new ActivityStoryGroupLeaderboardImpl(source);
        }

        public ActivityStoryGroupLeaderboardImpl[] newArray(int size) {
            return new ActivityStoryGroupLeaderboardImpl[size];
        }
    };
    @SerializedName("rank")
    int rank;
    @SerializedName("user_id")
    long userId;
    @SerializedName("value")
    Object value;

    public ActivityStoryGroupLeaderboardImpl() {
    }

    private ActivityStoryGroupLeaderboardImpl(Parcel in) {
        this.value = in.readValue(Object.class.getClassLoader());
        this.userId = in.readLong();
        this.rank = in.readInt();
    }

    public Double getValueDouble() {
        if (this.value == null) {
            return null;
        }
        if (this.value instanceof Double) {
            return (Double) this.value;
        }
        if (this.value instanceof Long) {
            return Double.valueOf(((Long) this.value).doubleValue());
        }
        return null;
    }

    public Long getValueLong() {
        if (this.value == null) {
            return null;
        }
        if (this.value instanceof Long) {
            return (Long) this.value;
        }
        if (this.value instanceof Double) {
            return Long.valueOf(((Double) this.value).longValue());
        }
        return null;
    }

    public void addValue(Object value2) {
        this.value = value2;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId2) {
        this.userId = userId2;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank2) {
        this.rank = rank2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.value);
        dest.writeLong(this.userId);
        dest.writeInt(this.rank);
    }
}
