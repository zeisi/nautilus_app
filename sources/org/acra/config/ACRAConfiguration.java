package org.acra.config;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import java.io.Serializable;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.builder.ReportPrimer;
import org.acra.collections.ImmutableList;
import org.acra.collections.ImmutableMap;
import org.acra.collections.ImmutableSet;
import org.acra.dialog.BaseCrashReportDialog;
import org.acra.file.Directory;
import org.acra.security.KeyStoreFactory;
import org.acra.sender.HttpSender;
import org.acra.sender.ReportSenderFactory;

public final class ACRAConfiguration implements Serializable {
    @NonNull
    private ImmutableList<String> additionalDropBoxTags;
    @NonNull
    private ImmutableList<String> additionalSharedPreferences;
    private boolean alsoReportToAndroidFramework;
    @NonNull
    private String applicationLogFile;
    @NonNull
    private Directory applicationLogFileDir;
    private int applicationLogFileLines;
    @NonNull
    private Class<? extends AttachmentUriProvider> attachmentUriProvider;
    @NonNull
    private ImmutableList<String> attachmentUris;
    @NonNull
    private Class buildConfigClass;
    @NonNull
    private String certificatePath;
    @NonNull
    private String certificateType;
    private int connectionTimeout;
    private boolean deleteOldUnsentReportsOnApplicationStart;
    private boolean deleteUnapprovedReportsOnApplicationStart;
    private int dropboxCollectionMinutes;
    @NonNull
    private ImmutableList<String> excludeMatchingSettingsKeys;
    @NonNull
    private ImmutableList<String> excludeMatchingSharedPreferencesKeys;
    @NonNull
    private String formUri;
    @NonNull
    private String formUriBasicAuthLogin;
    @NonNull
    private String formUriBasicAuthPassword;
    @NonNull
    private ImmutableMap<String, String> httpHeaders;
    @NonNull
    private HttpSender.Method httpMethod;
    private boolean includeDropBoxSystemTags;
    @NonNull
    private Class<? extends KeyStoreFactory> keyStoreFactoryClass;
    @NonNull
    private ImmutableList<String> logcatArguments;
    private boolean logcatFilterByPid;
    @NonNull
    private String mailTo;
    private boolean nonBlockingReadForLogcat;
    private boolean reportAsFile;
    @NonNull
    private ImmutableSet<ReportField> reportContent;
    @NonNull
    private Class<? extends BaseCrashReportDialog> reportDialogClass;
    @NonNull
    private Class<? extends ReportPrimer> reportPrimerClass;
    @NonNull
    private ImmutableList<Class<? extends ReportSenderFactory>> reportSenderFactoryClasses;
    @NonNull
    private HttpSender.Type reportType;
    @NonNull
    private ReportingInteractionMode reportingInteractionMode;
    @RawRes
    private int resCertificate;
    @StringRes
    private int resDialogCommentPrompt;
    @StringRes
    private int resDialogEmailPrompt;
    @DrawableRes
    private int resDialogIcon;
    @StringRes
    private int resDialogNegativeButtonText;
    @StringRes
    private int resDialogOkToast;
    @StringRes
    private int resDialogPositiveButtonText;
    @StringRes
    private int resDialogText;
    @StyleRes
    private int resDialogTheme;
    @StringRes
    private int resDialogTitle;
    @DrawableRes
    private int resNotifIcon;
    @StringRes
    private int resNotifText;
    @StringRes
    private int resNotifTickerText;
    @StringRes
    private int resNotifTitle;
    @StringRes
    private int resToastText;
    @NonNull
    private Class<? extends RetryPolicy> retryPolicyClass;
    private boolean sendReportsInDevMode;
    private int sharedPreferencesMode;
    @NonNull
    private String sharedPreferencesName;
    private int socketTimeout;
    private boolean stopServicesOnCrash;

