package org.acra.collector;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Iterator;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONException;

final class SettingsCollector extends Collector {
    private static final String ERROR = "Error: ";
    private final ACRAConfiguration config;
    private final Context context;

    SettingsCollector(@NonNull Context context2, @NonNull ACRAConfiguration config2) {
        super(ReportField.SETTINGS_SYSTEM, ReportField.SETTINGS_SECURE, ReportField.SETTINGS_GLOBAL);
        this.context = context2;
        this.config = config2;
    }

    @NonNull
    private Element collectSystemSettings() throws JSONException {
        ComplexElement result = new ComplexElement();
        for (Field key : Settings.System.class.getFields()) {
            if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class) {
                try {
                    String value = Settings.System.getString(this.context.getContentResolver(), (String) key.get((Object) null));
                    if (value != null) {
                        result.put(key.getName(), value);
                    }
                } catch (IllegalArgumentException e) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e);
                } catch (IllegalAccessException e2) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e2);
                }
            }
        }
        return result;
    }

    @NonNull
    private Element collectSecureSettings() throws JSONException {
        ComplexElement result = new ComplexElement();
        for (Field key : Settings.Secure.class.getFields()) {
            if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class && isAuthorized(key)) {
                try {
                    String value = Settings.Secure.getString(this.context.getContentResolver(), (String) key.get((Object) null));
                    if (value != null) {
                        result.put(key.getName(), value);
                    }
                } catch (IllegalArgumentException e) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e);
                } catch (IllegalAccessException e2) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e2);
                }
            }
        }
        return result;
    }

    @NonNull
    private Element collectGlobalSettings() throws JSONException {
        if (Build.VERSION.SDK_INT < 17) {
            return ACRAConstants.NOT_AVAILABLE;
        }
        ComplexElement result = new ComplexElement();
        for (Field key : Settings.Global.class.getFields()) {
            if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class && isAuthorized(key)) {
                try {
                    String value = Settings.Global.getString(this.context.getContentResolver(), (String) key.get((Object) null));
                    if (value != null) {
                        result.put(key.getName(), value);
                    }
                } catch (IllegalArgumentException e) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e);
                } catch (SecurityException e2) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e2);
                } catch (IllegalAccessException e3) {
                    ACRA.log.w(ACRA.LOG_TAG, ERROR, e3);
                }
            }
        }
        return result;
    }

    private boolean isAuthorized(@Nullable Field key) {
        if (key == null || key.getName().startsWith("WIFI_AP")) {
            return false;
        }
        Iterator<String> it = this.config.excludeMatchingSettingsKeys().iterator();
        while (it.hasNext()) {
            if (key.getName().matches(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        try {
            switch (reportField) {
                case SETTINGS_SYSTEM:
                    return collectSystemSettings();
                case SETTINGS_SECURE:
                    return collectSecureSettings();
                case SETTINGS_GLOBAL:
                    return collectGlobalSettings();
                default:
                    throw new IllegalArgumentException();
            }
        } catch (JSONException e) {
            ACRA.log.w("Could not collect Settings", (Throwable) e);
            return ACRAConstants.NOT_AVAILABLE;
        }
        ACRA.log.w("Could not collect Settings", (Throwable) e);
        return ACRAConstants.NOT_AVAILABLE;
    }
}
