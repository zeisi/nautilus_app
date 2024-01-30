package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Subscription;

public class zzbn implements Parcelable.Creator<zzbm> {
    static void zza(zzbm zzbm, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzbm.zzDm(), i, false);
        zzc.zza(parcel, 2, zzbm.zzDn());
        zzc.zza(parcel, 3, zzbm.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzbm.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfb */
    public zzbm createFromParcel(Parcel parcel) {
        boolean z;
        Subscription subscription;
        int zzg;
        IBinder iBinder;
        IBinder iBinder2 = null;
        boolean z2 = false;
        int zzaY = zzb.zzaY(parcel);
        Subscription subscription2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    boolean z3 = z2;
                    subscription = (Subscription) zzb.zza(parcel, zzaX, Subscription.CREATOR);
                    iBinder = iBinder2;
                    z = z3;
                    break;
                case 2:
                    subscription = subscription2;
                    zzg = i;
                    IBinder iBinder3 = iBinder2;
                    z = zzb.zzc(parcel, zzaX);
                    iBinder = iBinder3;
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, zzaX);
                    z = z2;
                    subscription = subscription2;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder4 = iBinder2;
                    z = z2;
                    subscription = subscription2;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder4;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder = iBinder2;
                    z = z2;
                    subscription = subscription2;
                    zzg = i;
                    break;
            }
            i = zzg;
            subscription2 = subscription;
            z2 = z;
            iBinder2 = iBinder;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzbm(i, subscription2, z2, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhO */
    public zzbm[] newArray(int i) {
        return new zzbm[i];
    }
}
