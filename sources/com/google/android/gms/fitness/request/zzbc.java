package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzbc implements Parcelable.Creator<zzbb> {
    static void zza(zzbb zzbb, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzbb.getIntent(), i, false);
        zzc.zza(parcel, 2, zzbb.getCallbackBinder(), false);
        zzc.zzc(parcel, 4, zzbb.zzDk());
        zzc.zzc(parcel, 1000, zzbb.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeV */
    public zzbb createFromParcel(Parcel parcel) {
        IBinder iBinder;
        PendingIntent pendingIntent;
        int zzg;
        int i;
        IBinder iBinder2 = null;
        int i2 = 0;
        int zzaY = zzb.zzaY(parcel);
        PendingIntent pendingIntent2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i3;
                    IBinder iBinder3 = iBinder2;
                    pendingIntent = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
                    i = i2;
                    iBinder = iBinder3;
                    break;
                case 2:
                    pendingIntent = pendingIntent2;
                    zzg = i3;
                    int i4 = i2;
                    iBinder = zzb.zzr(parcel, zzaX);
                    i = i4;
                    break;
                case 4:
                    i = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder2;
                    pendingIntent = pendingIntent2;
                    zzg = i3;
                    break;
                case 1000:
                    int i5 = i2;
                    iBinder = iBinder2;
                    pendingIntent = pendingIntent2;
                    zzg = zzb.zzg(parcel, zzaX);
                    i = i5;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    i = i2;
                    iBinder = iBinder2;
                    pendingIntent = pendingIntent2;
                    zzg = i3;
                    break;
            }
            i3 = zzg;
            pendingIntent2 = pendingIntent;
            iBinder2 = iBinder;
            i2 = i;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzbb(i3, pendingIntent2, iBinder2, i2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhI */
    public zzbb[] newArray(int i) {
        return new zzbb[i];
    }
}
