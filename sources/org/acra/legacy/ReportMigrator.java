package org.acra.legacy;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.File;
import java.io.FilenameFilter;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.file.CrashReportFileNameParser;
import org.acra.file.ReportLocator;

final class ReportMigrator {
    private final Context context;
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    @NonNull
    private final ReportLocator reportLocator;

    ReportMigrator(@NonNull Context context2) {
        this.context = context2;
        this.reportLocator = new ReportLocator(context2);
    }

    /* access modifiers changed from: package-private */
    public void migrate() {
        ACRA.log.i(ACRA.LOG_TAG, "Migrating unsent ACRA reports to new file locations");
        File[] reportFiles = getCrashReportFiles();
        for (File file : reportFiles) {
            String fileName = file.getName();
            if (this.fileNameParser.isApproved(fileName)) {
                if (file.renameTo(new File(this.reportLocator.getApprovedFolder(), fileName)) && ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "Cold not migrate unsent ACRA crash report : " + fileName);
                }
            } else if (file.renameTo(new File(this.reportLocator.getUnapprovedFolder(), fileName)) && ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Cold not migrate unsent ACRA crash report : " + fileName);
            }
        }
        ACRA.log.i(ACRA.LOG_TAG, "Migrated " + reportFiles.length + " unsent reports");
    }

    @NonNull
    private File[] getCrashReportFiles() {
        File dir = this.context.getFilesDir();
        if (dir == null) {
            ACRA.log.w(ACRA.LOG_TAG, "Application files directory does not exist! The application may not be installed correctly. Please try reinstalling.");
            return new File[0];
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Looking for error files in " + dir.getAbsolutePath());
        }
        File[] result = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, @NonNull String name) {
                return name.endsWith(ACRAConstants.REPORTFILE_EXTENSION);
            }
        });
        return result == null ? new File[0] : result;
    }
}
