package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzj;

public class zzany {
    private static int zzaUv = -1;

    private static boolean zzCG() {
        return !TextUtils.isEmpty(Build.PRODUCT) && Build.PRODUCT.startsWith("glass_");
    }

    public static boolean zzbo(Context context) {
        return zzj.zzaZ(context);
    }

    public static int zzbp(Context context) {
        if (zzaUv == -1) {
            if (zzbo(context)) {
                zzaUv = 3;
            } else if (zzbs(context)) {
                zzaUv = 0;
            } else if (zzbq(context)) {
                zzaUv = 2;
            } else if (zzCG()) {
                zzaUv = 6;
            } else {
                zzaUv = 1;
            }
        }
        return zzaUv;
    }

    private static boolean zzbq(Context context) {
        return zzj.zzb(context.getResources()) && !zzbr(context);
    }

    private static boolean zzbr(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getPhoneType() != 0;
        } catch (Resources.NotFoundException e) {
            Log.wtf("Fitness", "Unable to determine type of device, assuming phone.", e);
            return true;
        }
    }

    private static boolean zzbs(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature("com.google.android.tv") || packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback");
    }
}
