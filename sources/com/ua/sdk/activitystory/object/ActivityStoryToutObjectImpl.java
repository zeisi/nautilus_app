package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryToutObject;

public class ActivityStoryToutObjectImpl implements ActivityStoryToutObject {
    public static Parcelable.Creator<ActivityStoryToutObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryToutObjectImpl>() {
        public ActivityStoryToutObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryToutObjectImpl(source);
        }

        public ActivityStoryToutObjectImpl[] newArray(int size) {
            return new ActivityStoryToutObjectImpl[size];
        }
    };
    private final ActivityStoryToutObject.Subtype mSubtype;

    public ActivityStoryToutObjectImpl(ActivityStoryToutObject.Subtype subtype) {
        this.mSubtype = subtype;
    }

    public ActivityStoryToutObject.Subtype getSubtype() {
        return this.mSubtype;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.TOUT;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSubtype == null ? -1 : this.mSubtype.ordinal());
    }

    private ActivityStoryToutObjectImpl(Parcel in) {
        int tmpMSubtype = in.readInt();
        this.mSubtype = tmpMSubtype == -1 ? null : ActivityStoryToutObject.Subtype.values()[tmpMSubtype];
    }
}
