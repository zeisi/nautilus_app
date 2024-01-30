package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.internal.zzaoo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataTypeCreateRequest extends zza {
    public static final Parcelable.Creator<DataTypeCreateRequest> CREATOR = new zzm();
    private final String mName;
    private final List<Field> zzaVS;
    private final zzaoo zzaVT;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public String mName;
        /* access modifiers changed from: private */
        public List<Field> zzaVS = new ArrayList();

        public Builder addField(Field field) {
            if (!this.zzaVS.contains(field)) {
                this.zzaVS.add(field);
            }
            return this;
        }

        public Builder addField(String str, int i) {
            zzac.zzb(str != null && !str.isEmpty(), (Object) "Invalid name specified");
            return addField(Field.zzn(str, i));
        }

        public DataTypeCreateRequest build() {
            boolean z = true;
            zzac.zza(this.mName != null, (Object) "Must set the name");
            if (this.zzaVS.isEmpty()) {
                z = false;
            }
            zzac.zza(z, (Object) "Must specify the data fields");
            return new DataTypeCreateRequest(this);
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }
    }

    DataTypeCreateRequest(int i, String str, List<Field> list, IBinder iBinder) {
        this.zzaiI = i;
        this.mName = str;
        this.zzaVS = Collections.unmodifiableList(list);
        this.zzaVT = zzaoo.zza.zzcs(iBinder);
    }

    private DataTypeCreateRequest(Builder builder) {
        this(builder.mName, builder.zzaVS, (zzaoo) null);
    }

    public DataTypeCreateRequest(DataTypeCreateRequest dataTypeCreateRequest, zzaoo zzaoo) {
        this(dataTypeCreateRequest.mName, dataTypeCreateRequest.zzaVS, zzaoo);
    }

    public DataTypeCreateRequest(String str, List<Field> list, zzaoo zzaoo) {
        this.zzaiI = 3;
        this.mName = str;
        this.zzaVS = Collections.unmodifiableList(list);
        this.zzaVT = zzaoo;
    }

    private boolean zzb(DataTypeCreateRequest dataTypeCreateRequest) {
        return zzaa.equal(this.mName, dataTypeCreateRequest.mName) && zzaa.equal(this.zzaVS, dataTypeCreateRequest.zzaVS);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof DataTypeCreateRequest) && zzb((DataTypeCreateRequest) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVT == null) {
            return null;
        }
        return this.zzaVT.asBinder();
    }

    public List<Field> getFields() {
        return this.zzaVS;
    }

    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.mName, this.zzaVS);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("name", this.mName).zzg("fields", this.zzaVS).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }
}
