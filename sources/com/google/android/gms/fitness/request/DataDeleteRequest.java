package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzapf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataDeleteRequest extends zza {
    public static final Parcelable.Creator<DataDeleteRequest> CREATOR = new zzf();
    private final List<DataType> zzaSs;
    private final long zzaSt;
    private final zzapf zzaVt;
    private final List<DataSource> zzaVw;
    private final List<Session> zzaVx;
    private final boolean zzaVy;
    private final boolean zzaVz;
    private final long zzafe;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public List<DataType> zzaSs = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaSt;
        /* access modifiers changed from: private */
        public List<DataSource> zzaVw = new ArrayList();
        /* access modifiers changed from: private */
        public List<Session> zzaVx = new ArrayList();
        /* access modifiers changed from: private */
        public boolean zzaVy = false;
        /* access modifiers changed from: private */
        public boolean zzaVz = false;
        /* access modifiers changed from: private */
        public long zzafe;

        private void zzCP() {
            if (!this.zzaVx.isEmpty()) {
                for (Session next : this.zzaVx) {
                    zzac.zza(next.getStartTime(TimeUnit.MILLISECONDS) >= this.zzafe && next.getEndTime(TimeUnit.MILLISECONDS) <= this.zzaSt, "Session %s is outside the time interval [%d, %d]", next, Long.valueOf(this.zzafe), Long.valueOf(this.zzaSt));
                }
            }
        }

        public Builder addDataSource(DataSource dataSource) {
            boolean z = true;
            zzac.zzb(!this.zzaVy, (Object) "All data is already marked for deletion.  addDataSource() cannot be combined with deleteAllData()");
            if (dataSource == null) {
                z = false;
            }
            zzac.zzb(z, (Object) "Must specify a valid data source");
            if (!this.zzaVw.contains(dataSource)) {
                this.zzaVw.add(dataSource);
            }
            return this;
        }

        public Builder addDataType(DataType dataType) {
            boolean z = true;
            zzac.zzb(!this.zzaVy, (Object) "All data is already marked for deletion.  addDataType() cannot be combined with deleteAllData()");
            if (dataType == null) {
                z = false;
            }
            zzac.zzb(z, (Object) "Must specify a valid data type");
            if (!this.zzaSs.contains(dataType)) {
                this.zzaSs.add(dataType);
            }
            return this;
        }

        public Builder addSession(Session session) {
            boolean z = true;
            zzac.zzb(!this.zzaVz, (Object) "All sessions already marked for deletion.  addSession() cannot be combined with deleteAllSessions()");
            zzac.zzb(session != null, (Object) "Must specify a valid session");
            if (session.getEndTime(TimeUnit.MILLISECONDS) <= 0) {
                z = false;
            }
            zzac.zzb(z, (Object) "Cannot delete an ongoing session. Please stop the session prior to deleting it");
            this.zzaVx.add(session);
            return this;
        }

        public DataDeleteRequest build() {
            boolean z = false;
            zzac.zza(this.zzafe > 0 && this.zzaSt > this.zzafe, (Object) "Must specify a valid time interval");
            boolean z2 = this.zzaVy || !this.zzaVw.isEmpty() || !this.zzaSs.isEmpty();
            boolean z3 = this.zzaVz || !this.zzaVx.isEmpty();
            if (z2 || z3) {
                z = true;
            }
            zzac.zza(z, (Object) "No data or session marked for deletion");
            zzCP();
            return new DataDeleteRequest(this);
        }

        public Builder deleteAllData() {
            zzac.zzb(this.zzaSs.isEmpty(), (Object) "Specific data type already added for deletion. deleteAllData() will delete all data types and cannot be combined with addDataType()");
            zzac.zzb(this.zzaVw.isEmpty(), (Object) "Specific data source already added for deletion. deleteAllData() will delete all data sources and cannot be combined with addDataSource()");
            this.zzaVy = true;
            return this;
        }

        public Builder deleteAllSessions() {
            zzac.zzb(this.zzaVx.isEmpty(), (Object) "Specific session already added for deletion. deleteAllData() will delete all sessions and cannot be combined with addSession()");
            this.zzaVz = true;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            zzac.zzb(j > 0, "Invalid start time :%d", Long.valueOf(j));
            zzac.zzb(j2 > j, "Invalid end time :%d", Long.valueOf(j2));
            this.zzafe = timeUnit.toMillis(j);
            this.zzaSt = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataDeleteRequest(int i, long j, long j2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean z, boolean z2, IBinder iBinder) {
        this.zzaiI = i;
        this.zzafe = j;
        this.zzaSt = j2;
        this.zzaVw = Collections.unmodifiableList(list);
        this.zzaSs = Collections.unmodifiableList(list2);
        this.zzaVx = list3;
        this.zzaVy = z;
        this.zzaVz = z2;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public DataDeleteRequest(long j, long j2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean z, boolean z2, zzapf zzapf) {
        this.zzaiI = 3;
        this.zzafe = j;
        this.zzaSt = j2;
        this.zzaVw = Collections.unmodifiableList(list);
        this.zzaSs = Collections.unmodifiableList(list2);
        this.zzaVx = list3;
        this.zzaVy = z;
        this.zzaVz = z2;
        this.zzaVt = zzapf;
    }

    private DataDeleteRequest(Builder builder) {
        this(builder.zzafe, builder.zzaSt, builder.zzaVw, builder.zzaSs, builder.zzaVx, builder.zzaVy, builder.zzaVz, (zzapf) null);
    }

    public DataDeleteRequest(DataDeleteRequest dataDeleteRequest, zzapf zzapf) {
        this(dataDeleteRequest.zzafe, dataDeleteRequest.zzaSt, dataDeleteRequest.zzaVw, dataDeleteRequest.zzaSs, dataDeleteRequest.zzaVx, dataDeleteRequest.zzaVy, dataDeleteRequest.zzaVz, zzapf);
    }

    private boolean zzb(DataDeleteRequest dataDeleteRequest) {
        return this.zzafe == dataDeleteRequest.zzafe && this.zzaSt == dataDeleteRequest.zzaSt && zzaa.equal(this.zzaVw, dataDeleteRequest.zzaVw) && zzaa.equal(this.zzaSs, dataDeleteRequest.zzaSs) && zzaa.equal(this.zzaVx, dataDeleteRequest.zzaVx) && this.zzaVy == dataDeleteRequest.zzaVy && this.zzaVz == dataDeleteRequest.zzaVz;
    }

    public boolean deleteAllData() {
        return this.zzaVy;
    }

    public boolean deleteAllSessions() {
        return this.zzaVz;
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof DataDeleteRequest) && zzb((DataDeleteRequest) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public List<DataSource> getDataSources() {
        return this.zzaVw;
    }

    public List<DataType> getDataTypes() {
        return this.zzaSs;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaSt, TimeUnit.MILLISECONDS);
    }

    public List<Session> getSessions() {
        return this.zzaVx;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzafe, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(Long.valueOf(this.zzafe), Long.valueOf(this.zzaSt));
    }

    public String toString() {
        return zzaa.zzv(this).zzg("startTimeMillis", Long.valueOf(this.zzafe)).zzg("endTimeMillis", Long.valueOf(this.zzaSt)).zzg("dataSources", this.zzaVw).zzg("dateTypes", this.zzaSs).zzg("sessions", this.zzaVx).zzg("deleteAllData", Boolean.valueOf(this.zzaVy)).zzg("deleteAllSessions", Boolean.valueOf(this.zzaVz)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzf.zza(this, parcel, i);
    }

    public long zzAl() {
        return this.zzaSt;
    }

    public boolean zzCN() {
        return this.zzaVy;
    }

    public boolean zzCO() {
        return this.zzaVz;
    }

    public long zzqn() {
        return this.zzafe;
    }
}
