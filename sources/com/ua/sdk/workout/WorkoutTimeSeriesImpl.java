package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkoutTimeSeriesImpl implements TimeSeriesData {
    public static final Parcelable.Creator<WorkoutTimeSeriesImpl> CREATOR = new Parcelable.Creator<WorkoutTimeSeriesImpl>() {
        public WorkoutTimeSeriesImpl createFromParcel(Parcel source) {
            return new WorkoutTimeSeriesImpl(source);
        }

        public WorkoutTimeSeriesImpl[] newArray(int size) {
            return new WorkoutTimeSeriesImpl[size];
        }
    };
    TimeSeriesImpl<WorkoutCadenceEntry> workoutCadenceEntryTimeSeries;
    TimeSeriesImpl<WorkoutDistanceEntry> workoutDistanceTimeSeries;
    TimeSeriesImpl<WorkoutHeartRateEntry> workoutHeartRateEntryTimeSeries;
    TimeSeriesImpl<WorkoutPositionEntry> workoutPositionEntryTimeSeries;
    TimeSeriesImpl<WorkoutPowerEntry> workoutPowerEntryTimeSeries;
    TimeSeriesImpl<WorkoutSpeedEntry> workoutSpeedEntryTimeSeries;
    TimeSeriesImpl<WorkoutStepsEntry> workoutStepsEntryTimeSeries;
    TimeSeriesImpl<WorkoutTimerStopEntry> workoutStopTimeEntryTimeSeries;
    TimeSeriesImpl<WorkoutTorqueEntry> workoutTorqueEntryTimeSeries;

    public WorkoutTimeSeriesImpl() {
    }

    public TimeSeries<WorkoutHeartRateEntry> getHeartRateTimeSeries() {
        return this.workoutHeartRateEntryTimeSeries;
    }

    public TimeSeries<WorkoutSpeedEntry> getSpeedTimeSeries() {
        return this.workoutSpeedEntryTimeSeries;
    }

    public TimeSeries<WorkoutCadenceEntry> getCadenceTimeSeries() {
        return this.workoutCadenceEntryTimeSeries;
    }

    public TimeSeries<WorkoutPowerEntry> getPowerTimeSeries() {
        return this.workoutPowerEntryTimeSeries;
    }

    public TimeSeries<WorkoutTorqueEntry> getTorqueTimeSeries() {
        return this.workoutTorqueEntryTimeSeries;
    }

    public TimeSeries<WorkoutDistanceEntry> getDistanceTimeSeries() {
        return this.workoutDistanceTimeSeries;
    }

    public TimeSeries<WorkoutStepsEntry> getStepsTimeSeries() {
        return this.workoutStepsEntryTimeSeries;
    }

    public TimeSeries<WorkoutPositionEntry> getPositionTimeSeries() {
        return this.workoutPositionEntryTimeSeries;
    }

    public TimeSeries<WorkoutTimerStopEntry> getTimerStopTimeSeries() {
        return this.workoutStopTimeEntryTimeSeries;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.workoutHeartRateEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutSpeedEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutCadenceEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutPowerEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutTorqueEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutDistanceTimeSeries, flags);
        dest.writeParcelable(this.workoutStepsEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutPositionEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutStopTimeEntryTimeSeries, flags);
    }

    private WorkoutTimeSeriesImpl(Parcel in) {
        this.workoutHeartRateEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutSpeedEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutCadenceEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutPowerEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutTorqueEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutDistanceTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutStepsEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutPositionEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutStopTimeEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
    }
}
