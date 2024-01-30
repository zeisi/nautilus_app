package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import android.support.annotation.Nullable;

public class zza extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    @Nullable
    private String mTag;
    @Nullable
    private String zzaiu;
    private long zzbjo;
    private boolean zzbjp;
    @Nullable
    private WorkSource zzbjq;
    @Nullable
    private int[] zzbjr;
    @Nullable
    private boolean zzbjs;
    private final long zzbjt;

    zza(long j, boolean z, @Nullable WorkSource workSource, @Nullable String str, @Nullable int[] iArr, boolean z2, @Nullable String str2, long j2) {
        this.zzbjo = j;
        this.zzbjp = z;
        this.zzbjq = workSource;
        this.mTag = str;
        this.zzbjr = iArr;
        this.zzbjs = z2;
        this.zzaiu = str2;
        this.zzbjt = j2;
    }

    @Nullable
    public String getAccountName() {
        return this.zzaiu;
    }

    public long getIntervalMillis() {
        return this.zzbjo;
    }

    @Nullable
    public String getTag() {
        return this.mTag;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public boolean zzHW() {
        return this.zzbjp;
    }

    @Nullable
    public WorkSource zzHX() {
        return this.zzbjq;
    }

    @Nullable
    public int[] zzHY() {
        return this.zzbjr;
    }

    public boolean zzHZ() {
        return this.zzbjs;
    }

    public long zzIa() {
        return this.zzbjt;
    }
}
