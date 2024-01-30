package com.ua.sdk.sleep;

import com.google.android.gms.fitness.FitnessActivities;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.ua.sdk.sleep.SleepMetric;
import com.ua.sdk.sleep.SleepMetricImpl;
import java.io.IOException;
import java.util.ArrayList;

public class SleepTimeSeriesTypeAdapter extends TypeAdapter<SleepMetricImpl.TimeSeries> {
    public static final int BUFFER_SIZE = 256;

    public void write(JsonWriter out, SleepMetricImpl.TimeSeries value) throws IOException {
        if (value != null) {
            out.beginObject();
            out.name(FitnessActivities.SLEEP);
            out.beginObject();
            out.name("values");
            out.beginArray();
            ArrayList<SleepStateEntry> events = value.events;
            int size = events.size();
            for (int i = 0; i < size; i++) {
                SleepStateEntry entry = events.get(i);
                out.beginArray();
                out.value(entry.epoch);
                out.value((long) entry.state.value);
                out.endArray();
            }
            out.endArray();
            out.endObject();
            out.endObject();
            return;
        }
        out.nullValue();
    }

    public SleepMetricImpl.TimeSeries read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.skipValue();
            return null;
        }
        SleepMetricImpl.TimeSeries timeSeries = new SleepMetricImpl.TimeSeries();
        ArrayList<SleepStateEntry> events = timeSeries.events;
        in.beginObject();
        while (in.hasNext()) {
            if (FitnessActivities.SLEEP.equals(in.nextName())) {
                in.beginObject();
                while (in.hasNext()) {
                    if ("values".equals(in.nextName())) {
                        in.beginArray();
                        while (in.hasNext()) {
                            in.beginArray();
                            events.add(new SleepStateEntry(in.nextLong(), SleepMetric.State.getState(in.nextInt())));
                            in.endArray();
                        }
                        in.endArray();
                    } else {
                        in.skipValue();
                    }
                }
                in.endObject();
            } else {
                in.skipValue();
            }
        }
        in.endObject();
        return timeSeries;
    }
}
