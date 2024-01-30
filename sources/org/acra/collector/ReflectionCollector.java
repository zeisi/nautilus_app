package org.acra.collector;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class ReflectionCollector extends Collector {
    private final ACRAConfiguration config;
    private final Context context;

    ReflectionCollector(Context context2, ACRAConfiguration config2) {
        super(ReportField.BUILD, ReportField.BUILD_CONFIG, ReportField.ENVIRONMENT);
        this.context = context2;
        this.config = config2;
    }

    private static void collectConstants(@NonNull Class<?> someClass, @NonNull JSONObject container) throws JSONException {
        for (Field field : someClass.getFields()) {
            try {
                Object value = field.get((Object) null);
                if (value != null) {
                    if (field.getType().isArray()) {
                        container.put(field.getName(), new JSONArray(Arrays.asList((Object[]) value)));
                    } else {
                        container.put(field.getName(), value);
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException e) {
            }
        }
    }

    private static void collectStaticGettersResults(@NonNull Class<?> someClass, JSONObject container) throws JSONException {
        for (Method method : someClass.getMethods()) {
            if (method.getParameterTypes().length == 0 && ((method.getName().startsWith("get") || method.getName().startsWith("is")) && !"getClass".equals(method.getName()))) {
                try {
                    container.put(method.getName(), method.invoke((Object) null, (Object[]) null));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        ComplexElement result = new ComplexElement();
        try {
            switch (reportField) {
                case BUILD:
                    collectConstants(Build.class, result);
                    JSONObject version = new JSONObject();
                    collectConstants(Build.VERSION.class, version);
                    result.put("VERSION", version);
                    return result;
                case BUILD_CONFIG:
                    try {
                        collectConstants(getBuildConfigClass(), result);
                        return result;
                    } catch (ClassNotFoundException e) {
                        return result;
                    }
                case ENVIRONMENT:
                    collectStaticGettersResults(Environment.class, result);
                    return result;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (JSONException e2) {
            ACRA.log.w("Couldn't collect constants", (Throwable) e2);
            return ACRAConstants.NOT_AVAILABLE;
        }
        ACRA.log.w("Couldn't collect constants", (Throwable) e2);
        return ACRAConstants.NOT_AVAILABLE;
    }

    @NonNull
    private Class<?> getBuildConfigClass() throws ClassNotFoundException {
        Class configuredBuildConfig = this.config.buildConfigClass();
        if (!configuredBuildConfig.equals(Object.class)) {
            return configuredBuildConfig;
        }
        String className = this.context.getPackageName() + ".BuildConfig";
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Not adding buildConfig to log. Class Not found : " + className + ". Please configure 'buildConfigClass' in your ACRA config");
            throw e;
        }
    }
}
