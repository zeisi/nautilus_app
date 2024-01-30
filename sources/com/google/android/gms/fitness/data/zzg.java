package com.google.android.gms.fitness.data;

import com.google.android.gms.internal.zzanw;

class zzg implements zzanw<DataType> {
    public static final zzg zzaSz = new zzg();

    private zzg() {
    }

    private Field zzd(DataType dataType, int i) {
        return dataType.getFields().get(i);
    }

    /* renamed from: zza */
    public String zzE(DataType dataType) {
        return dataType.getName();
    }

    /* renamed from: zza */
    public String zzg(DataType dataType, int i) {
        return zzd(dataType, i).getName();
    }

    /* renamed from: zzb */
    public int zzD(DataType dataType) {
        return dataType.getFields().size();
    }

    /* renamed from: zzb */
    public boolean zzf(DataType dataType, int i) {
        return Boolean.TRUE.equals(zzd(dataType, i).isOptional());
    }

    /* renamed from: zzc */
    public int zze(DataType dataType, int i) {
        return zzd(dataType, i).getFormat();
    }

    public boolean zzdW(String str) {
        return zzl.zzdX(str) != null;
    }
}
