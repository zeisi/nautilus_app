package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzt implements Parcelable.Creator<MapValue> {
    static void zza(MapValue mapValue, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, mapValue.getFormat());
        zzc.zza(parcel, 2, mapValue.zzCw());
        zzc.zzc(parcel, 1000, mapValue.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzef */
    public MapValue createFromParcel(Parcel parcel) {
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        float f = 0.0f;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 2:
                    f = zzb.zzl(parcel, zzaX);
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
            return new MapValue(i2, i, f);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgP */
    public MapValue[] newArray(int i) {
        return new MapValue[i];
    }
}
