package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzaol;

public class zzd extends zza {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    private final int versionCode;
    private DataType zzaSg;
    private final zzaol zzaVu;
    private final boolean zzaVv;

    zzd(int i, IBinder iBinder, DataType dataType, boolean z) {
        this.versionCode = i;
        this.zzaVu = zzaol.zza.zzcp(iBinder);
        this.zzaSg = dataType;
        this.zzaVv = z;
    }

    public zzd(zzaol zzaol, DataType dataType, boolean z) {
        this.versionCode = 3;
        this.zzaVu = zzaol;
        this.zzaSg = dataType;
        this.zzaVv = z;
    }

    public IBinder getCallbackBinder() {
        return this.zzaVu.asBinder();
    }

    public DataType getDataType() {
        return this.zzaSg;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public String toString() {
        Object[] objArr = new Object[1];
        objArr[0] = this.zzaSg == null ? "null" : this.zzaSg.zzCj();
        return String.format("DailyTotalRequest{%s}", objArr);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }

    public boolean zzCM() {
        return this.zzaVv;
    }
}