    public ACRAConfiguration(@NonNull ConfigurationBuilder builder) {
        this.reportPrimerClass = builder.reportPrimerClass();
        this.resDialogCommentPrompt = builder.resDialogCommentPrompt();
        this.resDialogPositiveButtonText = builder.resDialogPositiveButtonText();
        this.httpHeaders = new ImmutableMap<>(builder.httpHeaders());
        this.reportType = builder.reportType();
        this.certificatePath = builder.certificatePath();
        this.excludeMatchingSettingsKeys = new ImmutableList<>((E[]) builder.excludeMatchingSettingsKeys());
        this.applicationLogFileDir = builder.applicationLogFileDir();
        this.formUriBasicAuthPassword = builder.formUriBasicAuthPassword();
        this.stopServicesOnCrash = builder.stopServicesOnCrash();
        this.formUri = builder.formUri();
        this.socketTimeout = builder.socketTimeout();
        this.resNotifIcon = builder.resNotifIcon();
        this.mailTo = builder.mailTo();
        this.buildConfigClass = builder.buildConfigClass();
        this.sendReportsInDevMode = builder.sendReportsInDevMode();
        this.resNotifText = builder.resNotifText();
        this.sharedPreferencesName = builder.sharedPreferencesName();
        this.reportingInteractionMode = builder.reportingInteractionMode();
        this.dropboxCollectionMinutes = builder.dropboxCollectionMinutes();
        this.deleteUnapprovedReportsOnApplicationStart = builder.deleteUnapprovedReportsOnApplicationStart();
        this.resDialogNegativeButtonText = builder.resDialogNegativeButtonText();
        this.resDialogOkToast = builder.resDialogOkToast();
        this.includeDropBoxSystemTags = builder.includeDropBoxSystemTags();
        this.resDialogText = builder.resDialogText();
        this.excludeMatchingSharedPreferencesKeys = new ImmutableList<>((E[]) builder.excludeMatchingSharedPreferencesKeys());
        this.certificateType = builder.certificateType();
        this.reportAsFile = builder.reportAsFile();
        this.sharedPreferencesMode = builder.sharedPreferencesMode();
        this.resNotifTickerText = builder.resNotifTickerText();
        this.resDialogTitle = builder.resDialogTitle();
        this.resNotifTitle = builder.resNotifTitle();
        this.resDialogIcon = builder.resDialogIcon();
        this.deleteOldUnsentReportsOnApplicationStart = builder.deleteOldUnsentReportsOnApplicationStart();
        this.logcatFilterByPid = builder.logcatFilterByPid();
        this.reportDialogClass = builder.reportDialogClass();
        this.httpMethod = builder.httpMethod();
        this.keyStoreFactoryClass = builder.keyStoreFactoryClass();
        this.additionalSharedPreferences = new ImmutableList<>((E[]) builder.additionalSharedPreferences());
        this.resDialogTheme = builder.resDialogTheme();
        this.logcatArguments = new ImmutableList<>((E[]) builder.logcatArguments());
        this.resToastText = builder.resToastText();
        this.applicationLogFile = builder.applicationLogFile();
        this.retryPolicyClass = builder.retryPolicyClass();
        this.resCertificate = builder.resCertificate();
        this.connectionTimeout = builder.connectionTimeout();
        this.alsoReportToAndroidFramework = builder.alsoReportToAndroidFramework();
        this.additionalDropBoxTags = new ImmutableList<>((E[]) builder.additionalDropBoxTags());
        this.reportContent = new ImmutableSet<>(builder.reportContent());
        this.resDialogEmailPrompt = builder.resDialogEmailPrompt();
        this.attachmentUris = new ImmutableList<>((E[]) builder.attachmentUris());
        this.reportSenderFactoryClasses = new ImmutableList<>((E[]) builder.reportSenderFactoryClasses());
        this.applicationLogFileLines = builder.applicationLogFileLines();
        this.formUriBasicAuthLogin = builder.formUriBasicAuthLogin();
        this.nonBlockingReadForLogcat = builder.nonBlockingReadForLogcat();
        this.attachmentUriProvider = builder.attachmentUriProvider();
    }

    @NonNull
    public Class<? extends ReportPrimer> reportPrimerClass() {
        return this.reportPrimerClass;
    }

    @StringRes
    public int resDialogCommentPrompt() {
        return this.resDialogCommentPrompt;
    }

    @StringRes
    public int resDialogPositiveButtonText() {
        return this.resDialogPositiveButtonText;
    }

    @NonNull
    public ImmutableMap<String, String> httpHeaders() {
        return this.httpHeaders;
    }

    @NonNull
    public HttpSender.Type reportType() {
        return this.reportType;
    }

    @NonNull
    public String certificatePath() {
        return this.certificatePath;
    }

    @NonNull
    public ImmutableList<String> excludeMatchingSettingsKeys() {
        return this.excludeMatchingSettingsKeys;
    }

    @NonNull
    public Directory applicationLogFileDir() {
        return this.applicationLogFileDir;
    }

    @NonNull
    public String formUriBasicAuthPassword() {
        return this.formUriBasicAuthPassword;
    }

    public boolean stopServicesOnCrash() {
        return this.stopServicesOnCrash;
    }

    @NonNull
    public String formUri() {
        return this.formUri;
    }

    public int socketTimeout() {
        return this.socketTimeout;
    }

