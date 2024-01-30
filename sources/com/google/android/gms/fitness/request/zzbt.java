package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzbt implements Parcelable.Creator<zzbs> {
    static void zza(zzbs zzbs, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzbs.getCallbackBinder(), false);
        zzc.zza(parcel, 2, zzbs.zzDo(), false);
        zzc.zzc(parcel, 1000, zzbs.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfe */
    public zzbs createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        IBinder iBinder2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    iBinder2 = zzb.zzr(parcel, zzaX);
                    break;
                case 2:
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
            return new zzbs(i, iBinder2, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhR */
    public zzbs[] newArray(int i) {
        return new zzbs[i];
    }
}
