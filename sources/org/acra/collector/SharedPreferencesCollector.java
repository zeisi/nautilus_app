package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.acra.model.StringElement;
import org.json.JSONException;
import org.json.JSONObject;

final class SharedPreferencesCollector extends Collector {
    private final ACRAConfiguration config;
    private final Context context;
    private final SharedPreferences prefs;

    SharedPreferencesCollector(@NonNull Context context2, @NonNull ACRAConfiguration config2, SharedPreferences prefs2) {
        super(ReportField.USER_EMAIL, ReportField.SHARED_PREFERENCES);
        this.context = context2;
        this.config = config2;
        this.prefs = prefs2;
    }

    @NonNull
    private Element collect() throws JSONException {
        ComplexElement result = new ComplexElement();
        Map<String, SharedPreferences> sharedPrefs = new TreeMap<>();
        sharedPrefs.put("default", PreferenceManager.getDefaultSharedPreferences(this.context));
        Iterator<String> it = this.config.additionalSharedPreferences().iterator();
        while (it.hasNext()) {
            String sharedPrefId = it.next();
            sharedPrefs.put(sharedPrefId, this.context.getSharedPreferences(sharedPrefId, 0));
        }
        for (Map.Entry<String, SharedPreferences> entry : sharedPrefs.entrySet()) {
            String sharedPrefId2 = entry.getKey();
            Map<String, ?> prefEntries = entry.getValue().getAll();
            if (prefEntries.isEmpty()) {
                result.put(sharedPrefId2, "empty");
            } else {
                Iterator<String> iterator = prefEntries.keySet().iterator();
                while (iterator.hasNext()) {
                    if (filteredKey(iterator.next())) {
                        iterator.remove();
                    }
                }
                result.put(sharedPrefId2, new JSONObject(prefEntries));
            }
        }
        return result;
    }

    private boolean filteredKey(@NonNull String key) {
        Iterator<String> it = this.config.excludeMatchingSharedPreferencesKeys().iterator();
        while (it.hasNext()) {
            if (key.matches(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        switch (reportField) {
            case USER_EMAIL:
                String email = this.prefs.getString(ACRA.PREF_USER_EMAIL_ADDRESS, (String) null);
                return email != null ? new StringElement(email) : ACRAConstants.NOT_AVAILABLE;
            case SHARED_PREFERENCES:
                try {
                    return collect();
                } catch (JSONException e) {
                    ACRA.log.w(ACRA.LOG_TAG, "Could not collect shared preferences", e);
                    return ACRAConstants.NOT_AVAILABLE;
                }
            default:
                throw new IllegalArgumentException();
        }
    }
}
