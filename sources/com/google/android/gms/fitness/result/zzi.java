package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataType;

public class zzi implements Parcelable.Creator<DataTypeResult> {
    static void zza(DataTypeResult dataTypeResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) dataTypeResult.getStatus(), i, false);
        zzc.zza(parcel, 3, (Parcelable) dataTypeResult.getDataType(), i, false);
        zzc.zzc(parcel, 1000, dataTypeResult.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfl */
    public DataTypeResult createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        DataType dataType;
        DataType dataType2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Status status3 = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    dataType = dataType2;
                    status = status3;
                    break;
                case 3:
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    DataType dataType3 = dataType2;
                    status = status2;
                    zzg = zzb.zzg(parcel, zzaX);
                    dataType = dataType3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    dataType = dataType2;
                    status = status2;
                    zzg = i;
                    break;
            }
            i = zzg;
            status2 = status;
            dataType2 = dataType;
        }
        if (parcel.dataPosition() == zzaY) {
            return new DataTypeResult(i, status2, dataType2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhY */
    public DataTypeResult[] newArray(int i) {
        return new DataTypeResult[i];
    }
}
