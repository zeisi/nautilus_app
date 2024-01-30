package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;

public class PointImpl extends ApiTransferObject implements Point, Parcelable {
    public static final Parcelable.Creator<PointImpl> CREATOR = new Parcelable.Creator<PointImpl>() {
        public PointImpl createFromParcel(Parcel source) {
            return new PointImpl(source);
        }

        public PointImpl[] newArray(int size) {
            return new PointImpl[size];
        }
    };
    @SerializedName("dis")
    Double distance;
    @SerializedName("elv")
    Double elevation;
    @SerializedName("lat")
    Double latitude;
    @SerializedName("lng")
    Double longitude;

    public PointImpl(Double latitude2, Double longitude2, Double elevation2, Double distance2) {
        this.latitude = latitude2;
        this.longitude = longitude2;
        this.elevation = elevation2;
        this.distance = distance2;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getElevation() {
        return this.elevation;
    }

    public Double getDistanceMeters() {
        return this.distance;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeValue(this.elevation);
        dest.writeValue(this.distance);
    }

    private PointImpl(Parcel in) {
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.elevation = (Double) in.readValue(Double.class.getClassLoader());
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
    }
}
