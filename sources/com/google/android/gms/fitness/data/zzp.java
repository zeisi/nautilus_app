package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzp implements Parcelable.Creator<Field> {
    static void zza(Field field, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, field.getName(), false);
        zzc.zzc(parcel, 2, field.getFormat());
        zzc.zza(parcel, 3, field.isOptional(), false);
        zzc.zzc(parcel, 1000, field.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzec */
    public Field createFromParcel(Parcel parcel) {
        Boolean bool = null;
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 3:
                    bool = zzb.zzd(parcel, zzaX);
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
            return new Field(i2, str, i, bool);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgJ */
    public Field[] newArray(int i) {
        return new Field[i];
    }
}
