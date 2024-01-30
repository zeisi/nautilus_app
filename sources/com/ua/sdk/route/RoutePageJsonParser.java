package com.ua.sdk.route;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class RoutePageJsonParser implements JsonParser<EntityList<Route>> {
    private Gson gson;

    public RoutePageJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<Route> parse(InputStream inputStream) throws UaException {
        try {
            return RoutePageTO.fromTransferObject((RoutePageTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RoutePageTO.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
