package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzanu;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@KeepName
public final class RawDataPoint extends zza {
    public static final Parcelable.Creator<RawDataPoint> CREATOR = new zzw();
    final int versionCode;
    public final long zzaUb;
    public final long zzaUc;
    public final Value[] zzaUd;
    public final int zzaUe;
    public final int zzaUf;
    public final long zzaUg;
    public final long zzaUh;

    public RawDataPoint(int i, long j, long j2, Value[] valueArr, int i2, int i3, long j3, long j4) {
        this.versionCode = i;
        this.zzaUb = j;
        this.zzaUc = j2;
        this.zzaUe = i2;
        this.zzaUf = i3;
        this.zzaUg = j3;
        this.zzaUh = j4;
        this.zzaUd = valueArr;
    }

    RawDataPoint(DataPoint dataPoint, List<DataSource> list) {
        this.versionCode = 4;
        this.zzaUb = dataPoint.getTimestamp(TimeUnit.NANOSECONDS);
        this.zzaUc = dataPoint.getStartTime(TimeUnit.NANOSECONDS);
        this.zzaUd = dataPoint.zzBZ();
        this.zzaUe = zzanu.zza(dataPoint.getDataSource(), list);
        this.zzaUf = zzanu.zza(dataPoint.zzCa(), list);
        this.zzaUg = dataPoint.zzCb();
        this.zzaUh = dataPoint.zzCc();
    }

    private boolean zza(RawDataPoint rawDataPoint) {
        return this.zzaUb == rawDataPoint.zzaUb && this.zzaUc == rawDataPoint.zzaUc && Arrays.equals(this.zzaUd, rawDataPoint.zzaUd) && this.zzaUe == rawDataPoint.zzaUe && this.zzaUf == rawDataPoint.zzaUf && this.zzaUg == rawDataPoint.zzaUg;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof RawDataPoint) && zza((RawDataPoint) obj));
    }

    public int hashCode() {
        return zzaa.hashCode(Long.valueOf(this.zzaUb), Long.valueOf(this.zzaUc));
    }

    public String toString() {
        return String.format("RawDataPoint{%s@[%s, %s](%d,%d)}", new Object[]{Arrays.toString(this.zzaUd), Long.valueOf(this.zzaUc), Long.valueOf(this.zzaUb), Integer.valueOf(this.zzaUe), Integer.valueOf(this.zzaUf)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzw.zza(this, parcel, i);
    }
}
