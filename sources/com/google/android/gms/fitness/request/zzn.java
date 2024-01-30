package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzaoo;

public class zzn extends zza {
    public static final Parcelable.Creator<zzn> CREATOR = new zzo();
    private final String mName;
    private final zzaoo zzaVT;
    private final int zzaiI;

    zzn(int i, String str, IBinder iBinder) {
        this.zzaiI = i;
        this.mName = str;
        this.zzaVT = zzaoo.zza.zzcs(iBinder);
    }

    public zzn(String str, zzaoo zzaoo) {
        this.zzaiI = 3;
        this.mName = str;
        this.zzaVT = zzaoo;
    }

    private boolean zzb(zzn zzn) {
        return zzaa.equal(this.mName, zzn.mName);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzn) && zzb((zzn) obj));
    }

    public IBinder getCallbackBinder() {
        return this.zzaVT.asBinder();
    }

    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.mName);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("name", this.mName).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }
}
