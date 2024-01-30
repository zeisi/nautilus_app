package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzac implements Parcelable.Creator<zzab> {
    static void zza(zzab zzab, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzab.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzab.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeI */
    public zzab createFromParcel(Parcel parcel) {
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
            return new zzab(i, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhv */
    public zzab[] newArray(int i) {
        return new zzab[i];
    }
}
