package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzm implements Parcelable.Creator<LocationRequest> {
    static void zza(LocationRequest locationRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, locationRequest.mPriority);
        zzc.zza(parcel, 2, locationRequest.zzbjY);
        zzc.zza(parcel, 3, locationRequest.zzbjZ);
        zzc.zza(parcel, 4, locationRequest.zzaWy);
        zzc.zza(parcel, 5, locationRequest.zzbjI);
        zzc.zzc(parcel, 6, locationRequest.zzbka);
        zzc.zza(parcel, 7, locationRequest.zzbkb);
        zzc.zza(parcel, 8, locationRequest.zzbkc);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgM */
    public LocationRequest createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 102;
        long j = 3600000;
        long j2 = 600000;
        boolean z = false;
        long j3 = Long.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        float f = 0.0f;
        long j4 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 5:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 7:
                    f = zzb.zzl(parcel, zzaX);
                    break;
                case 8:
                    j4 = zzb.zzi(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new LocationRequest(i, j, j2, z, j3, i2, f, j4);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkm */
    public LocationRequest[] newArray(int i) {
        return new LocationRequest[i];
    }
}
