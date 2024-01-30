package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;
import java.util.List;

public class zzc implements Parcelable.Creator<DataReadResult> {
    static void zza(DataReadResult dataReadResult, Parcel parcel, int i) {
        int zzaZ = com.google.android.gms.common.internal.safeparcel.zzc.zzaZ(parcel);
        com.google.android.gms.common.internal.safeparcel.zzc.zzd(parcel, 1, dataReadResult.zzDr(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 2, (Parcelable) dataReadResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzd(parcel, 3, dataReadResult.zzDq(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 5, dataReadResult.zzDp());
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 6, dataReadResult.zzCg(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 7, dataReadResult.zzDs(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 1000, dataReadResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfh */
    public DataReadResult createFromParcel(Parcel parcel) {
        int i = 0;
        ArrayList<DataType> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList<DataSource> arrayList4 = null;
        Status status = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzb.zza(parcel, zzaX, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 2:
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    break;
                case 3:
                    zzb.zza(parcel, zzaX, (List) arrayList3, getClass().getClassLoader());
                    break;
                case 5:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 6:
                    arrayList4 = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 7:
                    arrayList = zzb.zzc(parcel, zzaX, DataType.CREATOR);
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
            return new DataReadResult(i2, arrayList2, status, arrayList3, i, arrayList4, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhU */
    public DataReadResult[] newArray(int i) {
        return new DataReadResult[i];
    }
}
