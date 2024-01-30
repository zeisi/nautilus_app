package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutSpeedEntryImpl implements WorkoutSpeedEntry {
    public static final Parcelable.Creator<WorkoutSpeedEntryImpl> CREATOR = new Parcelable.Creator<WorkoutSpeedEntryImpl>() {
        public WorkoutSpeedEntryImpl createFromParcel(Parcel source) {
            return new WorkoutSpeedEntryImpl(source);
        }

        public WorkoutSpeedEntryImpl[] newArray(int size) {
            return new WorkoutSpeedEntryImpl[size];
        }
    };
    private double offset;
    private double speed;

    public WorkoutSpeedEntryImpl(Double offset2, Double speed2) {
        this.offset = offset2.doubleValue();
        this.speed = speed2.doubleValue();
    }

    public double getInstantaneousSpeed() {
        return this.speed;
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

    public int compareTo(WorkoutSpeedEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Double.valueOf(this.speed));
    }

    private WorkoutSpeedEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.speed = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutSpeedEntryImpl that = (WorkoutSpeedEntryImpl) o;
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        if (Double.compare(that.speed, this.speed) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.speed);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }
}
