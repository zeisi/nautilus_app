package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzapd;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionReadRequest extends zza {
    public static final Parcelable.Creator<SessionReadRequest> CREATOR = new zzba();
    private final String zzWD;
    private final List<DataType> zzaSs;
    private final long zzaSt;
    private final boolean zzaVH;
    private final List<DataSource> zzaVw;
    private final String zzaWD;
    private boolean zzaWE;
    private final List<String> zzaWF;
    private final zzapd zzaWG;
    private final long zzafe;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public String zzWD;
        /* access modifiers changed from: private */
        public List<DataType> zzaSs = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaSt = 0;
        /* access modifiers changed from: private */
        public boolean zzaVH = false;
        /* access modifiers changed from: private */
        public List<DataSource> zzaVw = new ArrayList();
        /* access modifiers changed from: private */
        public String zzaWD;
        /* access modifiers changed from: private */
        public boolean zzaWE = false;
        /* access modifiers changed from: private */
        public List<String> zzaWF = new ArrayList();
        /* access modifiers changed from: private */
        public long zzafe = 0;

        public SessionReadRequest build() {
            zzac.zzb(this.zzafe > 0, "Invalid start time: %s", Long.valueOf(this.zzafe));
            zzac.zzb(this.zzaSt > 0 && this.zzaSt > this.zzafe, "Invalid end time: %s", Long.valueOf(this.zzaSt));
            return new SessionReadRequest(this);
        }

        public Builder enableServerQueries() {
            this.zzaVH = true;
            return this;
        }

        public Builder excludePackage(String str) {
            zzac.zzb(str, (Object) "Attempting to use a null package name");
            if (!this.zzaWF.contains(str)) {
                this.zzaWF.add(str);
            }
            return this;
        }

        public Builder read(DataSource dataSource) {
            zzac.zzb(dataSource, (Object) "Attempting to add a null data source");
            if (!this.zzaVw.contains(dataSource)) {
                this.zzaVw.add(dataSource);
            }
            return this;
        }

        public Builder read(DataType dataType) {
            zzac.zzb(dataType, (Object) "Attempting to use a null data type");
            if (!this.zzaSs.contains(dataType)) {
                this.zzaSs.add(dataType);
            }
            return this;
        }

        public Builder readSessionsFromAllApps() {
            this.zzaWE = true;
            return this;
        }

        public Builder setSessionId(String str) {
            this.zzWD = str;
            return this;
        }

        public Builder setSessionName(String str) {
            this.zzaWD = str;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            this.zzafe = timeUnit.toMillis(j);
            this.zzaSt = timeUnit.toMillis(j2);
            return this;
        }
    }

    SessionReadRequest(int i, String str, String str2, long j, long j2, List<DataType> list, List<DataSource> list2, boolean z, boolean z2, List<String> list3, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaWD = str;
        this.zzWD = str2;
        this.zzafe = j;
        this.zzaSt = j2;
        this.zzaSs = list;
        this.zzaVw = list2;
        this.zzaWE = z;
        this.zzaVH = z2;
        this.zzaWF = list3;
        this.zzaWG = zzapd.zza.zzcH(iBinder);
    }

    private SessionReadRequest(Builder builder) {
        this(builder.zzaWD, builder.zzWD, builder.zzafe, builder.zzaSt, builder.zzaSs, builder.zzaVw, builder.zzaWE, builder.zzaVH, builder.zzaWF, (zzapd) null);
    }

    public SessionReadRequest(SessionReadRequest sessionReadRequest, zzapd zzapd) {
        this(sessionReadRequest.zzaWD, sessionReadRequest.zzWD, sessionReadRequest.zzafe, sessionReadRequest.zzaSt, sessionReadRequest.zzaSs, sessionReadRequest.zzaVw, sessionReadRequest.zzaWE, sessionReadRequest.zzaVH, sessionReadRequest.zzaWF, zzapd);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SessionReadRequest(String str, String str2, long j, long j2, List<DataType> list, List<DataSource> list2, boolean z, boolean z2, List<String> list3, zzapd zzapd) {
        this(5, str, str2, j, j2, list, list2, z, z2, list3, zzapd == null ? null : zzapd.asBinder());
    }

    private boolean zzb(SessionReadRequest sessionReadRequest) {
        return zzaa.equal(this.zzaWD, sessionReadRequest.zzaWD) && this.zzWD.equals(sessionReadRequest.zzWD) && this.zzafe == sessionReadRequest.zzafe && this.zzaSt == sessionReadRequest.zzaSt && zzaa.equal(this.zzaSs, sessionReadRequest.zzaSs) && zzaa.equal(this.zzaVw, sessionReadRequest.zzaVw) && this.zzaWE == sessionReadRequest.zzaWE && this.zzaWF.equals(sessionReadRequest.zzaWF) && this.zzaVH == sessionReadRequest.zzaVH;
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof SessionReadRequest) && zzb((SessionReadRequest) obj));
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWG == null) {
            return null;
        }
        return this.zzaWG.asBinder();
    }

    public List<DataSource> getDataSources() {
        return this.zzaVw;
    }

    public List<DataType> getDataTypes() {
        return this.zzaSs;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaSt, TimeUnit.MILLISECONDS);
    }

    public List<String> getExcludedPackages() {
        return this.zzaWF;
    }

    public String getSessionId() {
        return this.zzWD;
    }

    public String getSessionName() {
        return this.zzaWD;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzafe, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaWD, this.zzWD, Long.valueOf(this.zzafe), Long.valueOf(this.zzaSt));
    }

    public boolean includeSessionsFromAllApps() {
        return this.zzaWE;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("sessionName", this.zzaWD).zzg("sessionId", this.zzWD).zzg("startTimeMillis", Long.valueOf(this.zzafe)).zzg("endTimeMillis", Long.valueOf(this.zzaSt)).zzg("dataTypes", this.zzaSs).zzg("dataSources", this.zzaVw).zzg("sessionsFromAllApps", Boolean.valueOf(this.zzaWE)).zzg("excludedPackages", this.zzaWF).zzg("useServer", Boolean.valueOf(this.zzaVH)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzba.zza(this, parcel, i);
    }

    public long zzAl() {
        return this.zzaSt;
    }

    public boolean zzCR() {
        return this.zzaVH;
    }

    public boolean zzDj() {
        return this.zzaWE;
    }

    public long zzqn() {
        return this.zzafe;
    }
}
