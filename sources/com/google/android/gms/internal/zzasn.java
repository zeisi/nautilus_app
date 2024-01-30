package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzasn implements Parcelable.Creator<zzasm> {
    static void zza(zzasm zzasm, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzasm.getRequestId(), false);
        zzc.zza(parcel, 2, zzasm.getExpirationTime());
        zzc.zza(parcel, 3, zzasm.zzIt());
        zzc.zza(parcel, 4, zzasm.getLatitude());
        zzc.zza(parcel, 5, zzasm.getLongitude());
        zzc.zza(parcel, 6, zzasm.getRadius());
        zzc.zzc(parcel, 7, zzasm.zzIu());
        zzc.zzc(parcel, 8, zzasm.zzIv());
        zzc.zzc(parcel, 9, zzasm.zzIw());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgX */
    public zzasm createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        String str = null;
        int i = 0;
        short s = 0;
        double d = 0.0d;
        double d2 = 0.0d;
        float f = 0.0f;
        long j = 0;
        int i2 = 0;
        int i3 = -1;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    s = zzb.zzf(parcel, zzaX);
                    break;
                case 4:
                    d = zzb.zzn(parcel, zzaX);
                    break;
                case 5:
                    d2 = zzb.zzn(parcel, zzaX);
                    break;
                case 6:
                    f = zzb.zzl(parcel, zzaX);
                    break;
                case 7:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 8:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 9:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzasm(str, i, s, d, d2, f, j, i2, i3);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkC */
    public zzasm[] newArray(int i) {
        return new zzasm[i];
    }
}
