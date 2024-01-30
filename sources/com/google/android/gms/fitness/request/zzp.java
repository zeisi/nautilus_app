package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;

public class zzp implements Parcelable.Creator<DataUpdateListenerRegistrationRequest> {
    static void zza(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) dataUpdateListenerRegistrationRequest.getDataSource(), i, false);
        zzc.zza(parcel, 2, (Parcelable) dataUpdateListenerRegistrationRequest.getDataType(), i, false);
        zzc.zza(parcel, 3, (Parcelable) dataUpdateListenerRegistrationRequest.getIntent(), i, false);
        zzc.zza(parcel, 4, dataUpdateListenerRegistrationRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, dataUpdateListenerRegistrationRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeB */
    public DataUpdateListenerRegistrationRequest createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        PendingIntent pendingIntent = null;
        DataType dataType = null;
        DataSource dataSource = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 2:
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    break;
                case 3:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
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
            return new DataUpdateListenerRegistrationRequest(i, dataSource, dataType, pendingIntent, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzho */
    public DataUpdateListenerRegistrationRequest[] newArray(int i) {
        return new DataUpdateListenerRegistrationRequest[i];
    }
}
