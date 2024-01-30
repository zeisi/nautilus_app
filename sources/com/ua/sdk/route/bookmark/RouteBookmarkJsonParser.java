package com.ua.sdk.route.bookmark;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.route.RouteBookmark;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class RouteBookmarkJsonParser implements JsonParser<RouteBookmark> {
    Gson gson;

    public RouteBookmarkJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public RouteBookmark parse(InputStream inputStream) throws UaException {
        return RouteBookmarkTO.fromTransferObject((RouteBookmarkTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RouteBookmarkTO.class));
    }
}
