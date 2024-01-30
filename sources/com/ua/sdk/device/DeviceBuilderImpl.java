package com.ua.sdk.device;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceBuilderImpl implements DeviceBuilder {
    public static final Parcelable.Creator<DeviceBuilderImpl> CREATOR = new Parcelable.Creator<DeviceBuilderImpl>() {
        public DeviceBuilderImpl createFromParcel(Parcel source) {
            return new DeviceBuilderImpl(source);
        }

        public DeviceBuilderImpl[] newArray(int size) {
            return new DeviceBuilderImpl[size];
        }
    };
    String description;
    String manufacturer;
    String model;
    String name;

    public DeviceBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public DeviceBuilder setModel(String model2) {
        this.model = model2;
        return this;
    }

    public DeviceBuilder setDescription(String description2) {
        this.description = description2;
        return this;
    }

    public DeviceBuilder setManufacturer(String manufacturer2) {
        this.manufacturer = manufacturer2;
        return this;
    }

    public Device build() {
        if (this.name == null) {
            throw new IllegalArgumentException("A name must be set!");
        } else if (this.model == null) {
            throw new IllegalArgumentException("A model must be set!");
        } else if (this.manufacturer != null) {
            return new DeviceImpl(this.name, this.manufacturer, this.description, this.model);
        } else {
            throw new IllegalArgumentException("A manufacturer must be set!");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.model);
        dest.writeString(this.description);
        dest.writeString(this.manufacturer);
    }

    public DeviceBuilderImpl() {
    }

    private DeviceBuilderImpl(Parcel in) {
        this.name = in.readString();
        this.model = in.readString();
        this.description = in.readString();
        this.manufacturer = in.readString();
    }
}
