package com.ua.sdk.heartrate;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.user.User;

public class HeartRateZonesListRef implements EntityListRef<HeartRateZones> {
    public static Parcelable.Creator<HeartRateZonesListRef> CREATOR = new Parcelable.Creator<HeartRateZonesListRef>() {
        public HeartRateZonesListRef createFromParcel(Parcel source) {
            return new HeartRateZonesListRef(source);
        }

        public HeartRateZonesListRef[] newArray(int size) {
            return new HeartRateZonesListRef[size];
        }
    };
    private final String href;

    private HeartRateZonesListRef(Parcel in) {
        this.href = in.readString();
    }

    private HeartRateZonesListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public static class Builder extends BaseReferenceBuilder {
        private static final String USER_ID = "user";

        public Builder() {
            super(UrlBuilderImpl.BASE_HEART_RATE_ZONES);
        }

        public Builder setUser(EntityRef<User> user) {
            Precondition.isNotNull(user);
            setParam("user", user.getId());
            return this;
        }

        public Builder setUserId(String userId) {
            Precondition.isNotNull(userId);
            setParam("user", userId);
            return this;
        }

        public HeartRateZonesListRef build() {
            if (getParam("user") != null) {
                return new HeartRateZonesListRef(this);
            }
            throw new NullPointerException("User or User ID must be supplied.");
        }
    }
}
