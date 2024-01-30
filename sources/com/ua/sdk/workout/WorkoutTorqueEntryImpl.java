package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutTorqueEntryImpl implements WorkoutTorqueEntry {
    public static final Parcelable.Creator<WorkoutTorqueEntryImpl> CREATOR = new Parcelable.Creator<WorkoutTorqueEntryImpl>() {
        public WorkoutTorqueEntryImpl createFromParcel(Parcel source) {
            return new WorkoutTorqueEntryImpl(source);
        }

        public WorkoutTorqueEntryImpl[] newArray(int size) {
            return new WorkoutTorqueEntryImpl[size];
        }
    };
    private double offset;
    private double torque;

    public WorkoutTorqueEntryImpl(Double offset2, Double torque2) {
        this.offset = offset2.doubleValue();
        this.torque = torque2.doubleValue();
    }

    public double getInstantaneousTorque() {
        return this.torque;
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

    public int compareTo(WorkoutTorqueEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Double.valueOf(this.offset));
        dest.writeValue(Double.valueOf(this.torque));
    }

    private WorkoutTorqueEntryImpl(Parcel in) {
        this.offset = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
        this.torque = ((Double) in.readValue(Double.class.getClassLoader())).doubleValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutTorqueEntryImpl that = (WorkoutTorqueEntryImpl) o;
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        if (Double.compare(that.torque, this.torque) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.offset);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.torque);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }
}
