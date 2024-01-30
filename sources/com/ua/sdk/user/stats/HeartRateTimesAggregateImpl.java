package com.ua.sdk.user.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class HeartRateTimesAggregateImpl implements HeartRateTimesAggregate {
    public static Parcelable.Creator<HeartRateTimesAggregateImpl> CREATOR = new Parcelable.Creator<HeartRateTimesAggregateImpl>() {
        public HeartRateTimesAggregateImpl createFromParcel(Parcel source) {
            return new HeartRateTimesAggregateImpl(source);
        }

        public HeartRateTimesAggregateImpl[] newArray(int size) {
            return new HeartRateTimesAggregateImpl[size];
        }
    };
    @SerializedName("zone_5")
    Double mTimeInZoneFive;
    @SerializedName("zone_4")
    Double mTimeInZoneFour;
    @SerializedName("zone_1")
    Double mTimeInZoneOne;
    @SerializedName("zone_3")
    Double mTimeInZoneThree;
    @SerializedName("zone_2")
    Double mTimeInZoneTwo;

    public Double getTimeInZoneFive() {
        return this.mTimeInZoneFive;
    }

    public Double getTimeInZoneFour() {
        return this.mTimeInZoneFour;
    }

    public Double getTimeInZoneThree() {
        return this.mTimeInZoneThree;
    }

    public Double getTimeInZoneTwo() {
        return this.mTimeInZoneTwo;
    }

    public Double getTimeInZoneOne() {
        return this.mTimeInZoneOne;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mTimeInZoneFive);
        dest.writeValue(this.mTimeInZoneFour);
        dest.writeValue(this.mTimeInZoneThree);
        dest.writeValue(this.mTimeInZoneTwo);
        dest.writeValue(this.mTimeInZoneOne);
    }

    public HeartRateTimesAggregateImpl() {
    }

    private HeartRateTimesAggregateImpl(Parcel in) {
        this.mTimeInZoneFive = (Double) in.readValue(Double.class.getClassLoader());
        this.mTimeInZoneFour = (Double) in.readValue(Double.class.getClassLoader());
        this.mTimeInZoneThree = (Double) in.readValue(Double.class.getClassLoader());
        this.mTimeInZoneTwo = (Double) in.readValue(Double.class.getClassLoader());
        this.mTimeInZoneOne = (Double) in.readValue(Double.class.getClassLoader());
    }
}
