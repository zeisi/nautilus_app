package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzaa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionReadResult extends zza implements Result {
    public static final Parcelable.Creator<SessionReadResult> CREATOR = new zzr();
    private final List<Session> zzaVx;
    private final List<zzaa> zzaXd;
    private final int zzaiI;
    private final Status zzair;

    SessionReadResult(int i, List<Session> list, List<zzaa> list2, Status status) {
        this.zzaiI = i;
        this.zzaVx = list;
        this.zzaXd = Collections.unmodifiableList(list2);
        this.zzair = status;
    }

    public SessionReadResult(List<Session> list, List<zzaa> list2, Status status) {
        this.zzaiI = 3;
        this.zzaVx = list;
        this.zzaXd = Collections.unmodifiableList(list2);
        this.zzair = status;
    }

    public static SessionReadResult zzai(Status status) {
        return new SessionReadResult(new ArrayList(), new ArrayList(), status);
    }

    private boolean zzb(SessionReadResult sessionReadResult) {
        return this.zzair.equals(sessionReadResult.zzair) && com.google.android.gms.common.internal.zzaa.equal(this.zzaVx, sessionReadResult.zzaVx) && com.google.android.gms.common.internal.zzaa.equal(this.zzaXd, sessionReadResult.zzaXd);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof SessionReadResult) && zzb((SessionReadResult) obj));
    }

    public List<DataSet> getDataSet(Session session) {
        zzac.zzb(this.zzaVx.contains(session), "Attempting to read data for session %s which was not returned", session);
        ArrayList arrayList = new ArrayList();
        for (zzaa next : this.zzaXd) {
            if (com.google.android.gms.common.internal.zzaa.equal(session, next.getSession())) {
                arrayList.add(next.getDataSet());
            }
        }
        return arrayList;
    }

    public List<DataSet> getDataSet(Session session, DataType dataType) {
        zzac.zzb(this.zzaVx.contains(session), "Attempting to read data for session %s which was not returned", session);
        ArrayList arrayList = new ArrayList();
        for (zzaa next : this.zzaXd) {
            if (com.google.android.gms.common.internal.zzaa.equal(session, next.getSession()) && dataType.equals(next.getDataSet().getDataType())) {
                arrayList.add(next.getDataSet());
            }
        }
        return arrayList;
    }

    public List<Session> getSessions() {
        return this.zzaVx;
    }

    public Status getStatus() {
        return this.zzair;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return com.google.android.gms.common.internal.zzaa.hashCode(this.zzair, this.zzaVx, this.zzaXd);
    }

    public String toString() {
        return com.google.android.gms.common.internal.zzaa.zzv(this).zzg("status", this.zzair).zzg("sessions", this.zzaVx).zzg("sessionDataSets", this.zzaXd).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzr.zza(this, parcel, i);
    }

    public List<zzaa> zzDw() {
        return this.zzaXd;
    }
}
