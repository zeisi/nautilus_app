package com.ua.sdk.gear.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.LinkEntityRef;

public class UserGearRef extends LinkEntityRef<UserGear> {
    public static final Parcelable.Creator<UserGearRef> CREATOR = new Parcelable.Creator<UserGearRef>() {
        public UserGearRef createFromParcel(Parcel source) {
            return new UserGearRef(source);
        }

        public UserGearRef[] newArray(int size) {
            return new UserGearRef[size];
        }
    };

    public UserGearRef(String href) {
        super(href);
    }

    public UserGearRef(String id, String href) {
        super(id, href);
    }

    public UserGearRef(String id, long localId, String href) {
        super(id, localId, href);
    }

    private UserGearRef(Builder init) {
        super(init.id, init.getHref());
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
            super("/api/0.1/usergear/{id}/");
        }

        public Builder setGearId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public UserGearRef build() {
            if (this.id != null) {
                return new UserGearRef(this);
            }
            throw new IllegalArgumentException("An id must be specified in the builder.");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private UserGearRef(Parcel in) {
        super(in);
    }
}
