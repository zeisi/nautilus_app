package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzr implements Parcelable.Creator<LocationSettingsResult> {
    static void zza(LocationSettingsResult locationSettingsResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) locationSettingsResult.getStatus(), i, false);
        zzc.zza(parcel, 2, (Parcelable) locationSettingsResult.getLocationSettingsStates(), i, false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgQ */
    public LocationSettingsResult createFromParcel(Parcel parcel) {
        LocationSettingsStates locationSettingsStates;
        Status status;
        LocationSettingsStates locationSettingsStates2 = null;
        int zzaY = zzb.zzaY(parcel);
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    LocationSettingsStates locationSettingsStates3 = locationSettingsStates2;
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    locationSettingsStates = locationSettingsStates3;
                    break;
                case 2:
                    locationSettingsStates = (LocationSettingsStates) zzb.zza(parcel, zzaX, LocationSettingsStates.CREATOR);
                    status = status2;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    locationSettingsStates = locationSettingsStates2;
                    status = status2;
                    break;
            }
            status2 = status;
            locationSettingsStates2 = locationSettingsStates;
        }
        if (parcel.dataPosition() == zzaY) {
            return new LocationSettingsResult(status2, locationSettingsStates2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkq */
    public LocationSettingsResult[] newArray(int i) {
        return new LocationSettingsResult[i];
    }
}
