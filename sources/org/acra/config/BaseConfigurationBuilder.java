package org.acra.config;

import android.app.Application;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.attachment.DefaultAttachmentProvider;
import org.acra.builder.NoOpReportPrimer;
import org.acra.builder.ReportPrimer;
import org.acra.config.BaseConfigurationBuilder;
import org.acra.dialog.BaseCrashReportDialog;
import org.acra.dialog.CrashReportDialog;
import org.acra.file.Directory;
import org.acra.security.KeyStoreFactory;
import org.acra.security.NoKeyStoreFactory;
import org.acra.sender.HttpSender;
import org.acra.sender.ReportSenderFactory;

public abstract class BaseConfigurationBuilder<T extends BaseConfigurationBuilder> {
    @NonNull
    private String[] additionalDropBoxTags;
    @NonNull
    private String[] additionalSharedPreferences;
    private Boolean alsoReportToAndroidFramework;
    @NonNull
    private String applicationLogFile;
    @NonNull
    private Directory applicationLogFileDir;
    private Integer applicationLogFileLines;
    @NonNull
    private Class<? extends AttachmentUriProvider> attachmentUriProvider;
    @NonNull
    private String[] attachmentUris;
    @NonNull
    private Class buildConfigClass;
    @NonNull
    private String certificatePath;
    @NonNull
    private String certificateType;
    private Integer connectionTimeout;
    @NonNull
    private ReportField[] customReportContent;
    private Boolean deleteOldUnsentReportsOnApplicationStart;
    private Boolean deleteUnapprovedReportsOnApplicationStart;
    private Integer dropboxCollectionMinutes;
    @NonNull
    private String[] excludeMatchingSettingsKeys;
    @NonNull
    private String[] excludeMatchingSharedPreferencesKeys;
    @NonNull
    private String formUri;
    @NonNull
    private String formUriBasicAuthLogin;
    @NonNull
    private String formUriBasicAuthPassword;
    @NonNull
    private HttpSender.Method httpMethod;
    private Boolean includeDropBoxSystemTags;
    @NonNull
    private Class<? extends KeyStoreFactory> keyStoreFactoryClass;
    @NonNull
    private String[] logcatArguments;
    private Boolean logcatFilterByPid;
    @NonNull
    private String mailTo;
    private Boolean nonBlockingReadForLogcat;
    private Boolean reportAsFile;
    @NonNull
    private Class<? extends BaseCrashReportDialog> reportDialogClass;
    @NonNull
    private Class<? extends ReportPrimer> reportPrimerClass;
    @NonNull
    private Class<? extends ReportSenderFactory>[] reportSenderFactoryClasses;
    @NonNull
    private HttpSender.Type reportType;
    @NonNull
    private ReportingInteractionMode reportingInteractionMode;
    @RawRes
    private Integer resCertificate;
    @StringRes
    private Integer resDialogCommentPrompt;
    @StringRes
    private Integer resDialogEmailPrompt;
    @DrawableRes
    private Integer resDialogIcon;
    @StringRes
    private Integer resDialogNegativeButtonText;
    @StringRes
    private Integer resDialogOkToast;
    @StringRes
    private Integer resDialogPositiveButtonText;
    @StringRes
    private Integer resDialogText;
    @StyleRes
    private Integer resDialogTheme;
    @StringRes
    private Integer resDialogTitle;
    @DrawableRes
    private Integer resNotifIcon;
    @StringRes
    private Integer resNotifText;
    @StringRes
    private Integer resNotifTickerText;
    @StringRes
    private Integer resNotifTitle;
    @StringRes
    private Integer resToastText;
    @NonNull
    private Class<? extends RetryPolicy> retryPolicyClass;
    private Boolean sendReportsInDevMode;
    private Integer sharedPreferencesMode;
    @NonNull
    private String sharedPreferencesName;
    private Integer socketTimeout;
    private Boolean stopServicesOnCrash;

