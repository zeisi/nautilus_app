package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzbp implements Parcelable.Creator<zzbo> {
    static void zza(zzbo zzbo, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 2, zzbo.getDeviceAddress(), false);
        zzc.zza(parcel, 3, zzbo.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzbo.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfc */
    public zzbo createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 2:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
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
            return new zzbo(i, str, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhP */
    public zzbo[] newArray(int i) {
        return new zzbo[i];
    }
}
