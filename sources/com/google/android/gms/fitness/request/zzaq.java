package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzaq implements Parcelable.Creator<zzap> {
    static void zza(zzap zzap, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzap.getCallbackBinder(), false);
        zzc.zzb(parcel, 2, zzap.zzDa(), false);
        zzc.zzc(parcel, 1000, zzap.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeO */
    public zzap createFromParcel(Parcel parcel) {
        ArrayList<String> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 2:
                    arrayList = zzb.zzE(parcel, zzaX);
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
            return new zzap(i, iBinder, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhB */
    public zzap[] newArray(int i) {
        return new zzap[i];
    }
}
