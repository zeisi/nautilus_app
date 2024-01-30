package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zza;
import com.google.android.gms.fitness.request.zzai;
import com.google.android.gms.internal.zzapf;
import java.util.Collections;
import java.util.List;

public class StartBleScanRequest extends zza {
    public static final Parcelable.Creator<StartBleScanRequest> CREATOR = new zzbj();
    private final List<DataType> zzaSs;
    private final zzapf zzaVt;
    private final zzai zzaWJ;
    private final int zzaWK;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public DataType[] zzaVQ = new DataType[0];
        /* access modifiers changed from: private */
        public zzai zzaWJ;
        /* access modifiers changed from: private */
        public int zzaWK = 10;

        public StartBleScanRequest build() {
            zzac.zza(this.zzaWJ != null, (Object) "Must set BleScanCallback");
            return new StartBleScanRequest(this);
        }

        public Builder setBleScanCallback(BleScanCallback bleScanCallback) {
            zza((zzai) zza.C0018zza.zzCK().zza(bleScanCallback));
            return this;
        }

        public Builder setDataTypes(DataType... dataTypeArr) {
            this.zzaVQ = dataTypeArr;
            return this;
        }

        public Builder setTimeoutSecs(int i) {
            boolean z = true;
            zzac.zzb(i > 0, (Object) "Stop time must be greater than zero");
            if (i > 60) {
                z = false;
            }
            zzac.zzb(z, (Object) "Stop time must be less than 1 minute");
            this.zzaWK = i;
            return this;
        }

        public Builder zza(zzai zzai) {
            this.zzaWJ = zzai;
            return this;
        }
    }

    StartBleScanRequest(int i, List<DataType> list, IBinder iBinder, int i2, IBinder iBinder2) {
        this.zzaiI = i;
        this.zzaSs = list;
        this.zzaWJ = zzai.zza.zzcN(iBinder);
        this.zzaWK = i2;
        this.zzaVt = zzapf.zza.zzcJ(iBinder2);
    }

    private StartBleScanRequest(Builder builder) {
        this(zzb.zzb(builder.zzaVQ), builder.zzaWJ, builder.zzaWK, (zzapf) null);
    }

    public StartBleScanRequest(StartBleScanRequest startBleScanRequest, zzapf zzapf) {
        this(startBleScanRequest.zzaSs, startBleScanRequest.zzaWJ, startBleScanRequest.zzaWK, zzapf);
    }

    public StartBleScanRequest(List<DataType> list, zzai zzai, int i, zzapf zzapf) {
        this.zzaiI = 4;
        this.zzaSs = list;
        this.zzaWJ = zzai;
        this.zzaWK = i;
        this.zzaVt = zzapf;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public List<DataType> getDataTypes() {
        return Collections.unmodifiableList(this.zzaSs);
    }

    public int getTimeoutSecs() {
        return this.zzaWK;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("dataTypes", this.zzaSs).zzg("timeoutSecs", Integer.valueOf(this.zzaWK)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbj.zza(this, parcel, i);
    }

    public IBinder zzDl() {
        return this.zzaWJ.asBinder();
    }
}
