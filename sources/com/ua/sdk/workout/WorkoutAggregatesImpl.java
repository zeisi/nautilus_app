package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.IntensityCalculator;
import com.ua.sdk.heartrate.HeartRateZones;
import com.ua.sdk.internal.IntensityCalculatorImpl;

public class WorkoutAggregatesImpl implements WorkoutAggregates {
    public static final Parcelable.Creator<WorkoutAggregatesImpl> CREATOR = new Parcelable.Creator<WorkoutAggregatesImpl>() {
        public WorkoutAggregatesImpl createFromParcel(Parcel source) {
            return new WorkoutAggregatesImpl(source);
        }

        public WorkoutAggregatesImpl[] newArray(int size) {
            return new WorkoutAggregatesImpl[size];
        }
    };
    @SerializedName("active_time_total")
    Double activeTimeTotal;
    @SerializedName("cadence_avg")
    Integer cadenceAvg;
    @SerializedName("cadence_max")
    Integer cadenceMax;
    @SerializedName("cadence_min")
    Integer cadenceMin;
    @SerializedName("distance_total")
    Double distanceTotal;
    @SerializedName("elapsed_time_total")
    Double elapsedTimeTotal;
    @SerializedName("heartrate_avg")
    Integer heartRateAvg;
    @SerializedName("heartrate_max")
    Integer heartRateMax;
    @SerializedName("heartrate_min")
    Integer heartRateMin;
    transient IntensityCalculator intensityCalculator;
    @SerializedName("metabolic_energy_total")
    Double metabolicEnergyTotal;
    @SerializedName("power_avg")
    Double powerAvg;
    @SerializedName("power_max")
    Double powerMax;
    @SerializedName("power_min")
    Double powerMin;
    @SerializedName("speed_avg")
    Double speedAvg;
    @SerializedName("speed_max")
    Double speedMax;
    @SerializedName("speed_min")
    Double speedMin;
    @SerializedName("steps_total")
    Integer stepsTotal;
    @SerializedName("torque_avg")
    Double torqueAvg;
    @SerializedName("torque_max")
    Double torqueMax;
    @SerializedName("torque_min")
    Double torqueMin;
    @SerializedName("willpower")
    Double willPower;

    public WorkoutAggregatesImpl() {
    }

    public Integer getHeartRateMin() {
        return this.heartRateMin;
    }

    public Integer getHeartRateMax() {
        return this.heartRateMax;
    }

    public Integer getHeartRateAvg() {
        return this.heartRateAvg;
    }

    public Double getIntensityMin(HeartRateZones heartRateZones) {
        if (this.heartRateMin == null || heartRateZones == null) {
            return null;
        }
        if (this.intensityCalculator == null) {
            this.intensityCalculator = new IntensityCalculatorImpl();
        }
        return Double.valueOf(this.intensityCalculator.calculateCurrentIntensity(heartRateZones, (double) this.heartRateMin.intValue()));
    }

    public Double getIntensityMax(HeartRateZones heartRateZones) {
        if (this.heartRateMax == null || heartRateZones == null) {
            return null;
        }
        if (this.intensityCalculator == null) {
            this.intensityCalculator = new IntensityCalculatorImpl();
        }
        return Double.valueOf(this.intensityCalculator.calculateCurrentIntensity(heartRateZones, (double) this.heartRateMax.intValue()));
    }

    public Double getIntensityAvg(HeartRateZones heartRateZones) {
        if (this.heartRateAvg == null || heartRateZones == null) {
            return null;
        }
        if (this.intensityCalculator == null) {
            this.intensityCalculator = new IntensityCalculatorImpl();
        }
        return Double.valueOf(this.intensityCalculator.calculateCurrentIntensity(heartRateZones, (double) this.heartRateAvg.intValue()));
    }

    public Double getSpeedMin() {
        return this.speedMin;
    }

    public Double getSpeedMax() {
        return this.speedMax;
    }

    public Double getSpeedAvg() {
        return this.speedAvg;
    }

    public Integer getCadenceMin() {
        return this.cadenceMin;
    }

    public Integer getCadenceMax() {
        return this.cadenceMax;
    }

    public Integer getCadenceAvg() {
        return this.cadenceAvg;
    }

    public Double getPowerMin() {
        return this.powerMin;
    }

    public Double getPowerMax() {
        return this.powerMax;
    }

    public Double getPowerAvg() {
        return this.powerAvg;
    }

    public Double getTorqueMin() {
        return this.torqueMin;
    }

    public Double getTorqueMax() {
        return this.torqueMax;
    }

    public Double getTorqueAvg() {
        return this.torqueAvg;
    }

    public Double getWillPower() {
        return this.willPower;
    }

    public Double getDistanceTotal() {
        return this.distanceTotal;
    }

