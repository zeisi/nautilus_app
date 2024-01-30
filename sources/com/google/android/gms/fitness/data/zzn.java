package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzn implements Parcelable.Creator<Device> {
    static void zza(Device device, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, device.getManufacturer(), false);
        zzc.zza(parcel, 2, device.getModel(), false);
        zzc.zza(parcel, 3, device.getVersion(), false);
        zzc.zza(parcel, 4, device.zzCo(), false);
        zzc.zzc(parcel, 5, device.getType());
        zzc.zzc(parcel, 6, device.zzCm());
        zzc.zzc(parcel, 1000, device.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzea */
    public Device createFromParcel(Parcel parcel) {
        int i = 0;
        String str = null;
        int zzaY = zzb.zzaY(parcel);
        int i2 = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str4 = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    str3 = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, zzaX);
                    break;
                case 4:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 5:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 6:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 1000:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new Device(i3, str4, str3, str2, str, i2, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgH */
    public Device[] newArray(int i) {
        return new Device[i];
    }
}
