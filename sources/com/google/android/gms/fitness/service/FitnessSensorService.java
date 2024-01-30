package com.google.android.gms.fitness.service;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzt;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.internal.zzaon;
import com.google.android.gms.internal.zzapf;
import com.google.android.gms.internal.zzapv;
import com.google.android.gms.internal.zzapx;
import com.google.android.gms.internal.zzapz;
import java.util.List;

public abstract class FitnessSensorService extends Service {
    public static final String SERVICE_INTERFACE = "com.google.android.gms.fitness.service.FitnessSensorService";
    private zza zzaXg;

    private static class zza extends zzapz.zza {
        private final FitnessSensorService zzaXh;

        private zza(FitnessSensorService fitnessSensorService) {
            this.zzaXh = fitnessSensorService;
        }

        public void zza(FitnessSensorServiceRequest fitnessSensorServiceRequest, zzapf zzapf) throws RemoteException {
            this.zzaXh.zzDA();
            if (this.zzaXh.onRegister(fitnessSensorServiceRequest)) {
                zzapf.zzp(Status.zzazx);
            } else {
                zzapf.zzp(new Status(13));
            }
        }

        public void zza(zzapv zzapv, zzaon zzaon) throws RemoteException {
            this.zzaXh.zzDA();
            zzaon.zza(new DataSourcesResult(this.zzaXh.onFindDataSources(zzapv.getDataTypes()), Status.zzazx));
        }

        public void zza(zzapx zzapx, zzapf zzapf) throws RemoteException {
            this.zzaXh.zzDA();
            if (this.zzaXh.onUnregister(zzapx.getDataSource())) {
                zzapf.zzp(Status.zzazx);
            } else {
                zzapf.zzp(new Status(13));
            }
        }
    }

    @CallSuper
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (Log.isLoggable("FitnessSensorService", 3)) {
            String valueOf = String.valueOf(intent);
            String valueOf2 = String.valueOf(getClass().getName());
            Log.d("FitnessSensorService", new StringBuilder(String.valueOf(valueOf).length() + 20 + String.valueOf(valueOf2).length()).append("Intent ").append(valueOf).append(" received by ").append(valueOf2).toString());
        }
        return this.zzaXg.asBinder();
    }

    @CallSuper
    public void onCreate() {
        super.onCreate();
        this.zzaXg = new zza();
    }

    public abstract List<DataSource> onFindDataSources(List<DataType> list);

    public abstract boolean onRegister(FitnessSensorServiceRequest fitnessSensorServiceRequest);

    public abstract boolean onUnregister(DataSource dataSource);

    /* access modifiers changed from: protected */
    @TargetApi(19)
    public void zzDA() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (zzt.zzzl()) {
            ((AppOpsManager) getSystemService("appops")).checkPackage(callingUid, "com.google.android.gms");
            return;
        }
        String[] packagesForUid = getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            int length = packagesForUid.length;
            int i = 0;
            while (i < length) {
                if (!packagesForUid[i].equals("com.google.android.gms")) {
                    i++;
                } else {
                    return;
                }
            }
        }
        throw new SecurityException("Unauthorized caller");
    }
}
