package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzb implements Parcelable.Creator<zza> {
    static void zza(zza zza, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zza.getIntervalMillis());
        zzc.zza(parcel, 2, zza.zzHW());
        zzc.zza(parcel, 3, (Parcelable) zza.zzHX(), i, false);
        zzc.zza(parcel, 4, zza.getTag(), false);
        zzc.zza(parcel, 5, zza.zzHY(), false);
        zzc.zza(parcel, 6, zza.zzHZ());
        zzc.zza(parcel, 7, zza.getAccountName(), false);
        zzc.zza(parcel, 8, zza.zzIa());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgF */
    public zza createFromParcel(Parcel parcel) {
        long j = 0;
        boolean z = false;
        String str = null;
        int zzaY = com.google.android.gms.common.internal.safeparcel.zzb.zzaY(parcel);
        int[] iArr = null;
        String str2 = null;
        WorkSource workSource = null;
        boolean z2 = false;
        long j2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = com.google.android.gms.common.internal.safeparcel.zzb.zzaX(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzdc(zzaX)) {
                case 1:
                    j2 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(parcel, zzaX);
                    break;
                case 2:
                    z2 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, zzaX);
                    break;
                case 3:
                    workSource = (WorkSource) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, zzaX, WorkSource.CREATOR);
                    break;
                case 4:
                    str2 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaX);
                    break;
                case 5:
                    iArr = com.google.android.gms.common.internal.safeparcel.zzb.zzw(parcel, zzaX);
                    break;
                case 6:
                    z = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, zzaX);
                    break;
                case 7:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaX);
                    break;
                case 8:
                    j = com.google.android.gms.common.internal.safeparcel.zzb.zzi(parcel, zzaX);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zza(j2, z2, workSource, str2, iArr, z, str, j);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzka */
    public zza[] newArray(int i) {
        return new zza[i];
    }
}
