package com.google.android.gms.fitness.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;

public class zzk implements Parcelable.Creator<zzj> {
    static void zza(zzj zzj, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzj.zzaTi, i, false);
        zzc.zza(parcel, 2, zzj.zzait);
        zzc.zza(parcel, 3, zzj.zzaUc);
        zzc.zza(parcel, 4, zzj.zzaVL);
        zzc.zzc(parcel, 5, zzj.zzaVF);
        zzc.zzc(parcel, 6, zzj.zzaVM);
        zzc.zzc(parcel, 1000, zzj.versionCode);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzex */
    public zzj createFromParcel(Parcel parcel) {
        long j = 0;
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        DataSource dataSource = null;
        int i2 = 0;
        long j2 = 0;
        long j3 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 2:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 5:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 6:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 1000:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzj(i3, dataSource, j3, j2, j, i2, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhk */
    public zzj[] newArray(int i) {
        return new zzj[i];
    }
}
