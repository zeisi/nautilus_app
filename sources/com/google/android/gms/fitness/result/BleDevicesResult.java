package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BleDevicesResult extends zza implements Result {
    public static final Parcelable.Creator<BleDevicesResult> CREATOR = new zza();
    private final List<BleDevice> zzaWO;
    private final int zzaiI;
    private final Status zzair;

    BleDevicesResult(int i, List<BleDevice> list, Status status) {
        this.zzaiI = i;
        this.zzaWO = Collections.unmodifiableList(list);
        this.zzair = status;
    }

    public BleDevicesResult(List<BleDevice> list, Status status) {
        this.zzaiI = 3;
        this.zzaWO = Collections.unmodifiableList(list);
        this.zzair = status;
    }

    public static BleDevicesResult zzad(Status status) {
        return new BleDevicesResult(Collections.emptyList(), status);
    }

    private boolean zzb(BleDevicesResult bleDevicesResult) {
        return this.zzair.equals(bleDevicesResult.zzair) && zzaa.equal(this.zzaWO, bleDevicesResult.zzaWO);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof BleDevicesResult) && zzb((BleDevicesResult) obj));
    }

    public List<BleDevice> getClaimedBleDevices() {
        return this.zzaWO;
    }

    public List<BleDevice> getClaimedBleDevices(DataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (BleDevice next : this.zzaWO) {
            if (next.getDataTypes().contains(dataType)) {
                arrayList.add(next);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Status getStatus() {
        return this.zzair;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzair, this.zzaWO);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("status", this.zzair).zzg("bleDevices", this.zzaWO).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }
}
