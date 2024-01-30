package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.fitness.zzb;
import java.util.Set;

public abstract class zzaob<T extends IInterface> extends zzl<T> {
    protected zzaob(Context context, Looper looper, int i, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzg zzg) {
        super(context, looper, i, zzg, connectionCallbacks, onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public Set<Scope> zzc(Set<Scope> set) {
        return zzb.zzj(set);
    }

    public abstract String zzeA();

    public abstract String zzez();

    public abstract T zzh(IBinder iBinder);

    public boolean zzrd() {
        return !zzany.zzbo(getContext());
    }

    public boolean zzxE() {
        return true;
    }
}
