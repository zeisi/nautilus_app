package com.ua.sdk.activitytype;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class ActivityTypeListRef implements EntityListRef<ActivityType> {
    public static Parcelable.Creator<ActivityTypeListRef> CREATOR = new Parcelable.Creator<ActivityTypeListRef>() {
        public ActivityTypeListRef createFromParcel(Parcel source) {
            return new ActivityTypeListRef(source);
        }

        public ActivityTypeListRef[] newArray(int size) {
            return new ActivityTypeListRef[size];
        }
    };
    private final String href;

    public ActivityTypeListRef(Builder builder) {
        this.href = builder.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.href);
    }

    private ActivityTypeListRef(Parcel source) {
        this.href = source.readString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.href.equals(((ActivityTypeListRef) o).href);
    }

    public int hashCode() {
        return this.href.hashCode();
    }

    public static class Builder extends BaseReferenceBuilder {
        protected Builder() {
            super("/v7.0/activity_type/");
        }

        public ActivityTypeListRef build() {
            return new ActivityTypeListRef(this);
        }
    }
}
