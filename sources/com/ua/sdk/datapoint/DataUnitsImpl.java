package com.ua.sdk.datapoint;

import android.os.Parcel;
import android.os.Parcelable;

public class DataUnitsImpl implements DataUnits {
    public static Parcelable.Creator<DataUnitsImpl> CREATOR = new Parcelable.Creator<DataUnitsImpl>() {
        public DataUnitsImpl createFromParcel(Parcel source) {
            return new DataUnitsImpl(source);
        }

        public DataUnitsImpl[] newArray(int size) {
            return new DataUnitsImpl[size];
        }
    };
    private String name;
    private String symbol;

    public DataUnitsImpl(String name2, String symbol2) {
        this.name = name2;
        this.symbol = symbol2;
    }

    private DataUnitsImpl(Parcel in) {
        this.name = in.readString();
        this.symbol = in.readString();
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!this.name.equals(((DataUnitsImpl) o).name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.symbol);
    }
}
