package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;

public class ActigraphyMetricsImpl implements Parcelable, ActigraphyMetrics {
    public static Parcelable.Creator<ActigraphyMetricsImpl> CREATOR = new Parcelable.Creator<ActigraphyMetricsImpl>() {
        public ActigraphyMetricsImpl createFromParcel(Parcel source) {
            return new ActigraphyMetricsImpl(source);
        }

        public ActigraphyMetricsImpl[] newArray(int size) {
            return new ActigraphyMetricsImpl[size];
        }
    };
    private MetricImpl[] mBodyMass;
    private MetricImpl[] mDistance;
    private MetricImpl[] mEnergyBurned;
    private MetricImpl[] mSleep;
    private MetricImpl[] mSteps;

    protected ActigraphyMetricsImpl() {
    }

    protected ActigraphyMetricsImpl(MetricImpl[] distance, MetricImpl[] energyBurned, MetricImpl[] steps, MetricImpl[] sleep, MetricImpl[] bodyMass) {
        this.mDistance = distance;
        this.mEnergyBurned = energyBurned;
        this.mSteps = steps;
        this.mSleep = sleep;
        this.mBodyMass = bodyMass;
    }

    public Metric[] getBodyMass() {
        return this.mBodyMass;
    }

    public void setBodyMass(MetricImpl[] bodyMass) {
        this.mBodyMass = bodyMass;
    }

    public Metric[] getDistance() {
        return this.mDistance;
    }

    public void setDistance(MetricImpl[] distance) {
        this.mDistance = distance;
    }

    public Metric[] getEnergyBurned() {
        return this.mEnergyBurned;
    }

    public void setEnergyBurned(MetricImpl[] energyBurned) {
        this.mEnergyBurned = energyBurned;
    }

    public Metric[] getSteps() {
        return this.mSteps;
    }

    public void setSteps(MetricImpl[] steps) {
        this.mSteps = steps;
    }

    public Metric[] getSleep() {
        return this.mSleep;
    }

    public void setSleep(MetricImpl[] sleep) {
        this.mSleep = sleep;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.mDistance, flags);
        dest.writeParcelableArray(this.mEnergyBurned, flags);
        dest.writeParcelableArray(this.mSteps, flags);
        dest.writeParcelableArray(this.mSleep, flags);
        dest.writeParcelableArray(this.mBodyMass, flags);
    }

    protected ActigraphyMetricsImpl(Parcel in) {
        Parcelable[] tempDistance = in.readParcelableArray(MetricImpl.class.getClassLoader());
        if (tempDistance != null) {
            this.mDistance = new MetricImpl[tempDistance.length];
            System.arraycopy(tempDistance, 0, this.mDistance, 0, tempDistance.length);
        } else {
            this.mDistance = null;
        }
        Parcelable[] tempEnergy = in.readParcelableArray(MetricImpl.class.getClassLoader());
        if (tempEnergy != null) {
            this.mEnergyBurned = new MetricImpl[tempEnergy.length];
            System.arraycopy(tempEnergy, 0, this.mEnergyBurned, 0, tempEnergy.length);
        } else {
            this.mEnergyBurned = null;
        }
        Parcelable[] tempSteps = in.readParcelableArray(MetricImpl.class.getClassLoader());
        if (tempSteps != null) {
            this.mSteps = new MetricImpl[tempSteps.length];
            System.arraycopy(tempSteps, 0, this.mSteps, 0, tempSteps.length);
        } else {
            this.mSteps = null;
        }
        Parcelable[] tempSleep = in.readParcelableArray(MetricImpl.class.getClassLoader());
        if (tempSleep != null) {
            this.mSleep = new MetricImpl[tempSleep.length];
            System.arraycopy(tempSleep, 0, this.mSleep, 0, tempSleep.length);
        } else {
            this.mSleep = null;
        }
        Parcelable[] tempBodyMass = in.readParcelableArray(MetricImpl.class.getClassLoader());
        if (tempBodyMass != null) {
            this.mBodyMass = new MetricImpl[tempBodyMass.length];
            System.arraycopy(tempBodyMass, 0, this.mBodyMass, 0, tempBodyMass.length);
            return;
        }
        this.mBodyMass = null;
    }
}
