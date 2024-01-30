package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Goal extends zza {
    public static final Parcelable.Creator<Goal> CREATOR = new zzr();
    public static final int OBJECTIVE_TYPE_DURATION = 2;
    public static final int OBJECTIVE_TYPE_FREQUENCY = 3;
    public static final int OBJECTIVE_TYPE_METRIC = 1;
    private final int versionCode;
    private final long zzaTM;
    private final long zzaTN;
    private final List<Integer> zzaTO;
    private final Recurrence zzaTP;
    private final int zzaTQ;
    private final MetricObjective zzaTR;
    private final DurationObjective zzaTS;
    private final FrequencyObjective zzaTT;

    public static class DurationObjective extends zza {
        public static final Parcelable.Creator<DurationObjective> CREATOR = new zzo();
        private final int versionCode;
        private final long zzaTU;

        DurationObjective(int i, long j) {
            this.versionCode = i;
            this.zzaTU = j;
        }

        public DurationObjective(long j, TimeUnit timeUnit) {
            this(1, timeUnit.toNanos(j));
        }

        private boolean zza(DurationObjective durationObjective) {
            return this.zzaTU == durationObjective.zzaTU;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof DurationObjective) && zza((DurationObjective) obj));
        }

        public long getDuration() {
            return this.zzaTU;
        }

        public long getDuration(TimeUnit timeUnit) {
            return timeUnit.convert(this.zzaTU, TimeUnit.NANOSECONDS);
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.versionCode;
        }

        public int hashCode() {
            return (int) this.zzaTU;
        }

        public String toString() {
            return zzaa.zzv(this).zzg(MyFitnessPalConstants.MFP_DURATION_KEY, Long.valueOf(this.zzaTU)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzo.zza(this, parcel, i);
        }
    }

    public static class FrequencyObjective extends zza {
        public static final Parcelable.Creator<FrequencyObjective> CREATOR = new zzq();
        private final int frequency;
        private final int versionCode;

        public FrequencyObjective(int i) {
            this(1, i);
        }

        FrequencyObjective(int i, int i2) {
            this.versionCode = i;
            this.frequency = i2;
        }

        private boolean zza(FrequencyObjective frequencyObjective) {
            return this.frequency == frequencyObjective.frequency;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof FrequencyObjective) && zza((FrequencyObjective) obj));
        }

        public int getFrequency() {
            return this.frequency;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.versionCode;
        }

        public int hashCode() {
            return this.frequency;
        }

        public String toString() {
            return zzaa.zzv(this).zzg("frequency", Integer.valueOf(this.frequency)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzq.zza(this, parcel, i);
        }
    }

    public static class MetricObjective extends zza {
        public static final Parcelable.Creator<MetricObjective> CREATOR = new zzu();
        private final double value;
        private final int versionCode;
        private final String zzaTV;
        private final double zzaTW;

        MetricObjective(int i, String str, double d, double d2) {
            this.versionCode = i;
            this.zzaTV = str;
            this.value = d;
            this.zzaTW = d2;
        }

        public MetricObjective(String str, double d) {
            this(1, str, d, 0.0d);
        }

        private boolean zza(MetricObjective metricObjective) {
            return zzaa.equal(this.zzaTV, metricObjective.zzaTV) && this.value == metricObjective.value && this.zzaTW == metricObjective.zzaTW;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof MetricObjective) && zza((MetricObjective) obj));
        }

        public String getDataTypeName() {
            return this.zzaTV;
        }

        public double getValue() {
            return this.value;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.versionCode;
        }

        public int hashCode() {
            return this.zzaTV.hashCode();
        }

        public String toString() {
            return zzaa.zzv(this).zzg("dataTypeName", this.zzaTV).zzg(Award.VALUE, Double.valueOf(this.value)).zzg("initialValue", Double.valueOf(this.zzaTW)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzu.zza(this, parcel, i);
        }

        public double zzCv() {
            return this.zzaTW;
        }
    }

    public static class MismatchedGoalException extends IllegalStateException {
        public MismatchedGoalException(String str) {
            super(str);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ObjectiveType {
    }

    public static class Recurrence extends zza {
        public static final Parcelable.Creator<Recurrence> CREATOR = new zzy();
        public static final int UNIT_DAY = 1;
        public static final int UNIT_MONTH = 3;
        public static final int UNIT_WEEK = 2;
        private final int count;
        private final int versionCode;
        /* access modifiers changed from: private */
        public final int zzaTX;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RecurrenceUnit {
        }

        public Recurrence(int i, int i2) {
            this(1, i, i2);
        }

        Recurrence(int i, int i2, int i3) {
            this.versionCode = i;
            this.count = i2;
            zzac.zzaw(i3 > 0 && i3 <= 3);
            this.zzaTX = i3;
        }

        private boolean zza(Recurrence recurrence) {
            return this.count == recurrence.count && this.zzaTX == recurrence.zzaTX;
        }

        private static String zzgN(int i) {
            switch (i) {
                case 1:
                    return "day";
                case 2:
                    return "week";
                case 3:
                    return "month";
                default:
                    throw new IllegalArgumentException("invalid unit value");
            }
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Recurrence) && zza((Recurrence) obj));
        }

        public int getCount() {
            return this.count;
        }

        public int getUnit() {
            return this.zzaTX;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.versionCode;
        }

        public int hashCode() {
            return this.zzaTX;
        }

        public String toString() {
            return zzaa.zzv(this).zzg("count", Integer.valueOf(this.count)).zzg("unit", zzgN(this.zzaTX)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzy.zza(this, parcel, i);
        }
    }

    Goal(int i, long j, long j2, List<Integer> list, Recurrence recurrence, int i2, MetricObjective metricObjective, DurationObjective durationObjective, FrequencyObjective frequencyObjective) {
        this.versionCode = i;
        this.zzaTM = j;
        this.zzaTN = j2;
        this.zzaTO = list == null ? Collections.emptyList() : list;
        this.zzaTP = recurrence;
        this.zzaTQ = i2;
        this.zzaTR = metricObjective;
        this.zzaTS = durationObjective;
        this.zzaTT = frequencyObjective;
    }

    private boolean zza(Goal goal) {
        return this.zzaTM == goal.zzaTM && this.zzaTN == goal.zzaTN && zzaa.equal(this.zzaTO, goal.zzaTO) && zzaa.equal(this.zzaTP, goal.zzaTP) && this.zzaTQ == goal.zzaTQ && zzaa.equal(this.zzaTR, goal.zzaTR) && zzaa.equal(this.zzaTS, goal.zzaTS) && zzaa.equal(this.zzaTT, goal.zzaTT);
    }

    private static String zzgL(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "metric";
            case 2:
                return MyFitnessPalConstants.MFP_DURATION_KEY;
            case 3:
                return "frequency";
            default:
                throw new IllegalArgumentException("invalid objective type value");
        }
    }

    private void zzgM(int i) throws MismatchedGoalException {
        if (i != this.zzaTQ) {
            throw new MismatchedGoalException(String.format("%s goal does not have %s objective", new Object[]{zzgL(this.zzaTQ), zzgL(i)}));
        }
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof Goal) && zza((Goal) obj));
    }

    @Nullable
    public String getActivityName() {
        if (this.zzaTO.isEmpty() || this.zzaTO.size() > 1) {
            return null;
        }
        return com.google.android.gms.fitness.zza.getName(this.zzaTO.get(0).intValue());
    }

    public long getCreateTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaTM, TimeUnit.NANOSECONDS);
    }

    public DurationObjective getDurationObjective() {
        zzgM(2);
        return this.zzaTS;
    }

    public long getEndTime(Calendar calendar, TimeUnit timeUnit) {
        if (this.zzaTP == null) {
            return timeUnit.convert(this.zzaTN, TimeUnit.NANOSECONDS);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        switch (this.zzaTP.zzaTX) {
            case 1:
                instance.add(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 2:
                instance.add(4, 1);
                instance.set(7, 2);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 3:
                instance.add(2, 1);
                instance.set(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Invalid unit ").append(this.zzaTP.zzaTX).toString());
        }
    }

    public FrequencyObjective getFrequencyObjective() {
        zzgM(3);
        return this.zzaTT;
    }

    public MetricObjective getMetricObjective() {
        zzgM(1);
        return this.zzaTR;
    }

    public int getObjectiveType() {
        return this.zzaTQ;
    }

    public Recurrence getRecurrence() {
        return this.zzaTP;
    }

    public long getStartTime(Calendar calendar, TimeUnit timeUnit) {
        if (this.zzaTP == null) {
            return timeUnit.convert(this.zzaTM, TimeUnit.NANOSECONDS);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        switch (this.zzaTP.zzaTX) {
            case 1:
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 2:
                instance.set(7, 2);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 3:
                instance.set(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Invalid unit ").append(this.zzaTP.zzaTX).toString());
        }
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return this.zzaTQ;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("activity", getActivityName()).zzg("recurrence", this.zzaTP).zzg("metricObjective", this.zzaTR).zzg("durationObjective", this.zzaTS).zzg("frequencyObjective", this.zzaTT).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzr.zza(this, parcel, i);
    }

    public long zzCp() {
        return this.zzaTM;
    }

    public long zzCq() {
        return this.zzaTN;
    }

    public List<Integer> zzCr() {
        return this.zzaTO;
    }

    public MetricObjective zzCs() {
        return this.zzaTR;
    }

    public DurationObjective zzCt() {
        return this.zzaTS;
    }

    public FrequencyObjective zzCu() {
        return this.zzaTT;
    }
}
