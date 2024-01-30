package com.ua.sdk.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.UaException;
import com.ua.sdk.net.json.Iso8601PeriodFormat;
import java.lang.reflect.Type;

public class Period implements Parcelable {
    public static Parcelable.Creator<Period> CREATOR = new Parcelable.Creator<Period>() {
        public Period createFromParcel(Parcel source) {
            return new Period(source);
        }

        public Period[] newArray(int size) {
            return new Period[size];
        }
    };
    public static final Period ONE_DAY = new Period("P1D");
    public static final Period ONE_MONTH = new Period("P1M");
    public static final Period ONE_WEEK = new Period("P1W");
    public static final Period ONE_YEAR = new Period("P1Y");
    final String period;

    private Period(String period2) {
        this.period = period2;
    }

    private Period(Iso8601PeriodFormat format) {
        this(format.toString());
    }

    private Period(Parcel in) {
        this.period = in.readString();
    }

    public boolean isValid(Period... periods) {
        if (periods == null) {
            return true;
        }
        for (Period period2 : periods) {
            if (period2.toString().equalsIgnoreCase(this.period)) {
                return true;
            }
        }
        return false;
    }

    public static Period parse(String period2) throws UaException {
        return new Period(Iso8601PeriodFormat.parse(period2));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.period);
    }

    public String toString() {
        return this.period;
    }

    public static class PeriodAdapter implements JsonSerializer<Period>, JsonDeserializer<Period> {
        public Period deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return new Period(Iso8601PeriodFormat.parse(json.getAsString()));
            } catch (UaException e) {
                return null;
            }
        }

        public JsonElement serialize(Period src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.period);
        }
    }
}
