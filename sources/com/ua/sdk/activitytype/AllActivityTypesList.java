package com.ua.sdk.activitytype;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import java.util.ArrayList;
import java.util.List;

public class AllActivityTypesList implements EntityList<ActivityType>, Parcelable {
    public static final Parcelable.Creator<AllActivityTypesList> CREATOR = new Parcelable.Creator<AllActivityTypesList>() {
        public AllActivityTypesList createFromParcel(Parcel source) {
            return new AllActivityTypesList(source);
        }

        public AllActivityTypesList[] newArray(int size) {
            return new AllActivityTypesList[size];
        }
    };
    List<ActivityType> activityTypes;

    public AllActivityTypesList(List activityTypes2) {
        this.activityTypes = activityTypes2;
    }

    public int getTotalCount() {
        return this.activityTypes.size();
    }

    public int getSize() {
        return this.activityTypes.size();
    }

    public ActivityType get(int index) {
        return this.activityTypes.get(index);
    }

    public List getAll() {
        return this.activityTypes;
    }

    public boolean isEmpty() {
        return this.activityTypes.isEmpty();
    }

    public boolean hasPrevious() {
        return false;
    }

    public boolean hasNext() {
        return false;
    }

    public EntityListRef<ActivityType> getPreviousPage() {
        return null;
    }

    public EntityListRef<ActivityType> getNextPage() {
        return null;
    }

    public void set(int index, ActivityType object) {
        this.activityTypes.set(index, object);
    }

    public ActivityType remove(int index) {
        return this.activityTypes.remove(index);
    }

    public EntityListRef<ActivityType> getRef() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.activityTypes);
    }

    private AllActivityTypesList(Parcel in) {
        this.activityTypes = new ArrayList();
        in.readList(this.activityTypes, ActivityType.class.getClassLoader());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AllActivityTypesList that = (AllActivityTypesList) o;
        if (this.activityTypes != null) {
            if (this.activityTypes.equals(that.activityTypes)) {
                return true;
            }
        } else if (that.activityTypes == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.activityTypes != null) {
            return this.activityTypes.hashCode();
        }
        return 0;
    }
}
