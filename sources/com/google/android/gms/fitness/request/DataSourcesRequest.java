package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzaon;
import java.util.Arrays;
import java.util.List;

public class DataSourcesRequest extends zza {
    public static final Parcelable.Creator<DataSourcesRequest> CREATOR = new zzl();
    private final List<DataType> zzaSs;
    private final List<Integer> zzaVN;
    private final boolean zzaVO;
    private final zzaon zzaVP;
    private final int zzaiI;

    public static class Builder {
        private boolean zzaVO = false;
        /* access modifiers changed from: private */
        public DataType[] zzaVQ = new DataType[0];
        /* access modifiers changed from: private */
        public int[] zzaVR = {0, 1};

        public DataSourcesRequest build() {
            boolean z = true;
            zzac.zza(this.zzaVQ.length > 0, (Object) "Must add at least one data type");
            if (this.zzaVR.length <= 0) {
                z = false;
            }
            zzac.zza(z, (Object) "Must add at least one data source type");
            return new DataSourcesRequest(this);
        }

        public Builder setDataSourceTypes(int... iArr) {
            this.zzaVR = iArr;
            return this;
        }

        public Builder setDataTypes(DataType... dataTypeArr) {
            this.zzaVQ = dataTypeArr;
            return this;
        }
    }

    DataSourcesRequest(int i, List<DataType> list, List<Integer> list2, boolean z, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaSs = list;
        this.zzaVN = list2;
        this.zzaVO = z;
        this.zzaVP = zzaon.zza.zzcr(iBinder);
    }

    private DataSourcesRequest(Builder builder) {
        this(zzb.zzb(builder.zzaVQ), Arrays.asList(zzb.zza(builder.zzaVR)), false, (zzaon) null);
    }

    public DataSourcesRequest(DataSourcesRequest dataSourcesRequest, zzaon zzaon) {
        this(dataSourcesRequest.zzaSs, dataSourcesRequest.zzaVN, dataSourcesRequest.zzaVO, zzaon);
    }

    public DataSourcesRequest(List<DataType> list, List<Integer> list2, boolean z, zzaon zzaon) {
        this.zzaiI = 4;
        this.zzaSs = list;
        this.zzaVN = list2;
        this.zzaVO = z;
        this.zzaVP = zzaon;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVP == null) {
            return null;
        }
        return this.zzaVP.asBinder();
    }

    public List<DataType> getDataTypes() {
        return this.zzaSs;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        zzaa.zza zzg = zzaa.zzv(this).zzg("dataTypes", this.zzaSs).zzg("sourceTypes", this.zzaVN);
        if (this.zzaVO) {
            zzg.zzg("includeDbOnlySources", "true");
        }
        return zzg.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzl.zza(this, parcel, i);
    }

    public List<Integer> zzCV() {
        return this.zzaVN;
    }

    public boolean zzCW() {
        return this.zzaVO;
    }
}
