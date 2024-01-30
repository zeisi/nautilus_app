package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryHighlight;

public class ActivityStoryHighlightImpl implements ActivityStoryHighlight {
    public static Parcelable.Creator<ActivityStoryHighlightImpl> CREATOR = new Parcelable.Creator<ActivityStoryHighlightImpl>() {
        public ActivityStoryHighlightImpl createFromParcel(Parcel source) {
            return new ActivityStoryHighlightImpl(source);
        }

        public ActivityStoryHighlightImpl[] newArray(int size) {
            return new ActivityStoryHighlightImpl[size];
        }
    };
    @SerializedName("key")
    String mKey;
    @SerializedName("percentile")
    Double mPercentile;
    @SerializedName("thumbnail_url")
    String mThumbnailUrl;
    transient Number mValue;

    public ActivityStoryHighlightImpl() {
    }

    public String getKey() {
        return this.mKey;
    }

    public Number getValue() {
        return this.mValue;
    }

    public void setValue(Number val) {
        this.mValue = val;
    }

    public void setKey(String mKey2) {
        this.mKey = mKey2;
    }

    public void setPercentile(Double mPercentile2) {
        this.mPercentile = mPercentile2;
    }

    public void setThumbnailUrl(String mThumbnailUrl2) {
        this.mThumbnailUrl = mThumbnailUrl2;
    }

    public Double getPercentile() {
        return this.mPercentile;
    }

    public String getThumbnailUrl() {
        return this.mThumbnailUrl;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mKey);
        dest.writeValue(this.mPercentile);
        dest.writeString(this.mThumbnailUrl);
    }

    private ActivityStoryHighlightImpl(Parcel in) {
        this.mKey = in.readString();
        this.mPercentile = (Double) in.readValue(Double.class.getClassLoader());
        this.mThumbnailUrl = in.readString();
    }
}
