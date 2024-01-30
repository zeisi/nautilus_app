package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.request.zzak;
import com.google.android.gms.fitness.request.zzb;
import com.google.android.gms.fitness.request.zzbk;
import com.google.android.gms.fitness.request.zzbo;
import com.google.android.gms.fitness.result.BleDevicesResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaod;
import com.google.android.gms.internal.zzapt;

public class zzapj implements BleApi {

    private static class zza extends zzapt.zza {
        private final zzaad.zzb<BleDevicesResult> zzaGN;

        private zza(zzaad.zzb<BleDevicesResult> zzb) {
            this.zzaGN = zzb;
        }

        public void zza(BleDevicesResult bleDevicesResult) {
            this.zzaGN.setResult(bleDevicesResult);
        }
    }

    public PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, final BleDevice bleDevice) {
        return googleApiClient.zzb(new zzaod.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaod zzaod) throws RemoteException {
                ((zzaos) zzaod.zzxD()).zza(new zzb(bleDevice.getAddress(), bleDevice, new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.zzb(new zzaod.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaod zzaod) throws RemoteException {
                ((zzaos) zzaod.zzxD()).zza(new zzb(str, (BleDevice) null, new zzapr(this)));
            }
        });
    }

    public PendingResult<BleDevicesResult> listClaimedBleDevices(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzaod.zza<BleDevicesResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: zzU */
            public BleDevicesResult zzc(Status status) {
                return BleDevicesResult.zzad(status);
            }

            /* access modifiers changed from: protected */
            public void zza(zzaod zzaod) throws RemoteException {
                ((zzaos) zzaod.zzxD()).zza(new zzak(new zza(this)));
            }
        });
    }

    public PendingResult<Status> startBleScan(GoogleApiClient googleApiClient, final StartBleScanRequest startBleScanRequest) {
        return googleApiClient.zza(new zzaod.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaod zzaod) throws RemoteException {
                ((zzaos) zzaod.zzxD()).zza(new StartBleScanRequest(startBleScanRequest, (zzapf) new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> stopBleScan(GoogleApiClient googleApiClient, final BleScanCallback bleScanCallback) {
        return googleApiClient.zza(new zzaod.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaod zzaod) throws RemoteException {
                ((zzaos) zzaod.zzxD()).zza(new zzbk(bleScanCallback, (zzapf) new zzapr(this)));
            }
        });
    }

    public PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return unclaimBleDevice(googleApiClient, bleDevice.getAddress());
    }

    public PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.zzb(new zzaod.zzc(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzaod zzaod) throws RemoteException {
                ((zzaos) zzaod.zzxD()).zza(new zzbo(str, new zzapr(this)));
            }
        });
    }
}
