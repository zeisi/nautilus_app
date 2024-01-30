package org.acra.sender;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.acra.ACRA;
import org.acra.config.ACRAConfiguration;
import org.acra.file.CrashReportFileNameParser;
import org.acra.file.ReportLocator;
import org.acra.util.InstanceCreator;

public class SenderService extends IntentService {
    public static final String EXTRA_ACRA_CONFIG = "acraConfig";
    public static final String EXTRA_APPROVE_REPORTS_FIRST = "approveReportsFirst";
    public static final String EXTRA_ONLY_SEND_SILENT_REPORTS = "onlySendSilentReports";
    private final ReportLocator locator = new ReportLocator(this);

    public SenderService() {
        super("ACRA SenderService");
        setIntentRedelivery(true);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_ACRA_CONFIG)) {
            boolean onlySendSilentReports = intent.getBooleanExtra(EXTRA_ONLY_SEND_SILENT_REPORTS, false);
            boolean approveReportsFirst = intent.getBooleanExtra(EXTRA_APPROVE_REPORTS_FIRST, false);
            ACRAConfiguration config = (ACRAConfiguration) intent.getSerializableExtra(EXTRA_ACRA_CONFIG);
            Collection<Class<? extends ReportSenderFactory>> senderFactoryClasses = config.reportSenderFactoryClasses();
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "About to start sending reports from SenderService");
            }
            try {
                List<ReportSender> senderInstances = getSenderInstances(config, senderFactoryClasses);
                if (approveReportsFirst) {
                    markReportsAsApproved();
                }
                File[] reports = this.locator.getApprovedReports();
                ReportDistributor reportDistributor = new ReportDistributor(this, config, senderInstances);
                int reportsSentCount = 0;
                CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
                for (File report : reports) {
                    if (!onlySendSilentReports || fileNameParser.isSilent(report.getName())) {
                        if (reportsSentCount >= 5) {
                            break;
                        }
                        reportDistributor.distribute(report);
                        reportsSentCount++;
                    }
                }
            } catch (Exception e) {
                ACRA.log.e(ACRA.LOG_TAG, "", e);
            }
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Finished sending reports from SenderService");
            }
        } else if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "SenderService was started but no valid intent was delivered, will now quit");
        }
    }

    @NonNull
    private List<ReportSender> getSenderInstances(@NonNull ACRAConfiguration config, @NonNull Collection<Class<? extends ReportSenderFactory>> factoryClasses) {
        List<ReportSender> reportSenders = new ArrayList<>();
        for (T factory : new InstanceCreator().create(factoryClasses)) {
            reportSenders.add(factory.create(getApplication(), config));
        }
        return reportSenders;
    }

    private void markReportsAsApproved() {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Mark all pending reports as approved.");
        }
        for (File report : this.locator.getUnapprovedReports()) {
            File approvedReport = new File(this.locator.getApprovedFolder(), report.getName());
            if (!report.renameTo(approvedReport)) {
                ACRA.log.w(ACRA.LOG_TAG, "Could not rename approved report from " + report + " to " + approvedReport);
            }
        }
    }
}
