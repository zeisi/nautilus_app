package com.ua.sdk.group.objective;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class CriteriaImpl implements Criteria {
    public static final Parcelable.Creator<CriteriaImpl> CREATOR = new Parcelable.Creator<CriteriaImpl>() {
        public CriteriaImpl createFromParcel(Parcel source) {
            return new CriteriaImpl(source);
        }

        public CriteriaImpl[] newArray(int size) {
            return new CriteriaImpl[size];
        }
    };
    Map<String, CriteriaItem> criteria;

    public CriteriaImpl() {
        this.criteria = new HashMap(2);
    }

    private CriteriaImpl(Parcel in) {
        this.criteria = new HashMap(2);
        in.readMap(this.criteria, CriteriaItem.class.getClassLoader());
    }

    public void addCriteriaItem(CriteriaItem item) {
        this.criteria.put(item.getName(), item);
    }

    public CriteriaItem getCriteriaItem(String name) {
        return this.criteria.get(name);
    }

    public void removeAllItems() {
        this.criteria.clear();
    }

    public CriteriaItem removeItem(String name) {
        return this.criteria.remove(name);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.criteria);
    }
}
