package com.ua.sdk.group;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.group.purpose.GroupPurposeType;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class GroupListRef implements EntityListRef<Group> {
    public static Parcelable.Creator<GroupListRef> CREATOR = new Parcelable.Creator<GroupListRef>() {
        public GroupListRef createFromParcel(Parcel source) {
            return new GroupListRef(source);
        }

        public GroupListRef[] newArray(int size) {
            return new GroupListRef[size];
        }
    };
    private String params;

    private GroupListRef(Builder init) {
        this.params = "";
        this.params = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.params;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        String view;

        private Builder() {
            super(UrlBuilderImpl.GET_GROUPS_LIST_URL);
        }

        public Builder setUser(String id) {
            setParam(MyFitnessPalConstants.MFP_USER_ID_KEY, id);
            return this;
        }

        public Builder setGroupViewType(GroupViewType type) {
            Precondition.isNotNull(type);
            this.view = type.toString();
            setParam("view", type.toString());
            return this;
        }

        public GroupListRef build() {
            if (this.view != null) {
                setParam("purpose", GroupPurposeType.CHALLENGE.toString().toLowerCase());
            }
            return new GroupListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.params);
    }

    private GroupListRef(Parcel in) {
        this.params = "";
        this.params = in.readString();
    }
}
