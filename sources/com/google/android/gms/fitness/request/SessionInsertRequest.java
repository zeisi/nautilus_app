package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzant;
import com.google.android.gms.internal.zzapf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionInsertRequest extends zza {
    public static final Parcelable.Creator<SessionInsertRequest> CREATOR = new zzaz();
    private final int versionCode;
    private final List<DataSet> zzaWA;
    private final List<DataPoint> zzaWB;
    private final zzapf zzaWh;
    private final Session zzaWz;

    public static class Builder {
        /* access modifiers changed from: private */
        public List<DataSet> zzaWA = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataPoint> zzaWB = new ArrayList();
        private List<DataSource> zzaWC = new ArrayList();
        /* access modifiers changed from: private */
        public Session zzaWz;

        private void zzDi() {
            for (DataSet dataPoints : this.zzaWA) {
                for (DataPoint zzg : dataPoints.getDataPoints()) {
                    zzg(zzg);
                }
            }
            for (DataPoint zzg2 : this.zzaWB) {
                zzg(zzg2);
            }
        }

        private void zzg(DataPoint dataPoint) {
            zzi(dataPoint);
            zzh(dataPoint);
        }

        private void zzh(DataPoint dataPoint) {
            long startTime = this.zzaWz.getStartTime(TimeUnit.NANOSECONDS);
            long endTime = this.zzaWz.getEndTime(TimeUnit.NANOSECONDS);
            long startTime2 = dataPoint.getStartTime(TimeUnit.NANOSECONDS);
            long endTime2 = dataPoint.getEndTime(TimeUnit.NANOSECONDS);
            if (startTime2 != 0 && endTime2 != 0) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                if (endTime2 > endTime) {
                    endTime2 = zzant.zza(endTime2, TimeUnit.NANOSECONDS, timeUnit);
                }
                zzac.zza(startTime2 >= startTime && endTime2 <= endTime, "Data point %s has start and end times outside session interval [%d, %d]", dataPoint, Long.valueOf(startTime), Long.valueOf(endTime));
                if (endTime2 != dataPoint.getEndTime(TimeUnit.NANOSECONDS)) {
                    Log.w("Fitness", String.format("Data point end time [%d] is truncated to [%d] to match the precision [%s] of the session start and end time", new Object[]{Long.valueOf(dataPoint.getEndTime(TimeUnit.NANOSECONDS)), Long.valueOf(endTime2), timeUnit}));
                    dataPoint.setTimeInterval(startTime2, endTime2, TimeUnit.NANOSECONDS);
                }
            }
        }

        private void zzi(DataPoint dataPoint) {
            long startTime = this.zzaWz.getStartTime(TimeUnit.NANOSECONDS);
            long endTime = this.zzaWz.getEndTime(TimeUnit.NANOSECONDS);
            long timestamp = dataPoint.getTimestamp(TimeUnit.NANOSECONDS);
            if (timestamp != 0) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                if (timestamp < startTime || timestamp > endTime) {
                    timestamp = zzant.zza(timestamp, TimeUnit.NANOSECONDS, timeUnit);
                }
                zzac.zza(timestamp >= startTime && timestamp <= endTime, "Data point %s has time stamp outside session interval [%d, %d]", dataPoint, Long.valueOf(startTime), Long.valueOf(endTime));
                if (dataPoint.getTimestamp(TimeUnit.NANOSECONDS) != timestamp) {
                    Log.w("Fitness", String.format("Data point timestamp [%d] is truncated to [%d] to match the precision [%s] of the session start and end time", new Object[]{Long.valueOf(dataPoint.getTimestamp(TimeUnit.NANOSECONDS)), Long.valueOf(timestamp), timeUnit}));
                    dataPoint.setTimestamp(timestamp, TimeUnit.NANOSECONDS);
                }
            }
        }

        public Builder addAggregateDataPoint(DataPoint dataPoint) {
            zzac.zzb(dataPoint != null, (Object) "Must specify a valid aggregate data point.");
            DataSource dataSource = dataPoint.getDataSource();
            zzac.zza(!this.zzaWC.contains(dataSource), "Data set/Aggregate data point for this data source %s is already added.", dataSource);
            DataSet.zze(dataPoint);
            this.zzaWC.add(dataSource);
            this.zzaWB.add(dataPoint);
            return this;
        }

        public Builder addDataSet(DataSet dataSet) {
            boolean z = true;
            zzac.zzb(dataSet != null, (Object) "Must specify a valid data set.");
            DataSource dataSource = dataSet.getDataSource();
            zzac.zza(!this.zzaWC.contains(dataSource), "Data set for this data source %s is already added.", dataSource);
            if (dataSet.getDataPoints().isEmpty()) {
                z = false;
            }
            zzac.zzb(z, (Object) "No data points specified in the input data set.");
            this.zzaWC.add(dataSource);
            this.zzaWA.add(dataSet);
            return this;
        }

        public SessionInsertRequest build() {
            boolean z = true;
            zzac.zza(this.zzaWz != null, (Object) "Must specify a valid session.");
            if (this.zzaWz.getEndTime(TimeUnit.MILLISECONDS) == 0) {
                z = false;
            }
            zzac.zza(z, (Object) "Must specify a valid end time, cannot insert a continuing session.");
            zzDi();
            return new SessionInsertRequest(this);
        }

        public Builder setSession(Session session) {
            this.zzaWz = session;
            return this;
        }
    }

    SessionInsertRequest(int i, Session session, List<DataSet> list, List<DataPoint> list2, IBinder iBinder) {
        this.versionCode = i;
        this.zzaWz = session;
        this.zzaWA = Collections.unmodifiableList(list);
        this.zzaWB = Collections.unmodifiableList(list2);
        this.zzaWh = zzapf.zza.zzcJ(iBinder);
    }

    public SessionInsertRequest(Session session, List<DataSet> list, List<DataPoint> list2, zzapf zzapf) {
        this.versionCode = 3;
        this.zzaWz = session;
        this.zzaWA = Collections.unmodifiableList(list);
        this.zzaWB = Collections.unmodifiableList(list2);
        this.zzaWh = zzapf;
    }

    private SessionInsertRequest(Builder builder) {
        this(builder.zzaWz, builder.zzaWA, builder.zzaWB, (zzapf) null);
    }

    public SessionInsertRequest(SessionInsertRequest sessionInsertRequest, zzapf zzapf) {
        this(sessionInsertRequest.zzaWz, sessionInsertRequest.zzaWA, sessionInsertRequest.zzaWB, zzapf);
    }

    private boolean zzb(SessionInsertRequest sessionInsertRequest) {
        return zzaa.equal(this.zzaWz, sessionInsertRequest.zzaWz) && zzaa.equal(this.zzaWA, sessionInsertRequest.zzaWA) && zzaa.equal(this.zzaWB, sessionInsertRequest.zzaWB);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof SessionInsertRequest) && zzb((SessionInsertRequest) obj));
    }

    public List<DataPoint> getAggregateDataPoints() {
        return this.zzaWB;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWh == null) {
            return null;
        }
        return this.zzaWh.asBinder();
    }

    public List<DataSet> getDataSets() {
        return this.zzaWA;
    }

    public Session getSession() {
        return this.zzaWz;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaWz, this.zzaWA, this.zzaWB);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("session", this.zzaWz).zzg("dataSets", this.zzaWA).zzg("aggregateDataPoints", this.zzaWB).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaz.zza(this, parcel, i);
    }
}
