package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.zzs;
import com.google.android.gms.internal.zzapf;
import com.google.android.gms.internal.zzarw;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class zzav extends zza {
    public static final Parcelable.Creator<zzav> CREATOR = new zzaw();
    private final PendingIntent mPendingIntent;
    private DataSource zzaTi;
    private DataType zzaTj;
    private final long zzaUo;
    private final int zzaUp;
    private final zzapf zzaVt;
    private zzs zzaWo;
    int zzaWp;
    int zzaWq;
    private final long zzaWr;
    private final long zzaWs;
    private final List<LocationRequest> zzaWt;
    private final long zzaWu;
    private final List<zzarw> zzaWv;
    private final int zzaiI;

    zzav(int i, DataSource dataSource, DataType dataType, IBinder iBinder, int i2, int i3, long j, long j2, PendingIntent pendingIntent, long j3, int i4, List<LocationRequest> list, long j4, IBinder iBinder2) {
        this.zzaiI = i;
        this.zzaTi = dataSource;
        this.zzaTj = dataType;
        this.zzaWo = iBinder == null ? null : zzs.zza.zzcg(iBinder);
        this.zzaUo = j == 0 ? (long) i2 : j;
        this.zzaWs = j3;
        this.zzaWr = j2 == 0 ? (long) i3 : j2;
        this.zzaWt = list;
        this.mPendingIntent = pendingIntent;
        this.zzaUp = i4;
        this.zzaWv = Collections.emptyList();
        this.zzaWu = j4;
        this.zzaVt = zzapf.zza.zzcJ(iBinder2);
    }

    public zzav(DataSource dataSource, DataType dataType, zzs zzs, PendingIntent pendingIntent, long j, long j2, long j3, int i, List<LocationRequest> list, List<zzarw> list2, long j4, zzapf zzapf) {
        this.zzaiI = 6;
        this.zzaTi = dataSource;
        this.zzaTj = dataType;
        this.zzaWo = zzs;
        this.mPendingIntent = pendingIntent;
        this.zzaUo = j;
        this.zzaWs = j2;
        this.zzaWr = j3;
        this.zzaUp = i;
        this.zzaWt = list;
        this.zzaWv = list2;
        this.zzaWu = j4;
        this.zzaVt = zzapf;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzav(com.google.android.gms.fitness.request.SensorRequest r21, com.google.android.gms.fitness.data.zzs r22, android.app.PendingIntent r23, com.google.android.gms.internal.zzapf r24) {
        /*
            r20 = this;
            com.google.android.gms.fitness.data.DataSource r4 = r21.getDataSource()
            com.google.android.gms.fitness.data.DataType r5 = r21.getDataType()
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MICROSECONDS
            r0 = r21
            long r8 = r0.getSamplingRate(r2)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MICROSECONDS
            r0 = r21
            long r10 = r0.getFastestRate(r2)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MICROSECONDS
            r0 = r21
            long r12 = r0.getMaxDeliveryLatency(r2)
            int r14 = r21.getAccuracyMode()
            r15 = 0
            java.util.List r16 = java.util.Collections.emptyList()
            long r17 = r21.zzDh()
            r3 = r20
            r6 = r22
            r7 = r23
            r19 = r24
            r3.<init>(r4, r5, r6, r7, r8, r10, r12, r14, r15, r16, r17, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.request.zzav.<init>(com.google.android.gms.fitness.request.SensorRequest, com.google.android.gms.fitness.data.zzs, android.app.PendingIntent, com.google.android.gms.internal.zzapf):void");
    }

    private boolean zzb(zzav zzav) {
        return zzaa.equal(this.zzaTi, zzav.zzaTi) && zzaa.equal(this.zzaTj, zzav.zzaTj) && this.zzaUo == zzav.zzaUo && this.zzaWs == zzav.zzaWs && this.zzaWr == zzav.zzaWr && this.zzaUp == zzav.zzaUp && zzaa.equal(this.zzaWt, zzav.zzaWt);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzav) && zzb((zzav) obj));
    }

    public int getAccuracyMode() {
        return this.zzaUp;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public DataSource getDataSource() {
        return this.zzaTi;
    }

    public DataType getDataType() {
        return this.zzaTj;
    }

    public PendingIntent getIntent() {
        return this.mPendingIntent;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaTi, this.zzaTj, this.zzaWo, Long.valueOf(this.zzaUo), Long.valueOf(this.zzaWs), Long.valueOf(this.zzaWr), Integer.valueOf(this.zzaUp), this.zzaWt);
    }

    public String toString() {
        return String.format("SensorRegistrationRequest{type %s source %s interval %s fastest %s latency %s}", new Object[]{this.zzaTj, this.zzaTi, Long.valueOf(this.zzaUo), Long.valueOf(this.zzaWs), Long.valueOf(this.zzaWr)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaw.zza(this, parcel, i);
    }

    public long zzCy() {
        return this.zzaUo;
    }

    public long zzDc() {
        return this.zzaWs;
    }

    public long zzDd() {
        return this.zzaWr;
    }

    public List<LocationRequest> zzDe() {
        return this.zzaWt;
    }

    public long zzDf() {
        return this.zzaWu;
    }

    /* access modifiers changed from: package-private */
    public IBinder zzDg() {
        if (this.zzaWo == null) {
            return null;
        }
        return this.zzaWo.asBinder();
    }
}
