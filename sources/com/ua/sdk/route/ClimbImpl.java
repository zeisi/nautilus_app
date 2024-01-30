package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;

public class ClimbImpl extends ApiTransferObject implements Climb, Parcelable {
    public static final Parcelable.Creator<ClimbImpl> CREATOR = new Parcelable.Creator<ClimbImpl>() {
        public ClimbImpl createFromParcel(Parcel source) {
            return new ClimbImpl(source);
        }

        public ClimbImpl[] newArray(int size) {
            return new ClimbImpl[size];
        }
    };
    @SerializedName("cat")
    String category;
    @SerializedName("change")
    Double change;
    @SerializedName("dis")
    Double distance;
    @SerializedName("end")
    Double end;
    @SerializedName("end_index")
    int endIndex;
    @SerializedName("elevation_max")
    Double maxElevation;
    @SerializedName("start")
    Double start;
    @SerializedName("start_index")
    int startIndex;

    public String getCategory() {
        return this.category;
    }

    public Double getChangeMeters() {
        return null;
    }

    public Double getDistanceMeters() {
        return null;
    }

    public Double getMaxElevation() {
        return null;
    }

    public Double getEnd() {
        return null;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public Double getStart() {
        return null;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeValue(this.change);
        dest.writeValue(this.distance);
        dest.writeValue(this.maxElevation);
        dest.writeValue(this.end);
        dest.writeInt(this.endIndex);
        dest.writeValue(this.start);
        dest.writeInt(this.startIndex);
    }

    public ClimbImpl() {
    }

    private ClimbImpl(Parcel in) {
        this.category = in.readString();
        this.change = (Double) in.readValue(Double.class.getClassLoader());
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.maxElevation = (Double) in.readValue(Double.class.getClassLoader());
        this.end = (Double) in.readValue(Double.class.getClassLoader());
        this.endIndex = in.readInt();
        this.start = (Double) in.readValue(Double.class.getClassLoader());
        this.startIndex = in.readInt();
    }
}
