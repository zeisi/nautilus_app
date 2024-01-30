package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzasc;
import com.google.android.gms.location.zzj;
import com.google.android.gms.location.zzk;

public class zzask extends zza {
    public static final Parcelable.Creator<zzask> CREATOR = new zzasl();
    PendingIntent mPendingIntent;
    int zzbkT;
    zzasi zzbkU;
    zzk zzbkV;
    zzj zzbkW;
    zzasc zzbkX;

    zzask(int i, zzasi zzasi, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        zzasc zzasc = null;
        this.zzbkT = i;
        this.zzbkU = zzasi;
        this.zzbkV = iBinder == null ? null : zzk.zza.zzde(iBinder);
        this.mPendingIntent = pendingIntent;
        this.zzbkW = iBinder2 == null ? null : zzj.zza.zzdd(iBinder2);
        this.zzbkX = iBinder3 != null ? zzasc.zza.zzdg(iBinder3) : zzasc;
    }

    public static zzask zza(zzasi zzasi, PendingIntent pendingIntent, @Nullable zzasc zzasc) {
        return new zzask(1, zzasi, (IBinder) null, pendingIntent, (IBinder) null, zzasc != null ? zzasc.asBinder() : null);
    }

    public static zzask zza(zzasi zzasi, zzj zzj, @Nullable zzasc zzasc) {
        return new zzask(1, zzasi, (IBinder) null, (PendingIntent) null, zzj.asBinder(), zzasc != null ? zzasc.asBinder() : null);
    }

    public static zzask zza(zzasi zzasi, zzk zzk, @Nullable zzasc zzasc) {
        return new zzask(1, zzasi, zzk.asBinder(), (PendingIntent) null, (IBinder) null, zzasc != null ? zzasc.asBinder() : null);
    }

    public static zzask zza(zzj zzj, @Nullable zzasc zzasc) {
        return new zzask(2, (zzasi) null, (IBinder) null, (PendingIntent) null, zzj.asBinder(), zzasc != null ? zzasc.asBinder() : null);
    }

    public static zzask zza(zzk zzk, @Nullable zzasc zzasc) {
        return new zzask(2, (zzasi) null, zzk.asBinder(), (PendingIntent) null, (IBinder) null, zzasc != null ? zzasc.asBinder() : null);
    }

    public static zzask zzb(PendingIntent pendingIntent, @Nullable zzasc zzasc) {
        return new zzask(2, (zzasi) null, (IBinder) null, pendingIntent, (IBinder) null, zzasc != null ? zzasc.asBinder() : null);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzasl.zza(this, parcel, i);
    }

    /* access modifiers changed from: package-private */
    public IBinder zzIq() {
        if (this.zzbkV == null) {
            return null;
        }
        return this.zzbkV.asBinder();
    }

    /* access modifiers changed from: package-private */
    public IBinder zzIr() {
        if (this.zzbkW == null) {
            return null;
        }
        return this.zzbkW.asBinder();
    }

    /* access modifiers changed from: package-private */
    public IBinder zzIs() {
        if (this.zzbkX == null) {
            return null;
        }
        return this.zzbkX.asBinder();
    }
}
