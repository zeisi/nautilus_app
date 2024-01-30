package org.acra.model;

import android.support.annotation.NonNull;
import java.util.Map;
import org.acra.util.JsonUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ComplexElement extends JSONObject implements Element {
    public ComplexElement() {
    }

    public ComplexElement(String json) throws JSONException {
        super(json);
    }

    public ComplexElement(Map<String, ?> copyFrom) {
        super(copyFrom);
    }

    public ComplexElement(JSONObject copyFrom) throws JSONException {
        super(copyFrom, getNames(copyFrom));
    }

    @NonNull
    private static String[] getNames(JSONObject object) throws JSONException {
        JSONArray json = object.names();
        if (json == null) {
            return new String[0];
        }
        String[] names = new String[json.length()];
        for (int i = 0; i < json.length(); i++) {
            names[i] = json.getString(i);
        }
        return names;
    }

    public Object value() {
        return this;
    }

    public String[] flatten() {
        try {
            return (String[]) JsonUtils.flatten(this).toArray(new String[0]);
        } catch (JSONException e) {
            return new String[0];
        }
    }
}
