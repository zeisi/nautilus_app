package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Goal;
import java.util.ArrayList;
import java.util.List;

public class zzr implements Parcelable.Creator<Goal> {
    static void zza(Goal goal, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, goal.zzCp());
        zzc.zza(parcel, 2, goal.zzCq());
        zzc.zzd(parcel, 3, goal.zzCr(), false);
        zzc.zza(parcel, 4, (Parcelable) goal.getRecurrence(), i, false);
        zzc.zzc(parcel, 5, goal.getObjectiveType());
        zzc.zza(parcel, 6, (Parcelable) goal.zzCs(), i, false);
        zzc.zza(parcel, 7, (Parcelable) goal.zzCt(), i, false);
        zzc.zzc(parcel, 1000, goal.getVersionCode());
        zzc.zza(parcel, 8, (Parcelable) goal.zzCu(), i, false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzee */
    public Goal createFromParcel(Parcel parcel) {
        long j = 0;
        int i = 0;
        Goal.FrequencyObjective frequencyObjective = null;
        int zzaY = zzb.zzaY(parcel);
        ArrayList arrayList = new ArrayList();
        Goal.DurationObjective durationObjective = null;
        Goal.MetricObjective metricObjective = null;
        Goal.Recurrence recurrence = null;
        long j2 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    zzb.zza(parcel, zzaX, (List) arrayList, getClass().getClassLoader());
                    break;
                case 4:
                    recurrence = (Goal.Recurrence) zzb.zza(parcel, zzaX, Goal.Recurrence.CREATOR);
                    break;
                case 5:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 6:
                    metricObjective = (Goal.MetricObjective) zzb.zza(parcel, zzaX, Goal.MetricObjective.CREATOR);
                    break;
                case 7:
                    durationObjective = (Goal.DurationObjective) zzb.zza(parcel, zzaX, Goal.DurationObjective.CREATOR);
                    break;
                case 8:
                    frequencyObjective = (Goal.FrequencyObjective) zzb.zza(parcel, zzaX, Goal.FrequencyObjective.CREATOR);
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
            return new Goal(i2, j2, j, arrayList, recurrence, i, metricObjective, durationObjective, frequencyObjective);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgO */
    public Goal[] newArray(int i) {
        return new Goal[i];
    }
}
