package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutCadenceEntryImpl implements WorkoutCadenceEntry {
    public static final Parcelable.Creator<WorkoutCadenceEntryImpl> CREATOR = new Parcelable.Creator<WorkoutCadenceEntryImpl>() {
        public WorkoutCadenceEntryImpl createFromParcel(Parcel source) {
            return new WorkoutCadenceEntryImpl(source);
        }

        public WorkoutCadenceEntryImpl[] newArray(int size) {
            return new WorkoutCadenceEntryImpl[size];
        }
    };
    private int cadence;
    private double offset;

    public WorkoutCadenceEntryImpl(Double offset2, Integer cadence2) {
        this.offset = offset2.doubleValue();
        this.cadence = cadence2.intValue();
    }

    public int getInstantaneousCadence() {
        return this.cadence;
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

    public int describeContents() {
        return 0;
    }

    public int compareTo(WorkoutCadenceEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Integer.valueOf(this.cadence));
    }

    private WorkoutCadenceEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.cadence = ((Integer) in.readValue(Integer.class.getClassLoader())).intValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutCadenceEntryImpl that = (WorkoutCadenceEntryImpl) o;
        if (this.cadence != that.cadence) {
            return false;
        }
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        return (((int) ((temp >>> 32) ^ temp)) * 31) + this.cadence;
    }
}
