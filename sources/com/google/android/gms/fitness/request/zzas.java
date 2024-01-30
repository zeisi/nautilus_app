package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzas implements Parcelable.Creator<zzar> {
    static void zza(zzar zzar, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzar.getCallbackBinder(), false);
        zzc.zzc(parcel, 3, zzar.zzDb(), false);
        zzc.zza(parcel, 4, zzar.zzCS());
        zzc.zza(parcel, 5, zzar.zzCR());
        zzc.zzc(parcel, 1000, zzar.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeP */
    public zzar createFromParcel(Parcel parcel) {
        ArrayList<zzj> arrayList = null;
        boolean z = false;
        int zzaY = zzb.zzaY(parcel);
        boolean z2 = false;
        IBinder iBinder = null;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, zzaX, zzj.CREATOR);
                    break;
                case 4:
                    z2 = zzb.zzc(parcel, zzaX);
                    break;
                case 5:
                    z = zzb.zzc(parcel, zzaX);
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
            return new zzar(i, iBinder, arrayList, z2, z);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhC */
    public zzar[] newArray(int i) {
        return new zzar[i];
    }
}
