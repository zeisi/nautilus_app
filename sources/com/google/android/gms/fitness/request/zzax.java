package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.zzs;
import com.google.android.gms.internal.zzapf;

public class zzax extends zza {
    public static final Parcelable.Creator<zzax> CREATOR = new zzay();
    private final PendingIntent mPendingIntent;
    private final zzapf zzaVt;
    private final zzs zzaWo;
    private final int zzaiI;

    zzax(int i, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2) {
        this.zzaiI = i;
        this.zzaWo = iBinder == null ? null : zzs.zza.zzcg(iBinder);
        this.mPendingIntent = pendingIntent;
        this.zzaVt = zzapf.zza.zzcJ(iBinder2);
    }

    public zzax(zzs zzs, PendingIntent pendingIntent, zzapf zzapf) {
        this.zzaiI = 4;
        this.zzaWo = zzs;
        this.mPendingIntent = pendingIntent;
        this.zzaVt = zzapf;
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

    public String toString() {
        return String.format("SensorUnregistrationRequest{%s}", new Object[]{this.zzaWo});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzay.zza(this, parcel, i);
    }

    /* access modifiers changed from: package-private */
    public IBinder zzDg() {
        if (this.zzaWo == null) {
            return null;
        }
        return this.zzaWo.asBinder();
    }
}
