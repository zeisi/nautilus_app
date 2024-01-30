package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.internal.zzapf;

public class zzbs extends zza {
    public static final Parcelable.Creator<zzbs> CREATOR = new zzbt();
    private final int versionCode;
    private final zzaj zzaWN;
    private final zzapf zzaWh;

    zzbs(int i, IBinder iBinder, IBinder iBinder2) {
        zzaj zzaj = null;
        this.versionCode = i;
        this.zzaWh = iBinder == null ? null : zzapf.zza.zzcJ(iBinder);
        this.zzaWN = iBinder2 != null ? zzaj.zza.zzcO(iBinder2) : zzaj;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWh == null) {
            return null;
        }
        return this.zzaWh.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbt.zza(this, parcel, i);
    }

    public IBinder zzDo() {
        if (this.zzaWN == null) {
            return null;
        }
        return this.zzaWN.asBinder();
    }
}
