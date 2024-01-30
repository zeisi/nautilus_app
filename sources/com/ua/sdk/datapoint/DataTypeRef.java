package com.ua.sdk.datapoint;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;

public class DataTypeRef implements EntityRef<DataType> {
    public static Parcelable.Creator<DataTypeRef> CREATOR = new Parcelable.Creator<DataTypeRef>() {
        public DataTypeRef createFromParcel(Parcel source) {
            return new DataTypeRef(source);
        }

        public DataTypeRef[] newArray(int size) {
            return new DataTypeRef[size];
        }
    };
    final String href;
    final String id;

    private DataTypeRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
    }

    private DataTypeRef(Builder init) {
        this.id = init.id;
        this.href = init.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataTypeRef that = (DataTypeRef) o;
        if (this.id != null) {
            if (this.id.equals(that.id)) {
                return true;
            }
        } else if (that.id == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
    }

    public static class Builder extends BaseReferenceBuilder {
        String id;

        protected Builder() {
            super("/v7.0/data_type/{id}/");
        }

        public Builder setId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public DataTypeRef build() {
            Precondition.isNotNull(this.id, "id");
            return new DataTypeRef(this);
        }
    }
}
