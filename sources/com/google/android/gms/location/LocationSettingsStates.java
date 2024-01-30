package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class LocationSettingsStates extends zza {
    public static final Parcelable.Creator<LocationSettingsStates> CREATOR = new zzs();
    private final boolean zzbkn;
    private final boolean zzbko;
    private final boolean zzbkp;
    private final boolean zzbkq;
    private final boolean zzbkr;
    private final boolean zzbks;

    public LocationSettingsStates(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.zzbkn = z;
        this.zzbko = z2;
        this.zzbkp = z3;
        this.zzbkq = z4;
        this.zzbkr = z5;
        this.zzbks = z6;
    }

    public static LocationSettingsStates fromIntent(Intent intent) {
        return (LocationSettingsStates) zzd.zza(intent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
    }

    public boolean isBlePresent() {
        return this.zzbks;
    }

    public boolean isBleUsable() {
        return this.zzbkp;
    }

    public boolean isGpsPresent() {
        return this.zzbkq;
    }

    public boolean isGpsUsable() {
        return this.zzbkn;
    }

    public boolean isLocationPresent() {
        return this.zzbkq || this.zzbkr;
    }

    public boolean isLocationUsable() {
        return this.zzbkn || this.zzbko;
    }

    public boolean isNetworkLocationPresent() {
        return this.zzbkr;
    }

    public boolean isNetworkLocationUsable() {
        return this.zzbko;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzs.zza(this, parcel, i);
    }
}
