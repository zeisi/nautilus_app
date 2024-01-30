package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;

public class zzt extends zza implements Result {
    public static final Parcelable.Creator<zzt> CREATOR = new zzu();
    private final long timestamp;
    private final int versionCode;
    private final Boolean zzaXe;
    private final Status zzahw;

    zzt(int i, Status status, long j, Boolean bool) {
        this.versionCode = i;
        this.zzahw = status;
        this.timestamp = j;
        this.zzaXe = bool;
    }

    private boolean zzb(zzt zzt) {
        return this.zzahw.equals(zzt.zzahw) && zzaa.equal(Long.valueOf(this.timestamp), Long.valueOf(zzt.timestamp));
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzt) && zzb((zzt) obj));
    }

    public Status getStatus() {
        return this.zzahw;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzahw, Long.valueOf(this.timestamp));
    }

    public String toString() {
        return zzaa.zzv(this).zzg("status", this.zzahw).zzg("timestamp", Long.valueOf(this.timestamp)).zzg("syncEnabled", this.zzaXe).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzu.zza(this, parcel, i);
    }

    public long zzDx() {
        return this.timestamp;
    }

    public Boolean zzDy() {
        return this.zzaXe;
    }
}