    @DrawableRes
    public int resNotifIcon() {
        return this.resNotifIcon;
    }

    @NonNull
    public String mailTo() {
        return this.mailTo;
    }

    @NonNull
    public Class buildConfigClass() {
        return this.buildConfigClass;
    }

    public boolean sendReportsInDevMode() {
        return this.sendReportsInDevMode;
    }

    @StringRes
    public int resNotifText() {
        return this.resNotifText;
    }

    @NonNull
    public String sharedPreferencesName() {
        return this.sharedPreferencesName;
    }

    @NonNull
    public ReportingInteractionMode reportingInteractionMode() {
        return this.reportingInteractionMode;
    }

    public int dropboxCollectionMinutes() {
        return this.dropboxCollectionMinutes;
    }

    public boolean deleteUnapprovedReportsOnApplicationStart() {
        return this.deleteUnapprovedReportsOnApplicationStart;
    }

    @StringRes
    public int resDialogNegativeButtonText() {
        return this.resDialogNegativeButtonText;
    }

    @StringRes
    public int resDialogOkToast() {
        return this.resDialogOkToast;
    }

    public boolean includeDropBoxSystemTags() {
        return this.includeDropBoxSystemTags;
    }

    @StringRes
    public int resDialogText() {
        return this.resDialogText;
    }

    @NonNull
    public ImmutableList<String> excludeMatchingSharedPreferencesKeys() {
        return this.excludeMatchingSharedPreferencesKeys;
    }

    @NonNull
    public String certificateType() {
        return this.certificateType;
    }

    public boolean reportAsFile() {
        return this.reportAsFile;
    }

    public int sharedPreferencesMode() {
        return this.sharedPreferencesMode;
    }

    @StringRes
    public int resNotifTickerText() {
        return this.resNotifTickerText;
    }

    @StringRes
    public int resDialogTitle() {
        return this.resDialogTitle;
    }

    @StringRes
    public int resNotifTitle() {
        return this.resNotifTitle;
    }

    @DrawableRes
    public int resDialogIcon() {
        return this.resDialogIcon;
    }

    public boolean deleteOldUnsentReportsOnApplicationStart() {
        return this.deleteOldUnsentReportsOnApplicationStart;
    }

    public boolean logcatFilterByPid() {
        return this.logcatFilterByPid;
    }

    @NonNull
    public Class<? extends BaseCrashReportDialog> reportDialogClass() {
        return this.reportDialogClass;
    }

    @NonNull
    public HttpSender.Method httpMethod() {
        return this.httpMethod;
    }

    @NonNull
    public Class<? extends KeyStoreFactory> keyStoreFactoryClass() {
        return this.keyStoreFactoryClass;
    }

    @NonNull
    public ImmutableList<String> additionalSharedPreferences() {
        return this.additionalSharedPreferences;
    }

    @StyleRes
    public int resDialogTheme() {
        return this.resDialogTheme;
    }

    @NonNull
    public ImmutableList<String> logcatArguments() {
        return this.logcatArguments;
    }

    @StringRes
    public int resToastText() {
        return this.resToastText;
    }

    @NonNull
    public String applicationLogFile() {
        return this.applicationLogFile;
    }

    @NonNull
    public Class<? extends RetryPolicy> retryPolicyClass() {
        return this.retryPolicyClass;
    }

    @RawRes
    public int resCertificate() {
        return this.resCertificate;
    }

    public int connectionTimeout() {
        return this.connectionTimeout;
    }

    public boolean alsoReportToAndroidFramework() {
        return this.alsoReportToAndroidFramework;
    }

    @NonNull
    public ImmutableList<String> additionalDropBoxTags() {
        return this.additionalDropBoxTags;
    }

    @NonNull
    public ImmutableSet<ReportField> reportContent() {
        return this.reportContent;
    }

    @StringRes
    public int resDialogEmailPrompt() {
        return this.resDialogEmailPrompt;
    }

    @NonNull
    public ImmutableList<String> attachmentUris() {
        return this.attachmentUris;
    }

    @NonNull
    public ImmutableList<Class<? extends ReportSenderFactory>> reportSenderFactoryClasses() {
        return this.reportSenderFactoryClasses;
    }

    public int applicationLogFileLines() {
        return this.applicationLogFileLines;
    }

    @NonNull
    public String formUriBasicAuthLogin() {
        return this.formUriBasicAuthLogin;
    }

    public boolean nonBlockingReadForLogcat() {
        return this.nonBlockingReadForLogcat;
    }

    @NonNull
    public Class<? extends AttachmentUriProvider> attachmentUriProvider() {
        return this.attachmentUriProvider;
    }
}
