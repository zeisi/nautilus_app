package org.acra;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import org.acra.annotation.ReportsCrashes;
import org.acra.config.ACRAConfiguration;
import org.acra.config.ACRAConfigurationException;
import org.acra.config.ConfigurationBuilder;
import org.acra.legacy.LegacyFileHandler;
import org.acra.log.ACRALog;
import org.acra.log.AndroidLogDelegate;
import org.acra.prefs.SharedPreferencesFactory;
import org.acra.util.ApplicationStartupProcessor;
import org.acra.util.IOUtils;

public final class ACRA {
    private static final String ACRA_PRIVATE_PROCESS_NAME = ":acra";
    public static boolean DEV_LOGGING = false;
    public static final String LOG_TAG = ACRA.class.getSimpleName();
    public static final String PREF_ALWAYS_ACCEPT = "acra.alwaysaccept";
    public static final String PREF_DISABLE_ACRA = "acra.disable";
    public static final String PREF_ENABLE_ACRA = "acra.enable";
    public static final String PREF_ENABLE_DEVICE_ID = "acra.deviceid.enable";
    public static final String PREF_ENABLE_SYSTEM_LOGS = "acra.syslog.enable";
    public static final String PREF_LAST_VERSION_NR = "acra.lastVersionNr";
    public static final String PREF_USER_EMAIL_ADDRESS = "acra.user.email";
    @Nullable
    private static ACRAConfiguration configProxy;
    @Nullable
    private static ErrorReporter errorReporterSingleton;
    @NonNull
    public static ACRALog log = new AndroidLogDelegate();
    private static Application mApplication;
    private static SharedPreferences.OnSharedPreferenceChangeListener mPrefListener;

    private ACRA() {
    }

    public static void init(@NonNull Application app) {
        if (((ReportsCrashes) app.getClass().getAnnotation(ReportsCrashes.class)) == null) {
            log.e(LOG_TAG, "ACRA#init(Application) called but no ReportsCrashes annotation on Application " + app.getPackageName());
        } else {
            init(app, new ConfigurationBuilder(app));
        }
    }

    public static void init(@NonNull Application app, @NonNull ConfigurationBuilder builder) {
        init(app, builder, true);
    }

    public static void init(@NonNull Application app, @NonNull ConfigurationBuilder builder, boolean checkReportsOnApplicationStart) {
        try {
            init(app, builder.build(), checkReportsOnApplicationStart);
        } catch (ACRAConfigurationException e) {
            log.w(LOG_TAG, "Configuration Error - ACRA not started : " + e.getMessage());
        }
    }

    public static void init(@NonNull Application app, @NonNull ACRAConfiguration config) {
        init(app, config, true);
    }

    public static void init(@NonNull Application app, @NonNull ACRAConfiguration config, boolean checkReportsOnApplicationStart) {
        boolean supportedAndroidVersion;
        boolean enableAcra;
        boolean z = true;
        boolean senderServiceProcess = isACRASenderServiceProcess();
        if (senderServiceProcess && DEV_LOGGING) {
            log.d(LOG_TAG, "Not initialising ACRA to listen for uncaught Exceptions as this is the SendWorker process and we only send reports, we don't capture them to avoid infinite loops");
        }
        if (Build.VERSION.SDK_INT >= 8) {
            supportedAndroidVersion = true;
        } else {
            supportedAndroidVersion = false;
        }
        if (!supportedAndroidVersion) {
            log.w(LOG_TAG, "ACRA 4.7.0+ requires Froyo or greater. ACRA is disabled and will NOT catch crashes or send messages.");
        }
        if (mApplication != null) {
            log.w(LOG_TAG, "ACRA#init called more than once. Won't do anything more.");
            return;
        }
        mApplication = app;
        if (config == null) {
            log.e(LOG_TAG, "ACRA#init called but no ACRAConfiguration provided");
            return;
        }
        configProxy = config;
        SharedPreferences prefs = new SharedPreferencesFactory(mApplication, configProxy).create();
        new LegacyFileHandler(app, prefs).updateToCurrentVersionIfNecessary();
        if (!supportedAndroidVersion || shouldDisableACRA(prefs)) {
            enableAcra = false;
        } else {
            enableAcra = true;
        }
        if (!senderServiceProcess) {
            log.i(LOG_TAG, "ACRA is " + (enableAcra ? "enabled" : "disabled") + " for " + mApplication.getPackageName() + ", initializing...");
        }
        Application application = mApplication;
        ACRAConfiguration aCRAConfiguration = configProxy;
        if (senderServiceProcess) {
            z = false;
        }
        errorReporterSingleton = new ErrorReporter(application, aCRAConfiguration, prefs, enableAcra, supportedAndroidVersion, z);
        if (checkReportsOnApplicationStart && !senderServiceProcess) {
            ApplicationStartupProcessor startupProcessor = new ApplicationStartupProcessor(mApplication, config);
            if (config.deleteOldUnsentReportsOnApplicationStart()) {
                startupProcessor.deleteUnsentReportsFromOldAppVersion();
            }
            if (config.deleteUnapprovedReportsOnApplicationStart()) {
                startupProcessor.deleteAllUnapprovedReportsBarOne();
            }
            if (enableAcra) {
                startupProcessor.sendApprovedReports();
            }
        }
        mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(@NonNull SharedPreferences sharedPreferences, String key) {
                if (ACRA.PREF_DISABLE_ACRA.equals(key) || ACRA.PREF_ENABLE_ACRA.equals(key)) {
                    ACRA.getErrorReporter().setEnabled(!ACRA.shouldDisableACRA(sharedPreferences));
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(mPrefListener);
    }

    public static boolean isInitialised() {
        return configProxy != null;
    }

    public static boolean isACRASenderServiceProcess() {
        String processName = getCurrentProcessName();
        if (DEV_LOGGING) {
            log.d(LOG_TAG, "ACRA processName='" + processName + '\'');
        }
        return processName != null && processName.endsWith(ACRA_PRIVATE_PROCESS_NAME);
    }

    @Nullable
    private static String getCurrentProcessName() {
        try {
            return IOUtils.streamToString(new FileInputStream("/proc/self/cmdline")).trim();
        } catch (IOException e) {
            return null;
        }
    }

    @NonNull
    public static ErrorReporter getErrorReporter() {
        if (errorReporterSingleton != null) {
            return errorReporterSingleton;
        }
        throw new IllegalStateException("Cannot access ErrorReporter before ACRA#init");
    }

    /* access modifiers changed from: private */
    public static boolean shouldDisableACRA(@NonNull SharedPreferences prefs) {
        boolean z = true;
        try {
            if (prefs.getBoolean(PREF_ENABLE_ACRA, true)) {
                z = false;
            }
            return prefs.getBoolean(PREF_DISABLE_ACRA, z);
        } catch (Exception e) {
            return false;
        }
    }

    @NonNull
    public static SharedPreferences getACRASharedPreferences() {
        if (configProxy != null) {
            return new SharedPreferencesFactory(mApplication, configProxy).create();
        }
        throw new IllegalStateException("Cannot call ACRA.getACRASharedPreferences() before ACRA.init().");
    }

    @NonNull
    public static ACRAConfiguration getConfig() {
        if (configProxy != null) {
            return configProxy;
        }
        throw new IllegalStateException("Cannot call ACRA.getConfig() before ACRA.init().");
    }

    public static void setLog(@NonNull ACRALog log2) {
        if (log2 == null) {
            throw new NullPointerException("ACRALog cannot be null");
        }
        log = log2;
    }
}
