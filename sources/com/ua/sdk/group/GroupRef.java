package com.ua.sdk.group;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class GroupRef implements EntityRef<Group> {
    public static Parcelable.Creator<GroupRef> CREATOR = new Parcelable.Creator<GroupRef>() {
        public GroupRef createFromParcel(Parcel source) {
            return new GroupRef(source);
        }

        public GroupRef[] newArray(int size) {
            return new GroupRef[size];
        }
    };
    private final String href;
    private final String id;

    private GroupRef(Builder init) {
        this.id = init.id;
        this.href = init.getHref();
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return String.format(this.href, new Object[]{this.id});
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        private static final String INVITATION_CODE_KEY = "invitation_code";
        private String code;
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
            super(UrlBuilderImpl.GET_GROUP_URL);
        }

        public Builder setGroupId(String id2) {
            this.id = id2;
            return this;
        }

        public Builder setInvitationCode(String code2) {
            this.code = code2;
            return this;
        }

        public GroupRef build() {
            Precondition.isNotNull(this.id, "Group Id");
            if (this.code != null) {
                setParam(INVITATION_CODE_KEY, this.code);
            }
            return new GroupRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
    }

    private GroupRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
    }
}
