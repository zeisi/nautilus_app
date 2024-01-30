package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzn;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public final class Value extends zza {
    public static final Parcelable.Creator<Value> CREATOR = new zzad();
    private final int format;
    private float value;
    private final int versionCode;
    private String zzaGV;
    private boolean zzaUq;
    private Map<String, MapValue> zzaUr;
    private int[] zzaUs;
    private float[] zzaUt;
    private byte[] zzaUu;

    public Value(int i) {
        this(3, i, false, 0.0f, (String) null, (Bundle) null, (int[]) null, (float[]) null, (byte[]) null);
    }

    Value(int i, int i2, boolean z, float f, String str, Bundle bundle, int[] iArr, float[] fArr, byte[] bArr) {
        this.versionCode = i;
        this.format = i2;
        this.zzaUq = z;
        this.value = f;
        this.zzaGV = str;
        this.zzaUr = zzB(bundle);
        this.zzaUs = iArr;
        this.zzaUt = fArr;
        this.zzaUu = bArr;
    }

    private static Map<String, MapValue> zzB(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(MapValue.class.getClassLoader());
        ArrayMap arrayMap = new ArrayMap(bundle.size());
        for (String str : bundle.keySet()) {
            arrayMap.put(str, (MapValue) bundle.getParcelable(str));
        }
        return arrayMap;
    }

    private boolean zza(Value value2) {
        if (this.format != value2.format || this.zzaUq != value2.zzaUq) {
            return false;
        }
        switch (this.format) {
            case 1:
                return asInt() == value2.asInt();
            case 2:
                return this.value == value2.value;
            case 3:
                return zzaa.equal(this.zzaGV, value2.zzaGV);
            case 4:
                return zzaa.equal(this.zzaUr, value2.zzaUr);
            case 5:
                return Arrays.equals(this.zzaUs, value2.zzaUs);
            case 6:
                return Arrays.equals(this.zzaUt, value2.zzaUt);
            case 7:
                return Arrays.equals(this.zzaUu, value2.zzaUu);
            default:
                return this.value == value2.value;
        }
    }

    public String asActivity() {
        return com.google.android.gms.fitness.zza.getName(asInt());
    }

    public float asFloat() {
        zzac.zza(this.format == 2, (Object) "Value is not in float format");
        return this.value;
    }

    public int asInt() {
        boolean z = true;
        if (this.format != 1) {
            z = false;
        }
        zzac.zza(z, (Object) "Value is not in int format");
        return Float.floatToRawIntBits(this.value);
    }

    public String asString() {
        zzac.zza(this.format == 3, (Object) "Value is not in string format");
        return this.zzaGV;
    }

    public void clearKey(String str) {
        zzac.zza(this.format == 4, (Object) "Attempting to set a key's value to a field that is not in FLOAT_MAP format.  Please check the data type definition and use the right format.");
        if (this.zzaUr != null) {
            this.zzaUr.remove(str);
        }
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof Value) && zza((Value) obj));
    }

    public int getFormat() {
        return this.format;
    }

    @Nullable
    public Float getKeyValue(String str) {
        zzac.zza(this.format == 4, (Object) "Value is not in float map format");
        if (this.zzaUr == null || !this.zzaUr.containsKey(str)) {
            return null;
        }
        return Float.valueOf(this.zzaUr.get(str).asFloat());
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(Float.valueOf(this.value), this.zzaGV, this.zzaUr, this.zzaUs, this.zzaUt, this.zzaUu);
    }

    public boolean isSet() {
        return this.zzaUq;
    }

    public void setActivity(String str) {
        setInt(com.google.android.gms.fitness.zza.zzdU(str));
    }

    public void setFloat(float f) {
        zzac.zza(this.format == 2, (Object) "Attempting to set an float value to a field that is not in FLOAT format.  Please check the data type definition and use the right format.");
        this.zzaUq = true;
        this.value = f;
    }

    public void setInt(int i) {
        zzac.zza(this.format == 1, (Object) "Attempting to set an int value to a field that is not in INT32 format.  Please check the data type definition and use the right format.");
        this.zzaUq = true;
        this.value = Float.intBitsToFloat(i);
    }

    public void setKeyValue(String str, float f) {
        zzac.zza(this.format == 4, (Object) "Attempting to set a key's value to a field that is not in FLOAT_MAP format.  Please check the data type definition and use the right format.");
        this.zzaUq = true;
        if (this.zzaUr == null) {
            this.zzaUr = new HashMap();
        }
        this.zzaUr.put(str, MapValue.zzd(f));
    }

    public void setString(String str) {
        zzac.zza(this.format == 3, (Object) "Attempting to set a string value to a field that is not in STRING format.  Please check the data type definition and use the right format.");
        this.zzaUq = true;
        this.zzaGV = str;
    }

    public String toString() {
        if (!this.zzaUq) {
            return "unset";
        }
        switch (this.format) {
            case 1:
                return Integer.toString(asInt());
            case 2:
                return Float.toString(this.value);
            case 3:
                return this.zzaGV;
            case 4:
                return new TreeMap(this.zzaUr).toString();
            case 5:
                return Arrays.toString(this.zzaUs);
            case 6:
                return Arrays.toString(this.zzaUt);
            case 7:
                return zzn.zza(this.zzaUu, 0, this.zzaUu.length, false);
            default:
                return "unknown";
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzad.zza(this, parcel, i);
    }

    /* access modifiers changed from: package-private */
    public String zzCB() {
        return this.zzaGV;
    }

    /* access modifiers changed from: package-private */
    public Bundle zzCC() {
        if (this.zzaUr == null) {
            return null;
        }
        Bundle bundle = new Bundle(this.zzaUr.size());
        for (Map.Entry next : this.zzaUr.entrySet()) {
            bundle.putParcelable((String) next.getKey(), (Parcelable) next.getValue());
        }
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public int[] zzCD() {
        return this.zzaUs;
    }

    /* access modifiers changed from: package-private */
    public float[] zzCE() {
        return this.zzaUt;
    }

    /* access modifiers changed from: package-private */
    public byte[] zzCF() {
        return this.zzaUu;
    }

    /* access modifiers changed from: package-private */
    public float zzCw() {
        return this.value;
    }
}
