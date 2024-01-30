package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzh implements Parcelable.Creator<DataPoint> {
    static void zza(DataPoint dataPoint, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) dataPoint.getDataSource(), i, false);
        zzc.zza(parcel, 3, dataPoint.getTimestampNanos());
        zzc.zza(parcel, 4, dataPoint.zzCe());
        zzc.zza(parcel, 5, (T[]) dataPoint.zzBZ(), i, false);
        zzc.zza(parcel, 6, (Parcelable) dataPoint.zzCa(), i, false);
        zzc.zza(parcel, 7, dataPoint.zzCb());
        zzc.zzc(parcel, 1000, dataPoint.getVersionCode());
        zzc.zza(parcel, 8, dataPoint.zzCc());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdV */
    public DataPoint createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        DataSource dataSource = null;
        long j = 0;
        long j2 = 0;
        Value[] valueArr = null;
        DataSource dataSource2 = null;
        long j3 = 0;
        long j4 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 3:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 5:
                    valueArr = (Value[]) zzb.zzb(parcel, zzaX, Value.CREATOR);
                    break;
                case 6:
                    dataSource2 = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 7:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 8:
                    j4 = zzb.zzi(parcel, zzaX);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new DataPoint(i, dataSource, j, j2, valueArr, dataSource2, j3, j4);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgA */
    public DataPoint[] newArray(int i) {
        return new DataPoint[i];
    }
}
