package com.google.android.gms.fitness.result;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;

public class zzl extends zza implements Result {
    public static final Parcelable.Creator<zzl> CREATOR = new zzm();
    private final Uri uri;
    private final int versionCode;
    private final Status zzahw;

    zzl(int i, Uri uri2, Status status) {
        this.versionCode = i;
        this.uri = uri2;
        this.zzahw = status;
    }

    private boolean zzb(zzl zzl) {
        return this.zzahw.equals(zzl.zzahw) && zzaa.equal(this.uri, zzl.uri);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzl) && zzb((zzl) obj));
    }

    public Status getStatus() {
        return this.zzahw;
    }

    public Uri getUri() {
        return this.uri;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzahw, this.uri);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("status", this.zzahw).zzg("uri", this.uri).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }
}
