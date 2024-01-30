package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;

public class zzd extends zza {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    private final int zzaSu;
    private final int zzbjy;

    zzd(int i, int i2) {
        this.zzaSu = i;
        this.zzbjy = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzd)) {
            return false;
        }
        zzd zzd = (zzd) obj;
        return this.zzaSu == zzd.zzaSu && this.zzbjy == zzd.zzbjy;
    }

    public int hashCode() {
        return zzaa.hashCode(Integer.valueOf(this.zzaSu), Integer.valueOf(this.zzbjy));
    }

    public String toString() {
        int i = this.zzaSu;
        return new StringBuilder(75).append("ActivityTransition [mActivityType=").append(i).append(", mTransitionType=").append(this.zzbjy).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }

    public int zzBV() {
        return this.zzaSu;
    }

    public int zzIb() {
        return this.zzbjy;
    }
}
