package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;

public class zze implements Parcelable.Creator<zzd> {
    static void zza(zzd zzd, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzd.zzaTi, i, false);
        zzc.zza(parcel, 2, zzd.zzait);
        zzc.zza(parcel, 3, zzd.zzaWT);
        zzc.zza(parcel, 4, zzd.zzaWU);
        zzc.zza(parcel, 5, zzd.zzaWV);
        zzc.zza(parcel, 6, zzd.zzaWW);
        zzc.zzc(parcel, 1000, zzd.zzaiI);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfi */
    public zzd createFromParcel(Parcel parcel) {
        boolean z = false;
        long j = 0;
        int zzaY = zzb.zzaY(parcel);
        DataSource dataSource = null;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 2:
                    j4 = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 4:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 5:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 6:
                    j = zzb.zzi(parcel, zzaX);
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
            return new zzd(i, dataSource, j4, z, j3, j2, j);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhV */
    public zzd[] newArray(int i) {
        return new zzd[i];
    }
}
