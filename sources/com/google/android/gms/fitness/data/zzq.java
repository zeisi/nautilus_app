package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Goal;

public class zzq implements Parcelable.Creator<Goal.FrequencyObjective> {
    static void zza(Goal.FrequencyObjective frequencyObjective, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, frequencyObjective.getFrequency());
        zzc.zzc(parcel, 1000, frequencyObjective.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzed */
    public Goal.FrequencyObjective createFromParcel(Parcel parcel) {
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new Goal.FrequencyObjective(i2, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgK */
    public Goal.FrequencyObjective[] newArray(int i) {
        return new Goal.FrequencyObjective[i];
    }
}
