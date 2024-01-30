package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.Goal;
import java.util.Collections;
import java.util.List;

public class GoalsResult extends zza implements Result {
    public static final Parcelable.Creator<GoalsResult> CREATOR = new zzn();
    private final int versionCode;
    private final List<Goal> zzaXa;
    private final Status zzahw;

    GoalsResult(int i, Status status, List<Goal> list) {
        this.versionCode = i;
        this.zzahw = status;
        this.zzaXa = list;
    }

    public GoalsResult(Status status, List<Goal> list) {
        this(1, status, list);
    }

    public static GoalsResult zzag(Status status) {
        return new GoalsResult(status, Collections.emptyList());
    }

    public List<Goal> getGoals() {
        return this.zzaXa;
    }

    public Status getStatus() {
        return this.zzahw;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzn.zza(this, parcel, i);
    }
}
