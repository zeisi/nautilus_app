package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.internal.zzapf;

public class zzb extends zza {
    public static final Parcelable.Creator<zzb> CREATOR = new zzc();
    private final String zzaVr;
    private final BleDevice zzaVs;
    private final zzapf zzaVt;
    private final int zzaiI;

    zzb(int i, String str, BleDevice bleDevice, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaVr = str;
        this.zzaVs = bleDevice;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public zzb(String str, BleDevice bleDevice, zzapf zzapf) {
        this.zzaiI = 4;
        this.zzaVr = str;
        this.zzaVs = bleDevice;
        this.zzaVt = zzapf;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public String getDeviceAddress() {
        return this.zzaVr;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return String.format("ClaimBleDeviceRequest{%s %s}", new Object[]{this.zzaVr, this.zzaVs});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }

    public BleDevice zzCL() {
        return this.zzaVs;
    }
}
