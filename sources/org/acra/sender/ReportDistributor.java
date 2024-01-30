package org.acra.sender;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.acra.ACRA;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfiguration;
import org.acra.config.DefaultRetryPolicy;
import org.acra.config.RetryPolicy;
import org.acra.file.CrashReportPersister;
import org.acra.util.IOUtils;
import org.acra.util.InstanceCreator;
import org.json.JSONException;

final class ReportDistributor {
    private final ACRAConfiguration config;
    private final Context context;
    private final List<ReportSender> reportSenders;

    ReportDistributor(@NonNull Context context2, @NonNull ACRAConfiguration config2, @NonNull List<ReportSender> reportSenders2) {
        this.context = context2;
        this.config = config2;
        this.reportSenders = reportSenders2;
    }

    public void distribute(@NonNull File reportFile) {
        ACRA.log.i(ACRA.LOG_TAG, "Sending report " + reportFile);
        try {
            sendCrashReport(new CrashReportPersister().load(reportFile));
            IOUtils.deleteReport(reportFile);
        } catch (RuntimeException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to send crash reports for " + reportFile, e);
            IOUtils.deleteReport(reportFile);
        } catch (IOException e2) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to load crash report for " + reportFile, e2);
            IOUtils.deleteReport(reportFile);
        } catch (JSONException e3) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to load crash report for " + reportFile, e3);
            IOUtils.deleteReport(reportFile);
        } catch (ReportSenderException e4) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to send crash report for " + reportFile, e4);
        }
    }

    private void sendCrashReport(@NonNull CrashReportData errorContent) throws ReportSenderException {
        if (!isDebuggable() || this.config.sendReportsInDevMode()) {
            List<RetryPolicy.FailedSender> failedSenders = new LinkedList<>();
            for (ReportSender sender : this.reportSenders) {
                try {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Sending report using " + sender.getClass().getName());
                    }
                    sender.send(this.context, errorContent);
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Sent report using " + sender.getClass().getName());
                    }
                } catch (ReportSenderException e) {
                    failedSenders.add(new RetryPolicy.FailedSender(sender, e));
                }
            }
            InstanceCreator instanceCreator = new InstanceCreator();
            if (failedSenders.isEmpty()) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "Report was sent by all senders");
                }
            } else if (((RetryPolicy) instanceCreator.create(this.config.retryPolicyClass(), new DefaultRetryPolicy())).shouldRetrySend(this.reportSenders, failedSenders)) {
                throw new ReportSenderException("Policy marked this task as incomplete. ACRA will try to send this report again.", failedSenders.get(0).getException());
            } else {
                StringBuilder builder = new StringBuilder("ReportSenders of classes [");
                for (RetryPolicy.FailedSender failedSender : failedSenders) {
                    builder.append(failedSender.getSender().getClass().getName());
                    builder.append(", ");
                }
                builder.append("] failed, but Policy marked this task as complete. ACRA will not send this report again.");
                ACRA.log.w(ACRA.LOG_TAG, builder.toString());
            }
        }
    }

    private boolean isDebuggable() {
        try {
            if ((this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 0).flags & 2) > 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
