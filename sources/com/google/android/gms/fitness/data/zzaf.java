package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzaf implements Parcelable.Creator<zzae> {
    static void zza(zzae zzae, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, zzae.getStatus());
        zzc.zzc(parcel, 1000, zzae.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzep */
    public zzae createFromParcel(Parcel parcel) {
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzae(i2, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhb */
    public zzae[] newArray(int i) {
        return new zzae[i];
    }
}
