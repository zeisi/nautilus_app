package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataType;

public class zzan implements Parcelable.Creator<zzam> {
    static void zza(zzam zzam, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzam.getDataType(), i, false);
        zzc.zza(parcel, 2, zzam.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzam.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeN */
    public zzam createFromParcel(Parcel parcel) {
        DataType dataType;
        int zzg;
        IBinder iBinder;
        IBinder iBinder2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        DataType dataType2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    DataType dataType3 = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    iBinder = iBinder2;
                    dataType = dataType3;
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, zzaX);
                    dataType = dataType2;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder3 = iBinder2;
                    dataType = dataType2;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder = iBinder2;
                    dataType = dataType2;
                    zzg = i;
                    break;
            }
            i = zzg;
            dataType2 = dataType;
            iBinder2 = iBinder;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzam(i, dataType2, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhA */
    public zzam[] newArray(int i) {
        return new zzam[i];
    }
}
