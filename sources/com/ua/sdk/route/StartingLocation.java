package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class StartingLocation implements Parcelable {
    public static final Parcelable.Creator<StartingLocation> CREATOR = new Parcelable.Creator<StartingLocation>() {
        public StartingLocation createFromParcel(Parcel source) {
            return new StartingLocation(source);
        }

        public StartingLocation[] newArray(int size) {
            return new StartingLocation[size];
        }
    };
    @SerializedName("coordinates")
    double[] coordinates;
    @SerializedName("type")
    String type;

    public StartingLocation() {
        this.type = "Point";
        this.coordinates = new double[2];
    }

    public StartingLocation(Double lat, Double lng) {
        this.type = "Point";
        this.coordinates = new double[2];
        this.coordinates[0] = lat.doubleValue();
        this.coordinates[1] = lng.doubleValue();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeValue(Double.valueOf(this.coordinates[0]));
        dest.writeValue(Double.valueOf(this.coordinates[1]));
    }

    private StartingLocation(Parcel in) {
        this.type = in.readString();
        if (this.coordinates == null) {
            this.coordinates = new double[2];
        }
        this.coordinates[0] = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.coordinates[1] = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
    }
}
