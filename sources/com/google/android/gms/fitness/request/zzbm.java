package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzapf;

public class zzbm extends zza {
    public static final Parcelable.Creator<zzbm> CREATOR = new zzbn();
    private final zzapf zzaVt;
    private Subscription zzaWL;
    private final boolean zzaWM;
    private final int zzaiI;

    zzbm(int i, Subscription subscription, boolean z, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaWL = subscription;
        this.zzaWM = z;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public zzbm(Subscription subscription, boolean z, zzapf zzapf) {
        this.zzaiI = 3;
        this.zzaWL = subscription;
        this.zzaWM = z;
        this.zzaVt = zzapf;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("subscription", this.zzaWL).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbn.zza(this, parcel, i);
    }

    public Subscription zzDm() {
        return this.zzaWL;
    }

    public boolean zzDn() {
        return this.zzaWM;
    }
}
