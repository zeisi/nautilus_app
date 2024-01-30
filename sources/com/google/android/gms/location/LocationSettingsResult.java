package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class LocationSettingsResult extends zza implements Result {
    public static final Parcelable.Creator<LocationSettingsResult> CREATOR = new zzr();
    private final Status zzair;
    private final LocationSettingsStates zzbkm;

    public LocationSettingsResult(Status status) {
        this(status, (LocationSettingsStates) null);
    }

    public LocationSettingsResult(Status status, LocationSettingsStates locationSettingsStates) {
        this.zzair = status;
        this.zzbkm = locationSettingsStates;
    }

    public LocationSettingsStates getLocationSettingsStates() {
        return this.zzbkm;
    }

    public Status getStatus() {
        return this.zzair;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzr.zza(this, parcel, i);
    }
}
