package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzapf;

public class zzbq extends zza {
    public static final Parcelable.Creator<zzbq> CREATOR = new zzbr();
    private final DataSource zzaTi;
    private final DataType zzaTj;
    private final zzapf zzaVt;
    private final int zzaiI;

    zzbq(int i, DataType dataType, DataSource dataSource, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaTj = dataType;
        this.zzaTi = dataSource;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public zzbq(DataType dataType, DataSource dataSource, zzapf zzapf) {
        this.zzaiI = 3;
        this.zzaTj = dataType;
        this.zzaTi = dataSource;
        this.zzaVt = zzapf;
    }

    private boolean zzb(zzbq zzbq) {
        return zzaa.equal(this.zzaTi, zzbq.zzaTi) && zzaa.equal(this.zzaTj, zzbq.zzaTj);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzbq) && zzb((zzbq) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public DataSource getDataSource() {
        return this.zzaTi;
    }

    public DataType getDataType() {
        return this.zzaTj;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaTi, this.zzaTj);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbr.zza(this, parcel, i);
    }
}
