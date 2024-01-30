package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaop;

public class zzt extends zza {
    public static final Parcelable.Creator<zzt> CREATOR = new zzy();
    private final int versionCode;
    private final zzaop zzaVU;

    zzt(int i, IBinder iBinder) {
        this.versionCode = i;
        this.zzaVU = zzaop.zza.zzct(iBinder);
    }

    public IBinder getCallbackBinder() {
        return this.zzaVU.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzy.zza(this, parcel, i);
    }
}
