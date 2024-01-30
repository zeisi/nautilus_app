package com.ua.sdk.aggregate;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.datapoint.DataField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AggregateSummaryImpl implements AggregateSummary {
    public static Parcelable.Creator<AggregateSummaryImpl> CREATOR = new Parcelable.Creator<AggregateSummaryImpl>() {
        public AggregateSummaryImpl createFromParcel(Parcel source) {
            return new AggregateSummaryImpl(source);
        }

        public AggregateSummaryImpl[] newArray(int size) {
            return new AggregateSummaryImpl[size];
        }
    };
    @SerializedName("datetime")
    Date datetime;
    @SerializedName("start_datetime")
    Date startDatetime;
    @SerializedName("value")
    Map<String, Object> value;

    public AggregateSummaryImpl() {
        this.value = new HashMap(4);
    }

    private AggregateSummaryImpl(Parcel in) {
        this.value = new HashMap(4);
        this.startDatetime = (Date) in.readValue(Date.class.getClassLoader());
        this.datetime = (Date) in.readValue(Date.class.getClassLoader());
        in.readMap(this.value, HashMap.class.getClassLoader());
    }

    public Date getStartDatetime() {
        return this.startDatetime;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public Long getValueLong(DataField field) {
        if (field == null) {
            return null;
        }
        Object value2 = this.value.get(field.getId());
        if (value2 instanceof Long) {
            return (Long) value2;
        }
        if (value2 instanceof Double) {
            return Long.valueOf(((Double) value2).longValue());
        }
        return null;
    }

    public Double getValueDouble(DataField field) {
        if (field == null) {
            return null;
        }
        Object value2 = this.value.get(field.getId());
        if (value2 instanceof Double) {
            return (Double) value2;
        }
        if (value2 instanceof Long) {
            return Double.valueOf(((Long) value2).doubleValue());
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.startDatetime);
        dest.writeValue(this.datetime);
        dest.writeMap(this.value);
    }
}
