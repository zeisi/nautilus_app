package com.ua.sdk.gear;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class GearListRef implements EntityListRef<Gear> {
    public static final Parcelable.Creator<GearListRef> CREATOR = new Parcelable.Creator<GearListRef>() {
        public GearListRef createFromParcel(Parcel source) {
            return new GearListRef(source);
        }

        public GearListRef[] newArray(int size) {
            return new GearListRef[size];
        }
    };
    private String href;

    private GearListRef(Builder init) {
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

    public static class Builder extends BaseReferenceBuilder {
        String brand;

        private Builder() {
            super(UrlBuilderImpl.GET_GEAR_URL);
        }

        public Builder setBrand(String brand2) {
            this.brand = brand2;
            setParam("brand", brand2);
            return this;
        }

        public GearListRef build() {
            Precondition.isNotNull(this.brand);
            return new GearListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private GearListRef(Parcel in) {
        this.href = in.readString();
    }
}
