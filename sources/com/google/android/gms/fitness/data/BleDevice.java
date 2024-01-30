package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzans;
import java.util.Collections;
import java.util.List;

public class BleDevice extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<BleDevice> CREATOR = new zzd();
    private final String mName;
    private final String zzaSq;
    private final List<String> zzaSr;
    private final List<DataType> zzaSs;
    private final int zzaiI;

    BleDevice(int i, String str, String str2, List<String> list, List<DataType> list2) {
        this.zzaiI = i;
        this.zzaSq = str;
        this.mName = str2;
        this.zzaSr = Collections.unmodifiableList(list);
        this.zzaSs = Collections.unmodifiableList(list2);
    }

    private boolean zza(BleDevice bleDevice) {
        return this.mName.equals(bleDevice.mName) && this.zzaSq.equals(bleDevice.zzaSq) && zzans.zza(bleDevice.zzaSr, this.zzaSr) && zzans.zza(this.zzaSs, bleDevice.zzaSs);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof BleDevice) && zza((BleDevice) obj));
    }

    public String getAddress() {
        return this.zzaSq;
    }

    public List<DataType> getDataTypes() {
        return this.zzaSs;
    }

    public String getName() {
        return this.mName;
    }

    public List<String> getSupportedProfiles() {
        return this.zzaSr;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.mName, this.zzaSq, this.zzaSr, this.zzaSs);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("name", this.mName).zzg("address", this.zzaSq).zzg("dataTypes", this.zzaSs).zzg("supportedProfiles", this.zzaSr).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }
}
