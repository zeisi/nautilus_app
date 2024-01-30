package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.internal.zzaom;
import com.nautilus.omni.util.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataReadRequest extends zza {
    public static final Parcelable.Creator<DataReadRequest> CREATOR = new zzi();
    public static final int NO_LIMIT = 0;
    private final List<DataType> zzaSs;
    private final long zzaSt;
    private final int zzaSw;
    private final List<DataType> zzaVB;
    private final List<DataSource> zzaVC;
    private final long zzaVD;
    private final DataSource zzaVE;
    private final int zzaVF;
    private final boolean zzaVG;
    private final boolean zzaVH;
    private final zzaom zzaVI;
    private final List<Device> zzaVJ;
    private final List<Integer> zzaVK;
    private final List<DataSource> zzaVw;
    private final long zzafe;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public List<DataType> zzaSs = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaSt;
        /* access modifiers changed from: private */
        public int zzaSw = 0;
        /* access modifiers changed from: private */
        public List<DataType> zzaVB = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataSource> zzaVC = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaVD = 0;
        /* access modifiers changed from: private */
        public DataSource zzaVE;
        /* access modifiers changed from: private */
        public int zzaVF = 0;
        private boolean zzaVG = false;
        /* access modifiers changed from: private */
        public boolean zzaVH = false;
        /* access modifiers changed from: private */
        public final List<Device> zzaVJ = new ArrayList();
        /* access modifiers changed from: private */
        public final List<Integer> zzaVK = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataSource> zzaVw = new ArrayList();
        /* access modifiers changed from: private */
        public long zzafe;

        public Builder addFilteredDataQualityStandard(int i) {
            zzac.zzb(this.zzaVJ.isEmpty(), (Object) "Cannot add data quality standard filter when filtering by device.");
            this.zzaVK.add(Integer.valueOf(i));
            return this;
        }

        public Builder aggregate(DataSource dataSource, DataType dataType) {
            zzac.zzb(dataSource, (Object) "Attempting to add a null data source");
            zzac.zza(!this.zzaVw.contains(dataSource), (Object) "Cannot add the same data source for aggregated and detailed");
            DataType dataType2 = dataSource.getDataType();
            List<DataType> aggregatesForInput = DataType.getAggregatesForInput(dataType2);
            zzac.zzb(!aggregatesForInput.isEmpty(), "Unsupported input data type specified for aggregation: %s", dataType2);
            zzac.zzb(aggregatesForInput.contains(dataType), "Invalid output aggregate data type specified: %s -> %s", dataType2, dataType);
            if (!this.zzaVC.contains(dataSource)) {
                this.zzaVC.add(dataSource);
            }
            return this;
        }

        public Builder aggregate(DataType dataType, DataType dataType2) {
            zzac.zzb(dataType, (Object) "Attempting to use a null data type");
            zzac.zza(!this.zzaSs.contains(dataType), (Object) "Cannot add the same data type as aggregated and detailed");
            List<DataType> aggregatesForInput = DataType.getAggregatesForInput(dataType);
            zzac.zzb(!aggregatesForInput.isEmpty(), "Unsupported input data type specified for aggregation: %s", dataType);
            zzac.zzb(aggregatesForInput.contains(dataType2), "Invalid output aggregate data type specified: %s -> %s", dataType, dataType2);
            if (!this.zzaVB.contains(dataType)) {
                this.zzaVB.add(dataType);
            }
            return this;
        }

        public Builder bucketByActivitySegment(int i, TimeUnit timeUnit) {
            zzac.zzb(this.zzaSw == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaSw));
            zzac.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaSw = 4;
            this.zzaVD = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivitySegment(int i, TimeUnit timeUnit, DataSource dataSource) {
            zzac.zzb(this.zzaSw == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaSw));
            zzac.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            zzac.zzb(dataSource != null, (Object) "Invalid activity data source specified");
            zzac.zzb(dataSource.getDataType().equals(DataType.TYPE_ACTIVITY_SEGMENT), "Invalid activity data source specified: %s", dataSource);
            this.zzaVE = dataSource;
            this.zzaSw = 4;
            this.zzaVD = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivityType(int i, TimeUnit timeUnit) {
            zzac.zzb(this.zzaSw == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaSw));
            zzac.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaSw = 3;
            this.zzaVD = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivityType(int i, TimeUnit timeUnit, DataSource dataSource) {
            zzac.zzb(this.zzaSw == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaSw));
            zzac.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            zzac.zzb(dataSource != null, (Object) "Invalid activity data source specified");
            zzac.zzb(dataSource.getDataType().equals(DataType.TYPE_ACTIVITY_SEGMENT), "Invalid activity data source specified: %s", dataSource);
            this.zzaVE = dataSource;
            this.zzaSw = 3;
            this.zzaVD = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketBySession(int i, TimeUnit timeUnit) {
            zzac.zzb(this.zzaSw == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaSw));
            zzac.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaSw = 2;
            this.zzaVD = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByTime(int i, TimeUnit timeUnit) {
            zzac.zzb(this.zzaSw == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaSw));
            zzac.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaSw = 1;
            this.zzaVD = timeUnit.toMillis((long) i);
            return this;
        }

        public DataReadRequest build() {
            boolean z = true;
            zzac.zza(!this.zzaVw.isEmpty() || !this.zzaSs.isEmpty() || !this.zzaVC.isEmpty() || !this.zzaVB.isEmpty(), (Object) "Must add at least one data source (aggregated or detailed)");
            zzac.zza(this.zzafe > 0, "Invalid start time: %s", Long.valueOf(this.zzafe));
            zzac.zza(this.zzaSt > 0 && this.zzaSt > this.zzafe, "Invalid end time: %s", Long.valueOf(this.zzaSt));
            boolean z2 = this.zzaVC.isEmpty() && this.zzaVB.isEmpty();
            if ((!z2 || this.zzaSw != 0) && (z2 || this.zzaSw == 0)) {
                z = false;
            }
            zzac.zza(z, (Object) "Must specify a valid bucketing strategy while requesting aggregation");
            return new DataReadRequest(this);
        }

        public Builder enableServerQueries() {
            this.zzaVH = true;
            return this;
        }

        public Builder read(DataSource dataSource) {
            zzac.zzb(dataSource, (Object) "Attempting to add a null data source");
            zzac.zzb(!this.zzaVC.contains(dataSource), (Object) "Cannot add the same data source as aggregated and detailed");
            if (!this.zzaVw.contains(dataSource)) {
                this.zzaVw.add(dataSource);
            }
            return this;
        }

        public Builder read(DataType dataType) {
            zzac.zzb(dataType, (Object) "Attempting to use a null data type");
            zzac.zza(!this.zzaVB.contains(dataType), (Object) "Cannot add the same data type as aggregated and detailed");
            if (!this.zzaSs.contains(dataType)) {
                this.zzaSs.add(dataType);
            }
            return this;
        }

        public Builder setLimit(int i) {
            zzac.zzb(i > 0, "Invalid limit %d is specified", Integer.valueOf(i));
            this.zzaVF = i;
            return this;
        }

        public Builder setTimeRange(long j, long j2, TimeUnit timeUnit) {
            this.zzafe = timeUnit.toMillis(j);
            this.zzaSt = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataReadRequest(int i, List<DataType> list, List<DataSource> list2, long j, long j2, List<DataType> list3, List<DataSource> list4, int i2, long j3, DataSource dataSource, int i3, boolean z, boolean z2, IBinder iBinder, List<Device> list5, List<Integer> list6) {
        this.zzaiI = i;
        this.zzaSs = list;
        this.zzaVw = list2;
        this.zzafe = j;
        this.zzaSt = j2;
        this.zzaVB = list3;
        this.zzaVC = list4;
        this.zzaSw = i2;
        this.zzaVD = j3;
        this.zzaVE = dataSource;
        this.zzaVF = i3;
        this.zzaVG = z;
        this.zzaVH = z2;
        this.zzaVI = iBinder == null ? null : zzaom.zza.zzcq(iBinder);
        this.zzaVJ = list5 == null ? Collections.emptyList() : list5;
        this.zzaVK = list6 == null ? Collections.emptyList() : list6;
    }

    private DataReadRequest(Builder builder) {
        this(builder.zzaSs, builder.zzaVw, builder.zzafe, builder.zzaSt, builder.zzaVB, builder.zzaVC, builder.zzaSw, builder.zzaVD, builder.zzaVE, builder.zzaVF, false, builder.zzaVH, (zzaom) null, builder.zzaVJ, builder.zzaVK);
    }

    public DataReadRequest(DataReadRequest dataReadRequest, zzaom zzaom) {
        this(dataReadRequest.zzaSs, dataReadRequest.zzaVw, dataReadRequest.zzafe, dataReadRequest.zzaSt, dataReadRequest.zzaVB, dataReadRequest.zzaVC, dataReadRequest.zzaSw, dataReadRequest.zzaVD, dataReadRequest.zzaVE, dataReadRequest.zzaVF, dataReadRequest.zzaVG, dataReadRequest.zzaVH, zzaom, dataReadRequest.zzaVJ, dataReadRequest.zzaVK);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DataReadRequest(List<DataType> list, List<DataSource> list2, long j, long j2, List<DataType> list3, List<DataSource> list4, int i, long j3, DataSource dataSource, int i2, boolean z, boolean z2, zzaom zzaom, List<Device> list5, List<Integer> list6) {
        this(6, list, list2, j, j2, list3, list4, i, j3, dataSource, i2, z, z2, zzaom == null ? null : zzaom.asBinder(), list5, list6);
    }

    private boolean zzb(DataReadRequest dataReadRequest) {
        return this.zzaSs.equals(dataReadRequest.zzaSs) && this.zzaVw.equals(dataReadRequest.zzaVw) && this.zzafe == dataReadRequest.zzafe && this.zzaSt == dataReadRequest.zzaSt && this.zzaSw == dataReadRequest.zzaSw && this.zzaVC.equals(dataReadRequest.zzaVC) && this.zzaVB.equals(dataReadRequest.zzaVB) && zzaa.equal(this.zzaVE, dataReadRequest.zzaVE) && this.zzaVD == dataReadRequest.zzaVD && this.zzaVH == dataReadRequest.zzaVH && this.zzaVF == dataReadRequest.zzaVF && this.zzaVG == dataReadRequest.zzaVG && zzaa.equal(this.zzaVI, dataReadRequest.zzaVI) && zzaa.equal(this.zzaVJ, dataReadRequest.zzaVJ) && zzaa.equal(this.zzaVK, dataReadRequest.zzaVK);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof DataReadRequest) && zzb((DataReadRequest) obj));
    }

    public DataSource getActivityDataSource() {
        return this.zzaVE;
    }

    public List<DataSource> getAggregatedDataSources() {
        return this.zzaVC;
    }

    public List<DataType> getAggregatedDataTypes() {
        return this.zzaVB;
    }

    public long getBucketDuration(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaVD, TimeUnit.MILLISECONDS);
    }

    public int getBucketType() {
        return this.zzaSw;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVI == null) {
            return null;
        }
        return this.zzaVI.asBinder();
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

    public List<Integer> getFilteredDataQualityStandards() {
        return this.zzaVK;
    }

    public int getLimit() {
        return this.zzaVF;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzafe, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(Integer.valueOf(this.zzaSw), Long.valueOf(this.zzafe), Long.valueOf(this.zzaSt));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataReadRequest{");
        if (!this.zzaSs.isEmpty()) {
            for (DataType zzCj : this.zzaSs) {
                sb.append(zzCj.zzCj()).append(Constants.EMPTY_SPACE);
            }
        }
        if (!this.zzaVw.isEmpty()) {
            for (DataSource debugString : this.zzaVw) {
                sb.append(debugString.toDebugString()).append(Constants.EMPTY_SPACE);
            }
        }
        if (this.zzaSw != 0) {
            sb.append("bucket by ").append(Bucket.zzgw(this.zzaSw));
            if (this.zzaVD > 0) {
                sb.append(" >").append(this.zzaVD).append("ms");
            }
            sb.append(": ");
        }
        if (!this.zzaVB.isEmpty()) {
            for (DataType zzCj2 : this.zzaVB) {
                sb.append(zzCj2.zzCj()).append(Constants.EMPTY_SPACE);
            }
        }
        if (!this.zzaVC.isEmpty()) {
            for (DataSource debugString2 : this.zzaVC) {
                sb.append(debugString2.toDebugString()).append(Constants.EMPTY_SPACE);
            }
        }
        sb.append(String.format("(%tF %tT - %tF %tT)", new Object[]{Long.valueOf(this.zzafe), Long.valueOf(this.zzafe), Long.valueOf(this.zzaSt), Long.valueOf(this.zzaSt)}));
        if (this.zzaVE != null) {
            sb.append("activities: ").append(this.zzaVE.toDebugString());
        }
        if (!this.zzaVK.isEmpty()) {
            sb.append("quality: ");
            for (Integer intValue : this.zzaVK) {
                sb.append(DataSource.zzgD(intValue.intValue())).append(Constants.EMPTY_SPACE);
            }
        }
        if (this.zzaVH) {
            sb.append(" +server");
        }
        sb.append("}");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }

    public long zzAl() {
        return this.zzaSt;
    }

    public boolean zzCR() {
        return this.zzaVH;
    }

    public boolean zzCS() {
        return this.zzaVG;
    }

    public long zzCT() {
        return this.zzaVD;
    }

    public List<Device> zzCU() {
        return this.zzaVJ;
    }

    public long zzqn() {
        return this.zzafe;
    }
}
