package com.ua.sdk.activitytimeseries;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ua.sdk.activitytimeseries.ActivityTimeSeriesImpl;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.util.DoubleList;
import com.ua.sdk.util.IntList;
import com.ua.sdk.util.LongList;
import java.io.IOException;

public class ActivityTimeSeriesTypeAdapter extends TypeAdapter<ActivityTimeSeriesImpl.TimeSeries> {
    public void write(JsonWriter out, ActivityTimeSeriesImpl.TimeSeries value) throws IOException {
        if (value != null) {
            out.beginObject();
            writeIntValues(out, BaseDataTypes.ID_STEPS, value.stepEpochs, value.stepValues);
            writeDoubleValues(out, "distance", value.distanceEpochs, value.distanceValues);
            writeDoubleValues(out, "calories", value.calorieEpochs, value.calorieValues);
            out.endObject();
            return;
        }
        out.nullValue();
    }

    private void writeIntValues(JsonWriter out, String type, long[] epochs, int[] values) throws IOException {
        if (epochs != null) {
            out.name(type);
            out.beginObject();
            out.name("values");
            out.beginArray();
            int size = epochs.length;
            for (int i = 0; i < size; i++) {
                out.beginArray();
                out.value(epochs[i]);
                out.value((long) values[i]);
                out.endArray();
            }
            out.endArray();
            out.endObject();
        }
    }

    private void writeDoubleValues(JsonWriter out, String type, long[] epochs, double[] values) throws IOException {
        if (epochs != null) {
            out.name(type);
            out.beginObject();
            out.name("values");
            out.beginArray();
            int size = epochs.length;
            for (int i = 0; i < size; i++) {
                out.beginArray();
                out.value(epochs[i]);
                out.value(values[i]);
                out.endArray();
            }
            out.endArray();
            out.endObject();
        }
    }

    public ActivityTimeSeriesImpl.TimeSeries read(JsonReader in) throws IOException {
        ActivityTimeSeriesImpl.TimeSeries timeSeries = new ActivityTimeSeriesImpl.TimeSeries();
        LongList epochs = null;
        IntList intValues = null;
        DoubleList doubleValues = null;
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (BaseDataTypes.ID_STEPS.equals(name)) {
                if (epochs == null) {
                    epochs = new LongList();
                } else {
                    epochs.clear();
                }
                if (intValues == null) {
                    intValues = new IntList();
                } else {
                    intValues.clear();
                }
                readIntValues(in, epochs, intValues);
                timeSeries.stepEpochs = epochs.toArray();
                timeSeries.stepValues = intValues.toArray();
            } else if ("distance".equals(name)) {
                if (epochs == null) {
                    epochs = new LongList();
                } else {
                    epochs.clear();
                }
                if (doubleValues == null) {
                    doubleValues = new DoubleList();
                } else {
                    doubleValues.clear();
                }
                readDoubleValues(in, epochs, doubleValues);
                timeSeries.distanceEpochs = epochs.toArray();
                timeSeries.distanceValues = doubleValues.toArray();
            } else if ("calories".equals(name)) {
                if (epochs == null) {
                    epochs = new LongList();
                } else {
                    epochs.clear();
                }
                if (doubleValues == null) {
                    doubleValues = new DoubleList();
                } else {
                    doubleValues.clear();
                }
                readDoubleValues(in, epochs, doubleValues);
                timeSeries.calorieEpochs = epochs.toArray();
                timeSeries.calorieValues = doubleValues.toArray();
            } else {
                in.skipValue();
            }
        }
        in.endObject();
        return timeSeries;
    }

    private void readIntValues(JsonReader in, LongList epochs, IntList values) throws IOException {
        in.beginObject();
        while (in.hasNext()) {
            if ("values".equals(in.nextName())) {
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    long epoch = in.nextLong();
                    int value = in.nextInt();
                    epochs.add(epoch);
                    values.add(value);
                    in.endArray();
                }
                in.endArray();
            } else {
                in.skipValue();
            }
        }
        in.endObject();
    }

    private void readDoubleValues(JsonReader in, LongList epochs, DoubleList values) throws IOException {
        in.beginObject();
        while (in.hasNext()) {
            if ("values".equals(in.nextName())) {
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    long epoch = in.nextLong();
                    double value = in.nextDouble();
                    epochs.add(epoch);
                    values.add(value);
                    in.endArray();
                }
                in.endArray();
            } else {
                in.skipValue();
            }
        }
        in.endObject();
    }
}
