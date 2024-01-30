package org.acra.builder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import java.io.File;
import java.lang.Thread;
import java.util.Date;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.collector.CrashReportData;
import org.acra.collector.CrashReportDataFactory;
import org.acra.config.ACRAConfiguration;
import org.acra.file.CrashReportPersister;
import org.acra.file.ReportLocator;
import org.acra.prefs.SharedPreferencesFactory;
import org.acra.sender.SenderServiceStarter;
import org.acra.util.ProcessFinisher;
import org.acra.util.ToastSender;

public final class ReportExecutor {
    private static int mNotificationCounter = 0;
    /* access modifiers changed from: private */
    public final ACRAConfiguration config;
    /* access modifiers changed from: private */
    public final Context context;
    private final CrashReportDataFactory crashReportDataFactory;
    private final Thread.UncaughtExceptionHandler defaultExceptionHandler;
    private boolean enabled = false;
    private final ProcessFinisher processFinisher;
    private final ReportPrimer reportPrimer;

    public ReportExecutor(@NonNull Context context2, @NonNull ACRAConfiguration config2, @NonNull CrashReportDataFactory crashReportDataFactory2, @Nullable Thread.UncaughtExceptionHandler defaultExceptionHandler2, @NonNull ReportPrimer reportPrimer2, @NonNull ProcessFinisher processFinisher2) {
        this.context = context2;
        this.config = config2;
        this.crashReportDataFactory = crashReportDataFactory2;
        this.defaultExceptionHandler = defaultExceptionHandler2;
        this.reportPrimer = reportPrimer2;
        this.processFinisher = processFinisher2;
    }

    private static class TimeHelper {
        /* access modifiers changed from: private */
        public Long initialTimeMillis;

        private TimeHelper() {
        }

        public void setInitialTimeMillis(long initialTimeMillis2) {
            this.initialTimeMillis = Long.valueOf(initialTimeMillis2);
        }

        public long getElapsedTime() {
            if (this.initialTimeMillis == null) {
                return 0;
            }
            return System.currentTimeMillis() - this.initialTimeMillis.longValue();
        }
    }

