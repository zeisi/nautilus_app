package org.acra.collector;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONException;

public final class ConfigurationCollector extends Collector {
    private static final String FIELD_MCC = "mcc";
    private static final String FIELD_MNC = "mnc";
    private static final String FIELD_SCREENLAYOUT = "screenLayout";
    private static final String FIELD_UIMODE = "uiMode";
    private static final String PREFIX_HARDKEYBOARDHIDDEN = "HARDKEYBOARDHIDDEN_";
    private static final String PREFIX_KEYBOARD = "KEYBOARD_";
    private static final String PREFIX_KEYBOARDHIDDEN = "KEYBOARDHIDDEN_";
    private static final String PREFIX_NAVIGATION = "NAVIGATION_";
    private static final String PREFIX_NAVIGATIONHIDDEN = "NAVIGATIONHIDDEN_";
    private static final String PREFIX_ORIENTATION = "ORIENTATION_";
    private static final String PREFIX_SCREENLAYOUT = "SCREENLAYOUT_";
    private static final String PREFIX_TOUCHSCREEN = "TOUCHSCREEN_";
    private static final String PREFIX_UI_MODE = "UI_MODE_";
    private static final String SUFFIX_MASK = "_MASK";
    private final Context context;
    private final Element initialConfiguration;

    public ConfigurationCollector(@NonNull Context context2, @NonNull Element initialConfiguration2) {
        super(ReportField.INITIAL_CONFIGURATION, ReportField.CRASH_CONFIGURATION);
        this.context = context2;
        this.initialConfiguration = initialConfiguration2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        switch (reportField) {
            case INITIAL_CONFIGURATION:
                return this.initialConfiguration;
            case CRASH_CONFIGURATION:
                return collectConfiguration(this.context);
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    private static Element configToElement(@NonNull Configuration conf) {
        ComplexElement result = new ComplexElement();
        Map<String, SparseArray<String>> valueArrays = getValueArrays();
        for (Field f : conf.getClass().getFields()) {
            try {
                if (!Modifier.isStatic(f.getModifiers())) {
                    String fieldName = f.getName();
                    try {
                        if (f.getType().equals(Integer.TYPE)) {
                            result.put(fieldName, getFieldValueName(valueArrays, conf, f));
                        } else if (f.get(conf) != null) {
                            result.put(fieldName, f.get(conf));
                        }
                    } catch (JSONException e) {
                        ACRA.log.w(ACRA.LOG_TAG, "Could not collect configuration field " + fieldName, e);
                    }
                }
            } catch (IllegalArgumentException e2) {
                ACRA.log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e2);
            } catch (IllegalAccessException e3) {
                ACRA.log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e3);
            }
        }
        return result;
    }

    private static Map<String, SparseArray<String>> getValueArrays() {
        Map<String, SparseArray<String>> valueArrays = new HashMap<>();
        SparseArray<String> hardKeyboardHiddenValues = new SparseArray<>();
        SparseArray<String> keyboardValues = new SparseArray<>();
        SparseArray<String> keyboardHiddenValues = new SparseArray<>();
        SparseArray<String> navigationValues = new SparseArray<>();
        SparseArray<String> navigationHiddenValues = new SparseArray<>();
        SparseArray<String> orientationValues = new SparseArray<>();
        SparseArray<String> screenLayoutValues = new SparseArray<>();
        SparseArray<String> touchScreenValues = new SparseArray<>();
        SparseArray<String> uiModeValues = new SparseArray<>();
        Field[] fields = Configuration.class.getFields();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            Field f = fields[i];
            if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
                String fieldName = f.getName();
                try {
                    if (fieldName.startsWith(PREFIX_HARDKEYBOARDHIDDEN)) {
                        hardKeyboardHiddenValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_KEYBOARD)) {
                        keyboardValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_KEYBOARDHIDDEN)) {
                        keyboardHiddenValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_NAVIGATION)) {
                        navigationValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_NAVIGATIONHIDDEN)) {
                        navigationHiddenValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_ORIENTATION)) {
                        orientationValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_SCREENLAYOUT)) {
                        screenLayoutValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_TOUCHSCREEN)) {
                        touchScreenValues.put(f.getInt((Object) null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_UI_MODE)) {
                        uiModeValues.put(f.getInt((Object) null), fieldName);
                    }
                } catch (IllegalArgumentException e) {
                    ACRA.log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e);
                } catch (IllegalAccessException e2) {
                    ACRA.log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e2);
                }
            }
        }
        valueArrays.put(PREFIX_HARDKEYBOARDHIDDEN, hardKeyboardHiddenValues);
        valueArrays.put(PREFIX_KEYBOARD, keyboardValues);
        valueArrays.put(PREFIX_KEYBOARDHIDDEN, keyboardHiddenValues);
        valueArrays.put(PREFIX_NAVIGATION, navigationValues);
        valueArrays.put(PREFIX_NAVIGATIONHIDDEN, navigationHiddenValues);
        valueArrays.put(PREFIX_ORIENTATION, orientationValues);
        valueArrays.put(PREFIX_SCREENLAYOUT, screenLayoutValues);
        valueArrays.put(PREFIX_TOUCHSCREEN, touchScreenValues);
        valueArrays.put(PREFIX_UI_MODE, uiModeValues);
        return valueArrays;
    }

    private static Object getFieldValueName(Map<String, SparseArray<String>> valueArrays, @NonNull Configuration conf, @NonNull Field f) throws IllegalAccessException {
        String fieldName = f.getName();
        if (fieldName.equals(FIELD_MCC) || fieldName.equals(FIELD_MNC)) {
            return Integer.valueOf(f.getInt(conf));
        }
        if (fieldName.equals(FIELD_UIMODE)) {
            return activeFlags(valueArrays.get(PREFIX_UI_MODE), f.getInt(conf));
        }
        if (fieldName.equals(FIELD_SCREENLAYOUT)) {
            return activeFlags(valueArrays.get(PREFIX_SCREENLAYOUT), f.getInt(conf));
        }
        SparseArray<String> values = valueArrays.get(fieldName.toUpperCase() + '_');
        if (values == null) {
            return Integer.valueOf(f.getInt(conf));
        }
        String value = values.get(f.getInt(conf));
        if (value == null) {
            return Integer.valueOf(f.getInt(conf));
        }
        return value;
    }

    @NonNull
    private static String activeFlags(@NonNull SparseArray<String> valueNames, int bitfield) {
        int value;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < valueNames.size(); i++) {
            int maskValue = valueNames.keyAt(i);
            if (valueNames.get(maskValue).endsWith(SUFFIX_MASK) && (value = bitfield & maskValue) > 0) {
                if (result.length() > 0) {
                    result.append('+');
                }
                result.append(valueNames.get(value));
            }
        }
        return result.toString();
    }

    @NonNull
    public static Element collectConfiguration(@NonNull Context context2) {
        try {
            return configToElement(context2.getResources().getConfiguration());
        } catch (RuntimeException e) {
            ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve CrashConfiguration for : " + context2.getPackageName(), e);
            return ACRAConstants.NOT_AVAILABLE;
        }
    }
}
