package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzasa implements Parcelable.Creator<zzarz> {
    static void zza(zzarz zzarz, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzarz.getStatus(), i, false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgU */
    public zzarz createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        Status status = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzarz(status);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkw */
    public zzarz[] newArray(int i) {
        return new zzarz[i];
    }
}
