package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Session;

public class zzbe implements Parcelable.Creator<zzbd> {
    static void zza(zzbd zzbd, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzbd.getSession(), i, false);
        zzc.zza(parcel, 2, zzbd.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, zzbd.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeW */
    public zzbd createFromParcel(Parcel parcel) {
        Session session;
        int zzg;
        IBinder iBinder;
        IBinder iBinder2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Session session2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Session session3 = (Session) zzb.zza(parcel, zzaX, Session.CREATOR);
                    iBinder = iBinder2;
                    session = session3;
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, zzaX);
                    session = session2;
                    zzg = i;
                    break;
                case 1000:
                    IBinder iBinder3 = iBinder2;
                    session = session2;
                    zzg = zzb.zzg(parcel, zzaX);
                    iBinder = iBinder3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    iBinder = iBinder2;
                    session = session2;
                    zzg = i;
                    break;
            }
            i = zzg;
            session2 = session;
            iBinder2 = iBinder;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzbd(i, session2, iBinder2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhJ */
    public zzbd[] newArray(int i) {
        return new zzbd[i];
    }
}