    public BaseConfigurationBuilder(@NonNull Application app) {
        ReportsCrashes annotationConfig = (ReportsCrashes) app.getClass().getAnnotation(ReportsCrashes.class);
        if (annotationConfig != null) {
            this.formUri = annotationConfig.formUri();
            this.reportingInteractionMode = annotationConfig.mode();
            this.resDialogPositiveButtonText = Integer.valueOf(annotationConfig.resDialogPositiveButtonText());
            this.resDialogNegativeButtonText = Integer.valueOf(annotationConfig.resDialogNegativeButtonText());
            this.resDialogCommentPrompt = Integer.valueOf(annotationConfig.resDialogCommentPrompt());
            this.resDialogEmailPrompt = Integer.valueOf(annotationConfig.resDialogEmailPrompt());
            this.resDialogIcon = Integer.valueOf(annotationConfig.resDialogIcon());
            this.resDialogOkToast = Integer.valueOf(annotationConfig.resDialogOkToast());
            this.resDialogText = Integer.valueOf(annotationConfig.resDialogText());
            this.resDialogTitle = Integer.valueOf(annotationConfig.resDialogTitle());
            this.resDialogTheme = Integer.valueOf(annotationConfig.resDialogTheme());
            this.resNotifIcon = Integer.valueOf(annotationConfig.resNotifIcon());
            this.resNotifText = Integer.valueOf(annotationConfig.resNotifText());
            this.resNotifTickerText = Integer.valueOf(annotationConfig.resNotifTickerText());
            this.resNotifTitle = Integer.valueOf(annotationConfig.resNotifTitle());
            this.resToastText = Integer.valueOf(annotationConfig.resToastText());
            this.sharedPreferencesName = annotationConfig.sharedPreferencesName();
            this.sharedPreferencesMode = Integer.valueOf(annotationConfig.sharedPreferencesMode());
            this.includeDropBoxSystemTags = Boolean.valueOf(annotationConfig.includeDropBoxSystemTags());
            this.additionalDropBoxTags = annotationConfig.additionalDropBoxTags();
            this.dropboxCollectionMinutes = Integer.valueOf(annotationConfig.dropboxCollectionMinutes());
            this.logcatArguments = annotationConfig.logcatArguments();
            this.formUriBasicAuthLogin = annotationConfig.formUriBasicAuthLogin();
            this.formUriBasicAuthPassword = annotationConfig.formUriBasicAuthPassword();
            this.customReportContent = annotationConfig.customReportContent();
            this.mailTo = annotationConfig.mailTo();
            this.deleteUnapprovedReportsOnApplicationStart = Boolean.valueOf(annotationConfig.deleteUnapprovedReportsOnApplicationStart());
            this.deleteOldUnsentReportsOnApplicationStart = Boolean.valueOf(annotationConfig.deleteOldUnsentReportsOnApplicationStart());
            this.connectionTimeout = Integer.valueOf(annotationConfig.connectionTimeout());
            this.socketTimeout = Integer.valueOf(annotationConfig.socketTimeout());
            this.alsoReportToAndroidFramework = Boolean.valueOf(annotationConfig.alsoReportToAndroidFramework());
            this.additionalSharedPreferences = annotationConfig.additionalSharedPreferences();
            this.logcatFilterByPid = Boolean.valueOf(annotationConfig.logcatFilterByPid());
            this.nonBlockingReadForLogcat = Boolean.valueOf(annotationConfig.nonBlockingReadForLogcat());
            this.sendReportsInDevMode = Boolean.valueOf(annotationConfig.sendReportsInDevMode());
            this.excludeMatchingSharedPreferencesKeys = annotationConfig.excludeMatchingSharedPreferencesKeys();
            this.excludeMatchingSettingsKeys = annotationConfig.excludeMatchingSettingsKeys();
            this.buildConfigClass = annotationConfig.buildConfigClass();
            this.reportSenderFactoryClasses = annotationConfig.reportSenderFactoryClasses();
            this.applicationLogFile = annotationConfig.applicationLogFile();
            this.applicationLogFileLines = Integer.valueOf(annotationConfig.applicationLogFileLines());
            this.applicationLogFileDir = annotationConfig.applicationLogFileDir();
            this.reportDialogClass = annotationConfig.reportDialogClass();
            this.reportPrimerClass = annotationConfig.reportPrimerClass();
            this.httpMethod = annotationConfig.httpMethod();
            this.reportType = annotationConfig.reportType();
            this.keyStoreFactoryClass = annotationConfig.keyStoreFactoryClass();
            this.certificatePath = annotationConfig.certificatePath();
            this.resCertificate = Integer.valueOf(annotationConfig.resCertificate());
            this.certificateType = annotationConfig.certificateType();
            this.retryPolicyClass = annotationConfig.retryPolicyClass();
            this.stopServicesOnCrash = Boolean.valueOf(annotationConfig.stopServicesOnCrash());
            this.attachmentUris = annotationConfig.attachmentUris();
            this.attachmentUriProvider = annotationConfig.attachmentUriProvider();
            this.reportAsFile = Boolean.valueOf(annotationConfig.reportAsFile());
        }
    }

