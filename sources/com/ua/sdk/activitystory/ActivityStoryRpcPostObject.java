package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.Link;
import com.ua.sdk.privacy.Privacy;

public class ActivityStoryRpcPostObject extends ActivityStoryImpl implements Parcelable {
    public static Parcelable.Creator<ActivityStoryRpcPostObject> CREATOR = new Parcelable.Creator<ActivityStoryRpcPostObject>() {
        public ActivityStoryRpcPostObject createFromParcel(Parcel source) {
            return new ActivityStoryRpcPostObject(source);
        }

        public ActivityStoryRpcPostObject[] newArray(int size) {
            return new ActivityStoryRpcPostObject[size];
        }
    };
    @SerializedName("privacy")
    Privacy privacy;

    public ActivityStoryRpcPostObject() {
    }

    private ActivityStoryRpcPostObject(Parcel in) {
        this.privacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
    }

    private ActivityStoryRpcPostObject(Builder init) {
        this.privacy = init.privacy;
        setLink("self", init.link);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.privacy, flags);
    }

    public static class Builder {
        Link link;
        final Privacy privacy;

        public Builder(Privacy privacy2) {
            this.privacy = privacy2;
        }

        public Builder setLink(String href) {
            this.link = new Link(href);
            return this;
        }

        public ActivityStoryRpcPostObject build() {
            return new ActivityStoryRpcPostObject(this);
        }
    }
}
