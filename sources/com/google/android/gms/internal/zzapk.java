package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.request.zzn;
import com.google.android.gms.fitness.request.zzw;
import com.google.android.gms.fitness.result.DataTypeResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaoe;
import com.google.android.gms.internal.zzaoo;

public class zzapk implements ConfigApi {

    private static class zza extends zzaoo.zza {
        private final zzaad.zzb<DataTypeResult> zzaGN;

        private zza(zzaad.zzb<DataTypeResult> zzb) {
            this.zzaGN = zzb;
        }

        public void zza(DataTypeResult dataTypeResult) {
            this.zzaGN.setResult(dataTypeResult);
        }
    }

    public PendingResult<DataTypeResult> createCustomDataType(GoogleApiClient googleApiClient, final DataTypeCreateRequest dataTypeCreateRequest) {
        return googleApiClient.zzb(new zzaoe.zza<DataTypeResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzV */
            public DataTypeResult zzc(Status status) {
                return DataTypeResult.zzaf(status);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaoe zzaoe) throws RemoteException {
                ((zzaot) zzaoe.zzxD()).zza(new DataTypeCreateRequest(dataTypeCreateRequest, (zzaoo) new zza(this)));
            }
        });
    }

    public PendingResult<Status> disableFit(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new zzaoe.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoe zzaoe) throws RemoteException {
                ((zzaot) zzaoe.zzxD()).zza(new zzw(new zzapr(this)));
            }
        });
    }

    public PendingResult<DataTypeResult> readDataType(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.zza(new zzaoe.zza<DataTypeResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzV */
            public DataTypeResult zzc(Status status) {
                return DataTypeResult.zzaf(status);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaoe zzaoe) throws RemoteException {
                ((zzaot) zzaoe.zzxD()).zza(new zzn(str, new zza(this)));
            }
        });
    }
}
