package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzy implements Parcelable.Creator<zzt> {
    static void zza(zzt zzt, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzt.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzt.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeG */
    public zzt createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
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
            return new zzt(i, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzht */
    public zzt[] newArray(int i) {
        return new zzt[i];
    }
}
