package com.ua.sdk.net.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.TimeZone;

public class TimeZoneTypeAdapter extends TypeAdapter<TimeZone> {
    public void write(JsonWriter out, TimeZone value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getID());
        }
    }

    public TimeZone read(JsonReader in) throws IOException {
        String id = in.nextString();
        if (id == null) {
            return null;
        }
        return TimeZone.getTimeZone(id);
    }
}
