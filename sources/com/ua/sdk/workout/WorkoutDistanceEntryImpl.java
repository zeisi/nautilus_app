package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutDistanceEntryImpl implements WorkoutDistanceEntry {
    public static final Parcelable.Creator<WorkoutDistanceEntryImpl> CREATOR = new Parcelable.Creator<WorkoutDistanceEntryImpl>() {
        public WorkoutDistanceEntryImpl createFromParcel(Parcel source) {
            return new WorkoutDistanceEntryImpl(source);
        }

        public WorkoutDistanceEntryImpl[] newArray(int size) {
            return new WorkoutDistanceEntryImpl[size];
        }
    };
    double distance;
    double offset;

    public WorkoutDistanceEntryImpl(Double offset2, Double distance2) {
        this.offset = offset2.doubleValue();
        this.distance = distance2.doubleValue();
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

    public double getDistance() {
        return this.distance;
    }

    public int compareTo(WorkoutDistanceEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Double.valueOf(this.distance));
    }

    private WorkoutDistanceEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.distance = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutDistanceEntryImpl that = (WorkoutDistanceEntryImpl) o;
        if (Double.compare(that.distance, this.distance) != 0) {
            return false;
        }
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.distance);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }
}
