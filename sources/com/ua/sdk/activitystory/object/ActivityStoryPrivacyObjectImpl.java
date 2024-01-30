package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.privacy.PrivacyHelper;

public class ActivityStoryPrivacyObjectImpl implements ActivityStoryObject {
    public static Parcelable.Creator<ActivityStoryPrivacyObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryPrivacyObjectImpl>() {
        public ActivityStoryPrivacyObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryPrivacyObjectImpl(source);
        }

        public ActivityStoryPrivacyObjectImpl[] newArray(int size) {
            return new ActivityStoryPrivacyObjectImpl[size];
        }
    };
    @SerializedName("privacy")
    Privacy privacy;
    @SerializedName("type")
    ActivityStoryObject.Type type;

    public ActivityStoryPrivacyObjectImpl() {
    }

    private ActivityStoryPrivacyObjectImpl(Builder init) {
        this.type = init.type;
        this.privacy = init.privacy;
    }

    private ActivityStoryPrivacyObjectImpl(Parcel in) {
        this.type = (ActivityStoryObject.Type) in.readSerializable();
        this.privacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
    }

    public ActivityStoryObject.Type getType() {
        return this.type;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.type);
        dest.writeParcelable(this.privacy, flags);
    }

    public static class Builder {
        final Privacy privacy;
        final ActivityStoryObject.Type type;

        public Builder(ActivityStoryObject.Type type2, Privacy.Level privacyLevel) {
            this.type = type2;
            this.privacy = PrivacyHelper.getPrivacy(privacyLevel);
        }

        public ActivityStoryPrivacyObjectImpl build() {
            return new ActivityStoryPrivacyObjectImpl(this);
        }
    }
}
