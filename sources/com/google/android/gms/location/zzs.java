package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzs implements Parcelable.Creator<LocationSettingsStates> {
    static void zza(LocationSettingsStates locationSettingsStates, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, locationSettingsStates.isGpsUsable());
        zzc.zza(parcel, 2, locationSettingsStates.isNetworkLocationUsable());
        zzc.zza(parcel, 3, locationSettingsStates.isBleUsable());
        zzc.zza(parcel, 4, locationSettingsStates.isGpsPresent());
        zzc.zza(parcel, 5, locationSettingsStates.isNetworkLocationPresent());
        zzc.zza(parcel, 6, locationSettingsStates.isBlePresent());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgR */
    public LocationSettingsStates createFromParcel(Parcel parcel) {
        boolean z = false;
        int zzaY = zzb.zzaY(parcel);
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    z6 = zzb.zzc(parcel, zzaX);
                    break;
                case 2:
                    z5 = zzb.zzc(parcel, zzaX);
                    break;
                case 3:
                    z4 = zzb.zzc(parcel, zzaX);
                    break;
                case 4:
                    z3 = zzb.zzc(parcel, zzaX);
                    break;
                case 5:
                    z2 = zzb.zzc(parcel, zzaX);
                    break;
                case 6:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new LocationSettingsStates(z6, z5, z4, z3, z2, z);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkr */
    public LocationSettingsStates[] newArray(int i) {
        return new LocationSettingsStates[i];
    }
}
