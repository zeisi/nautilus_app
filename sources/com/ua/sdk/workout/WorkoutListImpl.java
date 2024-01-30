package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.model.dto.User;
import com.ua.sdk.internal.AbstractEntityList;

public class WorkoutListImpl extends AbstractEntityList<Workout> implements WorkoutList {
    public static final Parcelable.Creator<WorkoutListImpl> CREATOR = new Parcelable.Creator<WorkoutListImpl>() {
        public WorkoutListImpl createFromParcel(Parcel source) {
            return new WorkoutListImpl(source);
        }

        public WorkoutListImpl[] newArray(int size) {
            return new WorkoutListImpl[size];
        }
    };

    /* access modifiers changed from: protected */
    public String getListKey() {
        return User.WORKOUTS;
    }

    public WorkoutListImpl() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private WorkoutListImpl(Parcel in) {
        super(in);
    }
}
