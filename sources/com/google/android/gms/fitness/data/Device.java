package com.google.android.gms.fitness.data;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzany;
import com.google.android.gms.internal.zzapu;

public final class Device extends zza {
    public static final Parcelable.Creator<Device> CREATOR = new zzn();
    public static final int TYPE_CHEST_STRAP = 4;
    public static final int TYPE_HEAD_MOUNTED = 6;
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_SCALE = 5;
    public static final int TYPE_TABLET = 2;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WATCH = 3;
    private final int type;
    private final String version;
    private final int versionCode;
    private final String zzaTk;
    private final String zzaTl;
    private final String zzaTm;
    private final int zzaTn;

    Device(int i, String str, String str2, String str3, String str4, int i2, int i3) {
        this.versionCode = i;
        this.zzaTk = (String) zzac.zzw(str);
        this.zzaTl = (String) zzac.zzw(str2);
        this.version = "";
        this.zzaTm = zzdY(str4);
        this.type = i2;
        this.zzaTn = i3;
    }

    public Device(String str, String str2, String str3, int i) {
        this(str, str2, "", str3, i, 0);
    }

    public Device(String str, String str2, String str3, String str4, int i, int i2) {
        this(1, str, str2, "", str4, i, i2);
    }

    public static Device getLocalDevice(Context context) {
        int zzbp = zzany.zzbp(context);
        return new Device(Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE, zzbn(context), zzbp, 2);
    }

    private boolean zzCn() {
        return zzCm() == 1;
    }

    private boolean zza(Device device) {
        return zzaa.equal(this.zzaTk, device.zzaTk) && zzaa.equal(this.zzaTl, device.zzaTl) && zzaa.equal(this.version, device.version) && zzaa.equal(this.zzaTm, device.zzaTm) && this.type == device.type && this.zzaTn == device.zzaTn;
    }

    private static String zzbn(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    private String zzdY(String str) {
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Device UID is null.");
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof Device) && zza((Device) obj));
    }

    public String getManufacturer() {
        return this.zzaTk;
    }

    public String getModel() {
        return this.zzaTl;
    }

    /* access modifiers changed from: package-private */
    public String getStreamIdentifier() {
        return String.format("%s:%s:%s", new Object[]{this.zzaTk, this.zzaTl, this.zzaTm});
    }

    public int getType() {
        return this.type;
    }

    public String getUid() {
        return this.zzaTm;
    }

    public String getVersion() {
        return this.version;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaTk, this.zzaTl, this.version, this.zzaTm, Integer.valueOf(this.type));
    }

    public String toString() {
        return String.format("Device{%s:%s:%s:%s}", new Object[]{getStreamIdentifier(), this.version, Integer.valueOf(this.type), Integer.valueOf(this.zzaTn)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzn.zza(this, parcel, i);
    }

    public int zzCm() {
        return this.zzaTn;
    }

    public String zzCo() {
        return zzCn() ? this.zzaTm : zzapu.zzek(this.zzaTm);
    }
}
