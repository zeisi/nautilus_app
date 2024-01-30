package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzj implements Parcelable.Creator<DataSource> {
    static void zza(DataSource dataSource, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) dataSource.getDataType(), i, false);
        zzc.zza(parcel, 2, dataSource.getName(), false);
        zzc.zzc(parcel, 3, dataSource.getType());
        zzc.zza(parcel, 4, (Parcelable) dataSource.getDevice(), i, false);
        zzc.zza(parcel, 5, (Parcelable) dataSource.zzCh(), i, false);
        zzc.zza(parcel, 6, dataSource.getStreamName(), false);
        zzc.zzc(parcel, 1000, dataSource.getVersionCode());
        zzc.zza(parcel, 8, dataSource.getDataQualityStandards(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdX */
    public DataSource createFromParcel(Parcel parcel) {
        int i = 0;
        int[] iArr = null;
        int zzaY = zzb.zzaY(parcel);
        String str = null;
        zzb zzb = null;
        Device device = null;
        String str2 = null;
        DataType dataType = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 4:
                    device = (Device) zzb.zza(parcel, zzaX, Device.CREATOR);
                    break;
                case 5:
                    zzb = (zzb) zzb.zza(parcel, zzaX, zzb.CREATOR);
                    break;
                case 6:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 8:
                    iArr = zzb.zzw(parcel, zzaX);
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
            return new DataSource(i2, dataType, str2, i, device, zzb, str, iArr);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgE */
    public DataSource[] newArray(int i) {
        return new DataSource[i];
    }
}
