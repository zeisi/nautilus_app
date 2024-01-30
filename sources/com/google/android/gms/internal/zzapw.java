package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

public class zzapw implements Parcelable.Creator<zzapv> {
    static void zza(zzapv zzapv, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, zzapv.getDataTypes(), false);
        zzc.zzc(parcel, 1000, zzapv.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeq */
    public zzapv createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<DataType> arrayList = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList = zzb.zzc(parcel, zzaX, DataType.CREATOR);
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
            return new zzapv(i, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhd */
    public zzapv[] newArray(int i) {
        return new zzapv[i];
    }
}
