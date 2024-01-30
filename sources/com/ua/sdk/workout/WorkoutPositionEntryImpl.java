package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class WorkoutPositionEntryImpl implements WorkoutPositionEntry {
    public static final Parcelable.Creator<WorkoutPositionEntryImpl> CREATOR = new Parcelable.Creator<WorkoutPositionEntryImpl>() {
        public WorkoutPositionEntryImpl createFromParcel(Parcel source) {
            return new WorkoutPositionEntryImpl(source);
        }

        public WorkoutPositionEntryImpl[] newArray(int size) {
            return new WorkoutPositionEntryImpl[size];
        }
    };
    private Double elevation;
    private Double latitude;
    private Double longitude;
    private double offset;

    public WorkoutPositionEntryImpl(Double offset2, Double elevation2, Double latitude2, Double longitude2) {
        this.offset = offset2.doubleValue();
        this.elevation = elevation2;
        this.latitude = latitude2;
        this.longitude = longitude2;
    }

    public Double getElevation() {
        return this.elevation;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
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

    public int compareTo(WorkoutPositionEntry another) {
        return Double.compare(this.offset, another.getOffset());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.offset);
        dest.writeValue(this.elevation);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    private WorkoutPositionEntryImpl(Parcel in) {
        this.offset = in.readDouble();
        this.elevation = (Double) in.readValue(Double.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutPositionEntryImpl that = (WorkoutPositionEntryImpl) o;
        if (Double.compare(that.offset, this.offset) != 0) {
            return false;
        }
        if (this.elevation == null ? that.elevation != null : !this.elevation.equals(that.elevation)) {
            return false;
        }
        if (this.latitude == null ? that.latitude != null : !this.latitude.equals(that.latitude)) {
            return false;
        }
        if (this.longitude != null) {
            if (this.longitude.equals(that.longitude)) {
                return true;
            }
        } else if (that.longitude == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3 = 0;
        long temp = Double.doubleToLongBits(this.offset);
        int i4 = ((int) ((temp >>> 32) ^ temp)) * 31;
        if (this.elevation != null) {
            i = this.elevation.hashCode();
        } else {
            i = 0;
        }
        int i5 = (i4 + i) * 31;
        if (this.latitude != null) {
            i2 = this.latitude.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 31;
        if (this.longitude != null) {
            i3 = this.longitude.hashCode();
        }
        return i6 + i3;
    }
}
