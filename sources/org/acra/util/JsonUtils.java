package org.acra.util;

import android.util.Log;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {
    private JsonUtils() {
    }

    public static JSONObject toJson(CrashReportData data) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<ReportField, Element> entry : data.entrySet()) {
            map.put(entry.getKey().name(), entry.getValue().value());
        }
        return new JSONObject(map);
    }

    public static CrashReportData toCrashReportData(JSONObject json) {
        CrashReportData data = new CrashReportData();
        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                ReportField field = ReportField.valueOf(key);
                Object value = json.get(key);
                if (value instanceof JSONObject) {
                    data.put(field, new ComplexElement((JSONObject) value));
                } else if (value instanceof Number) {
                    data.putNumber(field, (Number) value);
                } else if (value instanceof Boolean) {
                    data.putBoolean(field, ((Boolean) value).booleanValue());
                } else {
                    data.putString(field, value.toString());
                }
            } catch (IllegalArgumentException e) {
                Log.w(ACRA.LOG_TAG, "Unknown report key " + key, e);
            } catch (JSONException e2) {
                Log.w(ACRA.LOG_TAG, "Unable to read report field " + key, e2);
            }
        }
        return data;
    }

    public static List<String> flatten(JSONObject json) throws JSONException {
        List<String> result = new ArrayList<>();
        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                for (String s : flatten((JSONObject) value)) {
                    result.add(key + "." + s);
                }
            } else {
                result.add(key + SimpleComparison.EQUAL_TO_OPERATION + value);
            }
        }
        return result;
    }
}
