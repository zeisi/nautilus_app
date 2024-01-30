package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Goal;

public class zzu implements Parcelable.Creator<Goal.MetricObjective> {
    static void zza(Goal.MetricObjective metricObjective, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, metricObjective.getDataTypeName(), false);
        zzc.zza(parcel, 2, metricObjective.getValue());
        zzc.zza(parcel, 3, metricObjective.zzCv());
        zzc.zzc(parcel, 1000, metricObjective.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeg */
    public Goal.MetricObjective createFromParcel(Parcel parcel) {
        double d = 0.0d;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        String str = null;
        double d2 = 0.0d;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    d2 = zzb.zzn(parcel, zzaX);
                    break;
                case 3:
                    d = zzb.zzn(parcel, zzaX);
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
            return new Goal.MetricObjective(i, str, d2, d);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgQ */
    public Goal.MetricObjective[] newArray(int i) {
        return new Goal.MetricObjective[i];
    }
}
