package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutHeartRateEntryImpl implements WorkoutHeartRateEntry {
    public static final Parcelable.Creator<WorkoutHeartRateEntryImpl> CREATOR = new Parcelable.Creator<WorkoutHeartRateEntryImpl>() {
        public WorkoutHeartRateEntryImpl createFromParcel(Parcel source) {
            return new WorkoutHeartRateEntryImpl(source);
        }

        public WorkoutHeartRateEntryImpl[] newArray(int size) {
            return new WorkoutHeartRateEntryImpl[size];
        }
    };
    private int bpm;
    private double offset;

    public WorkoutHeartRateEntryImpl(Double offset2, Integer bpm2) {
        this.offset = offset2.doubleValue();
        this.bpm = bpm2.intValue();
    }

    public int getBpm() {
        return this.bpm;
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

    public int compareTo(WorkoutHeartRateEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Integer.valueOf(this.bpm));
    }

    private WorkoutHeartRateEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.bpm = ((Integer) in.readValue(Integer.class.getClassLoader())).intValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutHeartRateEntryImpl that = (WorkoutHeartRateEntryImpl) o;
        if (this.bpm != that.bpm) {
            return false;
        }
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        return (((int) ((temp >>> 32) ^ temp)) * 31) + this.bpm;
    }
}
