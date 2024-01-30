package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzm implements Parcelable.Creator<DataUpdateNotification> {
    static void zza(DataUpdateNotification dataUpdateNotification, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, dataUpdateNotification.zzCk());
        zzc.zza(parcel, 2, dataUpdateNotification.zzCl());
        zzc.zzc(parcel, 3, dataUpdateNotification.getOperationType());
        zzc.zza(parcel, 4, (Parcelable) dataUpdateNotification.getDataSource(), i, false);
        zzc.zza(parcel, 5, (Parcelable) dataUpdateNotification.getDataType(), i, false);
        zzc.zzc(parcel, 1000, dataUpdateNotification.zzaiI);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdZ */
    public DataUpdateNotification createFromParcel(Parcel parcel) {
        long j = 0;
        DataType dataType = null;
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        DataSource dataSource = null;
        long j2 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 4:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 5:
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new DataUpdateNotification(i2, j2, j, i, dataSource, dataType);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgG */
    public DataUpdateNotification[] newArray(int i) {
        return new DataUpdateNotification[i];
    }
}
