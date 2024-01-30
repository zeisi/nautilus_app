package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzq implements Parcelable.Creator<LocationSettingsRequest> {
    static void zza(LocationSettingsRequest locationSettingsRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, locationSettingsRequest.zzDe(), false);
        zzc.zza(parcel, 2, locationSettingsRequest.zzIi());
        zzc.zza(parcel, 3, locationSettingsRequest.zzIj());
        zzc.zza(parcel, 5, (Parcelable) locationSettingsRequest.zzIk(), i, false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgP */
    public LocationSettingsRequest createFromParcel(Parcel parcel) {
        zzo zzo = null;
        boolean z = false;
        int zzaY = zzb.zzaY(parcel);
        boolean z2 = false;
        ArrayList<LocationRequest> arrayList = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList = zzb.zzc(parcel, zzaX, LocationRequest.CREATOR);
                    break;
                case 2:
                    z2 = zzb.zzc(parcel, zzaX);
                    break;
                case 3:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 5:
                    zzo = (zzo) zzb.zza(parcel, zzaX, zzo.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new LocationSettingsRequest(arrayList, z2, z, zzo);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkp */
    public LocationSettingsRequest[] newArray(int i) {
        return new LocationSettingsRequest[i];
    }
}
