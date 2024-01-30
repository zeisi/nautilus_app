package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzk implements Parcelable.Creator<DataType> {
    static void zza(DataType dataType, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, dataType.getName(), false);
        zzc.zzc(parcel, 2, dataType.getFields(), false);
        zzc.zzc(parcel, 1000, dataType.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdY */
    public DataType createFromParcel(Parcel parcel) {
        ArrayList<Field> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, zzaX, Field.CREATOR);
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
            return new DataType(i, str, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgF */
    public DataType[] newArray(int i) {
        return new DataType[i];
    }
}
