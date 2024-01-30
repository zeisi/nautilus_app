package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSubscriptionsResult extends zza implements Result {
    public static final Parcelable.Creator<ListSubscriptionsResult> CREATOR = new zzo();
    private final List<Subscription> zzaXb;
    private final int zzaiI;
    private final Status zzair;

    ListSubscriptionsResult(int i, List<Subscription> list, Status status) {
        this.zzaiI = i;
        this.zzaXb = list;
        this.zzair = status;
    }

    public ListSubscriptionsResult(List<Subscription> list, Status status) {
        this.zzaiI = 3;
        this.zzaXb = Collections.unmodifiableList(list);
        this.zzair = (Status) zzac.zzb(status, (Object) "status");
    }

    public static ListSubscriptionsResult zzah(Status status) {
        return new ListSubscriptionsResult(Collections.emptyList(), status);
    }

    private boolean zzb(ListSubscriptionsResult listSubscriptionsResult) {
        return this.zzair.equals(listSubscriptionsResult.zzair) && zzaa.equal(this.zzaXb, listSubscriptionsResult.zzaXb);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof ListSubscriptionsResult) && zzb((ListSubscriptionsResult) obj));
    }

    public Status getStatus() {
        return this.zzair;
    }

    public List<Subscription> getSubscriptions() {
        return this.zzaXb;
    }

    public List<Subscription> getSubscriptions(DataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (Subscription next : this.zzaXb) {
            if (dataType.equals(next.zzCz())) {
                arrayList.add(next);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzair, this.zzaXb);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("status", this.zzair).zzg("subscriptions", this.zzaXb).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }
}
