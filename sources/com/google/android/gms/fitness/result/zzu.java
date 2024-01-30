package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzu implements Parcelable.Creator<zzt> {
    static void zza(zzt zzt, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzt.getStatus(), i, false);
        zzc.zza(parcel, 2, zzt.zzDx());
        zzc.zza(parcel, 3, zzt.zzDy(), false);
        zzc.zzc(parcel, 1000, zzt.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzft */
    public zzt createFromParcel(Parcel parcel) {
        Boolean bool = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        long j = 0;
        Status status = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    bool = zzb.zzd(parcel, zzaX);
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
            return new zzt(i, status, j, bool);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzig */
    public zzt[] newArray(int i) {
        return new zzt[i];
    }
}
