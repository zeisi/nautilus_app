package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutStepsEntryImpl implements WorkoutStepsEntry {
    public static final Parcelable.Creator<WorkoutStepsEntryImpl> CREATOR = new Parcelable.Creator<WorkoutStepsEntryImpl>() {
        public WorkoutStepsEntryImpl createFromParcel(Parcel source) {
            return new WorkoutStepsEntryImpl(source);
        }

        public WorkoutStepsEntryImpl[] newArray(int size) {
            return new WorkoutStepsEntryImpl[size];
        }
    };
    double offset;
    int steps;

    public WorkoutStepsEntryImpl(Double offset2, Integer steps2) {
        this.offset = offset2.doubleValue();
        this.steps = steps2.intValue();
    }

    public int getInstantaneousSteps() {
        return this.steps;
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

    public int compareTo(WorkoutStepsEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Integer.valueOf(this.steps));
    }

    private WorkoutStepsEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.steps = ((Integer) in.readValue(Integer.class.getClassLoader())).intValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutStepsEntryImpl that = (WorkoutStepsEntryImpl) o;
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        if (this.steps != that.steps) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        return (((int) ((temp >>> 32) ^ temp)) * 31) + this.steps;
    }
}
