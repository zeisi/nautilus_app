package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.nautilus.omni.model.dto.AwardType;
import java.util.concurrent.TimeUnit;

public class Session extends zza {
    public static final Parcelable.Creator<Session> CREATOR = new zzz();
    public static final String EXTRA_SESSION = "vnd.google.fitness.session";
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.session/";
    private final String mName;
    private final long zzaSt;
    private final int zzaSu;
    private final String zzaUk;
    private final zzb zzaUl;
    private final Long zzaUm;
    private final String zzade;
    private final long zzafe;
    private final int zzaiI;

    public static class Builder {
        /* access modifiers changed from: private */
        public String mName = null;
        /* access modifiers changed from: private */
        public long zzaSt = 0;
        /* access modifiers changed from: private */
        public int zzaSu = 4;
        /* access modifiers changed from: private */
        public String zzaUk;
        /* access modifiers changed from: private */
        public Long zzaUm;
        /* access modifiers changed from: private */
        public String zzade;
        /* access modifiers changed from: private */
        public long zzafe = 0;

        public Session build() {
            boolean z = false;
            zzac.zza(this.zzafe > 0, (Object) "Start time should be specified.");
            if (this.zzaSt == 0 || this.zzaSt > this.zzafe) {
                z = true;
            }
            zzac.zza(z, (Object) "End time should be later than start time.");
            if (this.zzaUk == null) {
                String str = this.mName == null ? "" : this.mName;
                this.zzaUk = new StringBuilder(String.valueOf(str).length() + 20).append(str).append(this.zzafe).toString();
            }
            if (this.zzade == null) {
                this.zzade = "";
            }
            return new Session(this);
        }

        public Builder setActiveTime(long j, TimeUnit timeUnit) {
            this.zzaUm = Long.valueOf(timeUnit.toMillis(j));
            return this;
        }

        public Builder setActivity(String str) {
            return zzgV(com.google.android.gms.fitness.zza.zzdU(str));
        }

        public Builder setDescription(String str) {
            zzac.zzb(str.length() <= 1000, "Session description cannot exceed %d characters", 1000);
            this.zzade = str;
            return this;
        }

        public Builder setEndTime(long j, TimeUnit timeUnit) {
            zzac.zza(j >= 0, (Object) "End time should be positive.");
            this.zzaSt = timeUnit.toMillis(j);
            return this;
        }

        public Builder setIdentifier(String str) {
            zzac.zzax(str != null && TextUtils.getTrimmedLength(str) > 0);
            this.zzaUk = str;
            return this;
        }

        public Builder setName(String str) {
            zzac.zzb(str.length() <= 100, "Session name cannot exceed %d characters", 100);
            this.mName = str;
            return this;
        }

        public Builder setStartTime(long j, TimeUnit timeUnit) {
            zzac.zza(j > 0, (Object) "Start time should be positive.");
            this.zzafe = timeUnit.toMillis(j);
            return this;
        }

        public Builder zzgV(int i) {
            this.zzaSu = i;
            return this;
        }
    }

    Session(int i, long j, long j2, String str, String str2, String str3, int i2, zzb zzb, Long l) {
        this.zzaiI = i;
        this.zzafe = j;
        this.zzaSt = j2;
        this.mName = str;
        this.zzaUk = str2;
        this.zzade = str3;
        this.zzaSu = i2;
        this.zzaUl = zzb;
        this.zzaUm = l;
    }

    public Session(long j, long j2, String str, String str2, String str3, int i, zzb zzb, Long l) {
        this(3, j, j2, str, str2, str3, i, zzb, l);
    }

    private Session(Builder builder) {
        this(builder.zzafe, builder.zzaSt, builder.mName, builder.zzaUk, builder.zzade, builder.zzaSu, (zzb) null, builder.zzaUm);
    }

    public static Session extract(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (Session) zzd.zza(intent, EXTRA_SESSION, CREATOR);
    }

    public static String getMimeType(String str) {
        String valueOf = String.valueOf(MIME_TYPE_PREFIX);
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private boolean zza(Session session) {
        return this.zzafe == session.zzafe && this.zzaSt == session.zzaSt && zzaa.equal(this.mName, session.mName) && zzaa.equal(this.zzaUk, session.zzaUk) && zzaa.equal(this.zzade, session.zzade) && zzaa.equal(this.zzaUl, session.zzaUl) && this.zzaSu == session.zzaSu;
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof Session) && zza((Session) obj));
    }

    public long getActiveTime(TimeUnit timeUnit) {
        zzac.zza(this.zzaUm != null, (Object) "Active time is not set");
        return timeUnit.convert(this.zzaUm.longValue(), TimeUnit.MILLISECONDS);
    }

    public String getActivity() {
        return com.google.android.gms.fitness.zza.getName(this.zzaSu);
    }

    public String getAppPackageName() {
        if (this.zzaUl == null) {
            return null;
        }
        return this.zzaUl.getPackageName();
    }

    public String getDescription() {
        return this.zzade;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaSt, TimeUnit.MILLISECONDS);
    }

    public String getIdentifier() {
        return this.zzaUk;
    }

    public String getName() {
        return this.mName;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzafe, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public boolean hasActiveTime() {
        return this.zzaUm != null;
    }

    public int hashCode() {
        return zzaa.hashCode(Long.valueOf(this.zzafe), Long.valueOf(this.zzaSt), this.zzaUk);
    }

    public boolean isOngoing() {
        return this.zzaSt == 0;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("startTime", Long.valueOf(this.zzafe)).zzg("endTime", Long.valueOf(this.zzaSt)).zzg("name", this.mName).zzg("identifier", this.zzaUk).zzg(AwardType.DESCRIPTION, this.zzade).zzg("activity", Integer.valueOf(this.zzaSu)).zzg("application", this.zzaUl).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzz.zza(this, parcel, i);
    }

    public long zzAl() {
        return this.zzaSt;
    }

    public int zzBV() {
        return this.zzaSu;
    }

    public zzb zzCh() {
        return this.zzaUl;
    }

    public Long zzCx() {
        return this.zzaUm;
    }

    public long zzqn() {
        return this.zzafe;
    }
}
