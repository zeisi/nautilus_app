package com.ua.sdk.activitytype;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Precondition;

public class ActivityTypeRef extends LinkEntityRef<ActivityType> implements EntityRef<ActivityType> {
    public static Parcelable.Creator<ActivityTypeRef> CREATOR = new Parcelable.Creator<ActivityTypeRef>() {
        public ActivityTypeRef createFromParcel(Parcel source) {
            return new ActivityTypeRef(source);
        }

        public ActivityTypeRef[] newArray(int size) {
            return new ActivityTypeRef[size];
        }
    };

    public static Builder getBuilder() {
        return new Builder();
    }

    private ActivityTypeRef(Builder builder) {
        super(builder.id, builder.localId, builder.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    private ActivityTypeRef(Parcel source) {
        super(source);
    }

    public static class Builder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;
        /* access modifiers changed from: private */
        public long localId = -1;

        protected Builder() {
            super("/v7.0/activity_type/{id}/");
        }

        public Builder setActivityTypeId(String id2) {
            setParam("id", id2);
            this.id = id2;
            return this;
        }

        public Builder setLocalId(long localId2) {
            this.localId = localId2;
            return this;
        }

        public ActivityTypeRef build() {
            Precondition.isNotNull(this.id, "id");
            return new ActivityTypeRef(this);
        }
    }
}
