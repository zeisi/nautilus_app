package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

public class zzaz implements Parcelable.Creator<SessionInsertRequest> {
    static void zza(SessionInsertRequest sessionInsertRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) sessionInsertRequest.getSession(), i, false);
        zzc.zzc(parcel, 2, sessionInsertRequest.getDataSets(), false);
        zzc.zzc(parcel, 3, sessionInsertRequest.getAggregateDataPoints(), false);
        zzc.zza(parcel, 4, sessionInsertRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, sessionInsertRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeT */
    public SessionInsertRequest createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<DataPoint> arrayList = null;
        ArrayList<DataSet> arrayList2 = null;
        Session session = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    session = (Session) zzb.zza(parcel, zzaX, Session.CREATOR);
                    break;
                case 2:
                    arrayList2 = zzb.zzc(parcel, zzaX, DataSet.CREATOR);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, zzaX, DataPoint.CREATOR);
                    break;
                case 4:
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
            return new SessionInsertRequest(i, session, arrayList2, arrayList, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhG */
    public SessionInsertRequest[] newArray(int i) {
        return new SessionInsertRequest[i];
    }
}
