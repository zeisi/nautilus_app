package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.BleDevice;

public class zzc implements Parcelable.Creator<zzb> {
    static void zza(zzb zzb, Parcel parcel, int i) {
        int zzaZ = com.google.android.gms.common.internal.safeparcel.zzc.zzaZ(parcel);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 1, zzb.getDeviceAddress(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 2, (Parcelable) zzb.zzCL(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 3, zzb.getCallbackBinder(), false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 1000, zzb.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzes */
    public zzb createFromParcel(Parcel parcel) {
        BleDevice bleDevice;
        String str;
        int zzg;
        IBinder iBinder;
        IBinder iBinder2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        BleDevice bleDevice2 = null;
        String str2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    BleDevice bleDevice3 = bleDevice2;
                    str = zzb.zzq(parcel, zzaX);
                    iBinder = iBinder2;
                    bleDevice = bleDevice3;
                    break;
                case 2:
                    str = str2;
                    zzg = i;
                    IBinder iBinder3 = iBinder2;
                    bleDevice = (BleDevice) zzb.zza(parcel, zzaX, BleDevice.CREATOR);
                    iBinder = iBinder3;
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, zzaX);
                    bleDevice = bleDevice2;
                    str = str2;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder4 = iBinder2;
                    bleDevice = bleDevice2;
                    str = str2;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder4;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder = iBinder2;
                    bleDevice = bleDevice2;
                    str = str2;
                    zzg = i;
                    break;
            }
            i = zzg;
            str2 = str;
            bleDevice2 = bleDevice;
            iBinder2 = iBinder;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzb(i, str2, bleDevice2, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhf */
    public zzb[] newArray(int i) {
        return new zzb[i];
    }
}
