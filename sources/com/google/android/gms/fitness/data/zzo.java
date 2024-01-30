package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Goal;

public class zzo implements Parcelable.Creator<Goal.DurationObjective> {
    static void zza(Goal.DurationObjective durationObjective, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, durationObjective.getDuration());
        zzc.zzc(parcel, 1000, durationObjective.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeb */
    public Goal.DurationObjective createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        long j = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    j = zzb.zzi(parcel, zzaX);
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
            return new Goal.DurationObjective(i, j);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgI */
    public Goal.DurationObjective[] newArray(int i) {
        return new Goal.DurationObjective[i];
    }
}
