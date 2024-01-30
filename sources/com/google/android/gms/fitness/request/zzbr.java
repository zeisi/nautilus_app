package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;

public class zzbr implements Parcelable.Creator<zzbq> {
    static void zza(zzbq zzbq, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzbq.getDataType(), i, false);
        zzc.zza(parcel, 2, (Parcelable) zzbq.getDataSource(), i, false);
        zzc.zza(parcel, 3, zzbq.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzbq.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfd */
    public zzbq createFromParcel(Parcel parcel) {
        DataSource dataSource;
        DataType dataType;
        int zzg;
        IBinder iBinder;
        IBinder iBinder2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        DataSource dataSource2 = null;
        DataType dataType2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    DataSource dataSource3 = dataSource2;
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    iBinder = iBinder2;
                    dataSource = dataSource3;
                    break;
                case 2:
                    dataType = dataType2;
                    zzg = i;
                    IBinder iBinder3 = iBinder2;
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    iBinder = iBinder3;
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, zzaX);
                    dataSource = dataSource2;
                    dataType = dataType2;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder4 = iBinder2;
                    dataSource = dataSource2;
                    dataType = dataType2;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder4;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder = iBinder2;
                    dataSource = dataSource2;
                    dataType = dataType2;
                    zzg = i;
                    break;
            }
            i = zzg;
            dataType2 = dataType;
            dataSource2 = dataSource;
            iBinder2 = iBinder;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzbq(i, dataType2, dataSource2, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhQ */
    public zzbq[] newArray(int i) {
        return new zzbq[i];
    }
}
