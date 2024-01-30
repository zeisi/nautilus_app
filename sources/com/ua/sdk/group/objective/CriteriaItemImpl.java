package com.ua.sdk.group.objective;

import android.os.Parcel;

public class CriteriaItemImpl implements CriteriaItem<Object> {
    String name;
    private Object value;

    public CriteriaItemImpl() {
    }

    private CriteriaItemImpl(Parcel in) {
        this.value = in.readValue(Object.class.getClassLoader());
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value2) {
        this.value = value2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.value);
    }
}
