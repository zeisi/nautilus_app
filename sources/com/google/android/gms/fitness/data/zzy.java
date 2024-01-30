package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Goal;

public class zzy implements Parcelable.Creator<Goal.Recurrence> {
    static void zza(Goal.Recurrence recurrence, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, recurrence.getCount());
        zzc.zzc(parcel, 2, recurrence.getUnit());
        zzc.zzc(parcel, 1000, recurrence.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzek */
    public Goal.Recurrence createFromParcel(Parcel parcel) {
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 2:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 1000:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new Goal.Recurrence(i3, i2, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgU */
    public Goal.Recurrence[] newArray(int i) {
        return new Goal.Recurrence[i];
    }
}
