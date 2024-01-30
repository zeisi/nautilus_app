package com.ua.sdk.datapoint;

import android.os.Parcel;
import android.os.Parcelable;

public class DataFieldImpl implements DataField {
    public static Parcelable.Creator<DataFieldImpl> CREATOR = new Parcelable.Creator<DataFieldImpl>() {
        public DataFieldImpl createFromParcel(Parcel source) {
            return new DataFieldImpl(source);
        }

        public DataFieldImpl[] newArray(int size) {
            return new DataFieldImpl[size];
        }
    };
    private DataUnits dataUnits;
    private String id;
    private String type;

    public DataFieldImpl() {
    }

    public DataFieldImpl(String id2, String type2, DataUnits dataUnits2) {
        this.id = id2;
        this.type = type2;
        this.dataUnits = dataUnits2;
    }

    private DataFieldImpl(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.dataUnits = (DataUnits) in.readValue(DataUnits.class.getClassLoader());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public DataUnits getUnits() {
        return this.dataUnits;
    }

    public void setUnits(DataUnits dataUnits2) {
        this.dataUnits = dataUnits2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!this.id.equals(((DataFieldImpl) o).id)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeValue(this.dataUnits);
    }
}
