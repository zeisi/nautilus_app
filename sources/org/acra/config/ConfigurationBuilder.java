package org.acra.config;

import android.app.Application;
import android.support.annotation.NonNull;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.dialog.CrashReportDialog;

public final class ConfigurationBuilder extends BaseConfigurationBuilder<ConfigurationBuilder> {
    private final Map<String, String> httpHeaders = new HashMap();
    private final Map<ReportField, Boolean> reportContentChanges = new EnumMap(ReportField.class);

    public ConfigurationBuilder(@NonNull Application app) {
        super(app);
    }

    @NonNull
    public ACRAConfiguration build() throws ACRAConfigurationException {
        switch (reportingInteractionMode()) {
            case TOAST:
                if (resToastText() == 0) {
                    throw new ACRAConfigurationException("TOAST mode: you have to define the resToastText parameter in your application @ReportsCrashes() annotation.");
                }
                break;
            case NOTIFICATION:
                if (resNotifTickerText() == 0 || resNotifTitle() == 0 || resNotifText() == 0) {
                    throw new ACRAConfigurationException("NOTIFICATION mode: you have to define at least the resNotifTickerText, resNotifTitle, resNotifText parameters in your application @ReportsCrashes() annotation.");
                } else if (CrashReportDialog.class.equals(reportDialogClass()) && resDialogText() == 0) {
                    throw new ACRAConfigurationException("NOTIFICATION mode: using the (default) CrashReportDialog requires you have to define the resDialogText parameter in your application @ReportsCrashes() annotation.");
                }
                break;
            case DIALOG:
                if (CrashReportDialog.class.equals(reportDialogClass()) && resDialogText() == 0) {
                    throw new ACRAConfigurationException("DIALOG mode: using the (default) CrashReportDialog requires you to define the resDialogText parameter in your application @ReportsCrashes() annotation.");
                }
        }
        if (reportSenderFactoryClasses().length == 0) {
            throw new ACRAConfigurationException("Report sender factories: using no report senders will make ACRA useless. Configure at least one ReportSenderFactory.");
        }
        checkValidity(reportSenderFactoryClasses());
        checkValidity(reportDialogClass(), reportPrimerClass(), retryPolicyClass(), keyStoreFactoryClass());
        return new ACRAConfiguration(this);
    }

    private void checkValidity(Class<?>... classes) throws ACRAConfigurationException {
        int i = 0;
        int length = classes.length;
        while (i < length) {
            Class<?> clazz = classes[i];
            if (clazz.isInterface()) {
                throw new ACRAConfigurationException("Expected class, but found interface " + clazz.getName() + ".");
            } else if (Modifier.isAbstract(clazz.getModifiers())) {
                throw new ACRAConfigurationException("Class " + clazz.getName() + " cannot be abstract.");
            } else {
                try {
                    clazz.getConstructor(new Class[0]);
                    i++;
                } catch (NoSuchMethodException e) {
                    throw new ACRAConfigurationException("Class " + clazz.getName() + " is missing a no-args Constructor.", e);
                }
            }
        }
    }

    @NonNull
    public ConfigurationBuilder setReportField(@NonNull ReportField field, boolean enable) {
        this.reportContentChanges.put(field, Boolean.valueOf(enable));
        return this;
    }

    @NonNull
    public ConfigurationBuilder setHttpHeaders(@NonNull Map<String, String> headers) {
        this.httpHeaders.clear();
        this.httpHeaders.putAll(headers);
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Map<String, String> httpHeaders() {
        return this.httpHeaders;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ReportField[] customReportContent() {
        return super.customReportContent();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Set<ReportField> reportContent() {
        Set<ReportField> reportContent = new LinkedHashSet<>();
        if (customReportContent().length != 0) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Using custom Report Fields");
            }
            reportContent.addAll(Arrays.asList(customReportContent()));
        } else if ("".equals(mailTo())) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Using default Report Fields");
            }
            reportContent.addAll(Arrays.asList(ACRAConstants.DEFAULT_REPORT_FIELDS));
        } else {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Using default Mail Report Fields");
            }
            reportContent.addAll(Arrays.asList(ACRAConstants.DEFAULT_MAIL_REPORT_FIELDS));
        }
        for (Map.Entry<ReportField, Boolean> entry : this.reportContentChanges.entrySet()) {
            if (entry.getValue().booleanValue()) {
                reportContent.add(entry.getKey());
            } else {
                reportContent.remove(entry.getKey());
            }
        }
        return reportContent;
    }
}
