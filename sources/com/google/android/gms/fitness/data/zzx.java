package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzx implements Parcelable.Creator<RawDataSet> {
    static void zza(RawDataSet rawDataSet, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, rawDataSet.zzaUe);
        zzc.zzc(parcel, 2, rawDataSet.zzaUi);
        zzc.zzc(parcel, 3, rawDataSet.zzaUj, false);
        zzc.zza(parcel, 4, rawDataSet.zzaSx);
        zzc.zzc(parcel, 1000, rawDataSet.zzaiI);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzej */
    public RawDataSet createFromParcel(Parcel parcel) {
        boolean z = false;
        int zzaY = zzb.zzaY(parcel);
        ArrayList<RawDataPoint> arrayList = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 2:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, zzaX, RawDataPoint.CREATOR);
                    break;
                case 4:
                    z = zzb.zzc(parcel, zzaX);
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
            return new RawDataSet(i3, i2, i, arrayList, z);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgT */
    public RawDataSet[] newArray(int i) {
        return new RawDataSet[i];
    }
}
