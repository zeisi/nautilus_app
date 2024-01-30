package com.ua.sdk.datasourceidentifier;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.device.Device;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class DataSourceIdentifierImpl extends ApiTransferObject implements DataSourceIdentifier {
    public static final Parcelable.Creator<DataSourceIdentifierImpl> CREATOR = new Parcelable.Creator<DataSourceIdentifierImpl>() {
        public DataSourceIdentifierImpl createFromParcel(Parcel source) {
            return new DataSourceIdentifierImpl(source);
        }

        public DataSourceIdentifierImpl[] newArray(int size) {
            return new DataSourceIdentifierImpl[size];
        }
    };
    @SerializedName("device")
    Device device;
    @SerializedName("name")
    String name;

    public DataSourceIdentifierImpl() {
    }

    public DataSourceIdentifierImpl(Device device2, String name2) {
        this.device = device2;
        this.name = name2;
    }

    public Device getDevice() {
        return this.device;
    }

    public String getName() {
        return this.name;
    }

    public EntityRef getRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceIdentifierImpl that = (DataSourceIdentifierImpl) o;
        if (this.name != null) {
            if (this.name.equals(that.name)) {
                return true;
            }
        } else if (that.name == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.name != null) {
            return this.name.hashCode();
        }
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.device, flags);
        dest.writeString(this.name);
    }

    private DataSourceIdentifierImpl(Parcel in) {
        this.device = (Device) in.readParcelable(Device.class.getClassLoader());
        this.name = in.readString();
    }
}
