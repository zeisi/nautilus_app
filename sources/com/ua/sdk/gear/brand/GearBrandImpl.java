package com.ua.sdk.gear.brand;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class GearBrandImpl extends ApiTransferObject implements GearBrand {
    public static final Parcelable.Creator<GearBrandImpl> CREATOR = new Parcelable.Creator<GearBrandImpl>() {
        public GearBrandImpl createFromParcel(Parcel source) {
            return new GearBrandImpl(source);
        }

        public GearBrandImpl[] newArray(int size) {
            return new GearBrandImpl[size];
        }
    };
    public static final String REF_SELF = "self";
    @SerializedName("brand")
    String brandName;
    @SerializedName("gear_type_id")
    String gearTypeId;
    @SerializedName("popular")
    Boolean isPopular;

    public GearBrandImpl() {
    }

    public String getGearTypeId() {
        return this.gearTypeId;
    }

    public Boolean isPopular() {
        return this.isPopular;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public EntityRef getRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.gearTypeId);
        dest.writeValue(this.isPopular);
        dest.writeString(this.brandName);
    }

    private GearBrandImpl(Parcel in) {
        super(in);
        this.gearTypeId = in.readString();
        this.isPopular = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.brandName = in.readString();
    }
}
