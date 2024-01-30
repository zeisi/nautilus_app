package com.ua.sdk.group.objective;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivityTypeCriteriaItem implements CriteriaItem<Integer> {
    public static final Parcelable.Creator<ActivityTypeCriteriaItem> CREATOR = new Parcelable.Creator<ActivityTypeCriteriaItem>() {
        public ActivityTypeCriteriaItem createFromParcel(Parcel source) {
            return new ActivityTypeCriteriaItem(source);
        }

        public ActivityTypeCriteriaItem[] newArray(int size) {
            return new ActivityTypeCriteriaItem[size];
        }
    };
    private Integer value;

    public ActivityTypeCriteriaItem() {
    }

    private ActivityTypeCriteriaItem(Parcel in) {
        this.value = Integer.valueOf(in.readInt());
    }

    public String getName() {
        return CriteriaTypes.ACTIVITY_TYPE;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value2) {
        this.value = value2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value.intValue());
    }
}
