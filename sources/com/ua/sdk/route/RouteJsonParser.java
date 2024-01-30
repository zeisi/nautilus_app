package com.ua.sdk.route;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class RouteJsonParser implements JsonParser<Route> {
    private Gson gson;

    public RouteJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public RouteImpl parse(InputStream inputStream) throws UaException {
        try {
            return RouteTO.fromTransferObject((RouteTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RouteTO.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
