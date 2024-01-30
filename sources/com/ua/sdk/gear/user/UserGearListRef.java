package com.ua.sdk.gear.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;

public class UserGearListRef implements EntityListRef<UserGear> {
    public static final Parcelable.Creator<UserGearListRef> CREATOR = new Parcelable.Creator<UserGearListRef>() {
        public UserGearListRef createFromParcel(Parcel source) {
            return new UserGearListRef(source);
        }

        public UserGearListRef[] newArray(int size) {
            return new UserGearListRef[size];
        }
    };
    private String href;

    private UserGearListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        private EntityRef<User> user;

        private Builder() {
            super("/api/0.1/usergear/");
        }

        public Builder setUser(EntityRef<User> user2) {
            this.user = user2;
            setParam(MyFitnessPalConstants.MFP_USER_ID_KEY, user2.getId());
            return this;
        }

        public UserGearListRef build() {
            Precondition.isNotNull(this.user);
            return new UserGearListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private UserGearListRef(Parcel in) {
        this.href = in.readString();
    }
}
