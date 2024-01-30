package com.ua.sdk.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class DeviceImpl extends ApiTransferObject implements Device {
    public static final Parcelable.Creator<DeviceImpl> CREATOR = new Parcelable.Creator<DeviceImpl>() {
        public DeviceImpl createFromParcel(Parcel source) {
            return new DeviceImpl(source);
        }

        public DeviceImpl[] newArray(int size) {
            return new DeviceImpl[size];
        }
    };
    @SerializedName("description")
    String description;
    @SerializedName("manufacturer")
    String manufacturer;
    @SerializedName("model")
    String model;
    @SerializedName("name")
    String name;

    public DeviceImpl() {
    }

    public DeviceImpl(String name2, String manufacturer2, String description2, String model2) {
        this.name = name2;
        this.manufacturer = manufacturer2;
        this.description = description2;
        this.model = model2;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getDescription() {
        return this.description;
    }

    public String getModel() {
        return this.model;
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
        dest.writeString(this.name);
        dest.writeString(this.manufacturer);
        dest.writeString(this.description);
        dest.writeString(this.model);
    }

    private DeviceImpl(Parcel in) {
        this.name = in.readString();
        this.manufacturer = in.readString();
        this.description = in.readString();
        this.model = in.readString();
    }
}
