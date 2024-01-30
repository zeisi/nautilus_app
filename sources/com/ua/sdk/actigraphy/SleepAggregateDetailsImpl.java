package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;

public class SleepAggregateDetailsImpl implements Parcelable, SleepAggregateDetails {
    public static Parcelable.Creator<SleepAggregateDetailsImpl> CREATOR = new Parcelable.Creator<SleepAggregateDetailsImpl>() {
        public SleepAggregateDetailsImpl createFromParcel(Parcel source) {
            return new SleepAggregateDetailsImpl(source);
        }

        public SleepAggregateDetailsImpl[] newArray(int size) {
            return new SleepAggregateDetailsImpl[size];
        }
    };
    private Double mAwake;
    private Double mDeepSleep;
    private Double mLightSleep;
    private Double mTimeToSleep;
    private Integer mTimesAwaken;

    protected SleepAggregateDetailsImpl() {
    }

    public Double getDeepSleep() {
        return this.mDeepSleep;
    }

    public void setDeepSleep(Double deepSleep) {
        this.mDeepSleep = deepSleep;
    }

    public Double getAwake() {
        return this.mAwake;
    }

    public void setAwake(Double awake) {
        this.mAwake = awake;
    }

    public Double getTimeToSleep() {
        return this.mTimeToSleep;
    }

    public void setTimeToSleep(Double timeToSleep) {
        this.mTimeToSleep = timeToSleep;
    }

    public Integer getTimesAwaken() {
        return this.mTimesAwaken;
    }

    public void setTimesAwaken(Integer timesAwaken) {
        this.mTimesAwaken = timesAwaken;
    }

    public Double getLightSleep() {
        return this.mLightSleep;
    }

    public void setLightSleep(Double lightSleep) {
        this.mLightSleep = lightSleep;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mDeepSleep);
        dest.writeValue(this.mAwake);
        dest.writeValue(this.mTimeToSleep);
        dest.writeValue(this.mTimesAwaken);
        dest.writeValue(this.mLightSleep);
    }

    private SleepAggregateDetailsImpl(Parcel in) {
        this.mDeepSleep = (Double) in.readValue(Double.class.getClassLoader());
        this.mAwake = (Double) in.readValue(Double.class.getClassLoader());
        this.mTimeToSleep = (Double) in.readValue(Double.class.getClassLoader());
        this.mTimesAwaken = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mLightSleep = (Double) in.readValue(Double.class.getClassLoader());
    }
}
