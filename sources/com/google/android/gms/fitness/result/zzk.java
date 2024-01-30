package com.google.android.gms.fitness.result;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzk implements Parcelable.Creator<zzj> {
    static void zza(zzj zzj, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzj.getStatus(), i, false);
        zzc.zza(parcel, 2, zzj.zzDu(), false);
        zzc.zzc(parcel, 1000, zzj.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfm */
    public zzj createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        Bundle bundle;
        Bundle bundle2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Status status3 = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    bundle = bundle2;
                    status = status3;
                    break;
                case 2:
                    bundle = zzb.zzs(parcel, zzaX);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    Bundle bundle3 = bundle2;
                    status = status2;
                    zzg = zzb.zzg(parcel, zzaX);
                    bundle = bundle3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    bundle = bundle2;
                    status = status2;
                    zzg = i;
                    break;
            }
            i = zzg;
            status2 = status;
            bundle2 = bundle;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzj(i, status2, bundle2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhZ */
    public zzj[] newArray(int i) {
        return new zzj[i];
    }
}
