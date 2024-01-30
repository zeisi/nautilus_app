package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc implements Parcelable.Creator<zzb> {
    static void zza(zzb zzb, Parcel parcel, int i) {
        int zzaZ = com.google.android.gms.common.internal.safeparcel.zzc.zzaZ(parcel);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 1, zzb.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 2, zzb.getVersion(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 3, zzb.zzBU(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 1000, zzb.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdS */
    public zzb createFromParcel(Parcel parcel) {
        String str = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str3 = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
                    str = zzb.zzq(parcel, zzaX);
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
            return new zzb(i, str3, str2, str);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgu */
    public zzb[] newArray(int i) {
        return new zzb[i];
    }
}
