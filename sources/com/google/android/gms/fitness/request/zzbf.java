package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzape;

public class zzbf extends zza {
    public static final Parcelable.Creator<zzbf> CREATOR = new zzbg();
    private final String mName;
    private final String zzaUk;
    private final zzape zzaWI;
    private final int zzaiI;

    zzbf(int i, String str, String str2, IBinder iBinder) {
        this.zzaiI = i;
        this.mName = str;
        this.zzaUk = str2;
        this.zzaWI = zzape.zza.zzcI(iBinder);
    }

    public zzbf(String str, String str2, zzape zzape) {
        this.zzaiI = 3;
        this.mName = str;
        this.zzaUk = str2;
        this.zzaWI = zzape;
    }

    private boolean zzb(zzbf zzbf) {
        return zzaa.equal(this.mName, zzbf.mName) && zzaa.equal(this.zzaUk, zzbf.zzaUk);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzbf) && zzb((zzbf) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWI == null) {
            return null;
        }
        return this.zzaWI.asBinder();
    }

    public String getIdentifier() {
        return this.zzaUk;
    }

    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.mName, this.zzaUk);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("name", this.mName).zzg("identifier", this.zzaUk).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbg.zza(this, parcel, i);
    }
}
