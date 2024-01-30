package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzapf;

public class zzbh extends zza {
    public static final Parcelable.Creator<zzbh> CREATOR = new zzbi();
    private final PendingIntent mPendingIntent;
    private final zzapf zzaVt;
    private final int zzaiI;

    zzbh(int i, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzaiI = i;
        this.mPendingIntent = pendingIntent;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public zzbh(PendingIntent pendingIntent, zzapf zzapf) {
        this.zzaiI = 5;
        this.mPendingIntent = pendingIntent;
        this.zzaVt = zzapf;
    }

    private boolean zzb(zzbh zzbh) {
        return zzaa.equal(this.mPendingIntent, zzbh.mPendingIntent);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzbh) && zzb((zzbh) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public PendingIntent getIntent() {
        return this.mPendingIntent;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.mPendingIntent);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("pendingIntent", this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbi.zza(this, parcel, i);
    }
}
