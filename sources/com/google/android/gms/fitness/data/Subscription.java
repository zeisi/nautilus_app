package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;

public class Subscription extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<Subscription> CREATOR = new zzac();
    private final DataSource zzaTi;
    private final DataType zzaTj;
    private final long zzaUo;
    private final int zzaUp;
    private final int zzaiI;

    public static class zza {
        /* access modifiers changed from: private */
        public DataSource zzaTi;
        /* access modifiers changed from: private */
        public DataType zzaTj;
        /* access modifiers changed from: private */
        public long zzaUo = -1;
        /* access modifiers changed from: private */
        public int zzaUp = 2;

        public Subscription zzCA() {
            boolean z = false;
            zzac.zza((this.zzaTi == null && this.zzaTj == null) ? false : true, (Object) "Must call setDataSource() or setDataType()");
            if (this.zzaTj == null || this.zzaTi == null || this.zzaTj.equals(this.zzaTi.getDataType())) {
                z = true;
            }
            zzac.zza(z, (Object) "Specified data type is incompatible with specified data source");
            return new Subscription(this);
        }

        public zza zzb(DataSource dataSource) {
            this.zzaTi = dataSource;
            return this;
        }

        public zza zzd(DataType dataType) {
            this.zzaTj = dataType;
            return this;
        }
    }

    Subscription(int i, DataSource dataSource, DataType dataType, long j, int i2) {
        this.zzaiI = i;
        this.zzaTi = dataSource;
        this.zzaTj = dataType;
        this.zzaUo = j;
        this.zzaUp = i2;
    }

    private Subscription(zza zza2) {
        this.zzaiI = 1;
        this.zzaTj = zza2.zzaTj;
        this.zzaTi = zza2.zzaTi;
        this.zzaUo = zza2.zzaUo;
        this.zzaUp = zza2.zzaUp;
    }

    private boolean zza(Subscription subscription) {
        return zzaa.equal(this.zzaTi, subscription.zzaTi) && zzaa.equal(this.zzaTj, subscription.zzaTj) && this.zzaUo == subscription.zzaUo && this.zzaUp == subscription.zzaUp;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof Subscription) && zza((Subscription) obj));
    }

    public int getAccuracyMode() {
        return this.zzaUp;
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
        return zzaa.hashCode(this.zzaTi, this.zzaTi, Long.valueOf(this.zzaUo), Integer.valueOf(this.zzaUp));
    }

    public String toDebugString() {
        Object[] objArr = new Object[1];
        objArr[0] = this.zzaTi == null ? this.zzaTj.getName() : this.zzaTi.toDebugString();
        return String.format("Subscription{%s}", objArr);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("dataSource", this.zzaTi).zzg("dataType", this.zzaTj).zzg("samplingIntervalMicros", Long.valueOf(this.zzaUo)).zzg("accuracyMode", Integer.valueOf(this.zzaUp)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzac.zza(this, parcel, i);
    }

    public long zzCy() {
        return this.zzaUo;
    }

    public DataType zzCz() {
        return this.zzaTj == null ? this.zzaTi.getDataType() : this.zzaTj;
    }
}
