package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzay implements Parcelable.Creator<zzax> {
    static void zza(zzax zzax, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzax.zzDg(), false);
        zzc.zza(parcel, 2, (Parcelable) zzax.getIntent(), i, false);
        zzc.zza(parcel, 3, zzax.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzax.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeS */
    public zzax createFromParcel(Parcel parcel) {
        PendingIntent pendingIntent;
        IBinder iBinder;
        int zzg;
        IBinder iBinder2;
        IBinder iBinder3 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        PendingIntent pendingIntent2 = null;
        IBinder iBinder4 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    PendingIntent pendingIntent3 = pendingIntent2;
                    iBinder = zzb.zzr(parcel, zzaX);
                    iBinder2 = iBinder3;
                    pendingIntent = pendingIntent3;
                    break;
                case 2:
                    iBinder = iBinder4;
                    zzg = i;
                    IBinder iBinder5 = iBinder3;
                    pendingIntent = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
                    iBinder2 = iBinder5;
                    break;
                case 3:
                    iBinder2 = zzb.zzr(parcel, zzaX);
                    pendingIntent = pendingIntent2;
                    iBinder = iBinder4;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder6 = iBinder3;
                    pendingIntent = pendingIntent2;
                    iBinder = iBinder4;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder2 = iBinder6;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder2 = iBinder3;
                    pendingIntent = pendingIntent2;
                    iBinder = iBinder4;
                    zzg = i;
                    break;
            }
            i = zzg;
            iBinder4 = iBinder;
            pendingIntent2 = pendingIntent;
            iBinder3 = iBinder2;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzax(i, iBinder4, pendingIntent2, iBinder3);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhF */
    public zzax[] newArray(int i) {
        return new zzax[i];
    }
}
