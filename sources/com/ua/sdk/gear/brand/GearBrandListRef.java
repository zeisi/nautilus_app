package com.ua.sdk.gear.brand;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.gear.GearType;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class GearBrandListRef implements EntityListRef<GearBrand> {
    public static final Parcelable.Creator<GearBrandListRef> CREATOR = new Parcelable.Creator<GearBrandListRef>() {
        public GearBrandListRef createFromParcel(Parcel source) {
            return new GearBrandListRef(source);
        }

        public GearBrandListRef[] newArray(int size) {
            return new GearBrandListRef[size];
        }
    };
    private String href;

    private GearBrandListRef(Builder init) {
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
        GearType type;

        private Builder() {
            super(UrlBuilderImpl.GET_GEAR_BRAND_URL);
        }

        public Builder setType(GearType type2) {
            this.type = type2;
            setParam("type", type2.getValue());
            return this;
        }

        public GearBrandListRef build() {
            Precondition.isNotNull(this.type);
            return new GearBrandListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private GearBrandListRef(Parcel in) {
        this.href = in.readString();
    }
}
