package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzarw;
import java.util.ArrayList;

public class zzg implements Parcelable.Creator<zzf> {
    static void zza(zzf zzf, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, zzf.zzIc(), false);
        zzc.zza(parcel, 2, zzf.getTag(), false);
        zzc.zzc(parcel, 3, zzf.zzId(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgI */
    public zzf createFromParcel(Parcel parcel) {
        ArrayList<zzarw> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        String str = null;
        ArrayList<zzd> arrayList2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList2 = zzb.zzc(parcel, zzaX, zzd.CREATOR);
                    break;
                case 2:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, zzaX, zzarw.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzf(arrayList2, str, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkd */
    public zzf[] newArray(int i) {
        return new zzf[i];
    }
}
