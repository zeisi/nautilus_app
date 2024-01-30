package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataType;

public class zze implements Parcelable.Creator<zzd> {
    static void zza(zzd zzd, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzd.getCallbackBinder(), false);
        zzc.zza(parcel, 2, (Parcelable) zzd.getDataType(), i, false);
        zzc.zza(parcel, 4, zzd.zzCM());
        zzc.zzc(parcel, 1000, zzd.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzet */
    public zzd createFromParcel(Parcel parcel) {
        DataType dataType;
        IBinder iBinder;
        int zzg;
        boolean z;
        DataType dataType2 = null;
        boolean z2 = false;
        int zzaY = zzb.zzaY(parcel);
        IBinder iBinder2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    DataType dataType3 = dataType2;
                    iBinder = zzb.zzr(parcel, zzaX);
                    z = z2;
                    dataType = dataType3;
                    break;
                case 2:
                    iBinder = iBinder2;
                    zzg = i;
                    boolean z3 = z2;
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    z = z3;
                    break;
                case 4:
                    z = zzb.zzc(parcel, zzaX);
                    dataType = dataType2;
                    iBinder = iBinder2;
                    zzg = i;
                    break;
                case 1000:
                    boolean z4 = z2;
                    dataType = dataType2;
                    iBinder = iBinder2;
                    zzg = zzb.zzg(parcel, zzaX);
                    z = z4;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    z = z2;
                    dataType = dataType2;
                    iBinder = iBinder2;
                    zzg = i;
                    break;
            }
            i = zzg;
            iBinder2 = iBinder;
            dataType2 = dataType;
            z2 = z;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzd(i, iBinder2, dataType2, z2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhg */
    public zzd[] newArray(int i) {
        return new zzd[i];
    }
}
