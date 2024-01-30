package com.google.android.gms.fitness.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.zzs;
import java.util.concurrent.TimeUnit;

public class FitnessSensorServiceRequest extends zza {
    public static final Parcelable.Creator<FitnessSensorServiceRequest> CREATOR = new zza();
    public static final int UNSPECIFIED = -1;
    private final DataSource zzaTi;
    private final zzs zzaWo;
    private final long zzaXi;
    private final long zzaXj;
    private final int zzaiI;

    FitnessSensorServiceRequest(int i, DataSource dataSource, IBinder iBinder, long j, long j2) {
        this.zzaiI = i;
        this.zzaTi = dataSource;
        this.zzaWo = zzs.zza.zzcg(iBinder);
        this.zzaXi = j;
        this.zzaXj = j2;
    }

    private boolean zza(FitnessSensorServiceRequest fitnessSensorServiceRequest) {
        return zzaa.equal(this.zzaTi, fitnessSensorServiceRequest.zzaTi) && this.zzaXi == fitnessSensorServiceRequest.zzaXi && this.zzaXj == fitnessSensorServiceRequest.zzaXj;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof FitnessSensorServiceRequest) && zza((FitnessSensorServiceRequest) obj));
    }

    public long getBatchInterval(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaXj, TimeUnit.MICROSECONDS);
    }

    public DataSource getDataSource() {
        return this.zzaTi;
    }

    public SensorEventDispatcher getDispatcher() {
        return new zzb(this.zzaWo);
    }

    public long getSamplingRate(TimeUnit timeUnit) {
        if (this.zzaXi == -1) {
            return -1;
        }
        return timeUnit.convert(this.zzaXi, TimeUnit.MICROSECONDS);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaTi, Long.valueOf(this.zzaXi), Long.valueOf(this.zzaXj));
    }

    public String toString() {
        return String.format("FitnessSensorServiceRequest{%s}", new Object[]{this.zzaTi});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }

    public long zzCy() {
        return this.zzaXi;
    }

    public long zzDB() {
        return this.zzaXj;
    }

    /* access modifiers changed from: package-private */
    public IBinder zzDg() {
        return this.zzaWo.asBinder();
    }
}
