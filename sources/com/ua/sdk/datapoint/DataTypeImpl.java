package com.ua.sdk.datapoint;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class DataTypeImpl implements DataType {
    public static Parcelable.Creator<DataTypeImpl> CREATOR = new Parcelable.Creator<DataTypeImpl>() {
        public DataTypeImpl createFromParcel(Parcel source) {
            return new DataTypeImpl(source);
        }

        public DataTypeImpl[] newArray(int size) {
            return new DataTypeImpl[size];
        }
    };
    private String description;
    private List<DataField> fields;
    private String id;
    private DataPeriod period;

    public DataTypeImpl() {
    }

    public DataTypeImpl(String id2, DataPeriod period2, String description2, List<DataField> fields2) {
        this.id = id2;
        this.period = period2;
        this.description = description2;
        this.fields = fields2;
    }

    private DataTypeImpl(Parcel in) {
        this.id = in.readString();
        this.period = (DataPeriod) in.readValue(DataPeriod.class.getClassLoader());
        this.description = in.readString();
        this.fields = new ArrayList();
        in.readList(this.fields, DataField.class.getClassLoader());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public DataPeriod getPeriod() {
        return this.period;
    }

    public void setPeriod(DataPeriod period2) {
        this.period = period2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public List<DataField> getFields() {
        return this.fields;
    }

    public void setFields(List<DataField> fields2) {
        this.fields = fields2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!this.id.equals(((DataTypeImpl) o).id)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public DataTypeRef getRef() {
        if (this.id == null) {
            return null;
        }
        return DataTypeRef.getBuilder().setId(this.id).build();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.period);
        dest.writeString(this.description);
        dest.writeList(this.fields);
    }
}
