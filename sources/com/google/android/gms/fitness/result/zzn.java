package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Goal;
import java.util.ArrayList;

public class zzn implements Parcelable.Creator<GoalsResult> {
    static void zza(GoalsResult goalsResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) goalsResult.getStatus(), i, false);
        zzc.zzc(parcel, 2, goalsResult.getGoals(), false);
        zzc.zzc(parcel, 1000, goalsResult.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfo */
    public GoalsResult createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        ArrayList<Goal> arrayList;
        ArrayList<Goal> arrayList2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Status status3 = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    arrayList = arrayList2;
                    status = status3;
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, zzaX, Goal.CREATOR);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    ArrayList<Goal> arrayList3 = arrayList2;
                    status = status2;
                    zzg = zzb.zzg(parcel, zzaX);
                    arrayList = arrayList3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    arrayList = arrayList2;
                    status = status2;
                    zzg = i;
                    break;
            }
            i = zzg;
            status2 = status;
            arrayList2 = arrayList;
        }
        if (parcel.dataPosition() == zzaY) {
            return new GoalsResult(i, status2, arrayList2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzib */
    public GoalsResult[] newArray(int i) {
        return new GoalsResult[i];
    }
}
