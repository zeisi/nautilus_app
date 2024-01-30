package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzad implements Parcelable.Creator<Value> {
    static void zza(Value value, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, value.getFormat());
        zzc.zza(parcel, 2, value.isSet());
        zzc.zza(parcel, 3, value.zzCw());
        zzc.zza(parcel, 4, value.zzCB(), false);
        zzc.zza(parcel, 5, value.zzCC(), false);
        zzc.zza(parcel, 6, value.zzCD(), false);
        zzc.zza(parcel, 7, value.zzCE(), false);
        zzc.zzc(parcel, 1000, value.getVersionCode());
        zzc.zza(parcel, 8, value.zzCF(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeo */
    public Value createFromParcel(Parcel parcel) {
        boolean z = false;
        byte[] bArr = null;
        int zzaY = zzb.zzaY(parcel);
        float f = 0.0f;
        float[] fArr = null;
        int[] iArr = null;
        Bundle bundle = null;
        String str = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 2:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 3:
                    f = zzb.zzl(parcel, zzaX);
                    break;
                case 4:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 5:
                    bundle = zzb.zzs(parcel, zzaX);
                    break;
                case 6:
                    iArr = zzb.zzw(parcel, zzaX);
                    break;
                case 7:
                    fArr = zzb.zzz(parcel, zzaX);
                    break;
                case 8:
                    bArr = zzb.zzt(parcel, zzaX);
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
            return new Value(i2, i, z, f, str, bundle, iArr, fArr, bArr);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgZ */
    public Value[] newArray(int i) {
        return new Value[i];
    }
}
