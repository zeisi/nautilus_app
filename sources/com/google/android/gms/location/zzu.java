package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzu implements Parcelable.Creator<zzt> {
    static void zza(zzt zzt, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzb(parcel, 1, zzt.zzIl(), false);
        zzc.zza(parcel, 2, (Parcelable) zzt.zzvu(), i, false);
        zzc.zza(parcel, 3, zzt.getTag(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgS */
    public zzt createFromParcel(Parcel parcel) {
        String zzq;
        PendingIntent pendingIntent;
        ArrayList<String> arrayList;
        PendingIntent pendingIntent2 = null;
        int zzaY = zzb.zzaY(parcel);
        String str = "";
        ArrayList<String> arrayList2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    String str2 = str;
                    pendingIntent = pendingIntent2;
                    arrayList = zzb.zzE(parcel, zzaX);
                    zzq = str2;
                    break;
                case 2:
                    arrayList = arrayList2;
                    PendingIntent pendingIntent3 = (PendingIntent) zzb.zza(parcel, zzaX, PendingIntent.CREATOR);
                    zzq = str;
                    pendingIntent = pendingIntent3;
                    break;
                case 3:
                    zzq = zzb.zzq(parcel, zzaX);
                    pendingIntent = pendingIntent2;
                    arrayList = arrayList2;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    zzq = str;
                    pendingIntent = pendingIntent2;
                    arrayList = arrayList2;
                    break;
            }
            arrayList2 = arrayList;
            pendingIntent2 = pendingIntent;
            str = zzq;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzt(arrayList2, pendingIntent2, str);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzku */
    public zzt[] newArray(int i) {
        return new zzt[i];
    }
}
