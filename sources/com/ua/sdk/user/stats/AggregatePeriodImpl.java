package com.ua.sdk.user.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.LocalDate;

public class AggregatePeriodImpl implements AggregatePeriod {
    public static Parcelable.Creator<AggregatePeriodImpl> CREATOR = new Parcelable.Creator<AggregatePeriodImpl>() {
        public AggregatePeriodImpl createFromParcel(Parcel source) {
            return new AggregatePeriodImpl(source);
        }

        public AggregatePeriodImpl[] newArray(int size) {
            return new AggregatePeriodImpl[size];
        }
    };
    @SerializedName("end")
    LocalDate mEndDate;
    @SerializedName("start")
    LocalDate mStartDate;

    protected AggregatePeriodImpl() {
    }

    public LocalDate getStartDate() {
        return this.mStartDate;
    }

    public LocalDate getEndDate() {
        return this.mEndDate;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mStartDate, 0);
        dest.writeParcelable(this.mEndDate, 0);
    }

    private AggregatePeriodImpl(Parcel in) {
        this.mStartDate = (LocalDate) in.readParcelable(LocalDate.class.getClassLoader());
        this.mEndDate = (LocalDate) in.readParcelable(LocalDate.class.getClassLoader());
    }
}
