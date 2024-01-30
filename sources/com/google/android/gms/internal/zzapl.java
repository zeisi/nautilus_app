package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.GoalsApi;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.fitness.result.GoalsResult;
import com.google.android.gms.internal.zzaof;
import com.google.android.gms.internal.zzaor;

public class zzapl implements GoalsApi {
    public PendingResult<GoalsResult> readCurrentGoals(GoogleApiClient googleApiClient, final GoalsReadRequest goalsReadRequest) {
        return googleApiClient.zza(new zzaof.zza<GoalsResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzW */
            public GoalsResult zzc(Status status) {
                return GoalsResult.zzag(status);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaof zzaof) throws RemoteException {
                ((zzaou) zzaof.zzxD()).zza(new GoalsReadRequest(goalsReadRequest, (zzaor) new zzaor.zza() {
                    public void zza(GoalsResult goalsResult) throws RemoteException {
                        AnonymousClass1.this.zzb(goalsResult);
                    }
                }));
            }
        });
    }
}
