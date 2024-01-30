package com.ua.sdk.group.objective;

import android.os.Parcel;
import android.os.Parcelable;

public class SortCriteriaItem implements CriteriaItem<String> {
    public static final Parcelable.Creator<SortCriteriaItem> CREATOR = new Parcelable.Creator<SortCriteriaItem>() {
        public SortCriteriaItem createFromParcel(Parcel source) {
            return new SortCriteriaItem(source);
        }

        public SortCriteriaItem[] newArray(int size) {
            return new SortCriteriaItem[size];
        }
    };
    private String value;

    public enum Value {
        ASC,
        DESC
    }

    public SortCriteriaItem() {
    }

    private SortCriteriaItem(Parcel in) {
        this.value = in.readString();
    }

    public String getName() {
        return CriteriaTypes.SORT;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        if (value2 != null) {
            for (Value val : Value.values()) {
                if (value2.equalsIgnoreCase(val.toString())) {
                    setValue(val);
                    return;
                }
            }
            throw new EnumConstantNotPresentException(Value.class, "Unable to set value: " + value2);
        }
    }

    public void setValue(Value value2) {
        if (value2 != null) {
            this.value = value2.toString().toLowerCase();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
    }
}
