package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;

public class zzv extends zza implements Result {
    public static final Parcelable.Creator<zzv> CREATOR = new zzw();
    private final int versionCode;
    private final long zzaXf;
    private final Status zzahw;

    zzv(int i, Status status, long j) {
        this.versionCode = i;
        this.zzahw = status;
        this.zzaXf = j;
    }

    private boolean zzb(zzv zzv) {
        return this.zzahw.equals(zzv.zzahw) && this.zzaXf == zzv.zzaXf;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzv) && zzb((zzv) obj));
    }

    public Status getStatus() {
        return this.zzahw;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzahw, Long.valueOf(this.zzaXf));
    }

    public String toString() {
        return zzaa.zzv(this).zzg("status", this.zzahw).zzg("lastSuccessfulSyncTimeMillis", Long.valueOf(this.zzaXf)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzw.zza(this, parcel, i);
    }

    public long zzDz() {
        return this.zzaXf;
    }
}
