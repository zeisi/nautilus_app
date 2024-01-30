package com.ua.sdk.datapoint;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataPointImpl implements DataPoint {
    public static Parcelable.Creator<DataPointImpl> CREATOR = new Parcelable.Creator<DataPointImpl>() {
        public DataPointImpl createFromParcel(Parcel source) {
            return new DataPointImpl(source);
        }

        public DataPointImpl[] newArray(int size) {
            return new DataPointImpl[size];
        }
    };
    private Date datetime;
    private Map<DataField, Object> fields;
    private Date startDatetime;

    public DataPointImpl() {
        this.fields = new HashMap(4);
    }

    public DataPointImpl(DataPointImpl other) {
        this.fields = new HashMap(4);
        this.startDatetime = other.startDatetime;
        this.datetime = other.datetime;
        this.fields.putAll(other.fields);
    }

    private DataPointImpl(Parcel in) {
        this.fields = new HashMap(4);
        this.startDatetime = (Date) in.readValue(Date.class.getClassLoader());
        this.datetime = (Date) in.readValue(Date.class.getClassLoader());
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Object value = in.readValue(Object.class.getClassLoader());
            this.fields.put((DataField) in.readValue(DataField.class.getClassLoader()), value);
        }
    }

    public Date getStartDatetime() {
        return this.startDatetime;
    }

    public void setStartDatetime(Date startDatetime2) {
        this.startDatetime = startDatetime2;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Date datetime2) {
        this.datetime = datetime2;
    }

    public void setValue(DataField dataField, Long value) {
        this.fields.put(dataField, value);
    }

    public void setValue(DataField dataField, Double value) {
        this.fields.put(dataField, value);
    }

    public Long getValueLong(DataField dataField) {
        Object value = this.fields.get(dataField);
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Double) {
            return Long.valueOf(((Double) value).longValue());
        }
        return null;
    }

    public Double getValueDouble(DataField dataField) {
        Object value = this.fields.get(dataField);
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Long) {
            return Double.valueOf(((Long) value).doubleValue());
        }
        return null;
    }

    public void reset() {
        this.startDatetime = null;
        this.datetime = null;
        this.fields.clear();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataPointImpl dataPoint = (DataPointImpl) o;
        if (this.datetime == null ? dataPoint.datetime != null : !this.datetime.equals(dataPoint.datetime)) {
            return false;
        }
        if (this.startDatetime == null ? dataPoint.startDatetime != null : !this.startDatetime.equals(dataPoint.startDatetime)) {
            return false;
        }
        if (this.fields != null) {
            if (this.fields.equals(dataPoint.fields)) {
                return true;
            }
        } else if (dataPoint.fields == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i;
        int i2 = 0;
        if (this.startDatetime != null) {
            result = this.startDatetime.hashCode();
        } else {
            result = 0;
        }
        int i3 = result * 31;
        if (this.datetime != null) {
            i = this.datetime.hashCode();
        } else {
            i = 0;
        }
        int i4 = (i3 + i) * 31;
        if (this.fields != null) {
            i2 = this.fields.hashCode();
        }
        return i4 + i2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.startDatetime);
        dest.writeValue(this.datetime);
        dest.writeInt(this.fields.size());
        for (DataField key : this.fields.keySet()) {
            dest.writeValue(key);
            dest.writeValue(this.fields.get(key));
        }
    }
}
