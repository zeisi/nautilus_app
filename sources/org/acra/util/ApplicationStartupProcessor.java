package org.acra.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import java.io.File;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.config.ACRAConfiguration;
import org.acra.file.BulkReportDeleter;
import org.acra.file.CrashReportFileNameParser;
import org.acra.file.ReportLocator;
import org.acra.prefs.PrefUtils;
import org.acra.prefs.SharedPreferencesFactory;
import org.acra.sender.SenderServiceStarter;

public final class ApplicationStartupProcessor {
    private final ACRAConfiguration config;
    private final Context context;

    public ApplicationStartupProcessor(@NonNull Context context2, @NonNull ACRAConfiguration config2) {
        this.context = context2;
        this.config = config2;
    }

    public void deleteUnsentReportsFromOldAppVersion() {
        SharedPreferences prefs = new SharedPreferencesFactory(this.context, this.config).create();
        long lastVersionNr = (long) prefs.getInt(ACRA.PREF_LAST_VERSION_NR, 0);
        int appVersion = getAppVersion();
        if (((long) appVersion) > lastVersionNr) {
            BulkReportDeleter reportDeleter = new BulkReportDeleter(this.context);
            reportDeleter.deleteReports(true, 0);
            reportDeleter.deleteReports(false, 0);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putInt(ACRA.PREF_LAST_VERSION_NR, appVersion);
            PrefUtils.save(prefsEditor);
        }
    }

    public void deleteAllUnapprovedReportsBarOne() {
        new BulkReportDeleter(this.context).deleteReports(false, 1);
    }

    public void sendApprovedReports() {
        File[] reportFiles = new ReportLocator(this.context).getApprovedReports();
        if (reportFiles.length != 0) {
            if (this.config.reportingInteractionMode() == ReportingInteractionMode.TOAST && hasNonSilentApprovedReports(reportFiles)) {
                ToastSender.sendToast(this.context, this.config.resToastText(), 1);
            }
            new SenderServiceStarter(this.context, this.config).startService(false, false);
        }
    }

    private int getAppVersion() {
        PackageInfo packageInfo = new PackageManagerWrapper(this.context).getPackageInfo();
        if (packageInfo == null) {
            return 0;
        }
        return packageInfo.versionCode;
    }

    private boolean hasNonSilentApprovedReports(File[] reportFiles) {
        CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
        for (File file : reportFiles) {
            if (!fileNameParser.isSilent(file.getName())) {
                return true;
            }
        }
        return false;
    }
}
