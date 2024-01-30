package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzbg implements Parcelable.Creator<zzbf> {
    static void zza(zzbf zzbf, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzbf.getName(), false);
        zzc.zza(parcel, 2, zzbf.getIdentifier(), false);
        zzc.zza(parcel, 3, zzbf.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzbf.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeX */
    public zzbf createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
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
                    iBinder = zzb.zzr(parcel, zzaX);
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
            return new zzbf(i, str2, str, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhK */
    public zzbf[] newArray(int i) {
        return new zzbf[i];
    }
}
