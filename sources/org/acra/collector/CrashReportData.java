package org.acra.collector;

import android.support.annotation.NonNull;
import java.util.EnumMap;
import org.acra.ReportField;
import org.acra.model.BooleanElement;
import org.acra.model.Element;
import org.acra.model.NumberElement;
import org.acra.model.StringElement;
import org.acra.util.JsonUtils;
import org.json.JSONObject;

public final class CrashReportData extends EnumMap<ReportField, Element> {
    private static final long serialVersionUID = 5002578634500874842L;

    public CrashReportData() {
        super(ReportField.class);
    }

    public String getProperty(@NonNull ReportField key) {
        return ((Element) super.get(key)).toString();
    }

    public void putString(@NonNull ReportField key, String value) {
        put(key, new StringElement(value));
    }

    public void putNumber(@NonNull ReportField key, Number value) {
        put(key, new NumberElement(value));
    }

    public void putBoolean(@NonNull ReportField key, boolean value) {
        put(key, new BooleanElement(value));
    }

    @NonNull
    public JSONObject toJSON() {
        return JsonUtils.toJson(this);
    }
}