    public T setFormUri(@NonNull String formUri2) {
        this.formUri = formUri2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String formUri() {
        if (this.formUri != null) {
            return this.formUri;
        }
        return "";
    }

    public T setReportingInteractionMode(@NonNull ReportingInteractionMode reportingInteractionMode2) {
        this.reportingInteractionMode = reportingInteractionMode2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ReportingInteractionMode reportingInteractionMode() {
        if (this.reportingInteractionMode != null) {
            return this.reportingInteractionMode;
        }
        return ReportingInteractionMode.SILENT;
    }

    public T setResDialogPositiveButtonText(@StringRes int resDialogPositiveButtonText2) {
        this.resDialogPositiveButtonText = Integer.valueOf(resDialogPositiveButtonText2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogPositiveButtonText() {
        if (this.resDialogPositiveButtonText != null) {
            return this.resDialogPositiveButtonText.intValue();
        }
        return ACRAConstants.DEFAULT_DIALOG_POSITIVE_BUTTON_TEXT;
    }

    public T setResDialogNegativeButtonText(@StringRes int resDialogNegativeButtonText2) {
        this.resDialogNegativeButtonText = Integer.valueOf(resDialogNegativeButtonText2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogNegativeButtonText() {
        if (this.resDialogNegativeButtonText != null) {
            return this.resDialogNegativeButtonText.intValue();
        }
        return ACRAConstants.DEFAULT_DIALOG_NEGATIVE_BUTTON_TEXT;
    }

    public T setResDialogCommentPrompt(@StringRes int resDialogCommentPrompt2) {
        this.resDialogCommentPrompt = Integer.valueOf(resDialogCommentPrompt2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogCommentPrompt() {
        if (this.resDialogCommentPrompt != null) {
            return this.resDialogCommentPrompt.intValue();
        }
        return 0;
    }

    public T setResDialogEmailPrompt(@StringRes int resDialogEmailPrompt2) {
        this.resDialogEmailPrompt = Integer.valueOf(resDialogEmailPrompt2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogEmailPrompt() {
        if (this.resDialogEmailPrompt != null) {
            return this.resDialogEmailPrompt.intValue();
        }
        return 0;
    }

    public T setResDialogIcon(@DrawableRes int resDialogIcon2) {
        this.resDialogIcon = Integer.valueOf(resDialogIcon2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @DrawableRes
    public int resDialogIcon() {
        if (this.resDialogIcon != null) {
            return this.resDialogIcon.intValue();
        }
        return ACRAConstants.DEFAULT_DIALOG_ICON;
    }

    public T setResDialogOkToast(@StringRes int resDialogOkToast2) {
        this.resDialogOkToast = Integer.valueOf(resDialogOkToast2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogOkToast() {
        if (this.resDialogOkToast != null) {
            return this.resDialogOkToast.intValue();
        }
        return 0;
    }

    public T setResDialogText(@StringRes int resDialogText2) {
        this.resDialogText = Integer.valueOf(resDialogText2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogText() {
        if (this.resDialogText != null) {
            return this.resDialogText.intValue();
        }
        return 0;
    }

    public T setResDialogTitle(@StringRes int resDialogTitle2) {
        this.resDialogTitle = Integer.valueOf(resDialogTitle2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resDialogTitle() {
        if (this.resDialogTitle != null) {
            return this.resDialogTitle.intValue();
        }
        return 0;
    }

    public T setResDialogTheme(@StyleRes int resDialogTheme2) {
        this.resDialogTheme = Integer.valueOf(resDialogTheme2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StyleRes
    public int resDialogTheme() {
        if (this.resDialogTheme != null) {
            return this.resDialogTheme.intValue();
        }
        return 0;
    }

    public T setResNotifIcon(@DrawableRes int resNotifIcon2) {
        this.resNotifIcon = Integer.valueOf(resNotifIcon2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @DrawableRes
    public int resNotifIcon() {
        if (this.resNotifIcon != null) {
            return this.resNotifIcon.intValue();
        }
        return ACRAConstants.DEFAULT_NOTIFICATION_ICON;
    }

    public T setResNotifText(@StringRes int resNotifText2) {
        this.resNotifText = Integer.valueOf(resNotifText2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resNotifText() {
        if (this.resNotifText != null) {
            return this.resNotifText.intValue();
        }
        return 0;
    }

    public T setResNotifTickerText(@StringRes int resNotifTickerText2) {
        this.resNotifTickerText = Integer.valueOf(resNotifTickerText2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resNotifTickerText() {
        if (this.resNotifTickerText != null) {
            return this.resNotifTickerText.intValue();
        }
        return 0;
    }

    public T setResNotifTitle(@StringRes int resNotifTitle2) {
        this.resNotifTitle = Integer.valueOf(resNotifTitle2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resNotifTitle() {
        if (this.resNotifTitle != null) {
            return this.resNotifTitle.intValue();
        }
        return 0;
    }

    public T setResToastText(@StringRes int resToastText2) {
        this.resToastText = Integer.valueOf(resToastText2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @StringRes
    public int resToastText() {
        if (this.resToastText != null) {
            return this.resToastText.intValue();
        }
        return 0;
    }

    public T setSharedPreferencesName(@NonNull String sharedPreferencesName2) {
        this.sharedPreferencesName = sharedPreferencesName2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String sharedPreferencesName() {
        if (this.sharedPreferencesName != null) {
            return this.sharedPreferencesName;
        }
        return "";
    }

    public T setSharedPreferencesMode(int sharedPreferencesMode2) {
        this.sharedPreferencesMode = Integer.valueOf(sharedPreferencesMode2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public int sharedPreferencesMode() {
        if (this.sharedPreferencesMode != null) {
            return this.sharedPreferencesMode.intValue();
        }
        return 0;
    }

    public T setIncludeDropBoxSystemTags(boolean includeDropBoxSystemTags2) {
        this.includeDropBoxSystemTags = Boolean.valueOf(includeDropBoxSystemTags2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean includeDropBoxSystemTags() {
        if (this.includeDropBoxSystemTags != null) {
            return this.includeDropBoxSystemTags.booleanValue();
        }
        return false;
    }

    public T setAdditionalDropBoxTags(@NonNull String... additionalDropBoxTags2) {
        this.additionalDropBoxTags = additionalDropBoxTags2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String[] additionalDropBoxTags() {
        if (this.additionalDropBoxTags != null) {
            return this.additionalDropBoxTags;
        }
        return new String[0];
    }

    public T setDropboxCollectionMinutes(int dropboxCollectionMinutes2) {
        this.dropboxCollectionMinutes = Integer.valueOf(dropboxCollectionMinutes2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public int dropboxCollectionMinutes() {
        if (this.dropboxCollectionMinutes != null) {
            return this.dropboxCollectionMinutes.intValue();
        }
        return 5;
    }

    public T setLogcatArguments(@NonNull String... logcatArguments2) {
        this.logcatArguments = logcatArguments2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String[] logcatArguments() {
        if (this.logcatArguments != null) {
            return this.logcatArguments;
        }
        return new String[]{"-t", "100", "-v", "time"};
    }

    public T setFormUriBasicAuthLogin(@NonNull String formUriBasicAuthLogin2) {
        this.formUriBasicAuthLogin = formUriBasicAuthLogin2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String formUriBasicAuthLogin() {
        if (this.formUriBasicAuthLogin != null) {
            return this.formUriBasicAuthLogin;
        }
        return ACRAConstants.NULL_VALUE;
    }

    public T setFormUriBasicAuthPassword(@NonNull String formUriBasicAuthPassword2) {
        this.formUriBasicAuthPassword = formUriBasicAuthPassword2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String formUriBasicAuthPassword() {
        if (this.formUriBasicAuthPassword != null) {
            return this.formUriBasicAuthPassword;
        }
        return ACRAConstants.NULL_VALUE;
    }

    public T setCustomReportContent(@NonNull ReportField... customReportContent2) {
        this.customReportContent = customReportContent2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ReportField[] customReportContent() {
        if (this.customReportContent != null) {
            return this.customReportContent;
        }
        return new ReportField[0];
    }

    public T setMailTo(@NonNull String mailTo2) {
        this.mailTo = mailTo2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String mailTo() {
        if (this.mailTo != null) {
            return this.mailTo;
        }
        return "";
    }

    public T setDeleteUnapprovedReportsOnApplicationStart(boolean deleteUnapprovedReportsOnApplicationStart2) {
        this.deleteUnapprovedReportsOnApplicationStart = Boolean.valueOf(deleteUnapprovedReportsOnApplicationStart2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean deleteUnapprovedReportsOnApplicationStart() {
        if (this.deleteUnapprovedReportsOnApplicationStart != null) {
            return this.deleteUnapprovedReportsOnApplicationStart.booleanValue();
        }
        return true;
    }

    public T setDeleteOldUnsentReportsOnApplicationStart(boolean deleteOldUnsentReportsOnApplicationStart2) {
        this.deleteOldUnsentReportsOnApplicationStart = Boolean.valueOf(deleteOldUnsentReportsOnApplicationStart2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean deleteOldUnsentReportsOnApplicationStart() {
        if (this.deleteOldUnsentReportsOnApplicationStart != null) {
            return this.deleteOldUnsentReportsOnApplicationStart.booleanValue();
        }
        return true;
    }

    public T setConnectionTimeout(int connectionTimeout2) {
        this.connectionTimeout = Integer.valueOf(connectionTimeout2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public int connectionTimeout() {
        if (this.connectionTimeout != null) {
            return this.connectionTimeout.intValue();
        }
        return 5000;
    }

    public T setSocketTimeout(int socketTimeout2) {
        this.socketTimeout = Integer.valueOf(socketTimeout2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public int socketTimeout() {
        if (this.socketTimeout != null) {
            return this.socketTimeout.intValue();
        }
        return ACRAConstants.DEFAULT_SOCKET_TIMEOUT;
    }

    public T setAlsoReportToAndroidFramework(boolean alsoReportToAndroidFramework2) {
        this.alsoReportToAndroidFramework = Boolean.valueOf(alsoReportToAndroidFramework2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean alsoReportToAndroidFramework() {
        if (this.alsoReportToAndroidFramework != null) {
            return this.alsoReportToAndroidFramework.booleanValue();
        }
        return false;
    }

    public T setAdditionalSharedPreferences(@NonNull String... additionalSharedPreferences2) {
        this.additionalSharedPreferences = additionalSharedPreferences2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String[] additionalSharedPreferences() {
        if (this.additionalSharedPreferences != null) {
            return this.additionalSharedPreferences;
        }
        return new String[0];
    }

    public T setLogcatFilterByPid(boolean logcatFilterByPid2) {
        this.logcatFilterByPid = Boolean.valueOf(logcatFilterByPid2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean logcatFilterByPid() {
        if (this.logcatFilterByPid != null) {
            return this.logcatFilterByPid.booleanValue();
        }
        return false;
    }

    public T setNonBlockingReadForLogcat(boolean nonBlockingReadForLogcat2) {
        this.nonBlockingReadForLogcat = Boolean.valueOf(nonBlockingReadForLogcat2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean nonBlockingReadForLogcat() {
        if (this.nonBlockingReadForLogcat != null) {
            return this.nonBlockingReadForLogcat.booleanValue();
        }
        return false;
    }

    public T setSendReportsInDevMode(boolean sendReportsInDevMode2) {
        this.sendReportsInDevMode = Boolean.valueOf(sendReportsInDevMode2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean sendReportsInDevMode() {
        if (this.sendReportsInDevMode != null) {
            return this.sendReportsInDevMode.booleanValue();
        }
        return true;
    }

    public T setExcludeMatchingSharedPreferencesKeys(@NonNull String... excludeMatchingSharedPreferencesKeys2) {
        this.excludeMatchingSharedPreferencesKeys = excludeMatchingSharedPreferencesKeys2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String[] excludeMatchingSharedPreferencesKeys() {
        if (this.excludeMatchingSharedPreferencesKeys != null) {
            return this.excludeMatchingSharedPreferencesKeys;
        }
        return new String[0];
    }

    public T setExcludeMatchingSettingsKeys(@NonNull String... excludeMatchingSettingsKeys2) {
        this.excludeMatchingSettingsKeys = excludeMatchingSettingsKeys2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String[] excludeMatchingSettingsKeys() {
        if (this.excludeMatchingSettingsKeys != null) {
            return this.excludeMatchingSettingsKeys;
        }
        return new String[0];
    }

    public T setBuildConfigClass(@NonNull Class buildConfigClass2) {
        this.buildConfigClass = buildConfigClass2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Class buildConfigClass() {
        if (this.buildConfigClass != null) {
            return this.buildConfigClass;
        }
        return Object.class;
    }

    public T setReportSenderFactoryClasses(@NonNull Class<? extends ReportSenderFactory>... reportSenderFactoryClasses2) {
        this.reportSenderFactoryClasses = reportSenderFactoryClasses2;
        return this;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.Class<? extends org.acra.sender.ReportSenderFactory>[]} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Class<? extends org.acra.sender.ReportSenderFactory>[] reportSenderFactoryClasses() {
        /*
            r3 = this;
            java.lang.Class<? extends org.acra.sender.ReportSenderFactory>[] r0 = r3.reportSenderFactoryClasses
            if (r0 == 0) goto L_0x0007
            java.lang.Class<? extends org.acra.sender.ReportSenderFactory>[] r0 = r3.reportSenderFactoryClasses
        L_0x0006:
            return r0
        L_0x0007:
            r0 = 1
            java.lang.Class[] r0 = new java.lang.Class[r0]
            r1 = 0
            java.lang.Class<org.acra.sender.DefaultReportSenderFactory> r2 = org.acra.sender.DefaultReportSenderFactory.class
            r0[r1] = r2
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.config.BaseConfigurationBuilder.reportSenderFactoryClasses():java.lang.Class[]");
    }

    public T setApplicationLogFile(@NonNull String applicationLogFile2) {
        this.applicationLogFile = applicationLogFile2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String applicationLogFile() {
        if (this.applicationLogFile != null) {
            return this.applicationLogFile;
        }
        return "";
    }

    public T setApplicationLogFileLines(int applicationLogFileLines2) {
        this.applicationLogFileLines = Integer.valueOf(applicationLogFileLines2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public int applicationLogFileLines() {
        if (this.applicationLogFileLines != null) {
            return this.applicationLogFileLines.intValue();
        }
        return 100;
    }

    public T setApplicationLogFileDir(@NonNull Directory applicationLogFileDir2) {
        this.applicationLogFileDir = applicationLogFileDir2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Directory applicationLogFileDir() {
        if (this.applicationLogFileDir != null) {
            return this.applicationLogFileDir;
        }
        return Directory.FILES_LEGACY;
    }

    public T setReportDialogClass(@NonNull Class<? extends BaseCrashReportDialog> reportDialogClass2) {
        this.reportDialogClass = reportDialogClass2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Class<? extends BaseCrashReportDialog> reportDialogClass() {
        if (this.reportDialogClass != null) {
            return this.reportDialogClass;
        }
        return CrashReportDialog.class;
    }

    public T setReportPrimerClass(@NonNull Class<? extends ReportPrimer> reportPrimerClass2) {
        this.reportPrimerClass = reportPrimerClass2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Class<? extends ReportPrimer> reportPrimerClass() {
        if (this.reportPrimerClass != null) {
            return this.reportPrimerClass;
        }
        return NoOpReportPrimer.class;
    }

    public T setHttpMethod(@NonNull HttpSender.Method httpMethod2) {
        this.httpMethod = httpMethod2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public HttpSender.Method httpMethod() {
        if (this.httpMethod != null) {
            return this.httpMethod;
        }
        return HttpSender.Method.POST;
    }

    public T setReportType(@NonNull HttpSender.Type reportType2) {
        this.reportType = reportType2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public HttpSender.Type reportType() {
        if (this.reportType != null) {
            return this.reportType;
        }
        return HttpSender.Type.FORM;
    }

    public T setKeyStoreFactoryClass(@NonNull Class<? extends KeyStoreFactory> keyStoreFactoryClass2) {
        this.keyStoreFactoryClass = keyStoreFactoryClass2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Class<? extends KeyStoreFactory> keyStoreFactoryClass() {
        if (this.keyStoreFactoryClass != null) {
            return this.keyStoreFactoryClass;
        }
        return NoKeyStoreFactory.class;
    }

    public T setCertificatePath(@NonNull String certificatePath2) {
        this.certificatePath = certificatePath2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String certificatePath() {
        if (this.certificatePath != null) {
            return this.certificatePath;
        }
        return "";
    }

    public T setResCertificate(@RawRes int resCertificate2) {
        this.resCertificate = Integer.valueOf(resCertificate2);
        return this;
    }

    /* access modifiers changed from: package-private */
    @RawRes
    public int resCertificate() {
        if (this.resCertificate != null) {
            return this.resCertificate.intValue();
        }
        return 0;
    }

    public T setCertificateType(@NonNull String certificateType2) {
        this.certificateType = certificateType2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String certificateType() {
        if (this.certificateType != null) {
            return this.certificateType;
        }
        return ACRAConstants.DEFAULT_CERTIFICATE_TYPE;
    }

    public T setRetryPolicyClass(@NonNull Class<? extends RetryPolicy> retryPolicyClass2) {
        this.retryPolicyClass = retryPolicyClass2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Class<? extends RetryPolicy> retryPolicyClass() {
        if (this.retryPolicyClass != null) {
            return this.retryPolicyClass;
        }
        return DefaultRetryPolicy.class;
    }

    public T setStopServicesOnCrash(boolean stopServicesOnCrash2) {
        this.stopServicesOnCrash = Boolean.valueOf(stopServicesOnCrash2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean stopServicesOnCrash() {
        if (this.stopServicesOnCrash != null) {
            return this.stopServicesOnCrash.booleanValue();
        }
        return false;
    }

    public T setAttachmentUris(@NonNull String... attachmentUris2) {
        this.attachmentUris = attachmentUris2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public String[] attachmentUris() {
        if (this.attachmentUris != null) {
            return this.attachmentUris;
        }
        return new String[0];
    }

    public T setAttachmentUriProvider(@NonNull Class<? extends AttachmentUriProvider> attachmentUriProvider2) {
        this.attachmentUriProvider = attachmentUriProvider2;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Class<? extends AttachmentUriProvider> attachmentUriProvider() {
        if (this.attachmentUriProvider != null) {
            return this.attachmentUriProvider;
        }
        return DefaultAttachmentProvider.class;
    }

    public T setReportAsFile(boolean reportAsFile2) {
        this.reportAsFile = Boolean.valueOf(reportAsFile2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean reportAsFile() {
        if (this.reportAsFile != null) {
            return this.reportAsFile.booleanValue();
        }
        return false;
    }
}