    public Double getMetabolicEnergyTotal() {
        return this.metabolicEnergyTotal;
    }

    public Double getActiveTimeTotal() {
        return this.activeTimeTotal;
    }

    public Double getElapsedTimeTotal() {
        return this.elapsedTimeTotal;
    }

    public Integer getStepsTotal() {
        return this.stepsTotal;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.heartRateMin);
        dest.writeValue(this.heartRateMax);
        dest.writeValue(this.heartRateAvg);
        dest.writeValue(this.speedMin);
        dest.writeValue(this.speedMax);
        dest.writeValue(this.speedAvg);
        dest.writeValue(this.cadenceMin);
        dest.writeValue(this.cadenceMax);
        dest.writeValue(this.cadenceAvg);
        dest.writeValue(this.powerMin);
        dest.writeValue(this.powerMax);
        dest.writeValue(this.powerAvg);
        dest.writeValue(this.torqueMin);
        dest.writeValue(this.torqueMax);
        dest.writeValue(this.torqueAvg);
        dest.writeValue(this.willPower);
        dest.writeValue(this.distanceTotal);
        dest.writeValue(this.metabolicEnergyTotal);
        dest.writeValue(this.activeTimeTotal);
        dest.writeValue(this.elapsedTimeTotal);
        dest.writeValue(this.stepsTotal);
    }

    private WorkoutAggregatesImpl(Parcel in) {
        this.heartRateMin = (Integer) in.readValue(Integer.class.getClassLoader());
        this.heartRateMax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.heartRateAvg = (Integer) in.readValue(Integer.class.getClassLoader());
        this.speedMin = (Double) in.readValue(Double.class.getClassLoader());
        this.speedMax = (Double) in.readValue(Double.class.getClassLoader());
        this.speedAvg = (Double) in.readValue(Double.class.getClassLoader());
        this.cadenceMin = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cadenceMax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cadenceAvg = (Integer) in.readValue(Integer.class.getClassLoader());
        this.powerMin = (Double) in.readValue(Double.class.getClassLoader());
        this.powerMax = (Double) in.readValue(Double.class.getClassLoader());
        this.powerAvg = (Double) in.readValue(Double.class.getClassLoader());
        this.torqueMin = (Double) in.readValue(Double.class.getClassLoader());
        this.torqueMax = (Double) in.readValue(Double.class.getClassLoader());
        this.torqueAvg = (Double) in.readValue(Double.class.getClassLoader());
        this.willPower = (Double) in.readValue(Double.class.getClassLoader());
        this.distanceTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.metabolicEnergyTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.activeTimeTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.elapsedTimeTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.stepsTotal = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutAggregatesImpl that = (WorkoutAggregatesImpl) o;
        if (this.activeTimeTotal == null ? that.activeTimeTotal != null : !this.activeTimeTotal.equals(that.activeTimeTotal)) {
            return false;
        }
        if (this.cadenceAvg == null ? that.cadenceAvg != null : !this.cadenceAvg.equals(that.cadenceAvg)) {
            return false;
        }
        if (this.cadenceMax == null ? that.cadenceMax != null : !this.cadenceMax.equals(that.cadenceMax)) {
            return false;
        }
        if (this.cadenceMin == null ? that.cadenceMin != null : !this.cadenceMin.equals(that.cadenceMin)) {
            return false;
        }
        if (this.distanceTotal == null ? that.distanceTotal != null : !this.distanceTotal.equals(that.distanceTotal)) {
            return false;
        }
        if (this.elapsedTimeTotal == null ? that.elapsedTimeTotal != null : !this.elapsedTimeTotal.equals(that.elapsedTimeTotal)) {
            return false;
        }
        if (this.heartRateAvg == null ? that.heartRateAvg != null : !this.heartRateAvg.equals(that.heartRateAvg)) {
            return false;
        }
        if (this.heartRateMax == null ? that.heartRateMax != null : !this.heartRateMax.equals(that.heartRateMax)) {
            return false;
        }
        if (this.heartRateMin == null ? that.heartRateMin != null : !this.heartRateMin.equals(that.heartRateMin)) {
            return false;
        }
        if (this.metabolicEnergyTotal == null ? that.metabolicEnergyTotal != null : !this.metabolicEnergyTotal.equals(that.metabolicEnergyTotal)) {
            return false;
        }
        if (this.powerAvg == null ? that.powerAvg != null : !this.powerAvg.equals(that.powerAvg)) {
            return false;
        }
        if (this.powerMax == null ? that.powerMax != null : !this.powerMax.equals(that.powerMax)) {
            return false;
        }
        if (this.powerMin == null ? that.powerMin != null : !this.powerMin.equals(that.powerMin)) {
            return false;
        }
        if (this.speedAvg == null ? that.speedAvg != null : !this.speedAvg.equals(that.speedAvg)) {
            return false;
        }
        if (this.speedMax == null ? that.speedMax != null : !this.speedMax.equals(that.speedMax)) {
            return false;
        }
        if (this.speedMin == null ? that.speedMin != null : !this.speedMin.equals(that.speedMin)) {
            return false;
        }
        if (this.stepsTotal == null ? that.stepsTotal != null : !this.stepsTotal.equals(that.stepsTotal)) {
            return false;
        }
        if (this.torqueAvg == null ? that.torqueAvg != null : !this.torqueAvg.equals(that.torqueAvg)) {
            return false;
        }
        if (this.torqueMax == null ? that.torqueMax != null : !this.torqueMax.equals(that.torqueMax)) {
            return false;
        }
        if (this.torqueMin == null ? that.torqueMin != null : !this.torqueMin.equals(that.torqueMin)) {
            return false;
        }
        if (this.willPower != null) {
            if (this.willPower.equals(that.willPower)) {
                return true;
            }
        } else if (that.willPower == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20 = 0;
        if (this.heartRateMin != null) {
            result = this.heartRateMin.hashCode();
        } else {
            result = 0;
        }
        int i21 = result * 31;
        if (this.heartRateMax != null) {
            i = this.heartRateMax.hashCode();
        } else {
            i = 0;
        }
        int i22 = (i21 + i) * 31;
        if (this.heartRateAvg != null) {
            i2 = this.heartRateAvg.hashCode();
        } else {
            i2 = 0;
        }
        int i23 = (i22 + i2) * 31;
        if (this.speedMin != null) {
            i3 = this.speedMin.hashCode();
        } else {
            i3 = 0;
        }
        int i24 = (i23 + i3) * 31;
        if (this.speedMax != null) {
            i4 = this.speedMax.hashCode();
        } else {
            i4 = 0;
        }
        int i25 = (i24 + i4) * 31;
        if (this.speedAvg != null) {
            i5 = this.speedAvg.hashCode();
        } else {
            i5 = 0;
        }
        int i26 = (i25 + i5) * 31;
        if (this.cadenceMin != null) {
            i6 = this.cadenceMin.hashCode();
        } else {
            i6 = 0;
        }
        int i27 = (i26 + i6) * 31;
        if (this.cadenceMax != null) {
            i7 = this.cadenceMax.hashCode();
        } else {
            i7 = 0;
        }
        int i28 = (i27 + i7) * 31;
        if (this.cadenceAvg != null) {
            i8 = this.cadenceAvg.hashCode();
        } else {
            i8 = 0;
        }
        int i29 = (i28 + i8) * 31;
        if (this.powerMin != null) {
            i9 = this.powerMin.hashCode();
        } else {
            i9 = 0;
        }
        int i30 = (i29 + i9) * 31;
        if (this.powerMax != null) {
            i10 = this.powerMax.hashCode();
        } else {
            i10 = 0;
        }
        int i31 = (i30 + i10) * 31;
        if (this.powerAvg != null) {
            i11 = this.powerAvg.hashCode();
        } else {
            i11 = 0;
        }
        int i32 = (i31 + i11) * 31;
        if (this.torqueMin != null) {
            i12 = this.torqueMin.hashCode();
        } else {
            i12 = 0;
        }
        int i33 = (i32 + i12) * 31;
        if (this.torqueMax != null) {
            i13 = this.torqueMax.hashCode();
        } else {
            i13 = 0;
        }
        int i34 = (i33 + i13) * 31;
        if (this.torqueAvg != null) {
            i14 = this.torqueAvg.hashCode();
        } else {
            i14 = 0;
        }
        int i35 = (i34 + i14) * 31;
        if (this.willPower != null) {
            i15 = this.willPower.hashCode();
        } else {
            i15 = 0;
        }
        int i36 = (i35 + i15) * 31;
        if (this.distanceTotal != null) {
            i16 = this.distanceTotal.hashCode();
        } else {
            i16 = 0;
        }
        int i37 = (i36 + i16) * 31;
        if (this.metabolicEnergyTotal != null) {
            i17 = this.metabolicEnergyTotal.hashCode();
        } else {
            i17 = 0;
        }
        int i38 = (i37 + i17) * 31;
        if (this.activeTimeTotal != null) {
            i18 = this.activeTimeTotal.hashCode();
        } else {
            i18 = 0;
        }
        int i39 = (i38 + i18) * 31;
        if (this.elapsedTimeTotal != null) {
            i19 = this.elapsedTimeTotal.hashCode();
        } else {
            i19 = 0;
        }
        int i40 = (i39 + i19) * 31;
        if (this.stepsTotal != null) {
            i20 = this.stepsTotal.hashCode();
        }
        return i40 + i20;
    }
}