    public void handReportToDefaultExceptionHandler(@Nullable Thread t, @NonNull Throwable e) {
        if (this.defaultExceptionHandler != null) {
            ACRA.log.i(ACRA.LOG_TAG, "ACRA is disabled for " + this.context.getPackageName() + " - forwarding uncaught Exception on to default ExceptionHandler");
            this.defaultExceptionHandler.uncaughtException(t, e);
            return;
        }
        ACRA.log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.context.getPackageName() + " - no default ExceptionHandler");
        ACRA.log.e(ACRA.LOG_TAG, "ACRA caught a " + e.getClass().getSimpleName() + " for " + this.context.getPackageName(), e);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled2) {
        this.enabled = enabled2;
    }

    public void execute(@NonNull ReportBuilder reportBuilder) {
        ReportingInteractionMode reportingInteractionMode;
        boolean shouldDisplayToast;
        final boolean showDirectDialog;
        if (!this.enabled) {
            ACRA.log.v(ACRA.LOG_TAG, "ACRA is disabled. Report not sent.");
            return;
        }
        this.reportPrimer.primeReport(this.context, reportBuilder);
        boolean sendOnlySilentReports = false;
        if (!reportBuilder.isSendSilently()) {
            reportingInteractionMode = this.config.reportingInteractionMode();
        } else {
            reportingInteractionMode = ReportingInteractionMode.SILENT;
            if (this.config.reportingInteractionMode() != ReportingInteractionMode.SILENT) {
                sendOnlySilentReports = true;
            }
        }
        if (reportingInteractionMode == ReportingInteractionMode.TOAST || (this.config.resToastText() != 0 && (reportingInteractionMode == ReportingInteractionMode.NOTIFICATION || reportingInteractionMode == ReportingInteractionMode.DIALOG))) {
            shouldDisplayToast = true;
        } else {
            shouldDisplayToast = false;
        }
        final TimeHelper sentToastTimeMillis = new TimeHelper();
        if (shouldDisplayToast) {
            new Thread() {
                public void run() {
                    Looper.prepare();
                    ToastSender.sendToast(ReportExecutor.this.context, ReportExecutor.this.config.resToastText(), 1);
                    sentToastTimeMillis.setInitialTimeMillis(System.currentTimeMillis());
                    Looper.loop();
                }
            }.start();
        }
        CrashReportData crashReportData = this.crashReportDataFactory.createCrashData(reportBuilder);
        final File reportFile = getReportFileName(crashReportData);
        saveCrashReportFile(reportFile, crashReportData);
        SharedPreferences prefs = new SharedPreferencesFactory(this.context, this.config).create();
        if (reportingInteractionMode == ReportingInteractionMode.SILENT || reportingInteractionMode == ReportingInteractionMode.TOAST || prefs.getBoolean(ACRA.PREF_ALWAYS_ACCEPT, false)) {
            startSendingReports(sendOnlySilentReports);
            if (reportingInteractionMode == ReportingInteractionMode.SILENT && !reportBuilder.isEndApplication()) {
                return;
            }
        } else if (reportingInteractionMode == ReportingInteractionMode.NOTIFICATION) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Creating Notification.");
            }
            createNotification(reportFile, reportBuilder);
        }
        if (reportingInteractionMode != ReportingInteractionMode.DIALOG || prefs.getBoolean(ACRA.PREF_ALWAYS_ACCEPT, false)) {
            showDirectDialog = false;
        } else {
            showDirectDialog = true;
        }
        if (shouldDisplayToast) {
            final ReportBuilder reportBuilder2 = reportBuilder;
            new Thread() {
                public void run() {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Waiting for 2000 millis from " + sentToastTimeMillis.initialTimeMillis + " currentMillis=" + System.currentTimeMillis());
                    }
                    long sleep = 2000 - sentToastTimeMillis.getElapsedTime();
                    if (sleep > 0) {
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e1) {
                            if (ACRA.DEV_LOGGING) {
                                ACRA.log.d(ACRA.LOG_TAG, "Interrupted while waiting for Toast to end.", e1);
                            }
                        }
                    }
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Finished waiting for Toast");
                    }
                    ReportExecutor.this.dialogAndEnd(reportBuilder2, reportFile, showDirectDialog);
                }
            }.start();
            return;
        }
        dialogAndEnd(reportBuilder, reportFile, showDirectDialog);
    }

    /* access modifiers changed from: private */
    public void dialogAndEnd(@NonNull ReportBuilder reportBuilder, @NonNull File reportFile, boolean shouldShowDialog) {
        if (shouldShowDialog) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Creating CrashReportDialog for " + reportFile);
            }
            Intent dialogIntent = createCrashReportDialogIntent(reportFile, reportBuilder);
            dialogIntent.setFlags(268435456);
            this.context.startActivity(dialogIntent);
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Wait for Toast + worker ended. Kill Application ? " + reportBuilder.isEndApplication());
        }
        if (!reportBuilder.isEndApplication()) {
            return;
        }
        if (Debug.isDebuggerConnected()) {
            new Thread() {
                public void run() {
                    Looper.prepare();
                    Toast.makeText(ReportExecutor.this.context, "Warning: Acra may behave differently with a debugger attached", 1).show();
                    Looper.loop();
                }
            }.start();
            ACRA.log.w(ACRA.LOG_TAG, "Warning: Acra may behave differently with a debugger attached");
            this.processFinisher.finishLastActivity(reportBuilder.getUncaughtExceptionThread());
            return;
        }
        endApplication(reportBuilder.getUncaughtExceptionThread(), reportBuilder.getException());
    }

    private void endApplication(@Nullable Thread uncaughtExceptionThread, Throwable th) {
        boolean letDefaultHandlerEndApplication = this.config.alsoReportToAndroidFramework();
        if (!(uncaughtExceptionThread != null) || !letDefaultHandlerEndApplication || this.defaultExceptionHandler == null) {
            this.processFinisher.endApplication(uncaughtExceptionThread);
            return;
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Handing Exception on to default ExceptionHandler");
        }
        this.defaultExceptionHandler.uncaughtException(uncaughtExceptionThread, th);
    }

    private void startSendingReports(boolean onlySendSilentReports) {
        if (this.enabled) {
            new SenderServiceStarter(this.context, this.config).startService(onlySendSilentReports, true);
        } else {
            ACRA.log.w(ACRA.LOG_TAG, "Would be sending reports, but ACRA is disabled");
        }
    }

    private void createNotification(@NonNull File reportFile, @NonNull ReportBuilder reportBuilder) {
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService("notification");
        int icon = this.config.resNotifIcon();
        CharSequence tickerText = this.context.getText(this.config.resNotifTickerText());
        long when = System.currentTimeMillis();
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Creating Notification for " + reportFile);
        }
        Intent crashReportDialogIntent = createCrashReportDialogIntent(reportFile, reportBuilder);
        Context context2 = this.context;
        int i = mNotificationCounter;
        mNotificationCounter = i + 1;
        Notification notification = new NotificationCompat.Builder(this.context).setSmallIcon(icon).setTicker(tickerText).setWhen(when).setAutoCancel(true).setContentTitle(this.context.getText(this.config.resNotifTitle())).setContentText(this.context.getText(this.config.resNotifText())).setContentIntent(PendingIntent.getActivity(context2, i, crashReportDialogIntent, 134217728)).build();
        notification.flags |= 16;
        Intent deleteIntent = createCrashReportDialogIntent(reportFile, reportBuilder);
        deleteIntent.putExtra(ACRAConstants.EXTRA_FORCE_CANCEL, true);
        notification.deleteIntent = PendingIntent.getActivity(this.context, -1, deleteIntent, 0);
        notificationManager.notify(ACRAConstants.NOTIF_CRASH_ID, notification);
    }

    @NonNull
    private File getReportFileName(@NonNull CrashReportData crashData) {
        Object property = crashData.getProperty(ReportField.USER_CRASH_DATE);
        String isSilent = crashData.getProperty(ReportField.IS_SILENT);
        StringBuilder sb = new StringBuilder();
        if (property == null) {
            property = Long.valueOf(new Date().getTime());
        }
        return new File(new ReportLocator(this.context).getUnapprovedFolder(), sb.append(property).append(isSilent != null ? ACRAConstants.SILENT_SUFFIX : "").append(ACRAConstants.REPORTFILE_EXTENSION).toString());
    }

    private void saveCrashReportFile(@NonNull File file, @NonNull CrashReportData crashData) {
        try {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Writing crash report file " + file);
            }
            new CrashReportPersister().store(crashData, file);
        } catch (Exception e) {
            ACRA.log.e(ACRA.LOG_TAG, "An error occurred while writing the report file...", e);
        }
    }

    @NonNull
    private Intent createCrashReportDialogIntent(@NonNull File reportFile, @NonNull ReportBuilder reportBuilder) {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Creating DialogIntent for " + reportFile + " exception=" + reportBuilder.getException());
        }
        Intent dialogIntent = new Intent(this.context, this.config.reportDialogClass());
        dialogIntent.putExtra(ACRAConstants.EXTRA_REPORT_FILE, reportFile);
        dialogIntent.putExtra(ACRAConstants.EXTRA_REPORT_EXCEPTION, reportBuilder.getException());
        dialogIntent.putExtra(ACRAConstants.EXTRA_REPORT_CONFIG, this.config);
        return dialogIntent;
    }
}
