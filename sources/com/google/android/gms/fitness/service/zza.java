package com.google.android.gms.fitness.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;

public class zza implements Parcelable.Creator<FitnessSensorServiceRequest> {
    static void zza(FitnessSensorServiceRequest fitnessSensorServiceRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) fitnessSensorServiceRequest.getDataSource(), i, false);
        zzc.zza(parcel, 2, fitnessSensorServiceRequest.zzDg(), false);
        zzc.zza(parcel, 3, fitnessSensorServiceRequest.zzCy());
        zzc.zza(parcel, 4, fitnessSensorServiceRequest.zzDB());
        zzc.zzc(parcel, 1000, fitnessSensorServiceRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfv */
    public FitnessSensorServiceRequest createFromParcel(Parcel parcel) {
        long j = 0;
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        long j2 = 0;
        DataSource dataSource = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 3:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    j = zzb.zzi(parcel, zzaX);
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
            return new FitnessSensorServiceRequest(i, dataSource, iBinder, j2, j);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzii */
    public FitnessSensorServiceRequest[] newArray(int i) {
        return new FitnessSensorServiceRequest[i];
    }
}
