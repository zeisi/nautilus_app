package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.List;

public class zzn implements Parcelable.Creator<LocationResult> {
    static void zza(LocationResult locationResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, locationResult.getLocations(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgN */
    public LocationResult createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        List<Location> list = LocationResult.zzbkd;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    list = zzb.zzc(parcel, zzaX, Location.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new LocationResult(list);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkn */
    public LocationResult[] newArray(int i) {
        return new LocationResult[i];
    }
}
