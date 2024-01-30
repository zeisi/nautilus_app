package com.ua.sdk.group.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.group.Group;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;

public class GroupUserImpl extends ApiTransferObject implements GroupUser {
    public static Parcelable.Creator<GroupUserImpl> CREATOR = new Parcelable.Creator<GroupUserImpl>() {
        public GroupUserImpl createFromParcel(Parcel source) {
            return new GroupUserImpl(source);
        }

        public GroupUserImpl[] newArray(int size) {
            return new GroupUserImpl[size];
        }
    };
    private static final String GROUP_KEY = "group";
    private static final String USER_KEY = "user";
    transient EntityRef<Group> groupRef;
    transient EntityRef<User> userRef;

    private GroupUserImpl(Builder init) {
        this.userRef = new LinkEntityRef(init.userHref);
        this.groupRef = new LinkEntityRef(init.groupHref);
        addLink("user", new Link(init.userHref));
        addLink(GROUP_KEY, new Link(init.groupHref));
    }

    public GroupUserImpl() {
    }

    private GroupUserImpl(Parcel in) {
        super(in);
    }

    public EntityRef<User> getUserRef() {
        Link user;
        if (this.userRef == null && (user = getLink("user")) != null) {
            this.userRef = new LinkEntityRef(user.getId(), user.getHref());
        }
        return this.userRef;
    }

    public EntityRef<Group> getGroupRef() {
        Link group;
        if (this.groupRef == null && (group = getLink(GROUP_KEY)) != null) {
            this.groupRef = new LinkEntityRef(group.getId(), group.getHref());
        }
        return this.groupRef;
    }

    public EntityRef<GroupUser> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public static class Builder {
        String groupHref;
        String userHref;

        public Builder setGroupRef(EntityRef<Group> groupRef) {
            this.groupHref = groupRef.getHref();
            return this;
        }

        public Builder setUserRef(EntityRef<User> userRef) {
            this.userHref = userRef.getHref();
            return this;
        }

        public GroupUserImpl build() {
            return new GroupUserImpl(this);
        }
    }
}
