package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;

public class ActigraphyAggregatesImpl implements ActigraphyAggregates, Parcelable {
    public static Parcelable.Creator<ActigraphyAggregatesImpl> CREATOR = new Parcelable.Creator<ActigraphyAggregatesImpl>() {
        public ActigraphyAggregatesImpl createFromParcel(Parcel source) {
            return new ActigraphyAggregatesImpl(source);
        }

        public ActigraphyAggregatesImpl[] newArray(int size) {
            return new ActigraphyAggregatesImpl[size];
        }
    };
    private AggregateValueImpl mActiveTime;
    private AggregateValueImpl mBodyMass;
    private AggregateValueImpl mDistance;
    private AggregateValueImpl mEnergyBurned;
    private AggregateValueImpl mSleep;
    private AggregateValueImpl mSteps;

    protected ActigraphyAggregatesImpl() {
    }

    protected ActigraphyAggregatesImpl(AggregateValueImpl distance, AggregateValueImpl bodyMass, AggregateValueImpl activeTime, AggregateValueImpl energyBurned, AggregateValueImpl sleep, AggregateValueImpl steps) {
        this.mDistance = distance;
        this.mBodyMass = bodyMass;
        this.mActiveTime = activeTime;
        this.mEnergyBurned = energyBurned;
        this.mSleep = sleep;
        this.mSteps = steps;
    }

    public AggregateValueImpl getDistance() {
        return this.mDistance;
    }

    public void setDistance(AggregateValueImpl distance) {
        this.mDistance = distance;
    }

    public AggregateValueImpl getBodyMass() {
        return this.mBodyMass;
    }

    public void setBodyMass(AggregateValueImpl bodyMass) {
        this.mBodyMass = bodyMass;
    }

    public AggregateValueImpl getActiveTime() {
        return this.mActiveTime;
    }

    public void setActiveTime(AggregateValueImpl activeTime) {
        this.mActiveTime = activeTime;
    }

    public AggregateValueImpl getEnergyBurned() {
        return this.mEnergyBurned;
    }

    public void setEnergyBurned(AggregateValueImpl energyBurned) {
        this.mEnergyBurned = energyBurned;
    }

    public AggregateValueImpl getSleep() {
        return this.mSleep;
    }

    public void setSleep(AggregateValueImpl sleep) {
        this.mSleep = sleep;
    }

    public AggregateValueImpl getSteps() {
        return this.mSteps;
    }

    public void setSteps(AggregateValueImpl steps) {
        this.mSteps = steps;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mDistance, flags);
        dest.writeParcelable(this.mBodyMass, flags);
        dest.writeParcelable(this.mActiveTime, flags);
        dest.writeParcelable(this.mEnergyBurned, flags);
        dest.writeParcelable(this.mSleep, flags);
        dest.writeParcelable(this.mSteps, flags);
    }

    private ActigraphyAggregatesImpl(Parcel in) {
        this.mDistance = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
        this.mBodyMass = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
        this.mActiveTime = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
        this.mEnergyBurned = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
        this.mSleep = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
        this.mSteps = (AggregateValueImpl) in.readParcelable(AggregateValueImpl.class.getClassLoader());
    }
}
