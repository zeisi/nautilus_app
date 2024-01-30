package org.acra.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.acra.ACRA;

public final class PackageManagerWrapper {
    private final Context context;

    public PackageManagerWrapper(@NonNull Context context2) {
        this.context = context2;
    }

    public boolean hasPermission(@NonNull String permission) {
        PackageManager pm = this.context.getPackageManager();
        if (pm == null) {
            return false;
        }
        try {
            if (pm.checkPermission(permission, this.context.getPackageName()) == 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    @Nullable
    public PackageInfo getPackageInfo() {
        PackageManager pm = this.context.getPackageManager();
        if (pm == null) {
            return null;
        }
        try {
            return pm.getPackageInfo(this.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to find PackageInfo for current App : " + this.context.getPackageName());
            return null;
        } catch (Throwable th) {
            return null;
        }
    }
}
