package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.zzam;
import com.google.android.gms.fitness.request.zzbm;
import com.google.android.gms.fitness.request.zzbq;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaoi;
import com.google.android.gms.internal.zzapa;

public class zzapo implements RecordingApi {

    private static class zza extends zzapa.zza {
        private final zzaad.zzb<ListSubscriptionsResult> zzaGN;

        private zza(zzaad.zzb<ListSubscriptionsResult> zzb) {
            this.zzaGN = zzb;
        }

        public void zza(ListSubscriptionsResult listSubscriptionsResult) {
            this.zzaGN.setResult(listSubscriptionsResult);
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, final Subscription subscription) {
        return googleApiClient.zza(new zzaoi.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoi zzaoi) throws RemoteException {
                ((zzaox) zzaoi.zzxD()).zza(new zzbm(subscription, false, new zzapr(this)));
            }
        });
    }

    public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzaoi.zza<ListSubscriptionsResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzZ */
            public ListSubscriptionsResult zzc(Status status) {
                return ListSubscriptionsResult.zzah(status);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaoi zzaoi) throws RemoteException {
                ((zzaox) zzaoi.zzxD()).zza(new zzam((DataType) null, new zza(this)));
            }
        });
    }

    public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient, final DataType dataType) {
        return googleApiClient.zza(new zzaoi.zza<ListSubscriptionsResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzZ */
            public ListSubscriptionsResult zzc(Status status) {
                return ListSubscriptionsResult.zzah(status);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaoi zzaoi) throws RemoteException {
                ((zzaox) zzaoi.zzxD()).zza(new zzam(dataType, new zza(this)));
            }
        });
    }

    public PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataSource dataSource) {
        return zza(googleApiClient, new Subscription.zza().zzb(dataSource).zzCA());
    }

    public PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataType dataType) {
        return zza(googleApiClient, new Subscription.zza().zzd(dataType).zzCA());
    }

    public PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, final DataSource dataSource) {
        return googleApiClient.zzb(new zzaoi.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoi zzaoi) throws RemoteException {
                ((zzaox) zzaoi.zzxD()).zza(new zzbq((DataType) null, dataSource, new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, final DataType dataType) {
        return googleApiClient.zzb(new zzaoi.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaoi zzaoi) throws RemoteException {
                ((zzaox) zzaoi.zzxD()).zza(new zzbq(dataType, (DataSource) null, new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, Subscription subscription) {
        return subscription.getDataType() == null ? unsubscribe(googleApiClient, subscription.getDataSource()) : unsubscribe(googleApiClient, subscription.getDataType());
    }
}
