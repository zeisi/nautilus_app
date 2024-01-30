package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzr implements Parcelable.Creator<zzq> {
    static void zza(zzq zzq, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzq.getIntent(), i, false);
        zzc.zza(parcel, 2, zzq.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzq.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeC */
    public zzq createFromParcel(Parcel parcel) {
        PendingIntent pendingIntent;
        int zzg;
        IBinder iBinder;
        IBinder iBinder2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        PendingIntent pendingIntent2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    PendingIntent pendingIntent3 = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
                    iBinder = iBinder2;
                    pendingIntent = pendingIntent3;
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, zzaX);
                    pendingIntent = pendingIntent2;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder3 = iBinder2;
                    pendingIntent = pendingIntent2;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder = iBinder2;
                    pendingIntent = pendingIntent2;
                    zzg = i;
                    break;
            }
            i = zzg;
            pendingIntent2 = pendingIntent;
            iBinder2 = iBinder;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzq(i, pendingIntent2, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhp */
    public zzq[] newArray(int i) {
        return new zzq[i];
    }
}
