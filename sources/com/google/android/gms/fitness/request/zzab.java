package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaon;

public class zzab extends zza {
    public static final Parcelable.Creator<zzab> CREATOR = new zzac();
    private final int versionCode;
    private final zzaon zzaVW;

    zzab(int i, IBinder iBinder) {
        this.versionCode = i;
        this.zzaVW = zzaon.zza.zzcr(iBinder);
    }

    public IBinder getCallbackBinder() {
        return this.zzaVW.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzac.zza(this, parcel, i);
    }
}
