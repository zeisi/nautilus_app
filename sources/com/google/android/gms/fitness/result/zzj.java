package com.google.android.gms.fitness.result;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzj extends zza implements Result {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    private final int versionCode;
    private final Bundle zzaWZ;
    private final Status zzahw;

    zzj(int i, Status status, Bundle bundle) {
        this.versionCode = i;
        this.zzahw = status;
        this.zzaWZ = bundle;
    }

    public Status getStatus() {
        return this.zzahw;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzk.zza(this, parcel, i);
    }

    /* access modifiers changed from: package-private */
    public Bundle zzDu() {
        return this.zzaWZ;
    }
}
