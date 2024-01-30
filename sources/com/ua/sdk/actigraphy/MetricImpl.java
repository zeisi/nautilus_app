package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.TimeZone;

public class MetricImpl implements Parcelable, Metric {
    public static Parcelable.Creator<MetricImpl> CREATOR = new Parcelable.Creator<MetricImpl>() {
        public MetricImpl createFromParcel(Parcel source) {
            return new MetricImpl(source);
        }

        public MetricImpl[] newArray(int size) {
            return new MetricImpl[size];
        }
    };
    private AggregateValueImpl mAggregateValueImpl;
    private Date mEndDate;
    private long[] mEpochTimes;
    private Date mStartDate;
    private TimeZone mTimeZone;
    private double[] mValues;

    protected MetricImpl() {
    }

    public Date getStartDateTime() {
        return this.mStartDate;
    }

    public void setStartDateTime(Date startDate) {
        this.mStartDate = startDate;
    }

    public Date getEndDateTime() {
        return this.mEndDate;
    }

    public void setEndDateTime(Date endDate) {
        this.mEndDate = endDate;
    }

    public AggregateValueImpl getAggregateValue() {
        return this.mAggregateValueImpl;
    }

    public void setAggregateValue(AggregateValueImpl aggregateValue) {
        this.mAggregateValueImpl = aggregateValue;
    }

    public long[] getEpochTimes() {
        return this.mEpochTimes;
    }

    public void setEpochTimes(long[] epochTimes) {
        this.mEpochTimes = epochTimes;
    }

    public long getEpochTime(int i) {
        return this.mEpochTimes[i];
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    public double[] getValues() {
        return this.mValues;
    }

    public void setValues(double[] values) {
        this.mValues = values;
    }

    public double getValue(int i) {
        return this.mValues[i];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j = -1;
        dest.writeValue(Long.valueOf(this.mStartDate != null ? this.mStartDate.getTime() : -1));
        if (this.mEndDate != null) {
            j = this.mEndDate.getTime();
        }
        dest.writeValue(Long.valueOf(j));
        dest.writeParcelable(this.mAggregateValueImpl, flags);
        dest.writeLongArray(this.mEpochTimes);
        dest.writeString(this.mTimeZone == null ? null : this.mTimeZone.getID());
        dest.writeDoubleArray(this.mValues);
    }

    protected MetricImpl(Parcel in) {
        TimeZone timeZone = null;
        long tmpStartDate = ((Long) in.readValue(Long.class.getClassLoader())).longValue();
        this.mStartDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        long tmpEndDate = ((Long) in.readValue(Long.class.getClassLoader())).longValue();
        this.mEndDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        this.mAggregateValueImpl = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
        this.mEpochTimes = in.createLongArray();
        String tmpString = in.readString();
        this.mTimeZone = tmpString != null ? TimeZone.getTimeZone(tmpString) : timeZone;
        this.mValues = in.createDoubleArray();
    }
}
