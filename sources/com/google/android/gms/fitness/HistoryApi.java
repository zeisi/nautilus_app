package com.google.android.gms.fitness;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;
import java.util.concurrent.TimeUnit;

public interface HistoryApi {

    public static class ViewIntentBuilder {
        private long zzaKx;
        private long zzaKy;
        private final DataType zzaSg;
        private DataSource zzaSh;
        private String zzaSi;
        private final Context zzqn;

        public ViewIntentBuilder(Context context, DataType dataType) {
            this.zzqn = context;
            this.zzaSg = dataType;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
            r0 = new android.content.Intent(r5).setPackage(r4.zzaSi);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.content.Intent zzk(android.content.Intent r5) {
            /*
                r4 = this;
                java.lang.String r0 = r4.zzaSi
                if (r0 != 0) goto L_0x0005
            L_0x0004:
                return r5
            L_0x0005:
                android.content.Intent r0 = new android.content.Intent
                r0.<init>(r5)
                java.lang.String r1 = r4.zzaSi
                android.content.Intent r0 = r0.setPackage(r1)
                android.content.Context r1 = r4.zzqn
                android.content.pm.PackageManager r1 = r1.getPackageManager()
                r2 = 0
                android.content.pm.ResolveInfo r1 = r1.resolveActivity(r0, r2)
                if (r1 == 0) goto L_0x0004
                android.content.pm.ActivityInfo r1 = r1.activityInfo
                java.lang.String r1 = r1.name
                android.content.ComponentName r2 = new android.content.ComponentName
                java.lang.String r3 = r4.zzaSi
                r2.<init>(r3, r1)
                r0.setComponent(r2)
                r5 = r0
                goto L_0x0004
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.HistoryApi.ViewIntentBuilder.zzk(android.content.Intent):android.content.Intent");
        }

        public Intent build() {
            boolean z = true;
            zzac.zza(this.zzaKx > 0, (Object) "Start time must be set");
            if (this.zzaKy <= this.zzaKx) {
                z = false;
            }
            zzac.zza(z, (Object) "End time must be set and after start time");
            Intent intent = new Intent(Fitness.ACTION_VIEW);
            intent.setType(DataType.getMimeType(this.zzaSh.getDataType()));
            intent.putExtra(Fitness.EXTRA_START_TIME, this.zzaKx);
            intent.putExtra(Fitness.EXTRA_END_TIME, this.zzaKy);
            zzd.zza(this.zzaSh, intent, DataSource.EXTRA_DATA_SOURCE);
            return zzk(intent);
        }

        public ViewIntentBuilder setDataSource(DataSource dataSource) {
            zzac.zzb(dataSource.getDataType().equals(this.zzaSg), "Data source %s is not for the data type %s", dataSource, this.zzaSg);
            this.zzaSh = dataSource;
            return this;
        }

        public ViewIntentBuilder setPreferredApplication(String str) {
            this.zzaSi = str;
            return this;
        }

        public ViewIntentBuilder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            this.zzaKx = timeUnit.toMillis(j);
            this.zzaKy = timeUnit.toMillis(j2);
            return this;
        }
    }

    PendingResult<Status> deleteData(GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest);

    PendingResult<Status> insertData(GoogleApiClient googleApiClient, DataSet dataSet);

    PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient googleApiClient, DataType dataType);

    PendingResult<DailyTotalResult> readDailyTotalFromLocalDevice(GoogleApiClient googleApiClient, DataType dataType);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    PendingResult<DataReadResult> readData(GoogleApiClient googleApiClient, DataReadRequest dataReadRequest);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    PendingResult<Status> registerDataUpdateListener(GoogleApiClient googleApiClient, DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest);

    PendingResult<Status> unregisterDataUpdateListener(GoogleApiClient googleApiClient, PendingIntent pendingIntent);

    PendingResult<Status> updateData(GoogleApiClient googleApiClient, DataUpdateRequest dataUpdateRequest);
}
