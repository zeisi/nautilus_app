package org.acra;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.Thread;
import java.util.GregorianCalendar;
import org.acra.builder.LastActivityManager;
import org.acra.builder.NoOpReportPrimer;
import org.acra.builder.ReportBuilder;
import org.acra.builder.ReportExecutor;
import org.acra.builder.ReportPrimer;
import org.acra.collector.ConfigurationCollector;
import org.acra.collector.CrashReportDataFactory;
import org.acra.config.ACRAConfiguration;
import org.acra.model.Element;
import org.acra.util.ApplicationStartupProcessor;
import org.acra.util.InstanceCreator;
import org.acra.util.ProcessFinisher;

public class ErrorReporter implements Thread.UncaughtExceptionHandler {
    @NonNull
    private final ACRAConfiguration config;
    private final Application context;
    @NonNull
    private final CrashReportDataFactory crashReportDataFactory;
    @NonNull
    private volatile ExceptionHandlerInitializer exceptionHandlerInitializer = new ExceptionHandlerInitializer() {
        public void initializeExceptionHandler(ErrorReporter reporter) {
        }
    };
    @NonNull
    private final ReportExecutor reportExecutor;
    private final boolean supportedAndroidVersion;

    ErrorReporter(@NonNull Application context2, @NonNull ACRAConfiguration config2, @NonNull SharedPreferences prefs, boolean enabled, boolean supportedAndroidVersion2, boolean listenForUncaughtExceptions) {
        Element initialConfiguration;
        Thread.UncaughtExceptionHandler defaultExceptionHandler;
        this.context = context2;
        this.config = config2;
        this.supportedAndroidVersion = supportedAndroidVersion2;
        if (config2.reportContent().contains(ReportField.INITIAL_CONFIGURATION)) {
            initialConfiguration = ConfigurationCollector.collectConfiguration(this.context);
        } else {
            initialConfiguration = ACRAConstants.NOT_AVAILABLE;
        }
        this.crashReportDataFactory = new CrashReportDataFactory(this.context, config2, prefs, new GregorianCalendar(), initialConfiguration);
        if (listenForUncaughtExceptions) {
            defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        } else {
            defaultExceptionHandler = null;
        }
        LastActivityManager lastActivityManager = new LastActivityManager(this.context);
        ProcessFinisher processFinisher = new ProcessFinisher(context2, config2, lastActivityManager);
        CrashReportDataFactory crashReportDataFactory2 = this.crashReportDataFactory;
        Application application = context2;
        ACRAConfiguration aCRAConfiguration = config2;
        this.reportExecutor = new ReportExecutor(application, aCRAConfiguration, crashReportDataFactory2, defaultExceptionHandler, (ReportPrimer) new InstanceCreator().create(config2.reportPrimerClass(), new NoOpReportPrimer()), processFinisher);
        this.reportExecutor.setEnabled(enabled);
    }

    @Deprecated
    public void addCustomData(@NonNull String key, String value) {
        putCustomData(key, value);
    }

    public String putCustomData(@NonNull String key, String value) {
        return this.crashReportDataFactory.putCustomData(key, value);
    }

    public void setExceptionHandlerInitializer(@Nullable ExceptionHandlerInitializer initializer) {
        if (initializer == null) {
            initializer = new ExceptionHandlerInitializer() {
                public void initializeExceptionHandler(ErrorReporter reporter) {
                }
            };
        }
        this.exceptionHandlerInitializer = initializer;
    }

    public String removeCustomData(@NonNull String key) {
        return this.crashReportDataFactory.removeCustomData(key);
    }

    public void clearCustomData() {
        this.crashReportDataFactory.clearCustomData();
    }

    public String getCustomData(@NonNull String key) {
        return this.crashReportDataFactory.getCustomData(key);
    }

    public void uncaughtException(@Nullable Thread t, @NonNull Throwable e) {
        if (!this.reportExecutor.isEnabled()) {
            this.reportExecutor.handReportToDefaultExceptionHandler(t, e);
            return;
        }
        try {
            ACRA.log.e(ACRA.LOG_TAG, "ACRA caught a " + e.getClass().getSimpleName() + " for " + this.context.getPackageName(), e);
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Building report");
            }
            performDeprecatedReportPriming();
            new ReportBuilder().uncaughtExceptionThread(t).exception(e).endApplication().build(this.reportExecutor);
        } catch (Throwable fatality) {
            ACRA.log.e(ACRA.LOG_TAG, "ACRA failed to capture the error - handing off to native error reporter", fatality);
            this.reportExecutor.handReportToDefaultExceptionHandler(t, e);
        }
    }

    public void handleSilentException(@Nullable Throwable e) {
        performDeprecatedReportPriming();
        new ReportBuilder().exception(e).sendSilently().build(this.reportExecutor);
    }

    public void setEnabled(boolean enabled) {
        if (this.supportedAndroidVersion) {
            ACRA.log.i(ACRA.LOG_TAG, "ACRA is " + (enabled ? "enabled" : "disabled") + " for " + this.context.getPackageName());
            this.reportExecutor.setEnabled(enabled);
            return;
        }
        ACRA.log.w(ACRA.LOG_TAG, "ACRA 4.7.0+ requires Froyo or greater. ACRA is disabled and will NOT catch crashes or send messages.");
    }

    public void checkReportsOnApplicationStart() {
        ApplicationStartupProcessor startupProcessor = new ApplicationStartupProcessor(this.context, this.config);
        if (this.config.deleteOldUnsentReportsOnApplicationStart()) {
            startupProcessor.deleteUnsentReportsFromOldAppVersion();
        }
        if (this.config.deleteUnapprovedReportsOnApplicationStart()) {
            startupProcessor.deleteAllUnapprovedReportsBarOne();
        }
        if (this.reportExecutor.isEnabled()) {
            startupProcessor.sendApprovedReports();
        }
    }

    public void handleException(@Nullable Throwable e, boolean endApplication) {
        performDeprecatedReportPriming();
        ReportBuilder builder = new ReportBuilder();
        builder.exception(e);
        if (endApplication) {
            builder.endApplication();
        }
        builder.build(this.reportExecutor);
    }

    public void handleException(@Nullable Throwable e) {
        handleException(e, false);
    }

    private void performDeprecatedReportPriming() {
        try {
            this.exceptionHandlerInitializer.initializeExceptionHandler(this);
        } catch (Exception e) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to initialize " + this.exceptionHandlerInitializer + " from #handleException");
        }
    }
}
