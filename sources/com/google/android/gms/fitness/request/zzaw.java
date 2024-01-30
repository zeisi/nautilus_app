package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;

public class zzaw implements Parcelable.Creator<zzav> {
    static void zza(zzav zzav, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzav.getDataSource(), i, false);
        zzc.zza(parcel, 2, (Parcelable) zzav.getDataType(), i, false);
        zzc.zza(parcel, 3, zzav.zzDg(), false);
        zzc.zzc(parcel, 4, zzav.zzaWp);
        zzc.zzc(parcel, 5, zzav.zzaWq);
        zzc.zza(parcel, 6, zzav.zzCy());
        zzc.zza(parcel, 7, zzav.zzDd());
        zzc.zzc(parcel, 1000, zzav.getVersionCode());
        zzc.zza(parcel, 8, (Parcelable) zzav.getIntent(), i, false);
        zzc.zza(parcel, 9, zzav.zzDc());
        zzc.zzc(parcel, 10, zzav.getAccuracyMode());
        zzc.zzc(parcel, 11, zzav.zzDe(), false);
        zzc.zza(parcel, 12, zzav.zzDf());
        zzc.zza(parcel, 13, zzav.getCallbackBinder(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeR */
    public zzav createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        DataSource dataSource = null;
        DataType dataType = null;
        IBinder iBinder = null;
        int i2 = 0;
        int i3 = 0;
        long j = 0;
        long j2 = 0;
        PendingIntent pendingIntent = null;
        long j3 = 0;
        int i4 = 0;
        ArrayList<LocationRequest> arrayList = null;
        long j4 = 0;
        IBinder iBinder2 = null;
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
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 5:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                case 6:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 7:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 8:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
                    break;
                case 9:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 10:
                    i4 = zzb.zzg(parcel, zzaX);
                    break;
                case 11:
                    arrayList = zzb.zzc(parcel, zzaX, LocationRequest.CREATOR);
                    break;
                case 12:
                    j4 = zzb.zzi(parcel, zzaX);
                    break;
                case 13:
                    iBinder2 = zzb.zzr(parcel, zzaX);
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
            return new zzav(i, dataSource, dataType, iBinder, i2, i3, j, j2, pendingIntent, j3, i4, arrayList, j4, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhE */
    public zzav[] newArray(int i) {
        return new zzav[i];
    }
}
