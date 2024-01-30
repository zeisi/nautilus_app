package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzd implements Parcelable.Creator<BleDevice> {
    static void zza(BleDevice bleDevice, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, bleDevice.getAddress(), false);
        zzc.zza(parcel, 2, bleDevice.getName(), false);
        zzc.zzb(parcel, 3, bleDevice.getSupportedProfiles(), false);
        zzc.zzc(parcel, 4, bleDevice.getDataTypes(), false);
        zzc.zzc(parcel, 1000, bleDevice.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdT */
    public BleDevice createFromParcel(Parcel parcel) {
        ArrayList<DataType> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<String> arrayList2 = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str2 = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
                    arrayList2 = zzb.zzE(parcel, zzaX);
                    break;
                case 4:
                    arrayList = zzb.zzc(parcel, zzaX, DataType.CREATOR);
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
            return new BleDevice(i, str2, str, arrayList2, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgv */
    public BleDevice[] newArray(int i) {
        return new BleDevice[i];
    }
}
