package com.ua.sdk.datasourceidentifier;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.device.Device;

public class DataSourceIdentifierBuilderImpl implements DataSourceIdentifierBuilder {
    public static final Parcelable.Creator<DataSourceIdentifierBuilderImpl> CREATOR = new Parcelable.Creator<DataSourceIdentifierBuilderImpl>() {
        public DataSourceIdentifierBuilderImpl createFromParcel(Parcel source) {
            return new DataSourceIdentifierBuilderImpl(source);
        }

        public DataSourceIdentifierBuilderImpl[] newArray(int size) {
            return new DataSourceIdentifierBuilderImpl[size];
        }
    };
    Device device;
    String name;

    public DataSourceIdentifierBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public DataSourceIdentifierBuilder setDevice(Device device2) {
        this.device = device2;
        return this;
    }

    public DataSourceIdentifier build() {
        if (this.name == null) {
            throw new IllegalArgumentException("A name must be set!");
        } else if (this.device != null) {
            return new DataSourceIdentifierImpl(this.device, this.name);
        } else {
            throw new IllegalArgumentException("A device must be set!");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.device, flags);
    }

    public DataSourceIdentifierBuilderImpl() {
    }

    private DataSourceIdentifierBuilderImpl(Parcel in) {
        this.name = in.readString();
        this.device = (Device) in.readParcelable(Device.class.getClassLoader());
    }
}
