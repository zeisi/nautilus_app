package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSet;

public class zzb implements Parcelable.Creator<DailyTotalResult> {
    static void zza(DailyTotalResult dailyTotalResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) dailyTotalResult.getStatus(), i, false);
        zzc.zza(parcel, 2, (Parcelable) dailyTotalResult.getTotal(), i, false);
        zzc.zzc(parcel, 1000, dailyTotalResult.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfg */
    public DailyTotalResult createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        DataSet dataSet;
        DataSet dataSet2 = null;
        int zzaY = com.google.android.gms.common.internal.safeparcel.zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = com.google.android.gms.common.internal.safeparcel.zzb.zzaX(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Status status3 = (Status) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, zzaX, Status.CREATOR);
                    dataSet = dataSet2;
                    status = status3;
                    break;
                case 2:
                    dataSet = (DataSet) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, zzaX, DataSet.CREATOR);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    DataSet dataSet3 = dataSet2;
                    status = status2;
                    zzg = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaX);
                    dataSet = dataSet3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, zzaX);
                    dataSet = dataSet2;
                    status = status2;
                    zzg = i;
                    break;
            }
            i = zzg;
            status2 = status;
            dataSet2 = dataSet;
        }
        if (parcel.dataPosition() == zzaY) {
            return new DailyTotalResult(i, status2, dataSet2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhT */
    public DailyTotalResult[] newArray(int i) {
        return new DailyTotalResult[i];
    }
}
