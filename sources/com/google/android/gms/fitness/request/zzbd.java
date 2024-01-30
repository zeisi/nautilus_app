package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzapf;
import java.util.concurrent.TimeUnit;

public class zzbd extends zza {
    public static final Parcelable.Creator<zzbd> CREATOR = new zzbe();
    private final Session zzaSj;
    private final zzapf zzaVt;
    private final int zzaiI;

    zzbd(int i, Session session, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaSj = session;
        this.zzaVt = zzapf.zza.zzcJ(iBinder);
    }

    public zzbd(Session session, zzapf zzapf) {
        zzac.zzb(session.getStartTime(TimeUnit.MILLISECONDS) < System.currentTimeMillis(), (Object) "Cannot start a session in the future");
        zzac.zzb(session.isOngoing(), (Object) "Cannot start a session which has already ended");
        this.zzaiI = 3;
        this.zzaSj = session;
        this.zzaVt = zzapf;
    }

    private boolean zzb(zzbd zzbd) {
        return zzaa.equal(this.zzaSj, zzbd.zzaSj);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzbd) && zzb((zzbd) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    public Session getSession() {
        return this.zzaSj;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaSj);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("session", this.zzaSj).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbe.zza(this, parcel, i);
    }
}
