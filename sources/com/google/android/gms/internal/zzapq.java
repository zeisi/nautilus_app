package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.SessionsApi;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.request.zzbb;
import com.google.android.gms.fitness.request.zzbd;
import com.google.android.gms.fitness.request.zzbf;
import com.google.android.gms.fitness.request.zzbh;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaok;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzape;
import java.util.concurrent.TimeUnit;

public class zzapq implements SessionsApi {

    private static class zza extends zzapd.zza {
        private final zzaad.zzb<SessionReadResult> zzaGN;

        private zza(zzaad.zzb<SessionReadResult> zzb) {
            this.zzaGN = zzb;
        }

        public void zza(SessionReadResult sessionReadResult) throws RemoteException {
            this.zzaGN.setResult(sessionReadResult);
        }
    }

    private static class zzb extends zzape.zza {
        private final zzaad.zzb<SessionStopResult> zzaGN;

        private zzb(zzaad.zzb<SessionStopResult> zzb) {
            this.zzaGN = zzb;
        }

        public void zza(SessionStopResult sessionStopResult) {
            this.zzaGN.setResult(sessionStopResult);
        }
    }

    private PendingResult<SessionStopResult> zza(GoogleApiClient googleApiClient, final String str, final String str2) {
        return googleApiClient.zzb(new zzaok.zza<SessionStopResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaok zzaok) throws RemoteException {
                ((zzaoz) zzaok.zzxD()).zza(new zzbf(str, str2, new zzb(this)));
            }

            /* access modifiers changed from: protected */
            /* renamed from: zzab */
            public SessionStopResult zzc(Status status) {
                return SessionStopResult.zzaj(status);
            }
        });
    }

    public PendingResult<Status> insertSession(GoogleApiClient googleApiClient, final SessionInsertRequest sessionInsertRequest) {
        return googleApiClient.zza(new zzaok.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaok zzaok) throws RemoteException {
                ((zzaoz) zzaok.zzxD()).zza(new SessionInsertRequest(sessionInsertRequest, (zzapf) new zzapr(this)));
            }
        });
    }

    public PendingResult<SessionReadResult> readSession(GoogleApiClient googleApiClient, final SessionReadRequest sessionReadRequest) {
        return googleApiClient.zza(new zzaok.zza<SessionReadResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaok zzaok) throws RemoteException {
                ((zzaoz) zzaok.zzxD()).zza(new SessionReadRequest(sessionReadRequest, (zzapd) new zza(this)));
            }

            /* access modifiers changed from: protected */
            /* renamed from: zzac */
            public SessionReadResult zzc(Status status) {
                return SessionReadResult.zzai(status);
            }
        });
    }

    public PendingResult<Status> registerForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zza(googleApiClient, pendingIntent, 0);
    }

    public PendingResult<Status> startSession(GoogleApiClient googleApiClient, final Session session) {
        zzac.zzb(session, (Object) "Session cannot be null");
        zzac.zzb(session.getEndTime(TimeUnit.MILLISECONDS) == 0, (Object) "Cannot start a session which has already ended");
        return googleApiClient.zzb(new zzaok.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaok zzaok) throws RemoteException {
                ((zzaoz) zzaok.zzxD()).zza(new zzbd(session, new zzapr(this)));
            }
        });
    }

    public PendingResult<SessionStopResult> stopSession(GoogleApiClient googleApiClient, String str) {
        return zza(googleApiClient, (String) null, str);
    }

    public PendingResult<Status> unregisterForSessions(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzb(new zzaok.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaok zzaok) throws RemoteException {
                ((zzaoz) zzaok.zzxD()).zza(new zzbh(pendingIntent, new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> zza(GoogleApiClient googleApiClient, final PendingIntent pendingIntent, final int i) {
        return googleApiClient.zzb(new zzaok.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaok zzaok) throws RemoteException {
                ((zzaoz) zzaok.zzxD()).zza(new zzbb(pendingIntent, new zzapr(this), i));
            }
        });
    }
}
