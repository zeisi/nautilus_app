package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.request.zzd;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.request.zzq;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaog;
import com.google.android.gms.internal.zzaol;
import com.google.android.gms.internal.zzaom;

public class zzapm implements HistoryApi {

    private static class zza extends zzaom.zza {
        private final zzaad.zzb<DataReadResult> zzaGN;
        private int zzaUV;
        private DataReadResult zzaUW;

        private zza(zzaad.zzb<DataReadResult> zzb) {
            this.zzaUV = 0;
            this.zzaUW = null;
            this.zzaGN = zzb;
        }

        public void zza(DataReadResult dataReadResult) {
            synchronized (this) {
                if (Log.isLoggable("Fitness", 2)) {
                    Log.v("Fitness", new StringBuilder(33).append("Received batch result ").append(this.zzaUV).toString());
                }
                if (this.zzaUW == null) {
                    this.zzaUW = dataReadResult;
                } else {
                    this.zzaUW.zzb(dataReadResult);
                }
                this.zzaUV++;
                if (this.zzaUV == this.zzaUW.zzDp()) {
                    this.zzaGN.setResult(this.zzaUW);
                }
            }
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, final DataSet dataSet, final boolean z) {
        zzac.zzb(dataSet, (Object) "Must set the data set");
        zzac.zza(!dataSet.getDataPoints().isEmpty(), (Object) "Cannot use an empty data set");
        zzac.zzb(dataSet.getDataSource().zzCh(), (Object) "Must set the app package name for the data source");
        return googleApiClient.zza(new zzaog.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new zzg(dataSet, new zzapr(this), z));
            }
        });
    }

    private PendingResult<DailyTotalResult> zza(GoogleApiClient googleApiClient, final DataType dataType, final boolean z) {
        return googleApiClient.zza(new zzaog.zza<DailyTotalResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzY */
            public DailyTotalResult zzc(Status status) {
                return DailyTotalResult.zza(status, dataType);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new zzd(new zzaol.zza() {
                    public void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
                        AnonymousClass7.this.zzb(dailyTotalResult);
                    }
                }, dataType, z));
            }
        });
    }

    public PendingResult<Status> deleteData(GoogleApiClient googleApiClient, final DataDeleteRequest dataDeleteRequest) {
        return googleApiClient.zza(new zzaog.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new DataDeleteRequest(dataDeleteRequest, (zzapf) new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> insertData(GoogleApiClient googleApiClient, DataSet dataSet) {
        return zza(googleApiClient, dataSet, false);
    }

    public PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient googleApiClient, DataType dataType) {
        return zza(googleApiClient, dataType, false);
    }

    public PendingResult<DailyTotalResult> readDailyTotalFromLocalDevice(GoogleApiClient googleApiClient, DataType dataType) {
        return zza(googleApiClient, dataType, true);
    }

    public PendingResult<DataReadResult> readData(GoogleApiClient googleApiClient, final DataReadRequest dataReadRequest) {
        return googleApiClient.zza(new zzaog.zza<DataReadResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzX */
            public DataReadResult zzc(Status status) {
                return DataReadResult.zza(status, dataReadRequest.getDataTypes(), dataReadRequest.getDataSources());
            }

            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new DataReadRequest(dataReadRequest, (zzaom) new zza(this)));
            }
        });
    }

    public PendingResult<Status> registerDataUpdateListener(GoogleApiClient googleApiClient, final DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        return googleApiClient.zza(new zzaog.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new DataUpdateListenerRegistrationRequest(dataUpdateListenerRegistrationRequest, (IBinder) new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> unregisterDataUpdateListener(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzb(new zzaog.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new zzq(pendingIntent, new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> updateData(GoogleApiClient googleApiClient, final DataUpdateRequest dataUpdateRequest) {
        zzac.zzb(dataUpdateRequest.getDataSet(), (Object) "Must set the data set");
        zzac.zza(dataUpdateRequest.zzqn(), (Object) "Must set a non-zero value for startTimeMillis/startTime");
        zzac.zza(dataUpdateRequest.zzAl(), (Object) "Must set a non-zero value for endTimeMillis/endTime");
        return googleApiClient.zza(new zzaog.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaog zzaog) throws RemoteException {
                ((zzaov) zzaog.zzxD()).zza(new DataUpdateRequest(dataUpdateRequest, (IBinder) new zzapr(this)));
            }
        });
    }
}
