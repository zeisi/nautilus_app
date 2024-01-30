package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public class zzasm extends zza implements Geofence {
    public static final Parcelable.Creator<zzasm> CREATOR = new zzasn();
    private final String zzOV;
    private final int zzbjH;
    private final short zzbjJ;
    private final double zzbjK;
    private final double zzbjL;
    private final float zzbjM;
    private final int zzbjN;
    private final int zzbjO;
    private final long zzbkY;

    public zzasm(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        zzeT(str);
        zzf(f);
        zza(d, d2);
        int zzkA = zzkA(i);
        this.zzbjJ = s;
        this.zzOV = str;
        this.zzbjK = d;
        this.zzbjL = d2;
        this.zzbjM = f;
        this.zzbkY = j;
        this.zzbjH = zzkA;
        this.zzbjN = i2;
        this.zzbjO = i3;
    }

    private static void zza(double d, double d2) {
        if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException(new StringBuilder(42).append("invalid latitude: ").append(d).toString());
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException(new StringBuilder(43).append("invalid longitude: ").append(d2).toString());
        }
    }

    private static void zzeT(String str) {
        if (str == null || str.length() > 100) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "requestId is null or too long: ".concat(valueOf) : new String("requestId is null or too long: "));
        }
    }

    private static void zzf(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException(new StringBuilder(31).append("invalid radius: ").append(f).toString());
        }
    }

    private static int zzkA(int i) {
        int i2 = i & 7;
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException(new StringBuilder(46).append("No supported transition specified: ").append(i).toString());
    }

    @SuppressLint({"DefaultLocale"})
    private static String zzkB(int i) {
        switch (i) {
            case 1:
                return "CIRCLE";
            default:
                return null;
        }
    }

    public static zzasm zzw(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        zzasm createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return createFromParcel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof zzasm)) {
            return false;
        }
        zzasm zzasm = (zzasm) obj;
        if (this.zzbjM != zzasm.zzbjM) {
            return false;
        }
        if (this.zzbjK != zzasm.zzbjK) {
            return false;
        }
        if (this.zzbjL != zzasm.zzbjL) {
            return false;
        }
        return this.zzbjJ == zzasm.zzbjJ;
    }

    public long getExpirationTime() {
        return this.zzbkY;
    }

    public double getLatitude() {
        return this.zzbjK;
    }

    public double getLongitude() {
        return this.zzbjL;
    }

    public float getRadius() {
        return this.zzbjM;
    }

    public String getRequestId() {
        return this.zzOV;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzbjK);
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzbjL);
        return ((((((((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.zzbjM)) * 31) + this.zzbjJ) * 31) + this.zzbjH;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{zzkB(this.zzbjJ), this.zzOV, Integer.valueOf(this.zzbjH), Double.valueOf(this.zzbjK), Double.valueOf(this.zzbjL), Float.valueOf(this.zzbjM), Integer.valueOf(this.zzbjN / 1000), Integer.valueOf(this.zzbjO), Long.valueOf(this.zzbkY)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzasn.zza(this, parcel, i);
    }

    public short zzIt() {
        return this.zzbjJ;
    }

    public int zzIu() {
        return this.zzbjH;
    }

    public int zzIv() {
        return this.zzbjN;
    }

    public int zzIw() {
        return this.zzbjO;
    }
}
