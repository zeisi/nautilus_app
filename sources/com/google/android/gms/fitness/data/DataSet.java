package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzanz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DataSet extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<DataSet> CREATOR = new zzi();
    private final int versionCode;
    private final List<DataPoint> zzaSG;
    private final List<DataSource> zzaSH;
    private boolean zzaSI;
    private final DataType zzaSg;
    private final DataSource zzaSh;

    DataSet(int i, DataSource dataSource, DataType dataType, List<RawDataPoint> list, List<DataSource> list2, boolean z) {
        this.zzaSI = false;
        this.versionCode = i;
        this.zzaSh = dataSource;
        this.zzaSg = dataSource.getDataType();
        this.zzaSI = z;
        this.zzaSG = new ArrayList(list.size());
        this.zzaSH = i < 2 ? Collections.singletonList(dataSource) : list2;
        for (RawDataPoint dataPoint : list) {
            this.zzaSG.add(new DataPoint(this.zzaSH, dataPoint));
        }
    }

    public DataSet(DataSource dataSource) {
        this.zzaSI = false;
        this.versionCode = 3;
        this.zzaSh = (DataSource) zzac.zzw(dataSource);
        this.zzaSg = dataSource.getDataType();
        this.zzaSG = new ArrayList();
        this.zzaSH = new ArrayList();
        this.zzaSH.add(this.zzaSh);
    }

    public DataSet(RawDataSet rawDataSet, List<DataSource> list) {
        this.zzaSI = false;
        this.versionCode = 3;
        this.zzaSh = (DataSource) zzd(list, rawDataSet.zzaUe);
        this.zzaSg = this.zzaSh.getDataType();
        this.zzaSH = list;
        this.zzaSI = rawDataSet.zzaSx;
        List<RawDataPoint> list2 = rawDataSet.zzaUj;
        this.zzaSG = new ArrayList(list2.size());
        for (RawDataPoint dataPoint : list2) {
            this.zzaSG.add(new DataPoint(this.zzaSH, dataPoint));
        }
    }

    public static DataSet create(DataSource dataSource) {
        zzac.zzb(dataSource, (Object) "DataSource should be specified");
        return new DataSet(dataSource);
    }

    private boolean zza(DataSet dataSet) {
        return zzaa.equal(getDataType(), dataSet.getDataType()) && zzaa.equal(this.zzaSh, dataSet.zzaSh) && zzaa.equal(this.zzaSG, dataSet.zzaSG) && this.zzaSI == dataSet.zzaSI;
    }

    private static <T> T zzd(List<T> list, int i) {
        if (i < 0 || i >= list.size()) {
            return null;
        }
        return list.get(i);
    }

    public static void zze(DataPoint dataPoint) throws IllegalArgumentException {
        String zza = zzanz.zza(dataPoint, zzf.zzaSy);
        if (zza != null) {
            String valueOf = String.valueOf(dataPoint);
            Log.w("Fitness", new StringBuilder(String.valueOf(valueOf).length() + 20).append("Invalid data point: ").append(valueOf).toString());
            throw new IllegalArgumentException(zza);
        }
    }

    public void add(DataPoint dataPoint) {
        DataSource dataSource = dataPoint.getDataSource();
        zzac.zzb(dataSource.getStreamIdentifier().equals(this.zzaSh.getStreamIdentifier()), "Conflicting data sources found %s vs %s", dataSource, this.zzaSh);
        dataPoint.zzCd();
        zze(dataPoint);
        zzd(dataPoint);
    }

    public void addAll(Iterable<DataPoint> iterable) {
        for (DataPoint add : iterable) {
            add(add);
        }
    }

    public DataPoint createDataPoint() {
        return DataPoint.create(this.zzaSh);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof DataSet) && zza((DataSet) obj));
    }

    public List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(this.zzaSG);
    }

    public DataSource getDataSource() {
        return this.zzaSh;
    }

    public DataType getDataType() {
        return this.zzaSh.getDataType();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaSh);
    }

    public boolean isEmpty() {
        return this.zzaSG.isEmpty();
    }

    public String toString() {
        List<RawDataPoint> zzCf = zzCf();
        Object[] objArr = new Object[2];
        objArr[0] = this.zzaSh.toDebugString();
        Object obj = zzCf;
        if (this.zzaSG.size() >= 10) {
            obj = String.format("%d data points, first 5: %s", new Object[]{Integer.valueOf(this.zzaSG.size()), zzCf.subList(0, 5)});
        }
        objArr[1] = obj;
        return String.format("DataSet{%s %s}", objArr);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }

    public boolean zzBW() {
        return this.zzaSI;
    }

    /* access modifiers changed from: package-private */
    public List<RawDataPoint> zzC(List<DataSource> list) {
        ArrayList arrayList = new ArrayList(this.zzaSG.size());
        for (DataPoint rawDataPoint : this.zzaSG) {
            arrayList.add(new RawDataPoint(rawDataPoint, list));
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public List<RawDataPoint> zzCf() {
        return zzC(this.zzaSH);
    }

    /* access modifiers changed from: package-private */
    public List<DataSource> zzCg() {
        return this.zzaSH;
    }

    public void zzb(Iterable<DataPoint> iterable) {
        for (DataPoint zzd : iterable) {
            zzd(zzd);
        }
    }

    public void zzd(DataPoint dataPoint) {
        this.zzaSG.add(dataPoint);
        DataSource originalDataSource = dataPoint.getOriginalDataSource();
        if (originalDataSource != null && !this.zzaSH.contains(originalDataSource)) {
            this.zzaSH.add(originalDataSource);
        }
    }
}
