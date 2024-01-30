package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.data.zzs;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.zzao;
import com.google.android.gms.fitness.request.zzav;
import com.google.android.gms.fitness.request.zzax;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaoj;
import com.google.android.gms.internal.zzapf;

public class zzapp implements SensorsApi {

    private interface zza {
        void zzCI();
    }

    private static class zzb extends zzapf.zza {
        private final zzaad.zzb<Status> zzaGN;
        private final zza zzaVg;

        private zzb(zzaad.zzb<Status> zzb, zza zza) {
            this.zzaGN = zzb;
            this.zzaVg = zza;
        }

        public void zzp(Status status) {
            if (this.zzaVg != null && status.isSuccess()) {
                this.zzaVg.zzCI();
            }
            this.zzaGN.setResult(status);
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, zzs zzs, PendingIntent pendingIntent, zza zza2) {
        final zza zza3 = zza2;
        final zzs zzs2 = zzs;
        final PendingIntent pendingIntent2 = pendingIntent;
        return googleApiClient.zzb(new zzaoj.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoj zzaoj) throws RemoteException {
                ((zzaoy) zzaoj.zzxD()).zza(new zzax(zzs2, pendingIntent2, new zzb(this, zza3)));
            }

            /* access modifiers changed from: protected */
            /* renamed from: zzb */
            public Status zzc(Status status) {
                return status;
            }
        });
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, SensorRequest sensorRequest, zzs zzs, PendingIntent pendingIntent) {
        final SensorRequest sensorRequest2 = sensorRequest;
        final zzs zzs2 = zzs;
        final PendingIntent pendingIntent2 = pendingIntent;
        return googleApiClient.zza(new zzaoj.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoj zzaoj) throws RemoteException {
                ((zzaoy) zzaoj.zzxD()).zza(new zzav(sensorRequest2, zzs2, pendingIntent2, new zzapr(this)));
            }

            /* access modifiers changed from: protected */
            /* renamed from: zzb */
            public Status zzc(Status status) {
                return status;
            }
        });
    }

    public PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, PendingIntent pendingIntent) {
        return zza(googleApiClient, sensorRequest, (zzs) null, pendingIntent);
    }

    public PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, OnDataPointListener onDataPointListener) {
        return zza(googleApiClient, sensorRequest, (zzs) zzao.zza.zzCZ().zza(onDataPointListener), (PendingIntent) null);
    }

    public PendingResult<DataSourcesResult> findDataSources(GoogleApiClient googleApiClient, final DataSourcesRequest dataSourcesRequest) {
        return googleApiClient.zza(new zzaoj.zza<DataSourcesResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoj zzaoj) throws RemoteException {
                ((zzaoy) zzaoj.zzxD()).zza(new DataSourcesRequest(dataSourcesRequest, (zzaon) new zzaoc(this)));
            }

            /* access modifiers changed from: protected */
            /* renamed from: zzaa */
            public DataSourcesResult zzc(Status status) {
                return DataSourcesResult.zzae(status);
            }
        });
    }

    public PendingResult<Status> remove(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zza(googleApiClient, (zzs) null, pendingIntent, (zza) null);
    }

    public PendingResult<Status> remove(GoogleApiClient googleApiClient, final OnDataPointListener onDataPointListener) {
        zzao zzb2 = zzao.zza.zzCZ().zzb(onDataPointListener);
        return zzb2 == null ? PendingResults.zza(Status.zzazx, googleApiClient) : zza(googleApiClient, (zzs) zzb2, (PendingIntent) null, (zza) new zza(this) {
            public void zzCI() {
                zzao.zza.zzCZ().zzc(onDataPointListener);
            }
        });
    }
}
