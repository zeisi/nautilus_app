package org.acra.annotation;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.attachment.DefaultAttachmentProvider;
import org.acra.builder.NoOpReportPrimer;
import org.acra.builder.ReportPrimer;
import org.acra.config.DefaultRetryPolicy;
import org.acra.config.RetryPolicy;
import org.acra.dialog.BaseCrashReportDialog;
import org.acra.dialog.CrashReportDialog;
import org.acra.file.Directory;
import org.acra.security.KeyStoreFactory;
import org.acra.security.NoKeyStoreFactory;
import org.acra.sender.DefaultReportSenderFactory;
import org.acra.sender.HttpSender;
import org.acra.sender.ReportSenderFactory;

@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportsCrashes {
    @NonNull
    String[] additionalDropBoxTags() default {};

    @NonNull
    String[] additionalSharedPreferences() default {};

    boolean alsoReportToAndroidFramework() default false;

    @NonNull
    String applicationLogFile() default "";

    @NonNull
    Directory applicationLogFileDir() default Directory.FILES_LEGACY;

    int applicationLogFileLines() default 100;

    @NonNull
    Class<? extends AttachmentUriProvider> attachmentUriProvider() default DefaultAttachmentProvider.class;

    @NonNull
    String[] attachmentUris() default {};

    @NonNull
    Class buildConfigClass() default Object.class;

    @NonNull
    String certificatePath() default "";

    @NonNull
    String certificateType() default "X.509";

    int connectionTimeout() default 5000;

    @NonNull
    ReportField[] customReportContent() default {};

    boolean deleteOldUnsentReportsOnApplicationStart() default true;

    boolean deleteUnapprovedReportsOnApplicationStart() default true;

    int dropboxCollectionMinutes() default 5;

    @NonNull
    String[] excludeMatchingSettingsKeys() default {};

    @NonNull
    String[] excludeMatchingSharedPreferencesKeys() default {};

    @NonNull
    String formUri() default "";

    @NonNull
    String formUriBasicAuthLogin() default "ACRA-NULL-STRING";

    @NonNull
    String formUriBasicAuthPassword() default "ACRA-NULL-STRING";

    @NonNull
    HttpSender.Method httpMethod() default HttpSender.Method.POST;

    boolean includeDropBoxSystemTags() default false;

    @NonNull
    Class<? extends KeyStoreFactory> keyStoreFactoryClass() default NoKeyStoreFactory.class;

    @NonNull
    String[] logcatArguments() default {"-t", "100", "-v", "time"};

    boolean logcatFilterByPid() default false;

    @NonNull
    String mailTo() default "";

    @NonNull
    ReportingInteractionMode mode() default ReportingInteractionMode.SILENT;

    boolean nonBlockingReadForLogcat() default false;

    boolean reportAsFile() default false;

    @NonNull
    Class<? extends BaseCrashReportDialog> reportDialogClass() default CrashReportDialog.class;

    @NonNull
    Class<? extends ReportPrimer> reportPrimerClass() default NoOpReportPrimer.class;

    @NonNull
    Class<? extends ReportSenderFactory>[] reportSenderFactoryClasses() default {DefaultReportSenderFactory.class};

    @NonNull
    HttpSender.Type reportType() default HttpSender.Type.FORM;

    @RawRes
    int resCertificate() default 0;

    @StringRes
    int resDialogCommentPrompt() default 0;

    @StringRes
    int resDialogEmailPrompt() default 0;

    @DrawableRes
    int resDialogIcon() default 17301543;

    @StringRes
    int resDialogNegativeButtonText() default 17039360;

    @StringRes
    int resDialogOkToast() default 0;

    @StringRes
    int resDialogPositiveButtonText() default 17039370;

    @StringRes
    int resDialogText() default 0;

    @StyleRes
    int resDialogTheme() default 0;

    @StringRes
    int resDialogTitle() default 0;

    @DrawableRes
    int resNotifIcon() default 17301624;

    @StringRes
    int resNotifText() default 0;

    @StringRes
    int resNotifTickerText() default 0;

    @StringRes
    int resNotifTitle() default 0;

    @StringRes
    int resToastText() default 0;

    @NonNull
    Class<? extends RetryPolicy> retryPolicyClass() default DefaultRetryPolicy.class;

    @Deprecated
    boolean sendReportsAtShutdown() default true;

    boolean sendReportsInDevMode() default true;

    int sharedPreferencesMode() default 0;

    @NonNull
    String sharedPreferencesName() default "";

    int socketTimeout() default 20000;

    boolean stopServicesOnCrash() default false;
}
