package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSet;

public class zzs implements Parcelable.Creator<DataUpdateRequest> {
    static void zza(DataUpdateRequest dataUpdateRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, dataUpdateRequest.zzqn());
        zzc.zza(parcel, 2, dataUpdateRequest.zzAl());
        zzc.zza(parcel, 3, (Parcelable) dataUpdateRequest.getDataSet(), i, false);
        zzc.zza(parcel, 4, dataUpdateRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, dataUpdateRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeD */
    public DataUpdateRequest createFromParcel(Parcel parcel) {
        long j = 0;
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        DataSet dataSet = null;
        long j2 = 0;
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
                    dataSet = (DataSet) zzb.zza(parcel, zzaX, DataSet.CREATOR);
                    break;
                case 4:
                    iBinder = zzb.zzr(parcel, zzaX);
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
            return new DataUpdateRequest(i, j2, j, dataSet, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhq */
    public DataUpdateRequest[] newArray(int i) {
        return new DataUpdateRequest[i];
    }
}
