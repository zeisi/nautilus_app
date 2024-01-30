package org.acra.collector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.Element;
import org.acra.model.StringElement;
import org.acra.util.PackageManagerWrapper;

final class DeviceIdCollector extends Collector {
    private final Context context;
    private final PackageManagerWrapper pm;
    private final SharedPreferences prefs;

    DeviceIdCollector(Context context2, PackageManagerWrapper pm2, SharedPreferences prefs2) {
        super(ReportField.DEVICE_ID);
        this.context = context2;
        this.pm = pm2;
        this.prefs = prefs2;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        if (!super.shouldCollect(crashReportFields, collect, reportBuilder) || !this.prefs.getBoolean(ACRA.PREF_ENABLE_DEVICE_ID, true) || !this.pm.hasPermission("android.permission.READ_PHONE_STATE")) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        String result = getDeviceId();
        return result != null ? new StringElement(result) : ACRAConstants.NOT_AVAILABLE;
    }

    @Nullable
    @SuppressLint({"HardwareIds"})
    private String getDeviceId() {
        try {
            return ((TelephonyManager) this.context.getSystemService("phone")).getDeviceId();
        } catch (RuntimeException e) {
            ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve DeviceId for : " + this.context.getPackageName(), e);
            return null;
        }
    }
}
