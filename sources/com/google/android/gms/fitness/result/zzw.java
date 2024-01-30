package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzw implements Parcelable.Creator<zzv> {
    static void zza(zzv zzv, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzv.getStatus(), i, false);
        zzc.zza(parcel, 2, zzv.zzDz());
        zzc.zzc(parcel, 1000, zzv.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfu */
    public zzv createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        long j;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        long j2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    long j3 = j2;
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    zzg = i;
                    j = j3;
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    long j4 = j2;
                    status = status2;
                    zzg = zzb.zzg(parcel, zzaX);
                    j = j4;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    j = j2;
                    status = status2;
                    zzg = i;
                    break;
            }
            status2 = status;
            i = zzg;
            j2 = j;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzv(i, status2, j2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzih */
    public zzv[] newArray(int i) {
        return new zzv[i];
    }
}
