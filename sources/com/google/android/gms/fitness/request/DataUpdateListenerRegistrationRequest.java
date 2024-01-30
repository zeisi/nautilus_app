package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzapf;

public class DataUpdateListenerRegistrationRequest extends zza {
    public static final Parcelable.Creator<DataUpdateListenerRegistrationRequest> CREATOR = new zzp();
    private final PendingIntent mPendingIntent;
    private DataSource zzaTi;
    private DataType zzaTj;
    private final zzapf zzaVt;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public PendingIntent mPendingIntent;
        /* access modifiers changed from: private */
        public DataSource zzaTi;
        /* access modifiers changed from: private */
        public DataType zzaTj;

        public DataUpdateListenerRegistrationRequest build() {
            zzac.zza((this.zzaTi == null && this.zzaTj == null) ? false : true, (Object) "Set either dataSource or dataTYpe");
            zzac.zzb(this.mPendingIntent, (Object) "pendingIntent must be set");
            return new DataUpdateListenerRegistrationRequest(this);
        }

        public Builder setDataSource(DataSource dataSource) throws NullPointerException {
            zzac.zzw(dataSource);
            this.zzaTi = dataSource;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            zzac.zzw(dataType);
            this.zzaTj = dataType;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            zzac.zzw(pendingIntent);
            this.mPendingIntent = pendingIntent;
            return this;
        }
    }

    DataUpdateListenerRegistrationRequest(int i, DataSource dataSource, DataType dataType, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaTi = dataSource;
        this.zzaTj = dataType;
        this.mPendingIntent = pendingIntent;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public DataUpdateListenerRegistrationRequest(DataSource dataSource, DataType dataType, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzaiI = 1;
        this.zzaTi = dataSource;
        this.zzaTj = dataType;
        this.mPendingIntent = pendingIntent;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    private DataUpdateListenerRegistrationRequest(Builder builder) {
        this(builder.zzaTi, builder.zzaTj, builder.mPendingIntent, (IBinder) null);
    }

    public DataUpdateListenerRegistrationRequest(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest, IBinder iBinder) {
        this(dataUpdateListenerRegistrationRequest.zzaTi, dataUpdateListenerRegistrationRequest.zzaTj, dataUpdateListenerRegistrationRequest.mPendingIntent, iBinder);
    }

    private boolean zzb(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        return zzaa.equal(this.zzaTi, dataUpdateListenerRegistrationRequest.zzaTi) && zzaa.equal(this.zzaTj, dataUpdateListenerRegistrationRequest.zzaTj) && zzaa.equal(this.mPendingIntent, dataUpdateListenerRegistrationRequest.mPendingIntent);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof DataUpdateListenerRegistrationRequest) && zzb((DataUpdateListenerRegistrationRequest) obj));
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

    public PendingIntent getIntent() {
        return this.mPendingIntent;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaTi, this.zzaTj, this.mPendingIntent);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("dataSource", this.zzaTi).zzg("dataType", this.zzaTj).zzg("pendingIntent", this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzp.zza(this, parcel, i);
    }
}
