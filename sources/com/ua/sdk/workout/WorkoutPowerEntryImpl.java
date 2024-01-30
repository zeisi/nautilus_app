package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutPowerEntryImpl implements WorkoutPowerEntry {
    public static final Parcelable.Creator<WorkoutPowerEntryImpl> CREATOR = new Parcelable.Creator<WorkoutPowerEntryImpl>() {
        public WorkoutPowerEntryImpl createFromParcel(Parcel source) {
            return new WorkoutPowerEntryImpl(source);
        }

        public WorkoutPowerEntryImpl[] newArray(int size) {
            return new WorkoutPowerEntryImpl[size];
        }
    };
    private double offset;
    private double power;

    public WorkoutPowerEntryImpl(Double offset2, Double power2) {
        this.offset = offset2.doubleValue();
        this.power = power2.doubleValue();
    }

    public double getInstantaneousPower() {
        return this.power;
    }

    public double getOffset() {
        return this.offset;
    }

    public Date getTime() {
        return null;
    }

    public Long getTimeInMillis() {
        return null;
    }

    public int compareTo(WorkoutPowerEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Double.valueOf(this.power));
    }

    private WorkoutPowerEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.power = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutPowerEntryImpl that = (WorkoutPowerEntryImpl) o;
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        if (Double.compare(that.power, this.power) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.power);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }
}
