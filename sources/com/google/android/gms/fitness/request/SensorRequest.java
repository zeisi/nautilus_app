package com.google.android.gms.fitness.request;

import android.os.SystemClock;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzanx;
import com.google.android.gms.location.LocationRequest;
import java.util.concurrent.TimeUnit;

public class SensorRequest {
    public static final int ACCURACY_MODE_DEFAULT = 2;
    public static final int ACCURACY_MODE_HIGH = 3;
    public static final int ACCURACY_MODE_LOW = 1;
    private final DataSource zzaTi;
    private final DataType zzaTj;
    private final long zzaUo;
    private final int zzaUp;
    private final long zzaWr;
    private final long zzaWs;
    private final LocationRequest zzaWw;
    private final long zzaWx;

    public static class Builder {
        /* access modifiers changed from: private */
        public DataSource zzaTi;
        /* access modifiers changed from: private */
        public DataType zzaTj;
        /* access modifiers changed from: private */
        public long zzaUo = -1;
        /* access modifiers changed from: private */
        public int zzaUp = 2;
        /* access modifiers changed from: private */
        public long zzaWr = 0;
        /* access modifiers changed from: private */
        public long zzaWs = 0;
        /* access modifiers changed from: private */
        public long zzaWx = Long.MAX_VALUE;
        private boolean zzaWy = false;

        public SensorRequest build() {
            boolean z = false;
            zzac.zza((this.zzaTi == null && this.zzaTj == null) ? false : true, (Object) "Must call setDataSource() or setDataType()");
            if (this.zzaTj == null || this.zzaTi == null || this.zzaTj.equals(this.zzaTi.getDataType())) {
                z = true;
            }
            zzac.zza(z, (Object) "Specified data type is incompatible with specified data source");
            return new SensorRequest(this);
        }

        public Builder setAccuracyMode(int i) {
            this.zzaUp = zzanx.zzhc(i);
            return this;
        }

        public Builder setDataSource(DataSource dataSource) {
            this.zzaTi = dataSource;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            this.zzaTj = dataType;
            return this;
        }

        public Builder setFastestRate(int i, TimeUnit timeUnit) {
            zzac.zzb(i >= 0, (Object) "Cannot use a negative interval");
            this.zzaWy = true;
            this.zzaWs = timeUnit.toMicros((long) i);
            return this;
        }

        public Builder setMaxDeliveryLatency(int i, TimeUnit timeUnit) {
            zzac.zzb(i >= 0, (Object) "Cannot use a negative delivery interval");
            this.zzaWr = timeUnit.toMicros((long) i);
            return this;
        }

        public Builder setSamplingRate(long j, TimeUnit timeUnit) {
            zzac.zzb(j >= 0, (Object) "Cannot use a negative sampling interval");
            this.zzaUo = timeUnit.toMicros(j);
            if (!this.zzaWy) {
                this.zzaWs = this.zzaUo / 2;
            }
            return this;
        }

        public Builder setTimeout(long j, TimeUnit timeUnit) {
            boolean z = true;
            zzac.zzb(j > 0, "Invalid time out value specified: %d", Long.valueOf(j));
            if (timeUnit == null) {
                z = false;
            }
            zzac.zzb(z, (Object) "Invalid time unit specified");
            this.zzaWx = timeUnit.toMicros(j);
            return this;
        }
    }

    private SensorRequest(DataSource dataSource, LocationRequest locationRequest) {
        this.zzaWw = locationRequest;
        this.zzaUo = TimeUnit.MILLISECONDS.toMicros(locationRequest.getInterval());
        this.zzaWs = TimeUnit.MILLISECONDS.toMicros(locationRequest.getFastestInterval());
        this.zzaWr = this.zzaUo;
        this.zzaTj = dataSource.getDataType();
        this.zzaUp = zza(locationRequest);
        this.zzaTi = dataSource;
        long expirationTime = locationRequest.getExpirationTime();
        if (expirationTime == Long.MAX_VALUE) {
            this.zzaWx = Long.MAX_VALUE;
        } else {
            this.zzaWx = TimeUnit.MILLISECONDS.toMicros(expirationTime - SystemClock.elapsedRealtime());
        }
    }

    private SensorRequest(Builder builder) {
        this.zzaTi = builder.zzaTi;
        this.zzaTj = builder.zzaTj;
        this.zzaUo = builder.zzaUo;
        this.zzaWs = builder.zzaWs;
        this.zzaWr = builder.zzaWr;
        this.zzaUp = builder.zzaUp;
        this.zzaWw = null;
        this.zzaWx = builder.zzaWx;
    }

    public static SensorRequest fromLocationRequest(DataSource dataSource, LocationRequest locationRequest) {
        return new SensorRequest(dataSource, locationRequest);
    }

    private static int zza(LocationRequest locationRequest) {
        switch (locationRequest.getPriority()) {
            case 100:
                return 3;
            case 104:
                return 1;
            default:
                return 2;
        }
    }

    private boolean zza(SensorRequest sensorRequest) {
        return zzaa.equal(this.zzaTi, sensorRequest.zzaTi) && zzaa.equal(this.zzaTj, sensorRequest.zzaTj) && this.zzaUo == sensorRequest.zzaUo && this.zzaWs == sensorRequest.zzaWs && this.zzaWr == sensorRequest.zzaWr && this.zzaUp == sensorRequest.zzaUp && zzaa.equal(this.zzaWw, sensorRequest.zzaWw) && this.zzaWx == sensorRequest.zzaWx;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof SensorRequest) && zza((SensorRequest) obj));
    }

    public int getAccuracyMode() {
        return this.zzaUp;
    }

    public DataSource getDataSource() {
        return this.zzaTi;
    }

    public DataType getDataType() {
        return this.zzaTj;
    }

    public long getFastestRate(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaWs, TimeUnit.MICROSECONDS);
    }

    public long getMaxDeliveryLatency(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaWr, TimeUnit.MICROSECONDS);
    }

    public long getSamplingRate(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaUo, TimeUnit.MICROSECONDS);
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaTi, this.zzaTj, Long.valueOf(this.zzaUo), Long.valueOf(this.zzaWs), Long.valueOf(this.zzaWr), Integer.valueOf(this.zzaUp), this.zzaWw, Long.valueOf(this.zzaWx));
    }

    public String toString() {
        return zzaa.zzv(this).zzg("dataSource", this.zzaTi).zzg("dataType", this.zzaTj).zzg("samplingRateMicros", Long.valueOf(this.zzaUo)).zzg("deliveryLatencyMicros", Long.valueOf(this.zzaWr)).zzg("timeOutMicros", Long.valueOf(this.zzaWx)).toString();
    }

    public long zzDh() {
        return this.zzaWx;
    }
}
