package com.ua.sdk.actigraphysettings;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class ActigraphySettingsJsonParser implements JsonParser<ActigraphySettings> {
    private Gson gson;

    public ActigraphySettingsJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public ActigraphySettings parse(InputStream inputStream) throws UaException {
        return ActigraphySettingsTO.fromTransferObject((ActigraphySettingsTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) ActigraphySettingsTO.class));
    }
}
