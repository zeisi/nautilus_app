package com.google.android.gms.fitness.data;

import com.google.android.gms.internal.zzanv;
import com.google.android.gms.internal.zzanw;
import java.util.concurrent.TimeUnit;

class zzf implements zzanv<DataPoint, DataType> {
    public static final zzf zzaSy = new zzf();

    private zzf() {
    }

    public zzanw<DataType> zzBX() {
        return zzg.zzaSz;
    }

    public long zza(DataPoint dataPoint, TimeUnit timeUnit) {
        return dataPoint.getEndTime(timeUnit) - dataPoint.getStartTime(timeUnit);
    }

    /* renamed from: zza */
    public String zzC(DataPoint dataPoint) {
        return dataPoint.getDataType().getName();
    }

    /* renamed from: zza */
    public boolean zzd(DataPoint dataPoint, int i) {
        return dataPoint.zzgy(i).isSet();
    }

    /* renamed from: zzb */
    public int zzc(DataPoint dataPoint, int i) {
        return dataPoint.zzgy(i).asInt();
    }

    /* renamed from: zzb */
    public DataType zzB(DataPoint dataPoint) {
        return dataPoint.getDataType();
    }

    /* renamed from: zzc */
    public double zzb(DataPoint dataPoint, int i) {
        return (double) dataPoint.zzgy(i).asFloat();
    }
}
