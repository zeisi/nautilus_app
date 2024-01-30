package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaoq;
import java.util.Locale;

public class zzz extends zza {
    public static final Parcelable.Creator<zzz> CREATOR = new zzaa();
    private final int versionCode;
    private final zzaoq zzaVV;

    zzz(int i, IBinder iBinder) {
        this.versionCode = i;
        this.zzaVV = zzaoq.zza.zzcu(iBinder);
    }

    public IBinder getCallbackBinder() {
        return this.zzaVV.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "GetFileUriRequest {%d, %s}", new Object[]{Integer.valueOf(this.versionCode), this.zzaVV});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaa.zza(this, parcel, i);
    }
}
