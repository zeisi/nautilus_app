package com.ua.sdk.heartrate;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class HeartRateZonesRef implements EntityRef<HeartRateZones> {
    public static Parcelable.Creator<HeartRateZonesRef> CREATOR = new Parcelable.Creator<HeartRateZonesRef>() {
        public HeartRateZonesRef createFromParcel(Parcel source) {
            return new HeartRateZonesRef(source);
        }

        public HeartRateZonesRef[] newArray(int size) {
            return new HeartRateZonesRef[size];
        }
    };
    private final String id;

    private HeartRateZonesRef(Builder init) {
        this.id = init.id;
    }

    public String getId() {
        if (this.id.length() > 0) {
            return this.id;
        }
        return "";
    }

    public String getHref() {
        if (this.id.isEmpty()) {
            return "";
        }
        return String.format(UrlBuilderImpl.GET_HEART_RATE_ZONES, new Object[]{this.id});
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
            super(UrlBuilderImpl.GET_HEART_RATE_ZONES);
        }

        public Builder setId(String id2) {
            this.id = id2;
            return this;
        }

        public HeartRateZonesRef build() {
            Precondition.isNotNull(this.id, "HeartRateZones Id");
            return new HeartRateZonesRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    private HeartRateZonesRef(Parcel in) {
        this.id = in.readString();
    }
}
