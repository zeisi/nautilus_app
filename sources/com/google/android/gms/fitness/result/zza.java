package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.BleDevice;
import java.util.ArrayList;

public class zza implements Parcelable.Creator<BleDevicesResult> {
    static void zza(BleDevicesResult bleDevicesResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, bleDevicesResult.getClaimedBleDevices(), false);
        zzc.zza(parcel, 2, (Parcelable) bleDevicesResult.getStatus(), i, false);
        zzc.zzc(parcel, 1000, bleDevicesResult.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzff */
    public BleDevicesResult createFromParcel(Parcel parcel) {
        Status status = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<BleDevice> arrayList = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList = zzb.zzc(parcel, zzaX, BleDevice.CREATOR);
                    break;
                case 2:
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new BleDevicesResult(i, arrayList, status);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhS */
    public BleDevicesResult[] newArray(int i) {
        return new BleDevicesResult[i];
    }
}
