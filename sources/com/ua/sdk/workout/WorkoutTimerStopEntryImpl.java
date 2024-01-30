package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutTimerStopEntryImpl implements WorkoutTimerStopEntry {
    public static final Parcelable.Creator<WorkoutTimerStopEntryImpl> CREATOR = new Parcelable.Creator<WorkoutTimerStopEntryImpl>() {
        public WorkoutTimerStopEntryImpl createFromParcel(Parcel source) {
            return new WorkoutTimerStopEntryImpl(source);
        }

        public WorkoutTimerStopEntryImpl[] newArray(int size) {
            return new WorkoutTimerStopEntryImpl[size];
        }
    };
    private double offset;
    private double stoppedTime;

    public WorkoutTimerStopEntryImpl(Double offset2, Double stoppedTime2) {
        this.offset = offset2.doubleValue();
        this.stoppedTime = stoppedTime2.doubleValue();
    }

    public double getStoppedTime() {
        return this.stoppedTime;
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

    public int compareTo(WorkoutTimerStopEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Double.valueOf(this.stoppedTime));
    }

    private WorkoutTimerStopEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.stoppedTime = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutTimerStopEntryImpl that = (WorkoutTimerStopEntryImpl) o;
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        if (Double.compare(that.stoppedTime, this.stoppedTime) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.stoppedTime);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }
}
