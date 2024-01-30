package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzasl implements Parcelable.Creator<zzask> {
    static void zza(zzask zzask, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, zzask.zzbkT);
        zzc.zza(parcel, 2, (Parcelable) zzask.zzbkU, i, false);
        zzc.zza(parcel, 3, zzask.zzIq(), false);
        zzc.zza(parcel, 4, (Parcelable) zzask.mPendingIntent, i, false);
        zzc.zza(parcel, 5, zzask.zzIr(), false);
        zzc.zza(parcel, 6, zzask.zzIs(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgW */
    public zzask createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 1;
        IBinder iBinder2 = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder3 = null;
        zzasi zzasi = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 2:
                    zzasi = (zzasi) zzb.zza(parcel, zzaX, zzasi.CREATOR);
                    break;
                case 3:
                    iBinder3 = zzb.zzr(parcel, zzaX);
                    break;
                case 4:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
                    break;
                case 5:
                    iBinder2 = zzb.zzr(parcel, zzaX);
                    break;
                case 6:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzask(i, zzasi, iBinder3, pendingIntent, iBinder2, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkz */
    public zzask[] newArray(int i) {
        return new zzask[i];
    }
}
