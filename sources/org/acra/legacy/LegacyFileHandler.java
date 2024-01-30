package org.acra.legacy;

import android.content.Context;
import android.content.SharedPreferences;
import org.acra.prefs.PrefUtils;

public class LegacyFileHandler {
    private static final String PREF__LEGACY_ALREADY_CONVERTED_TO_4_8_0 = "acra.legacyAlreadyConvertedTo4.8.0";
    private static final String PREF__LEGACY_ALREADY_CONVERTED_TO_JSON = "acra.legacyAlreadyConvertedToJson";
    private final Context context;
    private final SharedPreferences prefs;

    public LegacyFileHandler(Context context2, SharedPreferences prefs2) {
        this.context = context2;
        this.prefs = prefs2;
    }

    public void updateToCurrentVersionIfNecessary() {
        if (!this.prefs.getBoolean(PREF__LEGACY_ALREADY_CONVERTED_TO_4_8_0, false)) {
            new ReportMigrator(this.context).migrate();
            PrefUtils.save(this.prefs.edit().putBoolean(PREF__LEGACY_ALREADY_CONVERTED_TO_4_8_0, true));
        }
        if (!this.prefs.getBoolean(PREF__LEGACY_ALREADY_CONVERTED_TO_JSON, false)) {
            new ReportConverter(this.context).convert();
            PrefUtils.save(this.prefs.edit().putBoolean(PREF__LEGACY_ALREADY_CONVERTED_TO_JSON, true));
        }
    }
}
